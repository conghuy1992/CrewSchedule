package com.dazone.crewschedule.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.dazone.crewschedule.Adapters.MainPageAdapter;
import com.dazone.crewschedule.R;

public class MainPageFragment extends BaseMonthPagerFragment {
    String TAG = "MainPageFragment";

    @Override
    protected void initAdapter() {
//        Log.e(TAG,"initAdapter");
        toolbarHandler();
        adapter = new MainPageAdapter(getChildFragmentManager());
    }

    @Override
    protected void initHeader(LayoutInflater inflater, View rootView) {
//        Log.e(TAG,"initHeader");
        View header = inflater.inflate(R.layout.horizontal_week, null);
        main_page_content_lnl.addView(header);
    }
}
