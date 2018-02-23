package com.dazone.crewschedule.Fragment;

import com.dazone.crewschedule.Adapters.ListMonthlyPageAdapter;

/**
 * Created by david on 1/8/16.
 */
public class ListMonthPagerFragment extends BaseMonthPagerFragment {
    @Override
    protected void initAdapter() {
        adapter = new ListMonthlyPageAdapter(getChildFragmentManager());
    }
}
