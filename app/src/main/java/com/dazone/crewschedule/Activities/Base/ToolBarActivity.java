package com.dazone.crewschedule.Activities.Base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.Utils;

public abstract class ToolBarActivity extends BaseActivity {

    protected Toolbar toolbar;
    protected FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        addFragment(savedInstanceState);
        setupFab();
    }
    private void init()
    {
        setContentView(R.layout.activity_tool_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayToolBarBackButton(true);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }
    protected abstract void addFragment(Bundle bundle);
    protected abstract void setupFab();
    /***** tool bar function ****/
    public void displayToolBarBackButton(boolean enableHome) {
        ActionBar actionBar = getSupportActionBar();
        if (enableHome && actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                } else {
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /***** end tool bar function ****/



    public void hideToolBarBackButton(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }


    public void setToolBarTitle(String title){
        if(getSupportActionBar() != null) {
            if (TextUtils.isEmpty(title)) {
                getSupportActionBar().setTitle(R.string.app_name);
            } else {
                getSupportActionBar().setTitle(title);
            }
        }
        else
        {
            Utils.printLogs("getSupportActionBar() : " + getSupportActionBar());
        }
    }
}
