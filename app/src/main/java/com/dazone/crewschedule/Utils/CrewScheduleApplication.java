package com.dazone.crewschedule.Utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dazone.crewschedule.Constant.Statics;

import java.util.HashMap;

public class CrewScheduleApplication extends Application {
    private static final String TAG = "EmcorApplication";

    private static CrewScheduleApplication _instance;
    private RequestQueue mRequestQueue;

    private static Prefs mPrefs;

    private HashMap<Object, Object> _data = new HashMap<Object, Object>();

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        init();


    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static synchronized CrewScheduleApplication getInstance() {
        return _instance;
    }


    private static void init() {
        mPrefs = new Prefs();
    }

    public Prefs getmPrefs() {
        return mPrefs;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setRetryPolicy(new DefaultRetryPolicy(Statics.REQUEST_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(Statics.REQUEST_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    public void clearCache()
    {
        getRequestQueue().getCache().clear();
    }

    public void putData(Object key, Object value) {
        try {
            _data.put(key, value);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            System.gc();
        }

    }

    public void removeData(Object key) {
        if (getData(key) != null)
            _data.remove(key);
    }

    public Object getData(Object key) {
        return _data.get(key);
    }
}
