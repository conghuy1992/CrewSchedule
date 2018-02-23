package com.dazone.crewschedule.Fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazone.crewschedule.Adapters.MonthPickerAdapter;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.Calendar;

public class MonthPickerFragmentNew extends DialogFragment {


    private static final String ARG_DTO = "monthly_millis";

    protected long month_millis = 0;

    private ViewPager month_picker_vpg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        month_millis = getArguments().getLong(ARG_DTO, 0);
    }

    public static MonthPickerFragmentNew newInstance(long month_millis) {
        MonthPickerFragmentNew fragment = new MonthPickerFragmentNew();
        Bundle args = new Bundle();
        args.putLong(ARG_DTO, month_millis);
        fragment.setArguments(args);
        return fragment;
    }

    TextView tv_year;
    ImageView right_arrow, left_arrow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_month_picker, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        month_picker_vpg = (ViewPager) v.findViewById(R.id.month_picker_vpg);
        tv_year = (TextView) v.findViewById(R.id.tv_year);
        right_arrow = (ImageView) v.findViewById(R.id.right_arrow);
        left_arrow = (ImageView) v.findViewById(R.id.left_arrow);
        initView();
        return v;
    }

    public MonthPickerAdapter adapter;

    protected void initAdapter() {
        adapter = new MonthPickerAdapter(getChildFragmentManager(), month_millis);
    }

    private void initView() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(month_millis);
        initAdapter();
        month_picker_vpg.setAdapter(adapter);
        month_picker_vpg.setCurrentItem(TimeUtils.getPositionForYear(cal));
        tv_year.setText(TimeUtils.showTime(month_millis, Statics.DATE_FORMAT_YEAR));
        month_picker_vpg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                tv_year.setText(TimeUtils.showTime(
                        TimeUtils.getYearForPosition(month_picker_vpg.getCurrentItem()).getTimeInMillis(), Statics.DATE_FORMAT_YEAR));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month_picker_vpg.setCurrentItem(month_picker_vpg.getCurrentItem() - 1);
            }
        });

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month_picker_vpg.setCurrentItem(month_picker_vpg.getCurrentItem() + 1);
            }
        });
    }
}
