package com.dazone.crewschedule.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazone.crewschedule.Adapters.MonthPickerYearlyAdapter;
import com.dazone.crewschedule.Interfaces.DatePickerFragmentDialogListener;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.Calendar;
import java.util.List;

public class MonthPickerFragmentYearly extends BaseFragment{


    List<Long> dataSet;

    private static final String ARG_DTO = "monthly_millis";
    private static final String ARG_DTO1 = "selected_millis";

    private long month_millis = 0;
    private long selected_millis = 0;

    private RecyclerView month_picker_rcv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        month_millis = getArguments().getLong(ARG_DTO, 0);
        selected_millis = getArguments().getLong(ARG_DTO1, 0);
    }

    public static MonthPickerFragmentYearly newInstance(long month_millis,long selected_millis) {
        MonthPickerFragmentYearly fragment = new MonthPickerFragmentYearly();
        Bundle args = new Bundle();
        args.putLong(ARG_DTO, month_millis);
        args.putLong(ARG_DTO1, selected_millis);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_month_picker_yearly, container, false);
        month_picker_rcv = (RecyclerView)v.findViewById(R.id.month_picker_rcv);
        initView();
        return v;
    }
    private MonthPickerYearlyAdapter adapter;
    private void initView()
    {
        dataSet  = TimeUtils.getTimeForMonth(month_millis, 12);
        month_picker_rcv.setHasFixedSize(true);
        month_picker_rcv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(month_millis);
        adapter = new MonthPickerYearlyAdapter(dataSet,mListener,selected_millis);
        month_picker_rcv.setAdapter(adapter);
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

    private DatePickerFragmentDialogListener mListener;
}
