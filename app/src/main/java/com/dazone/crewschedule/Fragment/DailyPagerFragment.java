package com.dazone.crewschedule.Fragment;

import android.util.Log;

import com.dazone.crewschedule.Adapters.DailyPagerAdapter;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.Calendar;

public class DailyPagerFragment extends BaseMonthPagerFragment {

    @Override
    protected void initAdapter() {
        toolbarHandler();
        adapter = new DailyPagerAdapter(getChildFragmentManager());
    }

    @Override
    public void setPage(Calendar cal) {
        if (dialog != null) {
            dialog.dismiss();
        }
//        Log.e(TAG,"date:"+cal.get(Calendar.DAY_OF_MONTH)+" month:"+cal.get(Calendar.MONTH)+" year:"+cal.get(Calendar.YEAR));
        verViewPager.setCurrentItem(TimeUtils.getPositionForDay(cal));
    }
}
