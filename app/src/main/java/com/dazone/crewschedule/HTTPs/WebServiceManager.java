package com.dazone.crewschedule.HTTPs;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Activities.LoginActivity;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Interfaces.OAUTHUrls;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;
import com.dazone.crewschedule.Utils.Prefs;
import com.dazone.crewschedule.Utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WebServiceManager<T> {

    private Map<String, String> mHeaders;

    private Request.Priority mPriority;

    public WebServiceManager() {
    }

    WebServiceManager(Map<String, String> headers, Request.Priority priority) {
        mHeaders = headers;
        mPriority = priority;
    }

    public void doJsonObjectRequest(int requestMethod, final String url, final JSONObject bodyParam, final RequestListener<String> listener) {

        Utils.printLogs("url : " + url);
        Utils.printLogs("data : "+ bodyParam.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, url, bodyParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Utils.printLogs("result : "+ response.toString());
//                    if(url.contains(OAUTHUrls.URL_GET_LOGIN)||url.contains(OAUTHUrls.URL_CHECK_DEVICE_TOKEN)
//                            ||url.contains(OAUTHUrls.URL_CHECK_SESSION)||url.contains(OAUTHUrls.URL_INSERT_PHONE_TOKEN)
//                            ||url.contains(OAUTHUrls.URL_LOG_OUT))
//                    {
                        response = new JSONObject(response.getString("d"));

//                    }
                    int isSuccess = response.getInt("success");
                    if (isSuccess ==1) {
                        listener.onSuccess(response.getString("data"));
                    } else {
                        ErrorDto errorDto = new Gson().fromJson(response.getString("error"), ErrorDto.class);
                        if (errorDto == null) {

                            errorDto = new ErrorDto();
                            errorDto.message = Utils.getString(R.string.no_network_error);
                        } else
                        {
                            if (errorDto.code ==0 && !url.contains(OAUTHUrls.URL_CHECK_DEVICE_TOKEN) && !url.contains(OAUTHUrls.URL_CHECK_SESSION)
                                    &&!url.contains(OAUTHUrls.URL_INSERT_PHONE_TOKEN))
                            {
                                new Prefs().putBooleanValue(Statics.PREFS_KEY_SESSION_ERROR,true);
                                CrewScheduleApplication.getInstance().getmPrefs().clearLogin();
                                BaseActivity.Instance.startNewActivity(LoginActivity.class);
                            }
                        }

                        listener.onFailure(errorDto);
                    }

                } catch (JSONException e) {

                    ErrorDto errorDto = new ErrorDto();
                    errorDto.message = Utils.getString(R.string.no_network_error);
                    listener.onFailure(errorDto);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorDto errorDto = new ErrorDto();
                if (null != error) {
                    listener.onFailure(errorDto);
                }

                if (null != error && null != error.networkResponse) {
                    Utils.printLogs("error.networkResponse. : " + error.networkResponse);
                }
                if (null != error && null != error.networkResponse) {
                    Utils.printLogs("error.networkResponse.statusCode : "+ error.networkResponse.statusCode);
                }
                if (null != error && null != error.networkResponse
                        && error.networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    errorDto.unAuthentication = true;
                    listener.onFailure(errorDto);
                }else if ((null != error && null != error.networkResponse)
                        && (error.networkResponse.statusCode == 500||error.networkResponse.statusCode == 405)) {
                    listener.onFailure(errorDto);
                }  else {
                    errorDto.message = Utils.getString(R.string.no_network_error);
                    listener.onFailure(errorDto);
                }
            }
        });
        CrewScheduleApplication.getInstance().addToRequestQueue(jsonObjectRequest, url);
    }
    public void doJsonObjectRequestScheduler(int requestMethod, final String url, final JSONObject bodyParam, final RequestListener<String> listener) {

        Utils.printLogs("url : " + url);
        Utils.printLogs("data : "+ bodyParam.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, url, bodyParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Utils.printLogs("result : "+ response.toString());
                    response = new JSONObject(response.getString("d"));
                    boolean isSuccess = response.getBoolean("success");
                    if (isSuccess) {
                        listener.onSuccess(response.getString("data"));
                    } else {
                        ErrorDto errorDto = new Gson().fromJson(response.getString("error"), ErrorDto.class);
                        if (errorDto == null) {

                            errorDto = new ErrorDto();
                            errorDto.message = Utils.getString(R.string.no_network_error);
                        }

                        listener.onFailure(errorDto);
                    }

                } catch (JSONException e) {

                    ErrorDto errorDto = new ErrorDto();
                    errorDto.message = Utils.getString(R.string.no_network_error);
                    listener.onFailure(errorDto);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorDto errorDto = new ErrorDto();
                if (null != error) {
                    listener.onFailure(errorDto);
                }

                if (null != error && null != error.networkResponse) {
                    Utils.printLogs("error.networkResponse. : " + error.networkResponse);
                }
                if (null != error && null != error.networkResponse) {
                    Utils.printLogs("error.networkResponse.statusCode : "+ error.networkResponse.statusCode);
                }
                if (null != error && null != error.networkResponse
                        && error.networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    errorDto.unAuthentication = true;
                    listener.onFailure(errorDto);
                }else if ((null != error && null != error.networkResponse)
                        && (error.networkResponse.statusCode == 500||error.networkResponse.statusCode == 405)) {
                    listener.onFailure(errorDto);
                }  else {
                    errorDto.message = Utils.getString(R.string.no_network_error);
                    listener.onFailure(errorDto);
                }
            }
        });
        CrewScheduleApplication.getInstance().addToRequestQueue(jsonObjectRequest, url);
    }
    public void doJsonArrayRequest(int requestMethod, String url, JSONObject bodyParam
            ,final Class<T[]> elementType,final RequestListener<List<T>> listener) {

        Utils.printLogs("url : " + url);
        Utils.printLogs("data : "+ bodyParam.toString());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(requestMethod, url, bodyParam, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Utils.printLogs("array response : "+ response.toString());
                T[] array = new Gson().fromJson(response.toString(), elementType);
                List<T> list = Arrays.asList(array);

                listener.onSuccess(list);

                listener.onSuccess(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ErrorDto errorDto = new ErrorDto();
                if (null != error && null != error.networkResponse
                        && error.networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

                    errorDto.unAuthentication = true;
                    listener.onFailure(errorDto);
                } else {

                    listener.onFailure(null);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return mHeaders != null ? mHeaders : super.getHeaders();
            }

            @Override
            public Priority getPriority() {
                return mPriority != null ? mPriority : super.getPriority();
            }
        };

        CrewScheduleApplication.getInstance().addToRequestQueue(jsonArrayRequest, url);
    }
    public interface RequestListener<T> {
        void onSuccess(T response);

        void onFailure(ErrorDto error);
    }

}
