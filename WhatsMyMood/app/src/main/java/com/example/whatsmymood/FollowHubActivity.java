package com.example.whatsmymood;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import static com.example.whatsmymood.R.id.none;


/**
 * The type Follow hub activity.
 * Created by ejtang
 *
 * Referenced off of https://www.youtube.com/watch?v=00LLd7qr9sA for ideas and what to do
 * to get our tabs working
 * Obtained Mar 29, 2017
 */
public class FollowHubActivity extends AppCompatActivity {

    private FollowHubActivityTab1Followers followersTab;
    private FollowHubActivityTab2Following followingTab;
    private FollowHubActivityTab3Requests requestsTab;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * The current logged in User
     */
    private final CurrentUser current = CurrentUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_hub);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // commenting out the fab since we do not need it for our current follow hub
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        final EditText addRequestText = (EditText) findViewById(R.id.body);
        Button addRequest = (Button) findViewById(R.id.add);

        addRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ElasticSearchUserController.GetUserTask getRequestUserTask = new ElasticSearchUserController.GetUserTask();
                getRequestUserTask.execute(addRequestText.getText().toString());

                try {
                    UserAccount user = getRequestUserTask.get().get(0);

                    if (!user.relations.isFollowedBy(current.getCurrentUser().getUsername())) {
                        if(!user.relations.hasRequests(current.getCurrentUser().getUsername())) {
                            user.relations.addToFollowRequests(current.getCurrentUser().getUsername());

                            ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
                            updateUser.execute(user);

                            //Todo this will have to be altered with offline
                            String successString = "Request Sent";
                            Toast.makeText(getBaseContext(),successString, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            addRequestText.setError("Already requested to follow user");
                        }
                    } else {
                        addRequestText.setError("Already following user");
                    }


                } catch (ExecutionException | IndexOutOfBoundsException e) {
                    addRequestText.setError("User Doesn't Exist");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_follow_hub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        // referenced off of http://stackoverflow.com/questions/32172341/how-i-can-call-method-in-fragment-from-activity
        // on April 2, 2017, to get methods in fragments inside a activity
        if (id == R.id.action_refresh) {
            if (followersTab != null){
                followersTab.refresh();
            }
            if (followersTab != null) {
                followingTab.refresh();
            }
            if (requestsTab != null) {
                requestsTab.refresh();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //deleted placeholder Fragment here

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /**
         * Instantiates a new Sections pager adapter.
         *
         * @param fm the fm
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Return the current tab
            switch (position) {
                case 0:
                    followersTab = new FollowHubActivityTab1Followers();
                    return followersTab;
                case 1:
                    followingTab = new FollowHubActivityTab2Following();
                    return followingTab;
                case 2:
                    requestsTab = new FollowHubActivityTab3Requests();
                    return requestsTab;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Followers";
                case 1:
                    return "Following";
                case 2:
                    return "Requests";
            }
            return null;
        }
    }
}
