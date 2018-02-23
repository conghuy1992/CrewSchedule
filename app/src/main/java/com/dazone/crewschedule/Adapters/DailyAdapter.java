package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.DailyViewHolder;

import java.util.List;

/**
 * Created by david on 1/5/16.
 */
public class DailyAdapter extends SelectionAdapter<ScheduleDto> {
    public DailyAdapter(List<ScheduleDto> dataSet) {
        super(dataSet);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_daily_layout, parent, false);
        vh = new DailyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final ScheduleDto item = dataSet.get(position);
        DailyViewHolder viewHolder = (DailyViewHolder) holder;
        viewHolder.bindData(item);
    }
}
