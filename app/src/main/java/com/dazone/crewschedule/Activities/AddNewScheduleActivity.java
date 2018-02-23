package com.dazone.crewschedule.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dazone.crewschedule.Activities.Base.BaseActionActivity;
import com.dazone.crewschedule.Fragment.AddNewScheduleFragment;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.Utils;

/**
 * Created by nguyentiendat on 1/13/16.
 */
public class AddNewScheduleActivity extends BaseActionActivity {
    String TAG = "AddNewScheduleActivity";

    @Override
    protected void addFragment(Bundle bundle) {
        if (bundle == null) {
            Utils.addFragmentToActivity(getSupportFragmentManager(), new AddNewScheduleFragment(), R.id.content_base_action, false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void setupFab() {
        fab.setVisibility(View.GONE);
    }
}
