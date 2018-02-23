package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by david on 1/5/16.
 */
public abstract class SelectionAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> dataSet;

    public SelectionAdapter(List<T> dataSet) {
        super();
        this.dataSet = dataSet;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
