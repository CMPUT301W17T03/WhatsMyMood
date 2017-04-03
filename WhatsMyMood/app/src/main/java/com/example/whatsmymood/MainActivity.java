package com.example.whatsmymood;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Main Activity
 * Used for displaying the moods of the people you follow
 * Also implements a footer that handles different activities
 */
public class MainActivity extends AppCompatActivity {
    private final CurrentUser current = CurrentUser.getInstance();

    private Dialog dialog;
    private Filter filter;
    private LinearLayout footer;
    private FooterHandler handler;

    private ArrayList<String> followers;

    private ArrayList<Mood> moods;

    private ArrayAdapter<Mood> moodAdapter;
    private ListView moodListView;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeController.setThemeForRecentMood(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if((savedInstanceState == null)){
            this.filter = new Filter();
        }
        this.dialog = new Dialog(this);
        Log.d("tag","creating activity");
        Log.d("tag",String.valueOf(filter.getType()));

        this.toast = Toast.makeText(getBaseContext(), null, Toast.LENGTH_SHORT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        footer = (LinearLayout)findViewById(R.id.footer);
        FooterHandler handler = new FooterHandler(this, footer);

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getActiveNetworkInfo();

        try {
            mWifi.isConnected();
            fetchData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setAdapters();

        final SwipeRefreshLayout pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getActiveNetworkInfo();

                try {
                    mWifi.isConnected();
                    try {
                        CommandQueue.getInstance().executeAllCommands();
                    } catch (NullPointerException e) {
                        Log.d("COMMANDSWOW", "DON'TWORK");
                        e.printStackTrace();
                    }
                    fetchData();
                    setAdapters();
                    pullToRefresh.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    toast.setText("No Internet Connection");
                    toast.show();
                    pullToRefresh.setRefreshing(false);
                }
            }
        });
    }

    /**
     * Sets the first query up every time main activity
     * is viewed. This ensures that we get the most updated
     * user.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable("filter",filter);
        Log.d("tag","parceling!");
        Log.d("tag",String.valueOf(filter.getType()));
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        filter = savedInstanceState.getParcelable("filter");
        Log.d("tag","restoring!");
        Log.d("tag",String.valueOf(filter.getType()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Checks the user is connected to wifi
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getActiveNetworkInfo();

        try {
            mWifi.isConnected();
            fetchData();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setAdapters();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        AddMoodController.processResult(requestCode, resultCode, intent);
    }

    private void setAdapters() {
        this.moodAdapter = new MoodAdapter(moods,this);

        // Sets the adapter
        this.moodListView = (ListView) findViewById(R.id.moodListView);
        this.moodListView.setAdapter(this.moodAdapter);
    }

    /**
     * Fetches the moods from each follower and adds them
     * to an ArrayList to be displayed
     */
    private void fetchData() {

        ElasticSearchUserController.GetUserTask getUserTask = new ElasticSearchUserController.GetUserTask();
        getUserTask.execute(current.getCurrentUser().getUsername());

        try {
            UserAccount user = getUserTask.get().get(0);

            followers = user.getRelations().getFollowingList();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        moods = new ArrayList<>();

        for (String follower : followers) {
            ElasticSearchUserController.GetUserTask getFollowersTask = new ElasticSearchUserController.GetUserTask();
            getFollowersTask.execute(follower);

            try {
                ArrayList<UserAccount> mFollower = getFollowersTask.get();
                if (!mFollower.isEmpty()) {
                    UserAccount temp = mFollower.get(mFollower.size()-1);

                    // Exception
                    // If you follow one person and they have no moods
                    if (!(temp.getMoodList().getSize() == 0)) {
                        moods.add(temp.getMoodList().getRecentMood());
                    }
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_recent) {
            //filter.setType(filter.RECENT);
            Log.d("tag","check filter:");
            Log.d("tag",String.valueOf(filter.getType()));
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_moodType) {
            dialog.setContentView(R.layout.mood_filter_popup);
            final Button submit =  (Button)dialog.findViewById(R.id.filter);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Spinner spinner = (Spinner)dialog.findViewById(R.id.select_mood);
                    filter.setType(filter.MOOD_TYPE);
                    filter.setValue(spinner.getSelectedItem().toString());
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_moodMessage) {
            dialog.setContentView(R.layout.message_filter_popup);
            final Button submit =  (Button)dialog.findViewById(R.id.filter);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText text = (EditText) dialog.findViewById(R.id.message);
                    filter.setType(filter.MOOD_MESSAGE);
                    filter.setValue(text.getText().toString());
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}