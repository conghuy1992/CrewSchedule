package com.dazone.crewschedule.RCVViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;

public abstract class ItemViewHolder<T> extends RecyclerView.ViewHolder {
    protected final String mRootLink;
    protected View v;
    public ItemViewHolder(View v) {
        super(v);
        mRootLink = CrewScheduleApplication.getInstance().getmPrefs().getServerSite();
        this.v = v;
        setup(v);
    }
    protected abstract void setup(View v);
    public abstract void bindData(T t);
    @Override
    public String toString() {
        return super.toString();
    }
}
