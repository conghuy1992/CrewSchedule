package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazone.crewschedule.Interfaces.DatePickerFragmentDialogListener;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.MonthPickerViewHolder;

import java.util.List;

public class MonthPickerYearlyAdapter extends SelectionAdapter<Long> {
    DatePickerFragmentDialogListener mListener;
    public MonthPickerYearlyAdapter(List<Long> dataSet,DatePickerFragmentDialogListener mListener,long month_millis) {
        super(dataSet);
        this.mListener = mListener;
        this.month_millis = month_millis;
    }
    long month_millis = 0;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.month_picker_item, parent, false);
        vh = new MonthPickerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final Long item = dataSet.get(position);
        MonthPickerViewHolder viewHolder = (MonthPickerViewHolder) holder;
        viewHolder.setListener(mListener);
        viewHolder.setMonth_millis(month_millis);
        viewHolder.bindData(item);
    }

}
