package com.dazone.crewschedule.DialogFragment;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.dazone.crewschedule.R;

public class MonthPickerFragment extends DatePickerFragment {

    @Override
    protected void setTitle(Dialog dialog)
    {
        dialog.setTitle(R.string.date_picker_title_month);
    }

    @Override
    protected void modifiedPickerForMonth()
    {
        if(datePicker!=null) {
           try {
               View v = datePicker.findViewById(Resources.getSystem().getIdentifier("day", "id", "android"));
               if(v!=null) {
                   v.setVisibility(View.GONE);
               }

           }catch (Exception e)
           {

           }
        }
    }

    public static MonthPickerFragment newInstance(long dateInMillis)
    {
        Bundle arg = new Bundle();
        arg.putLong(extraDate, dateInMillis);
        MonthPickerFragment fragment = new MonthPickerFragment();
        fragment.setArguments(arg);
        return fragment;
    }
}
