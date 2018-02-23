package com.dazone.crewschedule.AddNewViewHolder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Fragment.AddNewScheduleFragment;
import com.dazone.crewschedule.Interfaces.DatePickerDialogListener;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.DailyGridViewHolder;
import com.dazone.crewschedule.Utils.DialogUtils;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.Calendar;

/**
 * Created by nguyentiendat on 1/25/16.
 */
public class Row_select_date_add_new_view_holder extends BaseAddNewViewHolder implements DatePickerDialogListener {
    String TAG = "Row_select_date_add_new_view_holder";
    protected
    View startTime, endTime;
    protected TextView startTimeTitle, endTimeTitle;

    protected long startTimeMil;
    protected long endTimeMil;

    public Row_select_date_add_new_view_holder(View v) {
        super(v);
    }

    @Override
    public void setup(View v) {
        startTime = v.findViewById(R.id.start_time);
        startTimeTitle = (TextView) startTime.findViewById(R.id.title);
        endTime = v.findViewById(R.id.end_time);
        endTimeTitle = (TextView) endTime.findViewById(R.id.title);
        initTime();
        initScreen();
    }

    protected void initTime() {
        startTimeMil = Calendar.getInstance().getTimeInMillis();
        endTimeMil = Calendar.getInstance().getTimeInMillis();
    }

    protected void initScreen() {
        if (DailyGridViewHolder.date_current_int == 1) {
            startTimeTitle.setText(DailyGridViewHolder.date_current);
            endTimeTitle.setText(DailyGridViewHolder.date_current);
        } else {
            startTimeTitle.setText(TimeUtils.showTime(startTimeMil, Statics.DATE_FORMAT_YYYY_MM_DD));
            endTimeTitle.setText(TimeUtils.showTime(endTimeMil, Statics.DATE_FORMAT_YYYY_MM_DD));
        }
        AddNewScheduleFragment.startTime = startTimeTitle.getText().toString();
        AddNewScheduleFragment.endTime = endTimeTitle.getText().toString();
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.openCalendarDialog(startTime.getContext(), Utils.getString(R.string.date_picker_title_date), startTimeMil, 0, Row_select_date_add_new_view_holder.this);
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.openCalendarDialog(endTime.getContext(), Utils.getString(R.string.date_picker_title_date), endTimeMil, 1, Row_select_date_add_new_view_holder.this);
            }
        });
    }

    @Override
    public void onFinishEditDialog(Calendar mDate, int type) {
        switch (type) {
            case 0:
                startTimeMil = mDate.getTimeInMillis();
                startTimeTitle.setText(TimeUtils.showTime(startTimeMil, Statics.DATE_FORMAT_YYYY_MM_DD));
                AddNewScheduleFragment.startTime = startTimeTitle.getText().toString();
                break;
            case 1:
                endTimeMil = mDate.getTimeInMillis();
                endTimeTitle.setText(TimeUtils.showTime(endTimeMil, Statics.DATE_FORMAT_YYYY_MM_DD));
                AddNewScheduleFragment.endTime = endTimeTitle.getText().toString();
                break;
        }
    }
}
