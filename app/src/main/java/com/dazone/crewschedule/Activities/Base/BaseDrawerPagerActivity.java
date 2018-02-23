package com.dazone.crewschedule.Activities.Base;

import android.support.v4.view.ViewPager;

import com.dazone.crewschedule.Adapters.TabPagerAdapter;
import com.dazone.crewschedule.R;

/**
 * Created by nguyentiendat on 1/11/16.
 */
public abstract class BaseDrawerPagerActivity extends BaseDrawerActivity {
    protected ViewPager viewPager;
    protected TabPagerAdapter adapter;

    @Override
    protected void initView() {
        viewPager = (ViewPager)getLayoutInflater().inflate(R.layout.viewpager,null);
        viewPager.setOffscreenPageLimit(4);
        content_main.addView(viewPager);
        initAdapter();
    }

    protected abstract void initAdapter();
}
