package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazone.crewschedule.BaseListLoadMore.BaseLoadMoreRCVAdapter;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.ProgressViewHolder;

import java.util.List;

/**
 * Created by nguyentiendat on 2/2/16.
 */
public class ListScheduleAdapter extends BaseLoadMoreRCVAdapter<CalendarDto> {

    protected final int VIEW_FIRST_DAY_OF_MONTH = 2;
    public ListScheduleAdapter(List<CalendarDto> myDataSet, RecyclerView recyclerView) {
        super(myDataSet, recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if(viewType==VIEW_ITEM) {

//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chapter, parent, false);
//            vh =  new ChapterViewHolder(v);
            vh = null;
        }else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CalendarDto dto = dataSet.get(position);
//        if(holder instanceof  ChapterViewHolder) {
//            ChapterViewHolder viewHolder = (ChapterViewHolder) holder;
//            viewHolder.setStoryID(storyID);
//            viewHolder.bindData(dto);
//        }
    }
    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }
}
