package com.example.whatsmymood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_actions, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        //setContentView(R.layout.footer);

        /*
        Toolbar myToolbar = (Toolbar) findViewById(R.id.footer);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(null);
        */
    }

    /*
    public boolean onOptionsItemSelected(MenuItem item) {
        View menuItemView = findViewById(R.id.add_mood_popup);
        PopupMenu popupMenu = new PopupMenu(this, menuItemView);
        popupMenu.inflate(R.menu.add_mood);

        popupMenu.show();
        return true;
    }*/

}
