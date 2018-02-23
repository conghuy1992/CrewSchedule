package com.dazone.crewschedule.Fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dazone.crewschedule.Adapters.WeeklyPagerAdapter;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class WeeklyPagerFragment extends BaseMonthPagerFragment {
    public static WeeklyPagerFragment Instance = null;
    public static TextView tv_sun, tv_mon, tv_tue, tv_wed, tv_thu, tv_fri, tv_sat;

    @Override
    protected void initAdapter() {
        toolbarHandler();
        adapter = new WeeklyPagerAdapter(getChildFragmentManager());
    }

    @Override
    protected void initHeader(LayoutInflater inflater, View rootView) {
        Instance = this;
        View header = inflater.inflate(R.layout.horizontal_week_date, null);
        tv_sun = (TextView) header.findViewById(R.id.tv_sun);
        tv_mon = (TextView) header.findViewById(R.id.tv_mon);
        tv_tue = (TextView) header.findViewById(R.id.tv_tue);
        tv_wed = (TextView) header.findViewById(R.id.tv_wed);
        tv_thu = (TextView) header.findViewById(R.id.tv_thu);
        tv_fri = (TextView) header.findViewById(R.id.tv_fri);
        tv_sat = (TextView) header.findViewById(R.id.tv_sat);

//        main_page_content_lnl.addView(header);
    }

    @Override
    public void setPage(Calendar cal) {
        if (dialog != null) {
            dialog.dismiss();
        }
        verViewPager.setCurrentItem(TimeUtils.getPositionForWeek(cal));
    }
}
