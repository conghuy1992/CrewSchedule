package com.dazone.crewschedule.Activities.Base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.dazone.crewschedule.Fragment.OrganizationFragment;
import com.dazone.crewschedule.R;

public abstract class BaseActionActivity extends BaseActivity {
    protected Toolbar toolbar;
    protected FloatingActionButton fab;
    protected RelativeLayout content_main;
    String TAG = "BaseActionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        addFragment(savedInstanceState);
        setupFab();
    }

    private void init() {
        setContentView(R.layout.activity_base_action);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayToolBarBackButton(true);
        content_main = (RelativeLayout) findViewById(R.id.content_base_action);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    protected abstract void addFragment(Bundle bundle);

    protected abstract void setupFab();

    /*****
     * tool bar function
     ****/
    public void displayToolBarBackButton(boolean enableHome) {
        ActionBar actionBar = getSupportActionBar();
        if (enableHome && actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

//    @Override
//    public void onBackPressed() {
//        OrganizationFragment.instance.getdata();
////        super.onBackPressed();
////        Log.e(TAG, "onBackPressed");
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
//                Log.e(TAG, "home");
                if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                } else {
                    finish();

                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*****
     * end tool bar function
     ****/


    public void hideToolBarBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

}
