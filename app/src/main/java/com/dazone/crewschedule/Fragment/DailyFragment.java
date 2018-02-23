package com.dazone.crewschedule.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewschedule.Activities.DailyScheduleActivity;
import com.dazone.crewschedule.Adapters.DailyAdapter;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Interfaces.GetDayScheduleCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class DailyFragment extends BaseFragment implements GetDayScheduleCallBack {
    String TAG="DailyFragment";
    private String currDate = "";
    private RecyclerView daily_rcv;
    List<ScheduleDto> dataSet;
    private DailyAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long timeMillis = getArguments().getLong(ParamKeys.KEY_TIME_MILLISECONDS, 0);
        currDate = TimeUtils.showTime(timeMillis, Statics.DATE_FORMAT_YYYY_MM_DD);
//        Log.e(TAG,"currDate:"+currDate);
        ((DailyScheduleActivity) getActivity()).setToolBarTitle(currDate);
    }

    public static DailyFragment newInstance(long timeMillis) {
        DailyFragment fragment = new DailyFragment();
        Bundle args = new Bundle();
        args.putLong(ParamKeys.KEY_TIME_MILLISECONDS, timeMillis);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daily, container, false);
        daily_rcv = (RecyclerView) v.findViewById(R.id.daily_rcv);
        initView();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (HttpRequest.update_list) {
            dataSet = null;
            adapter = null;
            initView();
        }
    }

    private void initView() {
        dataSet = new ArrayList<>();
        daily_rcv.setHasFixedSize(true);
        daily_rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DailyAdapter(dataSet);
        daily_rcv.setAdapter(adapter);
        HttpRequest.getInstance().getDaySchedules(currDate, 0, this);

    }

    protected void setActionBarTitle(String title) {
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        if (toolbar_title != null) {
            if (TextUtils.isEmpty(title)) {
                toolbar_title.setText(R.string.app_name);
            } else {
                toolbar_title.setText(title);
            }
        }
    }

    @Override
    public void onGetDayScheduleSuccess(List<ScheduleDto> dtos) {
        dataSet.addAll(dtos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetDayScheduleFail(ErrorDto errorDto) {

    }
}
