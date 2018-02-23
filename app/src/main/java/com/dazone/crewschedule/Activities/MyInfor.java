package com.dazone.crewschedule.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.CustomView.FaceOverlayView;
import com.dazone.crewschedule.CustomView.FaceOverlayViewForSizeOther;
import com.dazone.crewschedule.Database.UserDBHelper;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Dtos.UserDto;
import com.dazone.crewschedule.HTTPs.HttpOauthRequest;
import com.dazone.crewschedule.Interfaces.BaseHTTPCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;
import com.dazone.crewschedule.Utils.ImageUtils;
import com.dazone.crewschedule.Utils.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maidinh on 23/3/2016.
 */
public class MyInfor extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "MyInfor";
    private ImageView imglogout, imgback, img_bg;
    private TextView tv_name, tv_personal, tv_email, tv_company, tv_phone;
    private JSONObject object;
    private String CellPhone = "";
    private String MailAddress = "";
    private ImageView avatar_imv;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myinfor);

//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        mToolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_personal = (TextView) findViewById(R.id.tv_personal);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_company = (TextView) findViewById(R.id.tv_company);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        imglogout = (ImageView) findViewById(R.id.imglogout);
        imgback = (ImageView) findViewById(R.id.imgback);
        avatar_imv = (ImageView) findViewById(R.id.avatar_imv);
        img_bg = (ImageView) findViewById(R.id.img_bg);
        imglogout.setOnClickListener(this);
        imgback.setOnClickListener(this);
        UserDto userDto = UserDBHelper.getUser();
        tv_name.setText(userDto.FullName);

        tv_personal.setText(userDto.userID);
        tv_company.setText(userDto.NameCompany);
        ImageUtils.showImage(userDto, avatar_imv);
        ImageUtils.showImage(userDto, img_bg);
        try {
            object = new JSONObject(HomeActivity.myInfor);
            CellPhone = object.optString("CellPhone");
            MailAddress = object.optString("MailAddress");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_email.setText("" + MailAddress);
        tv_phone.setText("" + CellPhone);
    }

    public Prefs prefs;

    @Override
    public void onClick(View v) {
        if (v == imglogout) {

            HttpOauthRequest.logout(new BaseHTTPCallBack() {
                @Override
                public void onHTTPSuccess() {
                    prefs = CrewScheduleApplication.getInstance().getmPrefs();
                    prefs.clearLogin();
                    prefs.putaccesstoken("");
//                    prefs.putBooleanValue(Statics.PREFS_KEY_SESSION_ERROR, true);
                    BaseActivity.Instance.startNewActivity(LoginActivity.class);
                    finish();
                }

                @Override
                public void onHTTPFail(ErrorDto errorDto) {

                }
            });
        } else if (v == imgback) {
            finish();
        }
    }
}
