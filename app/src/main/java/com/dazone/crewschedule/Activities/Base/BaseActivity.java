package com.dazone.crewschedule.Activities.Base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;
import com.dazone.crewschedule.Utils.Prefs;
import com.dazone.crewschedule.Utils.Utils;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    public static BaseActivity Instance = null;
    public Prefs prefs;
    private ProgressDialog mProgressDialog;
    protected String server_site;
    public String all = "", public_c = "", private_c = "", share_c = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        mContext = this;
        Instance = this;
        prefs = CrewScheduleApplication.getInstance().getmPrefs();
        server_site = prefs.getServerSite();
        all = getResources().getString(R.string.string_all_calendar);
        public_c = getResources().getString(R.string.string_public_calendar);
        private_c = getResources().getString(R.string.string_private_calendar);
        share_c = getResources().getString(R.string.string_share_calendar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Instance = this;
    }

    public void showProgressDialog() {
        if (null == mProgressDialog || !mProgressDialog.isShowing()) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle(getString(R.string.loading_title));
            mProgressDialog.setMessage(getString(R.string.loading_content));
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void gotoInfor(Class cls) {
        Intent newIntent = new Intent(this, cls);
        startActivity(newIntent);
    }

    public void callActivity(Class cls) {
        Intent newIntent = new Intent(this, cls);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(newIntent);
    }

    public void startNewActivity(Class cls) {
        Intent newIntent = new Intent(this, cls);
        newIntent.putExtra("count_id", 1);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(newIntent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public AlertDialog customDialog = null;

    public void showAlertDialog(String title, String content, String positiveTitle,
                                String negativeTitle, View.OnClickListener positiveListener,
                                View.OnClickListener negativeListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customView = LayoutInflater.from(this).inflate(R.layout.dialog_alert, null);
        builder.setView(customView);

        Button btnCancel = (Button) customView.findViewById(R.id.add_btn_cancel);
        Button btnAdd = (Button) customView.findViewById(R.id.add_btn_log_time);
        final TextView textView = (TextView) customView.findViewById(R.id.textView);
        final TextView contentTextView = (TextView) customView.findViewById(R.id.contentTextView);
        btnCancel.setText(getText(R.string.string_ok));
        if (TextUtils.isEmpty(title)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(title);
        }
        if (TextUtils.isEmpty(content)) {
            contentTextView.setVisibility(View.GONE);
        } else {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(content);
        }

        if (TextUtils.isEmpty(positiveTitle)) {
            btnAdd.setVisibility(View.GONE);
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            btnAdd.setText(positiveTitle);
            btnAdd.setOnClickListener(positiveListener);
        }
        if (TextUtils.isEmpty(negativeTitle)) {
            btnCancel.setVisibility(View.GONE);
        } else {
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(negativeTitle);
            btnCancel.setOnClickListener(negativeListener);
        }
        customDialog = builder.create();
        customDialog.show();
    }

    public void showAlertDialog(String content, String positiveTitle,
                                String negativeTitle, View.OnClickListener positiveListener) {
        showAlertDialog(getString(R.string.app_name), content, positiveTitle, negativeTitle,
                positiveListener, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();

                    }
                });
    }

    public void showAlertDialog(String title, Spannable content, String positiveTitle,
                                String negativeTitle, View.OnClickListener positiveListener,
                                View.OnClickListener negativeListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customView = LayoutInflater.from(this).inflate(R.layout.dialog_alert, null);
        builder.setView(customView);

        Button btnCancel = (Button) customView.findViewById(R.id.add_btn_cancel);
        Button btnAdd = (Button) customView.findViewById(R.id.add_btn_log_time);
        final TextView textView = (TextView) customView.findViewById(R.id.textView);
        final TextView contentTextView = (TextView) customView.findViewById(R.id.contentTextView);
        btnCancel.setText(getText(R.string.string_ok));
        if (TextUtils.isEmpty(title)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(title);
        }
        if (TextUtils.isEmpty(content)) {
            contentTextView.setVisibility(View.GONE);
        } else {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(content);
        }

        if (TextUtils.isEmpty(positiveTitle)) {
            btnAdd.setVisibility(View.GONE);
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            btnAdd.setText(positiveTitle);
            btnAdd.setOnClickListener(positiveListener);
        }
        if (TextUtils.isEmpty(negativeTitle)) {
            btnCancel.setVisibility(View.GONE);
        } else {
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(negativeTitle);
            btnCancel.setOnClickListener(negativeListener);
        }
        customDialog = builder.create();
        customDialog.show();
    }

    public void showNetworkDialog() {
        if (customDialog == null || !customDialog.isShowing()) {
            if (Utils.isWifiEnable()) {
                showAlertDialog(getString(R.string.app_name), getString(R.string.no_connection_error),
                        getString(R.string.string_ok), null, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                                finish();
                            }
                        }, null);
            } else {
                showAlertDialog(getString(R.string.app_name), getString(R.string.no_wifi_error),
                        getString(R.string.turn_wifi_on), getString(R.string.string_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent wireLess = new Intent(
                                        Settings.ACTION_WIFI_SETTINGS);
                                startActivity(wireLess);
                                customDialog.dismiss();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                                finish();

                            }
                        });
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (!isTaskRoot()) {
            super.onBackPressed();
        } else {
            if (mIsExit) {
                super.onBackPressed();
            } else {
                // press 2 times to exit app feature
                this.mIsExit = true;
                String Str = "";
//                Log.e("LL", Locale.getDefault().getLanguage());
                if (Locale.getDefault().getLanguage().equals("vi")) {
                    Str = "Click thêm lần nữa ứng dụng sẽ được đóng";
                } else if (Locale.getDefault().getLanguage().equals("ko")) {
                    Str = "'뒤로'버튼을 한번 더 누르시면 종료됩니다.";
                } else {
                    Str = "Press back again to quit.";
                }
                Toast.makeText(this, Str, Toast.LENGTH_SHORT).show();
                myHandler.postDelayed(myRunnable, 2000);
            }
        }

    }

    private boolean mIsExit;
    private Handler myHandler = new Handler();
    private Runnable myRunnable = new Runnable() {
        public void run() {
            mIsExit = false;
        }
    };
}

