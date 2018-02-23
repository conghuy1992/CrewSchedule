package com.dazone.crewschedule.Activities;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Activities.Base.BaseDrawerPagerActivity;
import com.dazone.crewschedule.Adapters.DrawerMenuAdapter;
import com.dazone.crewschedule.Adapters.TabPagerAdapter;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.CustomView.FaceOverlayView;
import com.dazone.crewschedule.Database.OrganizationUserDBHelper;
import com.dazone.crewschedule.Database.UserDBHelper;
import com.dazone.crewschedule.Dtos.DrawerDto;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.Dtos.UserDto;
import com.dazone.crewschedule.Fragment.BaseMonthPagerFragment;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Interfaces.DatePickerFragmentDialogListener;
import com.dazone.crewschedule.Interfaces.DrawerTrigger;
import com.dazone.crewschedule.Interfaces.GetCalendarTypeCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.ImageUtils;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends BaseDrawerPagerActivity implements DatePickerFragmentDialogListener,
        GetCalendarTypeCallBack, DrawerTrigger {
    String TAG = "HomeActivity";
    public static long tml = 0;
    public static boolean calendar_day = false;
    public static int _width = 0;
    public static int _width_week = 0;

    @Override
    protected void setupFAP() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callActivity(AddNewScheduleActivity.class);
            }
        });
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void initAdapter() {
        OrganizationUserDBHelper.clearData();
        HttpRequest.getInstance().getDepartment(null);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        _width = (int) (metrics.widthPixels - pxFromDp(this, 40));
        _width_week = metrics.widthPixels / 8;

        displayNavigationBar();
        adapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                BaseMonthPagerFragment fragment = adapter.getFragment(position);
                if (fragment != null) {
                    fragment.setupToolbar(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
//                Log.e(TAG, "" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupDrawer();
    }

    public static String myInfor = "";

    protected void setupDrawer() {
        drawer_rcl.setHasFixedSize(true);
        drawer_rcl.setLayoutManager(new LinearLayoutManager(this));
        HttpRequest.getInstance().getCalendarType(this);
        HttpRequest.getInstance().GetUser(BaseActivity.Instance.prefs.getUserNo());
        initUserInfo();
    }

    TextView name_tv, email_tv;
    ImageView avatar_imv, setting_imv;
    //    FaceOverlayView fol;
    public static ArrayList<Integer> arr_mon = new ArrayList<Integer>();
    public static ArrayList<Integer> arr_tue = new ArrayList<Integer>();
    public static ArrayList<Integer> arr_wed = new ArrayList<Integer>();
    public static ArrayList<Integer> arr_thu = new ArrayList<Integer>();
    public static ArrayList<Integer> arr_fri = new ArrayList<Integer>();
    public static ArrayList<Integer> arr_sat = new ArrayList<Integer>();
    public static ArrayList<Integer> arr_sun = new ArrayList<Integer>();

    private void initUserInfo() {
        avatar_imv = (ImageView) findViewById(R.id.avatar_imv);
        setting_imv = (ImageView) findViewById(R.id.setting_imv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        email_tv = (TextView) findViewById(R.id.email_tv);
//        fol = (FaceOverlayView) findViewById(R.id.avatar_imv);
        UserDto userDto = UserDBHelper.getUser();
        ImageUtils.showImage(userDto, avatar_imv);
//        ImageUtils.showImage_custom(userDto, fol);
        name_tv.setText(userDto.FullName);
        email_tv.setText(userDto.NameCompany);
        setting_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.Instance.gotoInfor(MyInfor.class);
            }
        });
        arr_sun.clear();
        arr_mon.clear();
        arr_tue.clear();
        arr_wed.clear();
        arr_thu.clear();
        arr_fri.clear();
        arr_sat.clear();
        arr_sun.add(1);
        arr_mon.add(2);
        arr_tue.add(3);
        arr_wed.add(4);
        arr_thu.add(5);
        arr_fri.add(6);
        arr_sat.add(7);
        for (int i = 9; i < 200; i += 8) {
            arr_sun.add(i);
        }
        for (int i = 10; i < 200; i += 8) {
            arr_mon.add(i);
        }
        for (int i = 11; i < 200; i += 8) {
            arr_tue.add(i);
        }
        for (int i = 12; i < 200; i += 8) {
            arr_wed.add(i);
        }
        for (int i = 13; i < 200; i += 8) {
            arr_thu.add(i);
        }
        for (int i = 14; i < 200; i += 8) {
            arr_fri.add(i);
        }
        for (int i = 15; i < 200; i += 8) {
            arr_sat.add(i);
        }

    }

    @Override
    public void onFinishEditDialog(Calendar mDate) {
        BaseMonthPagerFragment fragment = adapter.getFragment(viewPager.getCurrentItem());
        if (fragment != null) {
            fragment.setPage(mDate);
        }
    }

    public static DrawerMenuAdapter mDrawerMenuAdapter;

    @Override
    public void onGetGetDrawerSuccess(List<DrawerDto> childList) {

        List<DrawerDto> dtos = Utils.listDrawer();
        mDrawerMenuAdapter = new DrawerMenuAdapter(dtos, childList, this);
        drawer_rcl.setAdapter(mDrawerMenuAdapter);
    }

    @Override
    public void onGetGetDrawerFail(ErrorDto errorDto) {

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume");
    }

    @Override
    public void onItemSelect(int position) {
        switch (position) {
            case 0:
                viewPager.setCurrentItem(0);
                break;
            case 1:
                viewPager.setCurrentItem(3);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
            case 3:
                viewPager.setCurrentItem(1);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
    }
}
