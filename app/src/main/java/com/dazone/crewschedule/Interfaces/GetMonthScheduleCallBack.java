package com.dazone.crewschedule.Interfaces;

import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ErrorDto;

import java.util.List;

/**
 * Created by nguyentiendat on 1/12/16.
 */
public interface GetMonthScheduleCallBack {
    void onGetMonthScheduleSuccess(List<CalendarDto> dtos);
    void onGetMonthScheduleFail(ErrorDto errorDto);
}
