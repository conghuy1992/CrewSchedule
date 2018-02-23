package com.dazone.crewschedule.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences prefs;

    public static String SHAREDPREFERENCES_NAME = "oathsharedpreferences";
    private final String ACCESSTOKEN = "accesstoken";
    private final String USER_JSON_INFO = "user_json";
    private final String SERVERSITE = "serversite";
    private final String USER_NAME = "username";
    private final String USERNO = "user_no";
    private final String INTRO_COUNT = "introcount";


    public Prefs() {
        prefs = CrewScheduleApplication.getInstance().getApplicationContext().
                getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void putUserData(String userDataJson, String accessToken) {
        prefs.edit().putString(ACCESSTOKEN, accessToken).apply();
        prefs.edit().putString(USER_JSON_INFO, userDataJson).apply();
    }

    public String getUserJson() {
        return prefs.getString(USER_JSON_INFO, "");
    }

    public void removeUserData() {
        prefs.edit().remove(ACCESSTOKEN).apply();
        prefs.edit().remove(USER_JSON_INFO).apply();
    }

    public void putServerSite(String serversite) {
        putStringValue(SERVERSITE, serversite);
    }

    public String getServerSite() {
        return getStringValue(SERVERSITE, "");
    }

    public void putUserName(String username) {
        putStringValue(USER_NAME, username);
    }

    public String getUserName() {
        return getStringValue(USER_NAME, "");
    }

    public void putaccesstoken(String accesstoken) {
        putStringValue(ACCESSTOKEN, accesstoken);
    }

    public void putUserNo(int userNo) {
        putIntValue(USERNO, userNo);
    }

    public int getUserNo() {
        return getIntValue(USERNO, -1);
    }


    public String getaccesstoken() {
        return getStringValue(ACCESSTOKEN, "");
    }

    public void putBooleanValue(String KEY, boolean value) {
        prefs.edit().putBoolean(KEY, value).apply();
    }

    public boolean getBooleanValue(String KEY, boolean defvalue) {
        return prefs.getBoolean(KEY, defvalue);
    }

    public void putStringValue(String KEY, String value) {
        prefs.edit().putString(KEY, value).apply();
    }

    public String getStringValue(String KEY, String defvalue) {
        return prefs.getString(KEY, defvalue);
    }

    public void putIntValue(String KEY, int value) {
        prefs.edit().putInt(KEY, value).apply();
    }

    public int getIntValue(String KEY, int defvalue) {
        return prefs.getInt(KEY, defvalue);
    }

    public void putLongValue(String KEY, long value) {
        prefs.edit().putLong(KEY, value).apply();
    }

    public long getLongValue(String KEY, long defvalue) {
        return prefs.getLong(KEY, defvalue);
    }

    public void putFloatValue(String KEY, float value) {
        prefs.edit().putFloat(KEY, value).apply();
    }

    public void putintrocount(int introcount) {
        putIntValue(INTRO_COUNT, introcount);
    }

    public int getintrocount() {
        return getIntValue(INTRO_COUNT, 0);
    }

    public float getFloatValue(String KEY, float defvalue) {
        return prefs.getFloat(KEY, defvalue);
    }

    public void removeValue(String KEY) {
        prefs.edit().remove(KEY).apply();
    }

    public void clear() {
        prefs.edit().clear().apply();
    }

    public void clearLogin() {
        prefs.edit().remove(ACCESSTOKEN).apply();
    }
}
