package com.dazone.crewschedule.Activities.Base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.dazone.crewschedule.Activities.LoginActivity;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Interfaces.BaseHTTPCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.Utils;

public abstract class BaseDrawerActivity extends BaseActivity
        implements BaseHTTPCallBack {

    public static BaseDrawerActivity instance = null;
    protected RelativeLayout content_main;
    protected FloatingActionButton fab;
    Toolbar toolbar;
    protected DrawerLayout drawer;
    public static RecyclerView drawer_rcl;
    protected ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_base_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer_rcl = (RecyclerView) findViewById(R.id.drawer_rcl);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        content_main = (RelativeLayout) findViewById(R.id.base_drawer_content);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        initView();
        setupFAP();
    }

    protected abstract void initView();

    protected abstract void setupFAP();

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onHTTPSuccess() {

        prefs.clearLogin();
        startNewActivity(LoginActivity.class);
    }

    @Override
    public void onHTTPFail(ErrorDto errorDto) {

        Utils.printLogs(errorDto.message);
    }

    public void setDrawerState(boolean isEnabled) {
        if (drawer == null) return;
        if (isEnabled) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
        }
    }

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

    public void hideToolBarBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }


    public void setToolBarTitle(String title) {
        if (getSupportActionBar() != null) {
            if (TextUtils.isEmpty(title)) {
                getSupportActionBar().setTitle(R.string.app_name);
            } else {
                getSupportActionBar().setTitle(title);
            }
        }
    }

    public void displayNavigationBar() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        setDrawerState(true);
        toggle.syncState();
    }

}
