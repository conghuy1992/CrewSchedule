package com.dazone.crewschedule.Fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dazone.crewschedule.Activities.HomeActivity;
import com.dazone.crewschedule.Adapters.WeeklyItemAdapter;
import com.dazone.crewschedule.Adapters.WeeklyItemAdapter_week;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.MainFragmentDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Interfaces.GetMonthScheduleCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeeklyListItemFragment extends BaseFragment implements GetMonthScheduleCallBack {

    String TAG = "WeeklyListItemFragment";
    List<CalendarDto> dataSet;

    private static final String ARG_DTO = "monthly_millis";

    private MainFragmentDto dto = null;

    public RecyclerView monthly_rcv, monthly_week;
    public LinearLayout ln_week;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dto = getArguments().getParcelable(ARG_DTO);
    }

    public static WeeklyListItemFragment newInstance(MainFragmentDto dto) {
        WeeklyListItemFragment fragment = new WeeklyListItemFragment();
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
            dataSet = null;
            adapter = null;
            initView();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Log.e(TAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_weekly_page, container, false);
        monthly_rcv = (RecyclerView) v.findViewById(R.id.monthly_rcv);
        monthly_week = (RecyclerView) v.findViewById(R.id.monthly_week);
        initView();
        return v;
    }


    private WeeklyItemAdapter adapter;
    WeeklyItemAdapter_week adapter_week;
    public static int lineHeight;

    CalendarDto CDtos = new CalendarDto();


    private void initView() {
        CDtos.setScheduleDtos(null);
        CDtos.setTimeInMillis(-1);
        dataSet = TimeUtils.getWeeksFromTime(dto.getTimeInMilliSecond(), 200);
        dataSet.add(0, CDtos);
        dataSet.remove(dataSet.size() - 1);

        for (int i = 0; i < dataSet.size(); i += 8) {
            dataSet.get(i).setScheduleDtos(dataSet.get(0).getScheduleDtos());
            dataSet.get(i).setTimeInMillis(dataSet.get(0).getTimeInMillis());
        }
        for (int i = 1; i < dataSet.size(); i += 8) {
            dataSet.get(i).setScheduleDtos(dataSet.get(1).getScheduleDtos());
            dataSet.get(i).setTimeInMillis(dataSet.get(1).getTimeInMillis());
        }
        for (int i = 2; i < dataSet.size(); i += 8) {
            dataSet.get(i).setScheduleDtos(dataSet.get(2).getScheduleDtos());
            dataSet.get(i).setTimeInMillis(dataSet.get(2).getTimeInMillis());
        }
        for (int i = 3; i < dataSet.size(); i += 8) {
            dataSet.get(i).setScheduleDtos(dataSet.get(3).getScheduleDtos());
            dataSet.get(i).setTimeInMillis(dataSet.get(3).getTimeInMillis());
        }
        for (int i = 4; i < dataSet.size(); i += 8) {
            dataSet.get(i).setScheduleDtos(dataSet.get(4).getScheduleDtos());
            dataSet.get(i).setTimeInMillis(dataSet.get(4).getTimeInMillis());
        }
        for (int i = 5; i < dataSet.size(); i += 8) {
            dataSet.get(i).setScheduleDtos(dataSet.get(5).getScheduleDtos());
            dataSet.get(i).setTimeInMillis(dataSet.get(5).getTimeInMillis());
        }
        for (int i = 6; i < dataSet.size(); i += 8) {
            dataSet.get(i).setScheduleDtos(dataSet.get(6).getScheduleDtos());
            dataSet.get(i).setTimeInMillis(dataSet.get(6).getTimeInMillis());
        }
        for (int i = 7; i < dataSet.size(); i += 8) {
            dataSet.get(i).setScheduleDtos(dataSet.get(7).getScheduleDtos());
            dataSet.get(i).setTimeInMillis(dataSet.get(7).getTimeInMillis());
        }
        monthly_rcv.setHasFixedSize(true);
        monthly_rcv.setLayoutManager(new GridLayoutManager(getActivity(), 8));
//        monthly_rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        monthly_week.setHasFixedSize(true);
        monthly_week.setLayoutManager(new GridLayoutManager(getActivity(), 8));

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(dto.getTimeInMilliSecond());

        lineHeight = Utils.getScreenWidth(getActivity()) / 8;//-decorate_width;

        adapter = new WeeklyItemAdapter(dataSet, lineHeight, TimeUtils.getCurrentWeek(dto.getTimeInMilliSecond()));
        monthly_rcv.setAdapter(adapter);

        adapter_week = new WeeklyItemAdapter_week(dataSet, lineHeight, TimeUtils.getCurrentWeek(dto.getTimeInMilliSecond()));
        monthly_week.setAdapter(adapter_week);

        cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        Date date = cal.getTime();
        SimpleDateFormat sf = new SimpleDateFormat(Statics.DATE_FORMAT_YYYY_MM_DD);
        String start = sf.format(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        Date date2 = cal.getTime();
        SimpleDateFormat sf2 = new SimpleDateFormat(Statics.DATE_FORMAT_YYYY_MM_DD);
        String end = sf2.format(date2);
//        Log.e(TAG, "start:" + start + " end:" + end);
        HttpRequest.getInstance().getWeekSchedule(start, end, dto.getScheduleType(), dataSet, this);
    }


    @Override
    public void onGetMonthScheduleSuccess(List<CalendarDto> dtos) {
        dataSet = dtos;
        for (int i = 7; i >= 1; i--) {
            dataSet.get(i).setScheduleDtos(new ArrayList<ScheduleDto>(dataSet.get(i - 1).getScheduleDtos()));
//            dataSet.get(i).setTimeInMillis(dataSet.get(i - 1).getTimeInMillis());
        }
        dataSet.get(0).setScheduleDtos(null);
        dataSet.get(0).setTimeInMillis(-1);
        for (int i = 8; i < dataSet.size(); i++) {
            dataSet.get(i).setScheduleDtos(null);
            dataSet.get(i).setTimeInMillis(-1);
        }

        for (int i = 1; i < 8; i++) {
            List<ScheduleDto> scheduleDtos_temp = new ArrayList<ScheduleDto>();
            List<ScheduleDto> scheduleDtos_other_null = new ArrayList<ScheduleDto>();
            List<ScheduleDto> scheduleDtos = new ArrayList<ScheduleDto>(dataSet.get(i).getScheduleDtos());
            if (dataSet.get(i).getScheduleDtos().size() > 0) {
                dataSet.get(i).setScheduleDtos(null);
                for (ScheduleDto dto1 : scheduleDtos) {
                    int START = Integer.parseInt(dto1.getStartTime().split(":")[0]);
                    int END = Integer.parseInt(dto1.getEndTime().split(":")[0]);
                    if (START == 0 && END == 0) {
                        scheduleDtos_temp.add(dto1);
                    } else {
                        scheduleDtos_other_null.add(dto1);
                    }
                }
                for (int g = i + 8; g < dataSet.size(); g += 8) {
                    dataSet.get(g).setScheduleDtos(scheduleDtos_other_null);
                }
                dataSet.get(i).setScheduleDtos(scheduleDtos_temp);
            }
        }

        for (int i = 0; i < dataSet.size(); i += 8) {
            dataSet.get(i).setTimeInMillis(dataSet.get(0).getTimeInMillis());
        }
        for (int i = 1; i < dataSet.size(); i += 8) {
            dataSet.get(i).setTimeInMillis(dataSet.get(1).getTimeInMillis());
        }
        for (int i = 2; i < dataSet.size(); i += 8) {
            dataSet.get(i).setTimeInMillis(dataSet.get(2).getTimeInMillis());
        }
        for (int i = 3; i < dataSet.size(); i += 8) {
            dataSet.get(i).setTimeInMillis(dataSet.get(3).getTimeInMillis());
        }
        for (int i = 4; i < dataSet.size(); i += 8) {
            dataSet.get(i).setTimeInMillis(dataSet.get(4).getTimeInMillis());
        }
        for (int i = 5; i < dataSet.size(); i += 8) {
            dataSet.get(i).setTimeInMillis(dataSet.get(5).getTimeInMillis());
        }
        for (int i = 6; i < dataSet.size(); i += 8) {
            dataSet.get(i).setTimeInMillis(dataSet.get(6).getTimeInMillis());
        }
        for (int i = 7; i < dataSet.size(); i += 8) {
            dataSet.get(i).setTimeInMillis(dataSet.get(7).getTimeInMillis());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetMonthScheduleFail(ErrorDto errorDto) {

    }
}
