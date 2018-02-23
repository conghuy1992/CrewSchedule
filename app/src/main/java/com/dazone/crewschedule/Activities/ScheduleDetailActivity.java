package com.dazone.crewschedule.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Activities.Base.ToolBarActivity;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.Fragment.ScheduleDetailFragment;
import com.dazone.crewschedule.Fragment.UpdateScheduleFragment;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.Utils;

import java.util.Calendar;

/**
 * Created by nguyentiendat on 1/13/16.
 */
public class ScheduleDetailActivity extends ToolBarActivity {
    String TAG = "ScheduleDetailActivity";
    public static int scheduleNo = 0, RegUserNo = 0;
    public static String UserName = "", PositionName = "", RegDate = "", Title = "", Content = "", DayContent = "",
            TimeContent = "", RepeatContent = "", startHour = "", endHour = "", fromday = "", today = "", share_info = "",list_json="";

    @Override
    protected void addFragment(Bundle bundle) {
        if (bundle == null) {
            ScheduleDetailFragment fm = ScheduleDetailFragment.newInstance(
                    getIntent().getExtras().getInt(ParamKeys.KEY_SCHEDULE_NO, 0));
            Utils.addFragmentToActivity(getSupportFragmentManager(), fm, R.id.toolbar_content, false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
//            Log.e(TAG, "scheduleNo: "+scheduleNo);
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Delete");
            adb.setMessage("Are you sure?");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ProgressDialog pd = new ProgressDialog(ScheduleDetailActivity.this);
                    pd.setMessage("Wait...");
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                    HttpRequest.getInstance().DeleteSchedule(scheduleNo, ScheduleDetailActivity.this, pd);
                }
            });
            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            adb.create().show();
        } else if (id == R.id.action_edit) {
            Intent newIntent = new Intent(this, UpdateScheduleActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(newIntent);
            UpdateScheduleFragment.DayContent = DayContent.trim();
            UpdateScheduleFragment.scheduleNo = scheduleNo;
            UpdateScheduleFragment.RegUserNo = RegUserNo;
            UpdateScheduleFragment.UserName = UserName.trim();
            UpdateScheduleFragment.PositionName = PositionName.trim();
            UpdateScheduleFragment.RegDate = RegDate.trim();
            UpdateScheduleFragment.Title = Title.trim();
            UpdateScheduleFragment.Content = Content.trim();
            UpdateScheduleFragment.TimeContent = TimeContent.trim();
            UpdateScheduleFragment.RepeatContent = RepeatContent.trim();
            UpdateScheduleFragment.startHour = startHour.trim();
            UpdateScheduleFragment.endHour = endHour.trim();
            UpdateScheduleFragment.fromday = fromday.trim();
            UpdateScheduleFragment.today = today.trim();
            UpdateScheduleFragment.share_info = share_info;
            UpdateScheduleFragment.list_json = list_json;

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    protected void setupFab() {
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callActivity(AddNewScheduleActivity.class);
            }
        });
    }
}
