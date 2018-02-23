package com.dazone.crewschedule.Interfaces;

import com.dazone.crewschedule.Dtos.DrawerDto;
import com.dazone.crewschedule.Dtos.ErrorDto;

import java.util.List;

/**
 * Created by nguyentiendat on 1/12/16.
 */
public interface GetCalendarTypeCallBack {
    void onGetGetDrawerSuccess(List<DrawerDto> dtos);
    void onGetGetDrawerFail(ErrorDto errorDto);
}
