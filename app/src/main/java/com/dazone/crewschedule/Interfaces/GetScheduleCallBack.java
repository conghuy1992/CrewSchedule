package com.dazone.crewschedule.Interfaces;

import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.ScheduleDetailDto;

/**
 * Created by nguyentiendat on 1/12/16.
 */
public interface GetScheduleCallBack {
    void onGetScheduleSuccess(ScheduleDetailDto dto);
    void onGetScheduleFail(ErrorDto errorDto);
}
