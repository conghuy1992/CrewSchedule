package com.dazone.crewschedule.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.dazone.crewschedule.Fragment.BaseMonthPagerFragment;
import com.dazone.crewschedule.Fragment.DailyPagerFragment;
import com.dazone.crewschedule.Fragment.ListMonthPagerFragment;
import com.dazone.crewschedule.Fragment.MainPageFragment;
import com.dazone.crewschedule.Fragment.PlaceholderFragment;
import com.dazone.crewschedule.Fragment.WeeklyPagerFragment;

import java.util.HashMap;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    String TAG = "TabPagerAdapter";
    int count = 4;
    HashMap<Integer, BaseMonthPagerFragment> mPageReferenceMap = new HashMap<>();

    public TabPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BaseMonthPagerFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MainPageFragment();
                break;
            case 1:
                fragment = new ListMonthPagerFragment();
                break;
            case 2:
                fragment = new WeeklyPagerFragment();
                break;
            case 3:
                fragment = new DailyPagerFragment();
                break;
            default:
                return PlaceholderFragment.newInstance(position);
        }
        mPageReferenceMap.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public BaseMonthPagerFragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }

}
