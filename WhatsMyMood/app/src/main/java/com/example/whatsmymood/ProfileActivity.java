package com.example.whatsmymood;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

/**
 * The type Profile activity.
 * This activity will allow the user to view the profile of other users
 * we will also display the moods posted by the user who's profile is
 * being viewed
 */
public class ProfileActivity extends AppCompatActivity {
    private CurrentUser current = CurrentUser.getInstance();
    private ArrayList<Mood> moods = new ArrayList<Mood>();;

    ArrayAdapter<Mood> moodAdapter;
    private Dialog dialog;
    private Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeController.setThemeForRecentMood(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dialog = new Dialog(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        LinearLayout footer = (LinearLayout)findViewById(R.id.footer);
        FooterHandler handler = new FooterHandler(this, footer);

        setAdapters();

        if((savedInstanceState == null)){
            this.filter = new Filter();
            refresh();
        }
    }

    private void refresh(){
        Log.d("tag","refresh!");
        Log.d("tag",String.valueOf(filter.getType()));
        Log.d("tag",String.valueOf(filter.getValue()));
        Log.d("tag","mood:");
        Log.d("tag",moods.toString());
        moods.clear();
        moods.addAll(filter.filterArray(current.getCurrentUser().moodList.getMoodList()));
        moodAdapter.notifyDataSetChanged();
        Log.d("tag","mood:");
        Log.d("tag",moods.toString());
    }
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
        refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //super.onActivityResult(requestCode, resultCode, intent);
        AddMoodController.processResult(requestCode, resultCode, intent);
    }

    private void setAdapters() {
        moodAdapter = new MoodAdapter(moods, this);

        ListView moodListView = (ListView) findViewById(R.id.moodListView);
        moodListView.setAdapter(moodAdapter);
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
            filter.setType(filter.RECENT);
            refresh();
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
                    if(!spinner.getSelectedItem().toString().equals("Select a mood")){
                        filter.setType(filter.MOOD_TYPE);
                        filter.setValue(spinner.getSelectedItem().toString());
                        refresh();
                    }
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
                    if(!text.getText().toString().isEmpty()) {
                        filter.setType(filter.MOOD_MESSAGE);
                        filter.setValue(text.getText().toString());
                        Log.d("filter",String.valueOf(filter.MOOD_MESSAGE));
                        Log.d("filter",text.getText().toString());
                        refresh();
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        }
        if (id == R.id.action_mapView) {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putParcelableArrayListExtra("moods",moods);
            intent.putExtra("filter",filter);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}