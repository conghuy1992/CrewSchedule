package com.dazone.crewschedule.HTTPs;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.dazone.crewschedule.Database.ServerSiteDBHelper;
import com.dazone.crewschedule.Database.UserDBHelper;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.UserDto;
import com.dazone.crewschedule.Interfaces.BaseHTTPCallBack;
import com.dazone.crewschedule.Interfaces.OAUTHUrls;
import com.dazone.crewschedule.Interfaces.OnCheckDevice;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;
import com.dazone.crewschedule.Utils.LanguageUtils;
import com.dazone.crewschedule.Utils.Prefs;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpOauthRequest {
    String TAG = "HttpOauthRequest";
    private static HttpOauthRequest mInstance;
    private static String root_link;

    public static HttpOauthRequest getInstance() {
        if (null == mInstance) {
            mInstance = new HttpOauthRequest();
        }
        root_link = CrewScheduleApplication.getInstance().getmPrefs().getServerSite();
        return mInstance;
    }

    public void login(final BaseHTTPCallBack baseHTTPCallBack, String userID, String password, String companyDomain, String server_link) {
        final String url = server_link + OAUTHUrls.URL_GET_LOGIN;

        Map<String, String> params = new HashMap<>();
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", "" + TimeUtils.getTimezoneOffsetInMinutes());
        params.put("companyDomain", companyDomain);
        params.put("password", password);
        params.put("userID", userID);
        params.put("mobileOSVersion", "Android " + android.os.Build.VERSION.RELEASE);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG,"response:"+response);
                Gson gson = new Gson();
                UserDto userDto = gson.fromJson(response, UserDto.class);

                if (userDto != null) {
                    UserDBHelper.addUser(userDto);
                    Prefs prefs = new Prefs();
                    prefs.putaccesstoken(userDto.session);
                    prefs.putUserNo(userDto.Id);
                    prefs.putUserName(userDto.userID);
                    if (prefs != null)
                        prefs.putUserData(response, userDto.session);
                }
                baseHTTPCallBack.onHTTPSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                baseHTTPCallBack.onHTTPFail(error);
            }
        });
    }

    public void checLogin(final BaseHTTPCallBack baseHTTPCallBack) {
        final String url = root_link + OAUTHUrls.URL_CHECK_SESSION;
        Map<String, String> params = new HashMap<>();
        params.put("sessionId", "" + CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", "" + TimeUtils.getTimezoneOffsetInMinutes());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                UserDto userDto = gson.fromJson(response, UserDto.class);
                if (userDto != null) {
                    UserDBHelper.addUser(userDto);
                    Prefs prefs = new Prefs();
                    prefs.putaccesstoken(userDto.session);
                    prefs.putUserNo(userDto.Id);
                    prefs.putUserName(userDto.userID);
                    if (prefs != null)
                        prefs.putUserData(response, userDto.session);
                }
                if (baseHTTPCallBack != null) {
                    baseHTTPCallBack.onHTTPSuccess();
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (baseHTTPCallBack != null) {
                    baseHTTPCallBack.onHTTPFail(error);
                }
            }
        });
    }

    public static void logout(final BaseHTTPCallBack baseHTTPCallBack) {
        final String url = root_link + OAUTHUrls.URL_LOG_OUT;
        Map<String, String> params = new HashMap<>();
        params.put("sessionId", "" + CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                baseHTTPCallBack.onHTTPSuccess();
                Log.e("logout",response);
            }

            @Override
            public void onFailure(ErrorDto error) {
                baseHTTPCallBack.onHTTPFail(error);
                Log.e("logout", "error");
            }
        });
    }

    public void insertPhoneToken() {
        String url = OAUTHUrls.URL_INSERT_PHONE_TOKEN;
        Map<String, String> params = new HashMap<>();
        params.put("PhoneToken", Utils.getUniqueDeviceId(CrewScheduleApplication.getInstance()));
        params.put("SessionID", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("Domain", root_link);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
            }

            @Override
            public void onFailure(ErrorDto error) {
            }
        });
    }

    public void checkPhoneToken(final OnCheckDevice callBack) {
        String url = OAUTHUrls.URL_CHECK_DEVICE_TOKEN;
        Map<String, String> params = new HashMap<>();
        params.put("PhoneToken", Utils.getUniqueDeviceId(CrewScheduleApplication.getInstance()));
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    Prefs prefs = new Prefs();
                    JSONObject object = new JSONObject(response);
                    String SessionID = object.getString("SessionID");
                    String Domain = object.getString("Domain");
                    if (!TextUtils.isEmpty(SessionID)) {
                        prefs.putaccesstoken(SessionID);
                    }
                    if (!TextUtils.isEmpty(Domain)) {
                        prefs.putServerSite(Domain);
//                        ServerSiteDBHelper.addServerSite(Domain);
                    }
                } catch (Exception e) {
                    ErrorDto dto = new ErrorDto();
                    dto.message = "Cannot connect to server";
                    callBack.onHTTPFail(dto);
                }
                callBack.onDeviceSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                callBack.onHTTPFail(error);
            }
        });
    }

    public void getDomain(final BaseHTTPCallBack callBack) {
        String url = OAUTHUrls.URL_GET_DOMAIN;
        Map<String, String> params = new HashMap<>();
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Type listType = new TypeToken<List<String>>() {
                }.getType();
                List<String> listDomain = new Gson().fromJson(response, listType);
                ServerSiteDBHelper.addServerSites(listDomain);
                if (callBack != null)
                    callBack.onHTTPSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null)
                    callBack.onHTTPFail(error);
            }
        });
    }
}
