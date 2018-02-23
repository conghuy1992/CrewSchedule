package com.dazone.crewschedule.Dtos;


import android.text.TextUtils;

import com.dazone.crewschedule.Interfaces.DrawImageItem;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;
import com.google.gson.Gson;

public class UserDto extends DataDto implements DrawImageItem {

    private static UserDto _instance;

    public int Id;

    public int CompanyNo;

    public int PermissionType;//0 normal, 1 admin

    public String userID;

    public String FullName = "";

    public String session;

    public String avatar;

    public String NameCompany = "";

    public String MailAddress = "";

    public synchronized static UserDto getInstance() {
        if (_instance == null) {
            _instance = new UserDto();
        }
        return _instance;
    }

    public synchronized static UserDto getUserInformation() {
        getInstance();
        if (_instance.Id == 0) {
            String userJson = CrewScheduleApplication.getInstance().getmPrefs().getUserJson();
            if (!TextUtils.isEmpty(userJson)) {
                Gson gson = new Gson();
                _instance = gson.fromJson(userJson, UserDto.class);
            }
        }
        return _instance;
    }

    @Override
    public String getImageLink() {
        return avatar;
    }

    @Override
    public String getImageTitle() {
        return FullName;
    }
}
