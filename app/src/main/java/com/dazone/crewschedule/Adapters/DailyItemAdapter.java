package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.HourlyListViewHolder;

import java.util.List;

/**
 * Created by david on 1/5/16.
 */
public class DailyItemAdapter extends SelectionAdapter<CalendarDto> {
    String TAG = "DailyItemAdapter";
    int lineHeight = 0;

    protected final int VIEW_ITEM = 1;
    protected final int VIEW_TITLE = 0;

    public DailyItemAdapter(List<CalendarDto> dataSet, int lineHeight) {
        super(dataSet);
        this.lineHeight = lineHeight;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_hour_daily_layout, parent, false);
        vh = new HourlyListViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.e(TAG,"position:"+position);
        final CalendarDto item = dataSet.get(position);
        HourlyListViewHolder viewHolder = (HourlyListViewHolder) holder;
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, lineHeight); // (width, height)
        viewHolder.lnl_daily_bound.setLayoutParams(params);
        viewHolder.bindData(item);
    }
}
