package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.DailyGridViewHolder;

import java.util.List;

/**
 * Created by david on 1/5/16.
 */
public class MonthlyAdapter extends SelectionAdapter<CalendarDto> {
    String TAG = "MonthlyAdapter";
    int lineHeight = 0;
    int currentMonth = 0;

    public MonthlyAdapter(List<CalendarDto> dataSet, int lineHeight, int CurrentMonth) {
        super(dataSet);
        this.lineHeight = lineHeight;
        this.currentMonth = CurrentMonth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_main_layout, parent, false);
        vh = new DailyGridViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final CalendarDto item = dataSet.get(position);
            DailyGridViewHolder viewHolder = (DailyGridViewHolder) holder;
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, lineHeight); // (width, height)
            viewHolder.lnl_daily_bound.setLayoutParams(params);
            viewHolder.setCurrentMonth(currentMonth);
            viewHolder.bindData(item);
    }
}
