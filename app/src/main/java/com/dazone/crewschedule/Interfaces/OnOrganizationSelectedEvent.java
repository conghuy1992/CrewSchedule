package com.dazone.crewschedule.Interfaces;


import com.dazone.crewschedule.Dtos.PersonData;

/**
 * Created by Sherry on 12/31/15.
 */
public interface OnOrganizationSelectedEvent {
    void onOrganizationCheck(boolean isCheck, PersonData personData);
}
