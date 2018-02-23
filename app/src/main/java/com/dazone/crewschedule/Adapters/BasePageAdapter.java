package com.dazone.crewschedule.Adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.dazone.crewschedule.Utils.TimeUtils;

public abstract class BasePageAdapter extends FragmentStatePagerAdapter {

    public int scheduleType = 0;

    public BasePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
//        Log.e("BasePageAdapter", "TIME:" + TimeUtils.MONTHS_OF_TIME);
        return TimeUtils.MONTHS_OF_TIME;
    }
}
