package com.dazone.crewschedule.Activities;

import android.app.Activity;
import android.os.Bundle;

import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.R;

/**
 * Created by maidinh on 22/3/2016.
 */
public class Activity_finish extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_null);
        HttpRequest.update_list = true;
        finish();
    }
}
