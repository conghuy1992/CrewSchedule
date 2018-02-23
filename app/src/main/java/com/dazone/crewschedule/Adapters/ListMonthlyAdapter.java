package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.DailyListMonthlyViewHolder;

import java.util.List;

/**
 * Created by david on 1/5/16.
 */
public class ListMonthlyAdapter extends SelectionAdapter<CalendarDto> {
    int currentMonth = 0;

    public ListMonthlyAdapter(List<CalendarDto> dataSet, int CurrentMonth) {
        super(dataSet);
        this.currentMonth = CurrentMonth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_main_list_layout, parent, false);
        vh = new DailyListMonthlyViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CalendarDto item = dataSet.get(position);
        DailyListMonthlyViewHolder viewHolder = (DailyListMonthlyViewHolder) holder;
        viewHolder.setCurrentMonth(currentMonth);
        viewHolder.bindData(item);
    }

}
