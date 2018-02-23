package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Fragment.WeeklyPagerFragment;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.DailyGridViewHolder;
import com.dazone.crewschedule.RCVViewHolders.HourlyGridViewHolder;
import com.dazone.crewschedule.RCVViewHolders.HourlyTitleGridViewHolder;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.List;

/**
 * Created by david on 1/5/16.
 */
public class WeeklyItemAdapter extends SelectionAdapter<CalendarDto> {
    String TAG = "WeeklyItemAdapter";
    int lineHeight = 0;
    int currentMonth = 0;

    protected final int VIEW_ITEM = 1;
    protected final int VIEW_TITLE = 0;

    public WeeklyItemAdapter(List<CalendarDto> dataSet, int lineHeight, int CurrentMonth) {
        super(dataSet);
        this.lineHeight = lineHeight;
        this.currentMonth = CurrentMonth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_hour_week_layout, parent, false);
//        if (viewType == VIEW_ITEM) {
//            vh = new HourlyGridViewHolder(v);
//        } else {
        vh = new HourlyTitleGridViewHolder(v);
//        }
        return vh;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof HourlyGridViewHolder) {
//            final CalendarDto item = dataSet.get(position);
//            HourlyGridViewHolder viewHolder = (HourlyGridViewHolder) holder;
//            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, lineHeight); // (width, height)
//            viewHolder.lnl_daily_bound.setLayoutParams(params);
//            viewHolder.bindData(item);
//        } else {
        final CalendarDto item = dataSet.get(position);
        HourlyTitleGridViewHolder viewHolder = (HourlyTitleGridViewHolder) holder;
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, lineHeight); // (width, height)
        viewHolder.lnl_daily_bound.setLayoutParams(params);
        viewHolder.bindData(item);
//        }
    }


    @Override
    public int getItemViewType(int position) {
//        return position % 8 == 0 ? VIEW_TITLE : VIEW_ITEM;
        return position;
    }
}
