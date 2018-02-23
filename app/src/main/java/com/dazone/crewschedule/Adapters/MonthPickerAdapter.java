package com.dazone.crewschedule.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dazone.crewschedule.Fragment.MonthPickerFragmentYearly;
import com.dazone.crewschedule.Utils.TimeUtils;

public class MonthPickerAdapter extends BasePageAdapter {
    long monthly_millis;
    public MonthPickerAdapter(FragmentManager fm,long monthly_millis) {
        super(fm);
        this.monthly_millis = monthly_millis;
    }

    @Override
    public Fragment getItem(int position) {
        return MonthPickerFragmentYearly.newInstance(TimeUtils.getYearForPosition(position).getTimeInMillis(),monthly_millis);
    }

    @Override
    public int getCount() {
        return TimeUtils.YEARS_OF_TIME;
    }
}
