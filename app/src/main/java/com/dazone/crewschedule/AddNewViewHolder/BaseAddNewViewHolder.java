package com.dazone.crewschedule.AddNewViewHolder;

import android.view.View;

/**
 * Created by nguyentiendat on 1/25/16.
 */
public abstract class BaseAddNewViewHolder{
    public BaseAddNewViewHolder(View v) {
        setup(v);
    }

    public abstract void setup(View v);
}
