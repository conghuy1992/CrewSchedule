package com.dazone.crewschedule.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Context;

/**
 * Created by nguyentiendat on 1/13/16.
 */
public class TimePickerNew extends TimePickerDialog {
    public TimePickerNew(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
    }
}