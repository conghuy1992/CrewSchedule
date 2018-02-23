package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.HourlyGridViewHolder;
import com.dazone.crewschedule.RCVViewHolders.HourlyGridViewHolder_week;
import com.dazone.crewschedule.RCVViewHolders.HourlyTitleGridViewHolder;

import java.util.List;

/**
 * Created by maidinh on 29/3/2016.
 */
public class WeeklyItemAdapter_week extends SelectionAdapter<CalendarDto> {
    String TAG = "WeeklyItemAdapter";
    int lineHeight = 0;
    int currentMonth = 0;

    protected final int VIEW_ITEM = 1;
    protected final int VIEW_TITLE = 0;

    public WeeklyItemAdapter_week(List<CalendarDto> dataSet, int lineHeight, int CurrentMonth) {
        super(dataSet);
        this.lineHeight = lineHeight;
        this.currentMonth = CurrentMonth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_hour_weekly_layout_week, parent, false);

        vh = new HourlyGridViewHolder_week(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final CalendarDto item = dataSet.get(position);
        HourlyGridViewHolder_week viewHolder = (HourlyGridViewHolder_week) holder;
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, lineHeight); // (width, height)
        viewHolder.lnl_daily_bound.setLayoutParams(params);
        viewHolder.bindData(item);

    }


    @Override
    public int getItemViewType(int position) {
//        return position % 8 == 0 ? VIEW_TITLE : VIEW_ITEM;
        return position;
    }
}
