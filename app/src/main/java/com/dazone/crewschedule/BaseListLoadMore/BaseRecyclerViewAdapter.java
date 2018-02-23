package com.dazone.crewschedule.BaseListLoadMore;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by david on 1/5/16.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> dataSet;

    public BaseRecyclerViewAdapter(List<T> dataSet) {
        super();
        this.dataSet = dataSet;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
