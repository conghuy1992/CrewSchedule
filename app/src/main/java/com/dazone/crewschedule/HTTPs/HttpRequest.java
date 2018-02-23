package com.dazone.crewschedule.HTTPs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.dazone.crewschedule.Activities.HomeActivity;
import com.dazone.crewschedule.Database.OrganizationUserDBHelper;
import com.dazone.crewschedule.Database.ServerSiteDBHelper;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.DrawerDto;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.PersonData;
import com.dazone.crewschedule.Dtos.ScheduleDetailDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.Fragment.AddNewScheduleFragment;
import com.dazone.crewschedule.Fragment.BaseFragment;
import com.dazone.crewschedule.Fragment.MonthlyFragment;
import com.dazone.crewschedule.Interfaces.GetCalendarTypeCallBack;
import com.dazone.crewschedule.Interfaces.GetDayScheduleCallBack;
import com.dazone.crewschedule.Interfaces.GetMonthScheduleCallBack;
import com.dazone.crewschedule.Interfaces.GetScheduleCallBack;
import com.dazone.crewschedule.Interfaces.OnGetAllOfUser;
import com.dazone.crewschedule.Interfaces.Urls;
import com.dazone.crewschedule.RCVViewHolders.DrawerMenuViewHolder;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;
import com.dazone.crewschedule.Utils.LanguageUtils;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HttpRequest {
    public static boolean update_list = false;
    String TAG = "HttpRequest";
    private static HttpRequest mInstance;
    public static String root_link;
    Context c;

    public static HttpRequest getInstance() {
        if (null == mInstance) {
            mInstance = new HttpRequest();
        }
        root_link = CrewScheduleApplication.getInstance().getmPrefs().getServerSite();
        return mInstance;
    }

    public void DeleteSchedule(int ScheduleNo, final AppCompatActivity bf, final ProgressDialog pr) {
        String url = root_link + Urls.URL_DELETE_SCHEDULE;
//        Log.e(TAG, url);
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("ScheduleNo", ScheduleNo);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "onSuccess: " + response);
                if (pr != null && pr.isShowing())
                    pr.dismiss();
                bf.finish();
                update_list = true;
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "onFailure");
            }
        });
    }

    public void UpdateSchedule(final BaseFragment bf, final ProgressDialog pr, String ContactUsers, String AttachedFileList, String IsLunar,
                               String IsLastDay, String IsFiveWeek, int IsHoliday, int DivisionNo, int CalendarNo, String StartDate, String EndDate,
                               String StartTime, String EndTime, String Content, String ShareUsers, int RepeatType, int RepeatCount,
                               int RepeatMonth, int RepeatWeek, int RepeatDay, int RepeatDOWs, String Place, int NotiTimeType, boolean IsAllDay,
                               boolean IsNotiNote, boolean IsNotiMail, boolean IsNotiSMS, boolean IsNotiPopup, int CalendarType, String Title,
                               double Lati, double Long, int ScheduleNo, String DelFileList) {

        String url = root_link + Urls.URL_UPDATE_SCHEDULE;
//        Log.e(TAG, url);

        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("ScheduleNo", ScheduleNo);
        params.put("Title", Title);
        params.put("CalendarType", CalendarType);
        params.put("CalendarNo", CalendarNo);
        params.put("DivisionNo", DivisionNo);
        params.put("Content", Content);
        params.put("IsLunar", IsLunar);
        params.put("IsHoliday", IsHoliday);
        params.put("IsFiveWeek", IsFiveWeek);
        params.put("IsLastDay", IsLastDay);
        params.put("RepeatType", RepeatType);
        params.put("RepeatCount", RepeatCount);
        params.put("RepeatMonth", RepeatMonth);
        params.put("RepeatWeek", RepeatWeek);
        params.put("RepeatDay", RepeatDay);
        params.put("RepeatDOWs", RepeatDOWs);
        params.put("StartDate", StartDate);
        params.put("EndDate", EndDate);
        params.put("StartTime", StartTime);
        params.put("EndTime", EndTime);
        params.put("IsAllDay", IsAllDay);
        params.put("IsNotiNote", IsNotiNote);
        params.put("IsNotiMail", IsNotiMail);
        params.put("IsNotiSMS", IsNotiSMS);
        params.put("IsNotiPopup", IsNotiPopup);
        params.put("NotiTimeType", NotiTimeType);
        params.put("Place", Place);
        params.put("Lati", Lati);
        params.put("Long", Long);
        params.put("ShareUsers", ShareUsers);
        params.put("ContactUsers", ContactUsers);
        params.put("AttachedFileList", AttachedFileList);
        params.put("DelFileList", DelFileList);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "onSuccess: " + response);
                if (pr != null && pr.isShowing())
                    pr.dismiss();
                bf.getActivity().finish();
                update_list = true;
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "onFailure");
            }
        });
    }

    public void InsertSchedule(final BaseFragment bf, final ProgressDialog pr, String ContactUsers, String AttachedFileList, String IsLunar,
                               String IsLastDay, String IsFiveWeek, int IsHoliday, int DivisionNo, int CalendarNo, String StartDate, String EndDate,
                               String StartTime, String EndTime, String Content, String ShareUsers, int RepeatType, int RepeatCount,
                               int RepeatMonth, int RepeatWeek, int RepeatDay, int RepeatDOWs, String Place, int NotiTimeType, boolean IsAllDay,
                               boolean IsNotiNote, boolean IsNotiMail, boolean IsNotiSMS, boolean IsNotiPopup, int CalendarType, String Title, double Lati, double Long) {

        String url = root_link + Urls.URL_INSERT_SCHEDULE;
//        Log.e(TAG, url);
        AddNewScheduleFragment.URL_INSERT = url;

        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("Title", Content);
        params.put("CalendarType", CalendarType);
        params.put("CalendarNo", CalendarNo);
        params.put("DivisionNo", DivisionNo);
        params.put("Content", Content);
        params.put("IsLunar", IsLunar);
        params.put("IsHoliday", IsHoliday);
        params.put("IsFiveWeek", IsFiveWeek);
        params.put("IsLastDay", IsLastDay);
        params.put("RepeatType", RepeatType);
        params.put("RepeatCount", RepeatCount);
        params.put("RepeatMonth", RepeatMonth);
        params.put("RepeatWeek", RepeatWeek);
        params.put("RepeatDay", RepeatDay);
        params.put("RepeatDOWs", RepeatDOWs);
        params.put("StartDate", StartDate);
        params.put("EndDate", EndDate);
        params.put("StartTime", StartTime);
        params.put("EndTime", EndTime);
        params.put("IsAllDay", IsAllDay);
        params.put("IsNotiNote", IsNotiNote);
        params.put("IsNotiMail", IsNotiMail);
        params.put("IsNotiSMS", IsNotiSMS);
        params.put("IsNotiPopup", IsNotiPopup);
        params.put("NotiTimeType", NotiTimeType);
        params.put("Place", Place);
        params.put("Lati", Lati);
        params.put("Long", Long);
        params.put("ShareUsers", ShareUsers);
        params.put("ContactUsers", ContactUsers);
        params.put("AttachedFileList", AttachedFileList);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "onSuccess: " + response);
                if (pr != null && pr.isShowing())
                    pr.dismiss();
                bf.getActivity().finish();
//                MonthlyFragment.add = true;
                update_list = true;
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "onFailure");
            }
        });
    }

    public void GetMonthSchedules(int year, int month, int scheduleType, final List<CalendarDto> dtos, final GetMonthScheduleCallBack callBack) {
        String url = root_link + Urls.URL_GET_MONTH_SCHEDULE;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("year", year);
        params.put("month", month);
        params.put("isStartSunday", true);
        params.put("scheduleType", scheduleType);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e("GetMonthSchedules:", response);
                try {
                    Type listType = new TypeToken<List<ScheduleDto>>() {
                    }.getType();
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() != 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (i < dtos.size() && dtos.get(i) != null) {
                                List<ScheduleDto> list = new Gson().fromJson(jsonArray.getString(i), listType);
                                dtos.get(i).setScheduleDtos(list);
                            }
                        }
                        if (callBack != null) {
                            callBack.onGetMonthScheduleSuccess(dtos);
                        }
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        ErrorDto dto = new ErrorDto();
                        dto.message = "parsing error";
                        callBack.onGetMonthScheduleFail(dto);
                    }
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null)
                    callBack.onGetMonthScheduleFail(error);
            }
        });
    }

    public void getWeekSchedule(String StartDate, String EndDate, int scheduleType, final List<CalendarDto> dtos, final GetMonthScheduleCallBack callBack) {
        String url = root_link + Urls.URL_GET_PERIOD_SCHEDULE;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("StartDate", StartDate);
        params.put("EndDate", EndDate);
        params.put("scheduleType", scheduleType);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG,response);
                try {
                    Type listType = new TypeToken<List<ScheduleDto>>() {
                    }.getType();
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() != 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (i < dtos.size() && dtos.get(i) != null) {
                                List<ScheduleDto> list = new Gson().fromJson(jsonArray.getString(i), listType);
                                dtos.get(i).setScheduleDtos(list);
                            }
                        }
                        if (callBack != null) {
                            callBack.onGetMonthScheduleSuccess(dtos);
                        }
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        ErrorDto dto = new ErrorDto();
                        dto.message = "parsing error";
                        callBack.onGetMonthScheduleFail(dto);
                    }
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null)
                    callBack.onGetMonthScheduleFail(error);
            }
        });
    }

    public void getPeriodSchedules(String StartDate, String EndDate, int scheduleType, final List<CalendarDto> dtos, final GetMonthScheduleCallBack callBack) {
        String url = root_link + Urls.URL_GET_PERIOD_SCHEDULE;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("StartDate", StartDate);
        params.put("EndDate", EndDate);
        params.put("scheduleType", scheduleType);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    Type listType = new TypeToken<List<ScheduleDto>>() {
                    }.getType();
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() != 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (i < dtos.size() && dtos.get(i) != null) {
                                List<ScheduleDto> list = new Gson().fromJson(jsonArray.getString(i), listType);
                                dtos.get(i).setScheduleDtos(list);
                            }
                        }
                        if (callBack != null) {
                            callBack.onGetMonthScheduleSuccess(dtos);
                        }
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        ErrorDto dto = new ErrorDto();
                        dto.message = "parsing error";
                        callBack.onGetMonthScheduleFail(dto);
                    }
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null)
                    callBack.onGetMonthScheduleFail(error);
            }
        });
    }

    public void getDayTabSchedules(String currDate, int scheduleType, final List<CalendarDto> dtos, final GetMonthScheduleCallBack callBack) {
        String url = root_link + Urls.URL_GET_DAY_SCHEDULE;
        //sessionId,languageCode,timeZoneOffset,currDate,scheduleType
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("currDate", currDate);
        params.put("scheduleType", scheduleType);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "getDaySchedules:" + response);
                try {
                    Type listType = new TypeToken<List<ScheduleDto>>() {
                    }.getType();
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() != 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (i < dtos.size() && dtos.get(i) != null) {
//                                List<ScheduleDto> list = new Gson().fromJson(jsonArray.getString(i), listType);
                                List<ScheduleDto> list = new Gson().fromJson(response, listType);
                                dtos.get(i).setScheduleDtos(list);

                            }
                        }
                        if (callBack != null) {
                            callBack.onGetMonthScheduleSuccess(dtos);
                        }
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        ErrorDto dto = new ErrorDto();
                        dto.message = "parsing error";
                        callBack.onGetMonthScheduleFail(dto);
                    }
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null) {
                    callBack.onGetMonthScheduleFail(error);
                }
            }
        });
    }

    public void getDaySchedules(String currDate, int scheduleType, final GetDayScheduleCallBack callBack) {
        String url = root_link + Urls.URL_GET_DAY_SCHEDULE;
        //sessionId,languageCode,timeZoneOffset,currDate,scheduleType
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("currDate", currDate);
        params.put("scheduleType", scheduleType);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "getDaySchedules:" + response);
                try {
                    Type listType = new TypeToken<List<ScheduleDto>>() {
                    }.getType();
                    List<ScheduleDto> dtos = new Gson().fromJson(response, listType);
                    if (callBack != null) {
                        callBack.onGetDayScheduleSuccess(dtos);
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        ErrorDto dto = new ErrorDto();
                        dto.message = "parsing error";
                        callBack.onGetDayScheduleFail(dto);
                    }
                }
//                try {
//                    Type listType = new TypeToken<List<ScheduleDto>>() {
//                    }.getType();
//                    JSONArray jsonArray = new JSONArray(response);
//                    if (jsonArray.length() != 0) {
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            if (i < dtos.size() && dtos.get(i) != null) {
////                                List<ScheduleDto> list = new Gson().fromJson(jsonArray.getString(i), listType);
//                                List<ScheduleDto> list = new Gson().fromJson(response, listType);
//                                dtos.get(i).setScheduleDtos(list);
//
//                            }
//                        }
//                        if (callBack != null) {
//                            callBack.onGetMonthScheduleSuccess(dtos);
//                        }
//                    }
//                } catch (Exception e) {
//                    if (callBack != null) {
//                        ErrorDto dto = new ErrorDto();
//                        dto.message = "parsing error";
//                        callBack.onGetMonthScheduleFail(dto);
//                    }
//                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null) {
                    callBack.onGetDayScheduleFail(error);
                }
            }
        });
    }

    public void getSchedule(int scheduleNo, final GetScheduleCallBack callBack) {
        String url = root_link + Urls.URL_GET_SCHEDULE;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("scheduleNo", scheduleNo);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "response: " + response);
                try {
                    ScheduleDetailDto dto = new Gson().fromJson(response, ScheduleDetailDto.class);
                    if (callBack != null) {
                        callBack.onGetScheduleSuccess(dto);
                    }
                } catch (Exception e) {
                    if (callBack != null) {
                        ErrorDto dto = new ErrorDto();
                        dto.message = "parsing error";
                        callBack.onGetScheduleFail(dto);
                    }
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null) {
                    callBack.onGetScheduleFail(error);
                }
            }
        });
    }

    public static int CalendarNo = 0;

    public void getCalendarType(final GetCalendarTypeCallBack callback) {
        String url = root_link + Urls.URL_GET_CALENDARS;
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "getCalendarType:" + response);
                try {
                    Type listType = new TypeToken<List<DrawerDto>>() {
                    }.getType();
                    List<DrawerDto> list = new Gson().fromJson(response, listType);
                    if (callback != null) {
                        callback.onGetGetDrawerSuccess(list);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        ErrorDto dto = new ErrorDto();
                        dto.message = "parsing error";
                        callback.onGetGetDrawerFail(dto);
                    }
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callback != null)
                    callback.onGetGetDrawerFail(error);
            }
        });
    }

    public void getDepartment(final OnGetAllOfUser callBack) {
        String url = root_link + Urls.URL_GET_DEPARTMENT;
        Map<String, String> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", Locale.getDefault().getLanguage().toUpperCase());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes() + "");
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG,"response:"+response);
                try {
                    Type listType = new TypeToken<ArrayList<PersonData>>() {
                    }.getType();
                    ArrayList<PersonData> listUser = new Gson().fromJson(response, listType);
                    int serverSiteId = ServerSiteDBHelper.getServerSiteId(root_link);
                    OrganizationUserDBHelper.addTreeUser(listUser, serverSiteId);
                    getAllUser(callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
//                if(callBack !=null)
            }
        });
    }

    public void getAllUser(final OnGetAllOfUser callBack) {
        String url = root_link + Urls.URL_GET_ALL_USER;
        //String url = "http://test1.suziptong.com/UI/MobileMail3/MobileDataService.asmx/GetAllOfUsers";
        Map<String, String> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", Locale.getDefault().getLanguage().toUpperCase());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes() + "");
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
//                Log.e(TAG, "response:" + response);
                try {
                    Type listType = new TypeToken<ArrayList<PersonData>>() {
                    }.getType();
                    ArrayList<PersonData> listUser = new Gson().fromJson(response, listType);
                    if (callBack != null)
                        callBack.onGetAllOfUserSuccess(listUser);
                    int serverSiteId = ServerSiteDBHelper.getServerSiteId(root_link);
                    OrganizationUserDBHelper.addTreeUser(listUser, serverSiteId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (callBack != null)
                    callBack.onGetAllOfUserFail(error);
            }
        });
    }


    JSONObject object;

    public void GetUser(int userNo) {
        String url = root_link + Urls.URL_GET_USER;
//        Log.e(TAG, root_link + " GetUser:" + url);
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", CrewScheduleApplication.getInstance().getmPrefs().getaccesstoken());
        params.put("languageCode", LanguageUtils.getPhoneLanguage());
        params.put("timeZoneOffset", TimeUtils.getTimezoneOffsetInMinutes());
        params.put("userNo", userNo);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                HomeActivity.myInfor = response;
            }

            @Override
            public void onFailure(ErrorDto error) {
                Log.e(TAG, "onFailure");
            }
        });
    }
}
