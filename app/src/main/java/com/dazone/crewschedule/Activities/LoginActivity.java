package com.dazone.crewschedule.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.BuildConfig;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Database.OrganizationUserDBHelper;
import com.dazone.crewschedule.Database.ServerSiteDBHelper;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.HTTPs.HttpOauthRequest;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Interfaces.BaseHTTPCallBack;
import com.dazone.crewschedule.Interfaces.OAUTHUrls;
import com.dazone.crewschedule.Interfaces.OnCheckDevice;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends BaseActivity implements BaseHTTPCallBack, OnCheckDevice {
    String TAG = "LoginActivity";
    private Button login_btn;
    RelativeLayout logo;
    boolean firstLogin = true;
    boolean hasIntro = false;
    private EditText login_edt_username, login_edt_passsword;
    private AutoCompleteTextView login_edt_server;
    String username, password;
    ScrollView scl_login;
    protected int activityNumber = 0;
    private boolean firstStart = false;
    TextView forgot_pass, help_login, have_no_id_login;
    private final int MY_PERMISSIONS_REQUEST_CODE = 1;

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, MY_PERMISSIONS_REQUEST_CODE);
    }

    private void startApplication() {
        attachKeyboardListeners();
        Bundle bundle = getIntent().getExtras();
        logo = (RelativeLayout) findViewById(R.id.logo);
        if (bundle != null && bundle.getInt("count_id") != 0) {
            activityNumber = bundle.getInt("count_id");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE) {
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
                break;
            }
        }
        if (isGranted) {
            startApplication();
            if (Utils.isNetworkAvailable()) {
                Thread thread = new Thread(new UpdateRunnable());
                thread.setDaemon(true);
                thread.start();
            } else {
                firstChecking();
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (checkPermissions()) {
            startApplication();
        } else {
            setPermissions();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (Utils.isNetworkAvailable() && Utils.isWifiEnable()) {

//        if (checkPermissions()) {
//            if (Utils.isNetworkAvailable()) {
//                Thread thread = new Thread(new UpdateRunnable());
//                thread.setDaemon(true);
//                thread.start();
//            } else {
//                Log.e(TAG, "onResume");
//                firstChecking();
//            }
//        }
        firstChecking();
    }

    private void firstChecking() {
        if (firstLogin) {
            if (Utils.isNetworkAvailable()) {
//                if (prefs.getintrocount() < 1) {
////                    HttpOauthRequest.getInstance().checkPhoneToken(this);
//                    prefs.putintrocount(prefs.getintrocount() + 1);
//                } else {
//                    doLogin();
//                }
                doLogin();
            } else {
                showNetworkDialog();
            }
        }
    }

    private void doLogin() {
        if (Utils.checkStringValue(prefs.getaccesstoken()) && !prefs.getBooleanValue(Statics.PREFS_KEY_SESSION_ERROR, false)) {
            HttpOauthRequest.getInstance().checLogin(this);
        } else {
            prefs.putBooleanValue(Statics.PREFS_KEY_SESSION_ERROR, false);
            logo.setVisibility(View.GONE);
            firstLogin = false;
            init();
        }
    }

    private void init() {
        login_btn = (Button) findViewById(R.id.login_btn_login);
        login_edt_username = (EditText) findViewById(R.id.login_edt_username);
        login_edt_passsword = (EditText) findViewById(R.id.login_edt_passsword);
        login_edt_server = (AutoCompleteTextView) findViewById(R.id.login_edt_server);
        scl_login = (ScrollView) findViewById(R.id.scl_login);
        forgot_pass = (TextView) findViewById(R.id.forgot_pass);
        help_login = (TextView) findViewById(R.id.help_login);
        have_no_id_login = (TextView) findViewById(R.id.have_no_id_login);
//        forgot_pass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callActivity(ForgotPassActivity.class);
//            }
//        });
//        help_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callActivity(HelpActivity.class);
//            }
//        });
//        have_no_id_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callActivity(HaveNoIDActivity.class);
//            }
//        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = login_edt_username.getText().toString();
                password = login_edt_passsword.getText().toString();
                server_site = login_edt_server.getText().toString();
                if (TextUtils.isEmpty(checkStringValue(server_site, username, password))) {
                    server_site = getServerSite(server_site);
                    String company_domain = server_site;

                    String temp_server_site = server_site;
                    if (company_domain.startsWith("http")) {
                        company_domain.replace("http://", "");
                    } else {
                        server_site = "http://" + server_site;
                    }
                    if (temp_server_site.contains(".bizsw.co.kr")) {
                        temp_server_site = "http://www.bizsw.co.kr:8080";
                    } else {
                        if (temp_server_site.contains("crewcloud")) {
                            temp_server_site = OAUTHUrls.URL_DEFAULT_API;
                        }
                    }
                    showProgressDialog();
                    HttpOauthRequest.getInstance().login(LoginActivity.this, username, password, company_domain, temp_server_site);

                } else {
                    showAlertDialog(getString(R.string.app_name), checkStringValue(server_site, username, password), getString(R.string.string_ok), null, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customDialog.dismiss();
                        }
                    }, null);
                }
            }
        });

    }

    private String checkStringValue(String server_site, String username, String password) {
        String result = "";
        if (TextUtils.isEmpty(server_site)) {
            result += getString(R.string.string_server_site);
        }

        if (TextUtils.isEmpty(username)) {
            if (TextUtils.isEmpty(result)) {
                result += getString(R.string.login_username);
            } else {
                result += ", " + getString(R.string.login_username);
            }
        }
        if (TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(result)) {
                result += getString(R.string.login_password);
            } else {
                result += ", " + getString(R.string.login_password);
            }
        }
        if (TextUtils.isEmpty(result)) {
            return result;
        } else {
            return result += " " + getString(R.string.login_empty_input);
        }
    }

    private String getServerSite(String server_site) {
        String[] domains = server_site.split("[.]");
        String subDomain = "crewcloud";
        if (server_site.equalsIgnoreCase("vn.bizsw.co.kr")) {
            return "vn.bizsw.co.kr:8080";
        }

        if (domains.length <= 1 || server_site.contains("crewcloud")) {
            return domains[0] + ".crewcloud.net";
        } else {
            return server_site;
        }
    }

    @Override
    public void onHTTPSuccess() {
        if (!TextUtils.isEmpty(server_site)) {
            server_site.replace("http://", "");
            prefs.putServerSite(server_site);
            prefs.putUserName(username);
            ServerSiteDBHelper.addServerSite(server_site);
        }
//        HttpOauthRequest.getInstance().insertPhoneToken();
        loginSuccess();
    }

    private void loginSuccess() {
        dismissProgressDialog();
        callActivity(HomeActivity.class);
        finish();

    }

    @Override
    public void onDeviceSuccess() {
        doLogin();
    }

    @Override
    public void onHTTPFail(ErrorDto errorDto) {
        if (firstLogin) {
            dismissProgressDialog();
            firstLogin = false;
            logo.setVisibility(View.GONE);
            init();
        } else {
            dismissProgressDialog();
            String error_msg = errorDto.message;
            if (TextUtils.isEmpty(error_msg)) {
                error_msg = getString(R.string.connection_falsed);
            }
            showSaveDialog(error_msg);
        }
    }

    private void showSaveDialog(String message) {
        showAlertDialog(getString(R.string.app_name), message, getString(R.string.string_ok), null,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseActivity.Instance.customDialog.dismiss();
                    }
                }, null);
    }

    View v;
    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int heightDiff = rootLayout.getRootView().getHeight() - rootLayout.getHeight();
            int contentViewTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

            LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(LoginActivity.this);

            if (heightDiff <= 100) {
                onHideKeyboard();

                v = getCurrentFocus();
                Intent intent = new Intent("KeyboardWillHide");
                broadcastManager.sendBroadcast(intent);
            } else {
                int keyboardHeight = heightDiff - contentViewTop;
                onShowKeyboard();
                v = getCurrentFocus();
                Intent intent = new Intent("KeyboardWillShow");
                intent.putExtra("KeyboardHeight", keyboardHeight);
                broadcastManager.sendBroadcast(intent);
            }
        }
    };

    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;

    protected void onShowKeyboard() {
        if (!hasScroll) {
            if (scl_login != null) {
                scl_login.post(new Runnable() {

                    @Override
                    public void run() {
                        scl_login.scrollTo(0, Utils.getDimenInPx(R.dimen.scroll_height_login));
                        if (v != null) {
                            v.requestFocus();
                        }
                    }
                });
            }
            hasScroll = true;
        }
    }

    boolean hasScroll = false;

    protected void onHideKeyboard() {
        hasScroll = false;
    }

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }
        rootLayout = (ViewGroup) findViewById(R.id.root_login);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
        keyboardListenersAttached = true;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void onDestroy() {
        super.onDestroy();
        if (keyboardListenersAttached) {
            try {
                rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(keyboardLayoutListener);
            } catch (NoSuchMethodError x) {
                rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
            }
        }
    }

    private static final int ACTIVITY_HANDLER_NEXT_ACTIVITY = 1111;
    private static final int ACTIVITY_HANDLER_START_UPDATE = 1112;

    private class UpdateRunnable implements Runnable {
        @Override
        public void run() {
            try {
                URL txtUrl = new URL(OAUTHUrls.CHECK_VERSION_LINK + "CrewSchedule.txt");
                HttpURLConnection urlConnection = (HttpURLConnection) txtUrl.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String serverVersion = bufferedReader.readLine();
                inputStream.close();
                Utils.printLogs("serverVersion: " + serverVersion);
                String appVersion = BuildConfig.VERSION_NAME;

                if (serverVersion.equals(appVersion)) {

                    mActivityHandler.sendEmptyMessageDelayed(ACTIVITY_HANDLER_NEXT_ACTIVITY, 1);
                } else {
                    mActivityHandler.sendEmptyMessage(ACTIVITY_HANDLER_START_UPDATE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class ActivityHandler extends Handler {
        private final WeakReference<LoginActivity> mWeakActivity;

        public ActivityHandler(LoginActivity activity) {
            mWeakActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final LoginActivity activity = mWeakActivity.get();

            if (activity != null) {
                if (msg.what == ACTIVITY_HANDLER_NEXT_ACTIVITY) {
//                    if (activity.unInstallForOldPackage()) {
//                        return;
//                    }

//                    new WebClientAsyncTask2(activity).execute();
                    activity.firstChecking();
                } else if (msg.what == ACTIVITY_HANDLER_START_UPDATE) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(R.string.string_update_content);
                    builder.setPositiveButton(R.string.string_update, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new WebClientAsyncTask(activity).execute();
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        }
    }

    private final ActivityHandler mActivityHandler = new ActivityHandler(this);

    // ----------------------------------------------------------------------------------------------

    private static class WebClientAsyncTask extends AsyncTask<Void, Void, Void> {
        private final WeakReference<LoginActivity> mWeakActivity;
        private ProgressDialog mProgressDialog = null;

        public WebClientAsyncTask(LoginActivity activity) {
            mWeakActivity = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            LoginActivity activity = mWeakActivity.get();

            if (activity != null) {
                mProgressDialog = new ProgressDialog(activity);
                mProgressDialog.setMessage(activity.getString(R.string.wating_app_download));
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            BufferedInputStream bufferedInputStream = null;
            FileOutputStream fileOutputStream = null;

            try {
                Activity activity = mWeakActivity.get();
                URL apkUrl = new URL(OAUTHUrls.DOWNLOAD_LINK + "CrewSchedule.apk");
                urlConnection = (HttpURLConnection) apkUrl.openConnection();
                inputStream = urlConnection.getInputStream();
                bufferedInputStream = new BufferedInputStream(inputStream);

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/CrewSchedule.apk";
                fileOutputStream = new FileOutputStream(filePath);

                byte[] buffer = new byte[4096];
                int readCount;

                while (true) {
                    readCount = bufferedInputStream.read(buffer);
                    if (readCount == -1) {
                        break;
                    }

                    fileOutputStream.write(buffer, 0, readCount);
                    fileOutputStream.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (urlConnection != null) {
                    try {
                        urlConnection.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            LoginActivity activity = mWeakActivity.get();

            if (activity != null) {
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/CrewSchedule.apk";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
                activity.startActivity(intent);
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

}
