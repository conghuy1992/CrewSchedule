package com.dazone.crewschedule.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.dazone.crewschedule.Interfaces.DatePickerFragmentDialogListener;
import com.dazone.crewschedule.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    private DatePickerFragmentDialogListener mListener;
    public static final String extraDate = "extraDate";
    private long dateInMillis = 0;

    protected DatePicker datePicker;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null) {
            dateInMillis = getArguments().getLong(extraDate);
        }
        final Calendar c = Calendar.getInstance();
        if(dateInMillis!=0) {
            c.setTimeInMillis(dateInMillis);
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        modifiedPickerForMonth();
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                getArguments().putSerializable(extraDate, calendar.getTimeInMillis());

            }
        });
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title_date)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, datePicker.getYear());
                        cal.set(Calendar.MONTH, datePicker.getMonth());
                        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        mListener.onFinishEditDialog(cal);
                    }
                })
                .create();
        setTitle(dialog);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setTitle(Dialog dialog)
    {
    }
    protected void modifiedPickerForMonth()
    {

    }
    public static DatePickerFragment newInstance(long dateInMillis)
    {
        Bundle arg = new Bundle();
        arg.putLong(extraDate, dateInMillis);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DatePickerFragmentDialogListener) {
            mListener = (DatePickerFragmentDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
