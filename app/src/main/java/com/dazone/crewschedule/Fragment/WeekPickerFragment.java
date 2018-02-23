package com.dazone.crewschedule.Fragment;

import com.dazone.crewschedule.Adapters.WeekPickerAdapter;

public class WeekPickerFragment extends MonthPickerFragmentNew{

    protected void initAdapter()
    {
        adapter = new WeekPickerAdapter(getChildFragmentManager(),month_millis);
    }
}
