package com.example.whatsmymood;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class FollowHubActivity extends AppCompatActivity {

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
        if (id == R.id.action_settings) {
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

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Return the current tab
            switch (position) {
                case 0:
                    FollowHubActivityTab1Followers followersTab = new FollowHubActivityTab1Followers();
                    return followersTab;
                case 1:
                    FollowHubActivityTab2Following followingTab = new FollowHubActivityTab2Following();
                    return followingTab;
                case 2:
                    FollowHubActivityTab3Requests requestsTab = new FollowHubActivityTab3Requests();
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
