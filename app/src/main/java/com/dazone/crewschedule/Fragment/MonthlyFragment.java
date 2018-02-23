package com.dazone.crewschedule.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dazone.crewschedule.Adapters.MonthlyAdapter;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.MainFragmentDto;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Interfaces.GetMonthScheduleCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.DrawerMenuViewHolder;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.Calendar;
import java.util.List;

public class MonthlyFragment extends BaseFragment implements GetMonthScheduleCallBack {

    String TAG = "MonthlyFragment";
    public static List<CalendarDto> dataSet;
    public static MonthlyFragment mf;

    private static final String ARG_DTO = "monthly_millis";

    private MainFragmentDto dto = null;

    private RecyclerView monthly_rcv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dto = getArguments().getParcelable(ARG_DTO);
        mf = this;
    }

    public static MonthlyFragment newInstance() {
        MonthlyFragment fragment = new MonthlyFragment();
        return fragment;
    }

    public static MonthlyFragment newInstance(MainFragmentDto dto) {
        MonthlyFragment fragment = new MonthlyFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DTO, dto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_monthly_page, container, false);
        monthly_rcv = (RecyclerView) v.findViewById(R.id.monthly_rcv);
        initView();
        return v;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    public static boolean add = false;

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (HttpRequest.update_list) {
            adapter = null;
            dataSet = null;
            initView();
//            Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void update_adapter() {
        adapter = null;
        dataSet = null;
        initView();
    }

    private MonthlyAdapter adapter;
    int lineHeight;

    private void initView() {
        dataSet = TimeUtils.getDatesFromTime(dto.getTimeInMilliSecond(), 42);
        monthly_rcv.setHasFixedSize(true);
        monthly_rcv.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dto.getTimeInMilliSecond());
        lineHeight = Utils.getContentHeight(Utils.getDimenInPx(R.dimen.dimen_text_home_title_height), getActivity()) / 6;//-decorate_width;
//        monthly_rcv.addItemDecoration(new GridDecoration(decorate_width));
//        Log.e(TAG,"curMonth:"+TimeUtils.getCurrentMonth(dto.getTimeInMilliSecond()));
        adapter = new MonthlyAdapter(dataSet, lineHeight, TimeUtils.getCurrentMonth(dto.getTimeInMilliSecond()));
        monthly_rcv.setAdapter(adapter);
//        int month = cal.get(Calendar.MONTH) + 1;
//        Log.e(TAG, "Year:" + cal.get(Calendar.YEAR) + " Month:" + month + " DATE:" + cal.get(Calendar.DAY_OF_MONTH));
        // get data display on date
        HttpRequest.getInstance().GetMonthSchedules(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, dto.getScheduleType(), dataSet, this);
    }


    @Override
    public void onGetMonthScheduleSuccess(List<CalendarDto> dtos) {
        dataSet = dtos;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMonthScheduleFail(ErrorDto errorDto) {

    }
}
