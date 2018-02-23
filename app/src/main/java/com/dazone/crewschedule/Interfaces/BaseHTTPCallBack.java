package com.dazone.crewschedule.Interfaces;


import com.dazone.crewschedule.Dtos.ErrorDto;

public interface BaseHTTPCallBack {
    void onHTTPSuccess();
    void onHTTPFail(ErrorDto errorDto);
}
