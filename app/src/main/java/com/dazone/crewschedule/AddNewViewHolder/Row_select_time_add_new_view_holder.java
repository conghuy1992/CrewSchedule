package com.dazone.crewschedule.AddNewViewHolder;

import android.app.TimePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Fragment.AddNewScheduleFragment;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.Calendar;

/**
 * Created by nguyentiendat on 1/25/16.
 */
public class Row_select_time_add_new_view_holder extends Row_select_date_add_new_view_holder implements
        TimePickerDialog.OnTimeSetListener {
    String TAG = "Row_select_time_add_new_view_holder";

    public Row_select_time_add_new_view_holder(View v) {
        super(v);
    }

    boolean setStartTime = true;
    int chooser = 0;

    @Override
    protected void initScreen() {
        startTimeTitle.setText(TimeUtils.showTime(startTimeMil, Statics.DATE_FORMAT_HH_MM_AA));
        endTimeTitle.setText(TimeUtils.showTime(endTimeMil, Statics.DATE_FORMAT_HH_MM_AA));
        AddNewScheduleFragment.startHour = startTimeTitle.getText().toString();
        AddNewScheduleFragment.endHour = endTimeTitle.getText().toString();

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartTime = true;
//                chooser = 0;
                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(startTimeMil);
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(startTime.getContext(), Row_select_time_add_new_view_holder.this,
                        hours, minutes, false);
                dialog.show();
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStartTime = false;
//                chooser = 1;
                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(endTimeMil);
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int minutes = c.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(endTime.getContext(), Row_select_time_add_new_view_holder.this,
                        hours, minutes, false);
                dialog.show();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        if (setStartTime) {
            startTimeMil = c.getTimeInMillis();
            startTimeTitle.setText(TimeUtils.showTime(startTimeMil, Statics.DATE_FORMAT_HH_MM_AA));
            AddNewScheduleFragment.startHour = startTimeTitle.getText().toString();
        } else {
            endTimeMil = c.getTimeInMillis();
            endTimeTitle.setText(TimeUtils.showTime(endTimeMil, Statics.DATE_FORMAT_HH_MM_AA));
            AddNewScheduleFragment.endHour = endTimeTitle.getText().toString();
//            setStartTime = true;
        }
    }
}
