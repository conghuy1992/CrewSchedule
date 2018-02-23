package com.dazone.crewschedule.Interfaces;


import com.dazone.crewschedule.Dtos.ErrorDto;

/**
 * Created by david on 9/25/15.
 */
public interface OnCheckDevice {
    public void onDeviceSuccess();
    public void onHTTPFail(ErrorDto errorDto);
}
