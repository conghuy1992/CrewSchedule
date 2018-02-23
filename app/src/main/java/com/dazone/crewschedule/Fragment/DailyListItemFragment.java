package com.dazone.crewschedule.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dazone.crewschedule.Adapters.DailyItemAdapter;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.MainFragmentDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Interfaces.GetDayScheduleCallBack;
import com.dazone.crewschedule.Interfaces.GetMonthScheduleCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyListItemFragment extends BaseFragment implements GetMonthScheduleCallBack, GetDayScheduleCallBack {

    String TAG = "DailyListItemFragment";
    List<CalendarDto> dataSet;

    private static final String ARG_DTO = "monthly_millis";

    private MainFragmentDto dto = null;
    private RecyclerView monthly_rcv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dto = getArguments().getParcelable(ARG_DTO);
    }

    public static DailyListItemFragment newInstance(MainFragmentDto dto) {
        DailyListItemFragment fragment = new DailyListItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DTO, dto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (HttpRequest.update_list) {
            HttpRequest.update_list = false;
        }
    }

    public static int _width = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_monthly_page, container, false);
        monthly_rcv = (RecyclerView) v.findViewById(R.id.monthly_rcv);
        initView();
        return v;
    }

    private DailyItemAdapter adapter;
    public static int lineHeight;

    private void initView() {
        dataSet = TimeUtils.getDatesFromTime(dto.getTimeInMilliSecond(), 25);
        monthly_rcv.setHasFixedSize(true);
        monthly_rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dto.getTimeInMilliSecond());
//        Log.e(TAG, "date:" + cal.get(Calendar.DAY_OF_MONTH)+" month:"+cal.get(Calendar.MONTH));
        lineHeight = Utils.getScreenWidth(getActivity()) / 8;//-decorate_width;
        adapter = new DailyItemAdapter(dataSet, lineHeight);
        monthly_rcv.setAdapter(adapter);
        String current = TimeUtils.showTime(TimeUtils.getCurrentDayOfMonth(cal).getTimeInMillis(), Statics.DATE_FORMAT_YYYY_MM_DD);
//        Log.e(TAG, "current:" + current);
        HttpRequest.getInstance().getDayTabSchedules(current, dto.getScheduleType(), dataSet, this);
    }

    @Override
    public void onGetMonthScheduleSuccess(List<CalendarDto> dtos) {
        dataSet = dtos;
        long tml = dtos.get(0).getTimeInMillis();
        List<ScheduleDto> scheduleDtos_temp = new ArrayList<ScheduleDto>();
        List<ScheduleDto> scheduleDtos_other_null = new ArrayList<ScheduleDto>();
        List<ScheduleDto> scheduleDtos = new ArrayList<ScheduleDto>(dataSet.get(0).getScheduleDtos());
        if (dataSet.get(0).getScheduleDtos().size() > 0) {
            dataSet.get(0).setScheduleDtos(null);

            for (ScheduleDto dto1 : scheduleDtos) {
                int START = Integer.parseInt(dto1.getStartTime().split(":")[0]);
                int END = Integer.parseInt(dto1.getEndTime().split(":")[0]);
                if (START == 0 && END == 0) {
                    scheduleDtos_temp.add(dto1);
                } else {
                    scheduleDtos_other_null.add(dto1);
                }
            }
            dataSet.get(0).setScheduleDtos(scheduleDtos_temp);
            for (int i = 1; i < dataSet.size(); i++) {
                dataSet.get(i).setScheduleDtos(new ArrayList<ScheduleDto>(scheduleDtos_other_null));
                dataSet.get(i).setTimeInMillis(tml);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMonthScheduleFail(ErrorDto errorDto) {

    }

    @Override
    public void onGetDayScheduleSuccess(List<ScheduleDto> dtos) {

    }

    @Override
    public void onGetDayScheduleFail(ErrorDto errorDto) {

    }
}
