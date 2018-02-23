package com.dazone.crewschedule.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.HomeActivity;
import com.dazone.crewschedule.Adapters.BasePageAdapter;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.CustomView.VerticalViewPager;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.DailyGridViewHolder;
import com.dazone.crewschedule.Utils.DialogUtils;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.Calendar;

/**
 * Created by david on 1/8/16.
 */
public abstract class BaseMonthPagerFragment extends BaseFragment {
    String TAG = "BaseMonthPagerFragment";
    public static BaseMonthPagerFragment Instance = null;
    protected BasePageAdapter adapter;
    protected Calendar currentDay;
    protected LinearLayout main_page_content_lnl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDay = Calendar.getInstance();
        Instance = this;
        setupToolbar(pagePosition);
        Log.d(TAG, "onCreate");
    }

    public void toolbarHandler() {
        setupToolBar(R.layout.tool_base_monthly);
        initToolbarItem();

    }

    int pagePosition = 0;

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    protected TextView toolbar_title, tv_current_day;
    protected ImageView toolbar_title_icon;
    protected VerticalViewPager verViewPager;

    private void initToolbarItem() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tv_current_day = (TextView) toolbar.findViewById(R.id.tv_current_day);
        toolbar_title_icon = (ImageView) toolbar.findViewById(R.id.toolbar_title_icon);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        main_page_content_lnl = (LinearLayout) rootView.findViewById(R.id.main_page_content_lnl);
        initHeader(inflater, rootView);
        initView(inflater);
        return rootView;
    }

    protected void initHeader(LayoutInflater inflater, View rootView) {

    }

    private void initView(LayoutInflater inflater) {
//        Log.e(TAG, "initView");
        verViewPager = (VerticalViewPager) inflater.inflate(R.layout.vertical_viewpager, null);
        main_page_content_lnl.addView(verViewPager);
        initAdapter();
        verViewPager.setAdapter(adapter);
        setPage(currentDay);
        verViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Calendar time;
                switch (pagePosition) {
                    case 0:
                        time = TimeUtils.getMonthForPosition(position);
                        HomeActivity.tml = time.getTimeInMillis();
                        toolbar_title.setText(TimeUtils.showTime(time.getTimeInMillis(), Statics.DATE_TOOLBAR_FORMAT_YY_MM));
                        break;
                    case 1:
                        time = TimeUtils.getMonthForPosition(position);
                        HomeActivity.tml = time.getTimeInMillis();
                        toolbar_title.setText(TimeUtils.showTime(time.getTimeInMillis(), Statics.DATE_TOOLBAR_FORMAT_YY_MM));
                        break;
                    case 2:
                        time = TimeUtils.getWeekForPosition(position);
                        HomeActivity.tml = time.getTimeInMillis();
                        toolbar_title.setText(TimeUtils.showTime(time.getTimeInMillis(), Statics.DATE_TOOLBAR_FORMAT_YY_MM));
                        break;
                    case 3:
                        time = TimeUtils.getDayForPosition(position);
                        HomeActivity.tml = time.getTimeInMillis();
                        toolbar_title.setText(TimeUtils.showTime(time.getTimeInMillis(), Statics.DATE_FORMAT_YYYY_MM_DD));
                        DailyGridViewHolder.date_current = toolbar_title.getText().toString();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected abstract void initAdapter();

    DialogFragment dialog;

    public void setPage(Calendar cal) {
        if (dialog != null) {
            dialog.dismiss();
        }
        switch (pagePosition) {
            case 0:
            case 1:
                verViewPager.setCurrentItem(TimeUtils.getPositionForMonth(cal));
                break;
            case 2:
                verViewPager.setCurrentItem(TimeUtils.getPositionForWeek(cal));
                break;
            case 3:
                verViewPager.setCurrentItem(TimeUtils.getPositionForDay(cal));
                break;
        }
    }

    public void setupToolbar(int position) {
        this.pagePosition = position;
        if (toolbar == null) {
            initToolbarItem();
        }
        if (toolbar_title == null) {
            return;
        }
        switch (position) {
            case 0:
                HomeActivity.calendar_day = false;
                if (verViewPager == null) {
                    toolbar_title.setText(TimeUtils.showTime(currentDay.getTimeInMillis(), Statics.DATE_TOOLBAR_FORMAT_YY_MM));
                } else {
                    toolbar_title.setText(TimeUtils.showTime(TimeUtils.getMonthForPosition(verViewPager.getCurrentItem()).getTimeInMillis(), Statics.DATE_TOOLBAR_FORMAT_YY_MM));
                    if (HomeActivity.tml != 0) {
                        int cur_year = Integer.parseInt(toolbar_title.getText().toString().trim().split("-")[0]);
                        int cur_month = Integer.parseInt(toolbar_title.getText().toString().trim().split("-")[1]);
                        Calendar cal_temp = Calendar.getInstance();
                        cal_temp.setTimeInMillis(HomeActivity.tml);
                        int year = cal_temp.get(Calendar.YEAR);
                        int month = cal_temp.get(Calendar.MONTH) + 1;
                        if (cur_year != year || cur_month != month) {
                            cal_temp.set(Calendar.DAY_OF_MONTH, 1);
                            verViewPager.setCurrentItem(TimeUtils.getPositionForMonth(cal_temp));
                        }
                    }

                }
                tv_current_day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verViewPager.setCurrentItem(TimeUtils.getPositionForMonth(currentDay));
                        HomeActivity.tml = currentDay.getTimeInMillis();
                    }
                });

                toolbar_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = DialogUtils.openCalendarFragmentDialog(getFragmentManager(), TimeUtils.getMonthForPosition(verViewPager.getCurrentItem()).getTimeInMillis(), 1);

                    }
                });
                break;
            case 1:
                HomeActivity.calendar_day = false;
                if (verViewPager == null) {
                    toolbar_title.setText(TimeUtils.showTime(currentDay.getTimeInMillis(), Statics.DATE_TOOLBAR_FORMAT_YY_MM));
                } else {
                    toolbar_title.setText(TimeUtils.showTime(TimeUtils.getMonthForPosition(verViewPager.getCurrentItem()).getTimeInMillis(), Statics.DATE_TOOLBAR_FORMAT_YY_MM));
                    if (HomeActivity.tml != 0) {
                        int cur_year = Integer.parseInt(toolbar_title.getText().toString().trim().split("-")[0]);
                        int cur_month = Integer.parseInt(toolbar_title.getText().toString().trim().split("-")[1]);
                        Calendar cal_temp = Calendar.getInstance();
                        cal_temp.setTimeInMillis(HomeActivity.tml);
                        int year = cal_temp.get(Calendar.YEAR);
                        int month = cal_temp.get(Calendar.MONTH) + 1;
                        if (cur_year != year || cur_month != month) {
                            cal_temp.set(Calendar.DAY_OF_MONTH, 1);
                            verViewPager.setCurrentItem(TimeUtils.getPositionForMonth(cal_temp));
                        }
                    }

                }
                tv_current_day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verViewPager.setCurrentItem(TimeUtils.getPositionForMonth(currentDay));
                        HomeActivity.tml = currentDay.getTimeInMillis();
                    }
                });

                toolbar_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = DialogUtils.openCalendarFragmentDialog(getFragmentManager(), TimeUtils.getMonthForPosition(verViewPager.getCurrentItem()).getTimeInMillis(), 1);

                    }
                });

                break;
            case 2:
                HomeActivity.calendar_day = false;
                if (verViewPager == null) {
                    toolbar_title.setText(TimeUtils.showTime(currentDay.getTimeInMillis(), Statics.DATE_TOOLBAR_FORMAT_YY_MM));
                } else {
                    toolbar_title.setText(TimeUtils.showTime(TimeUtils.getWeekForPosition(verViewPager.getCurrentItem()).getTimeInMillis(), Statics.DATE_TOOLBAR_FORMAT_YY_MM));
                    if (HomeActivity.tml != 0) {
                        int cur_year = Integer.parseInt(toolbar_title.getText().toString().trim().split("-")[0]);
                        int cur_month = Integer.parseInt(toolbar_title.getText().toString().trim().split("-")[1]);
                        Calendar cal_temp = Calendar.getInstance();
                        int cur_date = cal_temp.get(Calendar.DAY_OF_MONTH);
                        cal_temp.setTimeInMillis(HomeActivity.tml);
                        int year = cal_temp.get(Calendar.YEAR);
                        int month = cal_temp.get(Calendar.MONTH) + 1;
                        if (cur_year != year || cur_month != month) {
                            cal_temp.set(Calendar.DAY_OF_MONTH, cur_date);
                            verViewPager.setCurrentItem(TimeUtils.getPositionForWeek(cal_temp));
                        }
                    }

                }
                tv_current_day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verViewPager.setCurrentItem(TimeUtils.getPositionForWeek(currentDay));
                        HomeActivity.tml = currentDay.getTimeInMillis();
                    }
                });

                toolbar_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = DialogUtils.openCalendarFragmentDialog(getFragmentManager(), TimeUtils.getWeekForPosition(verViewPager.getCurrentItem()).getTimeInMillis(), 0);

                    }
                });
                break;
            case 3:
                HomeActivity.calendar_day = true;
                if (verViewPager == null) {
                    toolbar_title.setText(TimeUtils.showTime(currentDay.getTimeInMillis(), Statics.DATE_FORMAT_YYYY_MM_DD));
                    DailyGridViewHolder.date_current = toolbar_title.getText().toString().trim();
                } else {
                    toolbar_title.setText(TimeUtils.showTime(TimeUtils.getDayForPosition(verViewPager.getCurrentItem()).getTimeInMillis(), Statics.DATE_FORMAT_YYYY_MM_DD));
                    DailyGridViewHolder.date_current = toolbar_title.getText().toString();
                    if (HomeActivity.tml != 0) {
                        int cur_year = Integer.parseInt(toolbar_title.getText().toString().trim().split("-")[0]);
                        int cur_month = Integer.parseInt(toolbar_title.getText().toString().trim().split("-")[1]);
                        Calendar cal_temp = Calendar.getInstance();
                        int cur_date = cal_temp.get(Calendar.DAY_OF_MONTH);
                        cal_temp.setTimeInMillis(HomeActivity.tml);
                        int year = cal_temp.get(Calendar.YEAR);
                        int month = cal_temp.get(Calendar.MONTH) + 1;
                        if (cur_year != year || cur_month != month) {
                            cal_temp.set(Calendar.DAY_OF_MONTH, cur_date);
                            verViewPager.setCurrentItem(TimeUtils.getPositionForDay(cal_temp));
                        }
                    }

                }
                tv_current_day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        verViewPager.setCurrentItem(TimeUtils.getPositionForDay(currentDay));
                        HomeActivity.tml = currentDay.getTimeInMillis();
                    }
                });

                toolbar_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = DialogUtils.openCalendarFragmentDialog(getFragmentManager(), TimeUtils.getDayForPosition(verViewPager.getCurrentItem()).getTimeInMillis(), 0);

                    }
                });
                break;
        }
    }
}
