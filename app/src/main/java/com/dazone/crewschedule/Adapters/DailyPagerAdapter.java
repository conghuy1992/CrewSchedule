package com.dazone.crewschedule.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.dazone.crewschedule.Dtos.MainFragmentDto;
import com.dazone.crewschedule.Fragment.DailyListItemFragment;

import com.dazone.crewschedule.Utils.TimeUtils;

public class DailyPagerAdapter extends BasePageAdapter {
    String TAG = "DailyPagerAdapter";

    public DailyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        Log.e(TAG, "POS:" + position);
        MainFragmentDto dto = new MainFragmentDto();
        dto.setScheduleType(scheduleType);
        dto.setTimeInMilliSecond(TimeUtils.getDayForPosition(position).getTimeInMillis());
        return DailyListItemFragment.newInstance(dto);
    }

    @Override
    public int getCount() {
        return TimeUtils.DAYS_OF_TIME;
    }
}
