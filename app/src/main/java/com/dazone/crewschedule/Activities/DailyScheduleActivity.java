package com.dazone.crewschedule.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dazone.crewschedule.Activities.Base.ToolBarActivity;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.Fragment.DailyFragment;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.Utils;

/**
 * Created by nguyentiendat on 1/13/16.
 */
public class DailyScheduleActivity extends ToolBarActivity {
    String TAG = "DailyScheduleActivity";

    @Override
    protected void addFragment(Bundle bundle) {
        if (bundle == null) {
            DailyFragment fm = DailyFragment.newInstance(getIntent().getExtras().getLong(ParamKeys.KEY_TIME_MILLISECONDS, 0));
            Utils.addFragmentToActivity(getSupportFragmentManager(), fm, R.id.toolbar_content, false);
        }
    }

    @Override
    protected void setupFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callActivity(AddNewScheduleActivity.class);
            }
        });
    }
}
