package com.dazone.crewschedule.Interfaces;

import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.PersonData;

import java.util.ArrayList;

/**
 * Created by THANHTUNG on 23/12/2015.
 */
public interface OnGetAllOfUser {
    void onGetAllOfUserSuccess(ArrayList<PersonData> list);
    void onGetAllOfUserFail(ErrorDto errorData);
}
