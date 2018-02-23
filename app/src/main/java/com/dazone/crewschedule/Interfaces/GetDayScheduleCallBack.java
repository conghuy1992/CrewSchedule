package com.dazone.crewschedule.Interfaces;

import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;

import java.util.List;

/**
 * Created by nguyentiendat on 1/12/16.
 */
public interface GetDayScheduleCallBack {
    void onGetDayScheduleSuccess(List<ScheduleDto> dtos);
    void onGetDayScheduleFail(ErrorDto errorDto);
}
