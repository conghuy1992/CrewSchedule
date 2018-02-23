package com.dazone.crewschedule.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dazone.crewschedule.Activities.Base.BaseActionActivity;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.PersonData;
import com.dazone.crewschedule.Fragment.OrganizationFragment;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.Utils;

import java.util.ArrayList;

/**
 * Created by nguyentiendat on 1/26/16.
 */
public class OrganizationActivity extends BaseActionActivity {
    String TAG = "OrganizationActivity";

    @Override
    protected void addFragment(Bundle bundle) {
        if (bundle == null) {
            Bundle myBundle = getIntent().getExtras();
            ArrayList<PersonData> selectedPerson = myBundle.getParcelableArrayList(Statics.BUNDLE_LIST_PERSON);
            boolean isDisplaySelectedOnly = myBundle.getBoolean(Statics.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, false);
            OrganizationFragment fm = OrganizationFragment.newInstance(selectedPerson, isDisplaySelectedOnly);
            Utils.addFragmentToActivity(getSupportFragmentManager(), fm, R.id.content_base_action, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
//                Log.e(TAG, "home");
//                if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
//                    getSupportFragmentManager().popBackStackImmediate();
//                } else {
////                    finish();
//                    OrganizationFragment.instance.getdata();
//                }
                OrganizationFragment.instance.getdata();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        OrganizationFragment.instance.getdata();
//        super.onBackPressed();
//        Log.e(TAG, "onBackPressed");
    }

    @Override
    protected void setupFab() {
        fab.setVisibility(View.GONE);
    }
}
