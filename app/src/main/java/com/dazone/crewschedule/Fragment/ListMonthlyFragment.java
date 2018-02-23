package com.dazone.crewschedule.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewschedule.Adapters.ListMonthlyAdapter;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.MainFragmentDto;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Interfaces.GetMonthScheduleCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListMonthlyFragment extends BaseFragment implements GetMonthScheduleCallBack {

    String TAG = "ListMonthlyFragment";
    List<CalendarDto> dataSet;

    private static final String ARG_DTO = "monthly_millis";

    private MainFragmentDto dto = null;

    private RecyclerView monthly_rcv;
    private TextView tv_no_data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dto = getArguments().getParcelable(ARG_DTO);
    }

    public static ListMonthlyFragment newInstance(MainFragmentDto dto) {
        ListMonthlyFragment fragment = new ListMonthlyFragment();
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
        tv_no_data = (TextView) v.findViewById(R.id.tv_no_data);
        initView();
        return v;
    }

    private ListMonthlyAdapter adapter;

    private void initView() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dto.getTimeInMilliSecond());
        dataSet = TimeUtils.getListCalForMonth(dto.getTimeInMilliSecond(), calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        monthly_rcv.setHasFixedSize(true);
        monthly_rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dto.getTimeInMilliSecond());

        adapter = new ListMonthlyAdapter(dataSet, TimeUtils.getCurrentMonth(dto.getTimeInMilliSecond()));
        monthly_rcv.setAdapter(adapter);
        String startDate = TimeUtils.showTime(TimeUtils.getFirstDayOfMonth(cal).getTimeInMillis(), Statics.DATE_FORMAT_YYYY_MM_DD);
        String endDate = TimeUtils.showTime(TimeUtils.getEndDayOfMonth(cal).getTimeInMillis(), Statics.DATE_FORMAT_YYYY_MM_DD);
        HttpRequest.getInstance().getPeriodSchedules(startDate, endDate, dto.getScheduleType(), dataSet, this);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (HttpRequest.update_list) {
            dataSet = null;
            adapter = null;
            initView();
        }
    }

    @Override
    public void onGetMonthScheduleSuccess(List<CalendarDto> dtos) {
        List<CalendarDto> temp = getValidList(dtos);
        dataSet.clear();
        dataSet.addAll(temp);
        if (temp.size() == 0) {
            tv_no_data.setVisibility(View.VISIBLE);
        } else {
            tv_no_data.setVisibility(View.GONE);
        }
        Utils.printLogs(" dtos: " + new Gson().toJson(dtos));
        Utils.printLogs(" dtos: " + new Gson().toJson(dataSet));
        adapter.notifyDataSetChanged();
    }

    private List<CalendarDto> getValidList(List<CalendarDto> dtos) {
        List<CalendarDto> result = new ArrayList<>();
        {
            for (CalendarDto dto : dtos) {
                Utils.printLogs(" dto.getScheduleDtos().size(): " + dto.getScheduleDtos().size());
                if (dto.getScheduleDtos() != null && dto.getScheduleDtos().size() != 0) {
                    result.add(dto);
                }
            }
        }
        return result;
    }

    @Override
    public void onGetMonthScheduleFail(ErrorDto errorDto) {

    }
}
