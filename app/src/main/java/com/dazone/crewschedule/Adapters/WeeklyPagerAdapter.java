package com.dazone.crewschedule.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.dazone.crewschedule.Dtos.MainFragmentDto;
import com.dazone.crewschedule.Fragment.WeeklyListItemFragment;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.Calendar;

public class WeeklyPagerAdapter extends BasePageAdapter {
    String TAG = "WeeklyPagerAdapter";

    public WeeklyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        Log.e(TAG, "position:" + position);
        MainFragmentDto dto = new MainFragmentDto();
        dto.setScheduleType(scheduleType);
        dto.setTimeInMilliSecond(TimeUtils.getWeekForPosition(position + 1).getTimeInMillis());
        return WeeklyListItemFragment.newInstance(dto);
    }

    @Override
    public int getCount() {
        return TimeUtils.WEEKS_OF_TIME;
    }
}
