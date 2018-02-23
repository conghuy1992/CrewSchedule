package com.dazone.crewschedule.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.dazone.crewschedule.Dtos.MainFragmentDto;
import com.dazone.crewschedule.Fragment.MonthlyFragment;
import com.dazone.crewschedule.Utils.TimeUtils;

public class MainPageAdapter extends BasePageAdapter {
    String TAG = "MainPageAdapter";


    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        Log.e(TAG, "POS:" + position);
        MainFragmentDto dto = new MainFragmentDto();
        dto.setScheduleType(scheduleType);
        dto.setTimeInMilliSecond(TimeUtils.getMonthForPosition(position).getTimeInMillis());
        return MonthlyFragment.newInstance(dto);
    }
}
