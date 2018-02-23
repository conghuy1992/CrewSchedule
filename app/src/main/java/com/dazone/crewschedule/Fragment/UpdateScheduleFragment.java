package com.dazone.crewschedule.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dazone.crewschedule.Activities.OrganizationActivity;
import com.dazone.crewschedule.Adapters.ScheduleDivAdapter;
import com.dazone.crewschedule.AddNewViewHolder.Row_select_calendar_add_new_view_holder;
import com.dazone.crewschedule.AddNewViewHolder.Row_select_date_add_new_view_holder;
import com.dazone.crewschedule.AddNewViewHolder.Row_select_schedule_div_add_new_view_holder;
import com.dazone.crewschedule.AddNewViewHolder.Row_select_time_add_new_view_holder;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Database.OrganizationUserDBHelper;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.PersonData;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.Dtos.ScheduleShareDto;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.HTTPs.JSONParser;
import com.dazone.crewschedule.Interfaces.GetMonthScheduleCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.DailyGridViewHolder;
import com.dazone.crewschedule.Utils.CrewScheduleApplication;
import com.dazone.crewschedule.Utils.LanguageUtils;
import com.dazone.crewschedule.Utils.ListUtils;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nononsenseapps.filepicker.FilePickerActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateScheduleFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static int scheduleNo = 0, RegUserNo = 0;
    public static String UserName = "", PositionName = "", RegDate = "", Title = "", Content = "", list_json = "",
            DayContent = "", TimeContent = "", RepeatContent = "", fromday = "", today = "", share_info = "";
    String TAG = "UpdateScheduleFragment";
    LinearLayout custom_toolbar_bound;
    public static String startTime = "", endTime = "", startHour = "", start_h = "", endHour = "", end_h = "", content_ed = "", title_ed = "", share = "",
            IsLunar = "0", IsLastDay = "0", IsFiveWeek = "0", AttachedFileList = "", ContactUsers = "", Place = "", DelFileList = "";
    int DivisionNo = 1, IsHoliday = 0, RepeatType = 0, RepeatCount = 0, RepeatMonth = 0, RepeatWeek = 0, RepeatDay = 0, RepeatDOWs = 0, NotiTimeType = 0, RepeatDay_month = 0;
    public static int CalendarNo = 0, CalendarType = 1;
    private ProgressDialog progressDialog;
    private boolean IsAllDay = false, IsNotiNote = false, IsNotiMail = false, IsNotiSMS = false, IsNotiPopup = false;
    private double Lati = 0.0, Long = 0.0;
    private String END_TIME = "2100-12-31";
    private int RepeatCount_day = 1, RepeatCount_week = 1, RepeatCount_month = 1, if_holiday = 0, if_holiday_y = 0, RepeatMonth_choose = 0;
    int getDayOfWeek = 0, getWeekOfMonth = 0, NotiTimeType_count = 1;
    String getDOW = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar(R.layout.toolbar_add_new_schedule);
        TextView txttitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        txttitle.setText("Update");
//        Log.e(TAG, "scheduleNo:" + scheduleNo);
//        Log.e(TAG, "RegUserNo:" + RegUserNo);
//        Log.e(TAG, "UserName:" + UserName);
//        Log.e(TAG, "PositionName:" + PositionName);
//        Log.e(TAG, "RegDate:" + RegDate);
//        Log.e(TAG, "Title:" + Title);
//        Log.e(TAG, "Content:" + Content);
//        Log.e(TAG, "DayContent:" + DayContent);
//        Log.e(TAG, "TimeContent:" + TimeContent);
//        Log.e(TAG, "RepeatContent:" + RepeatContent);
//        Log.e(TAG, "startHour:" + startHour);
//        Log.e(TAG, "endHour:" + endHour);

        custom_toolbar_bound = (LinearLayout) toolbar.findViewById(R.id.custom_toolbar_bound);
        custom_toolbar_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content_edt.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "you need input content", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    setDataPost();
                    content_ed = content_edt.getText().toString().trim();
                    title_ed = title_edt.getText().toString().trim();


                    startTime = startTime.replace("-", "");
                    endTime = endTime.replace("-", "");
                    if (infiniteloop) {
                        endTime = "21001231";
                    } else {
                        endTime = endTime.replace("-", "");
                    }
                    AttachedFileList = tv_attach_title.getText().toString();
                    if (AttachedFileList.equals("Attach"))
                        AttachedFileList = "";
                    if (resultList == null) {

                        share = "";
                    } else {
                        if (resultList.size() == 0) {

                            share = "";
                        } else {

                            share = "";
                            for (PersonData data : resultList) {
                                if (data.getmEmail().length() != 0)
                                    share += data.getmEmail() + ";";
                            }
                            Log.e(TAG, share);
                            share = share.substring(0, share.length() - 1);
                        }
                    }
                    HttpRequest.getInstance().UpdateSchedule(UpdateScheduleFragment.this, progressDialog, ContactUsers, AttachedFileList, IsLunar, IsLastDay,
                            IsFiveWeek, IsHoliday, DivisionNo, CalendarNo, startTime, endTime, start_h, end_h, content_ed, share, RepeatType, RepeatCount,
                            RepeatMonth, RepeatWeek, RepeatDay, RepeatDOWs, Place, NotiTimeType, IsAllDay,
                            IsNotiNote, IsNotiMail, IsNotiSMS, IsNotiPopup, CalendarType, content_ed, Lati, Long, scheduleNo, DelFileList);

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_new_schedule, container, false);
        bindData(v);
        return v;
    }

    Row_select_date_add_new_view_holder dayHolder;
    Row_select_time_add_new_view_holder timeHolder;
    Row_select_calendar_add_new_view_holder calendarHolder;
    Row_select_schedule_div_add_new_view_holder scheduleDivHolder;

    CheckBox lunar_cb, all_day_long_cb, alarm_cb1, alarm_cb2, alarm_cb3, alarm_cb4;
    EditText content_edt, title_edt;
    ImageButton imv_repeat, imv_alarm, add_btn_org, add_btn_attach;
    TextView tv_alarm, tv_share_title, tv_attach_title;
    View addNewTime, addNewShare, addNewAttach;
    LinearLayout schedule_div_layout;
    ArrayList<PersonData> resultList;
    String content[] = {"업무", "PT", "출장", "외근", "교육", "휴가"};
    String colors[] = {"#91ed91", "#ff6bb5", "#faebd7", "#f0e68d", "#bfbfbf", "#87d0fa"};
    ScheduleDivAdapter adapter_div;

    private void bindData(View v) {
        View addNewDay = v.findViewById(R.id.select_day);
        addNewTime = v.findViewById(R.id.select_hour);
        View addNewCalendar = v.findViewById(R.id.select_calendar_add_new_schedule);
        View addNewScheduleDive = v.findViewById(R.id.schedule_div_new_schedule);

        final TextView title = (TextView) addNewScheduleDive.findViewById(R.id.title);
        final ImageView color_imv = (ImageView) addNewScheduleDive.findViewById(R.id.color_imv);
        title.setText(content[0]);
        color_imv.setBackgroundColor(Color.parseColor(colors[0]));
        adapter_div = new ScheduleDivAdapter(getActivity(), content, colors);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(getResources().getString(R.string.string_schedule_div));
        dialog.setContentView(R.layout.schedule_div_layout);

        dialog.setCanceledOnTouchOutside(false);
        ListView list_schedule_div = (ListView) dialog.findViewById(R.id.list_schedule_div);
        list_schedule_div.setAdapter(adapter_div);
        list_schedule_div.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                title.setText(content[position]);
                color_imv.setBackgroundColor(Color.parseColor(colors[position]));
                DivisionNo = position + 1;
//                Log.e(TAG, "" + DivisionNo);
                dialog.dismiss();
            }
        });
        final LinearLayout schedule_div_layout = (LinearLayout) addNewScheduleDive.findViewById(R.id.schedule_div_layout);
        schedule_div_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        addNewShare = v.findViewById(R.id.share_new_schedule);

        addNewAttach = v.findViewById(R.id.attach_new_schedule);

        dayHolder = new Row_select_date_add_new_view_holder(addNewDay);
        timeHolder = new Row_select_time_add_new_view_holder(addNewTime);
        calendarHolder = new Row_select_calendar_add_new_view_holder(addNewCalendar);
        scheduleDivHolder = new Row_select_schedule_div_add_new_view_holder(addNewScheduleDive);

        lunar_cb = (CheckBox) v.findViewById(R.id.lunar_cb);

        all_day_long_cb = (CheckBox) v.findViewById(R.id.all_day_long_cb);

        imv_repeat = (ImageButton) v.findViewById(R.id.imv_repeat);

        content_edt = (EditText) v.findViewById(R.id.content_edt);
        content_edt.setText("" + Content);
        title_edt = (EditText) v.findViewById(R.id.title_edt);

        alarm_cb1 = (CheckBox) v.findViewById(R.id.cb1);
        alarm_cb2 = (CheckBox) v.findViewById(R.id.cb2);
        alarm_cb3 = (CheckBox) v.findViewById(R.id.cb3);
        alarm_cb4 = (CheckBox) v.findViewById(R.id.cb4);

        arr_alarm = new ArrayList<String>();
        arr_alarm.add("Right Time");
        arr_alarm.add("5 Minutes Ago");
        arr_alarm.add("10 Minutes Ago");
        arr_alarm.add("15 Minutes Ago");
        arr_alarm.add("30 Minutes Ago");
        arr_alarm.add("1 Hour Ago");
        arr_alarm.add("2 Hours Ago");
        arr_alarm.add("3 Hours Ago");
        arr_alarm.add("12 Hours Ago");
        arr_alarm.add("1 Days Before");
        arr_alarm.add("2 Days Before");
        alarm_spinner = (Spinner) v.findViewById(R.id.alarm_spinner);
        alarm_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr_alarm);
        alarm_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alarm_spinner.setAdapter(alarm_adapter);
        alarm_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NotiTimeType_count = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tv_alarm = (TextView) v.findViewById(R.id.tv_alarm);
        imv_alarm = (ImageButton) v.findViewById(R.id.imv_alarm);

        tv_share_title = (TextView) addNewShare.findViewById(R.id.title);
        tv_share_title.setText("" + share_info);
        tv_share_title.setSelected(true);

        add_btn_org = (ImageButton) addNewShare.findViewById(R.id.add_btn_org);

        tv_attach_title = (TextView) addNewAttach.findViewById(R.id.title);
        tv_attach_title.setSelected(true);
        add_btn_attach = (ImageButton) addNewAttach.findViewById(R.id.add_btn_attach);

        initClick();
        loadTabs(v);
        if (list_json.length() != 0) {
            resultList = new ArrayList<PersonData>();
            String serverLink = HttpRequest.getInstance().root_link;
            ArrayList<PersonData> alldata = OrganizationUserDBHelper.getAllOfOrganization(serverLink);
            Type listType = new TypeToken<List<ScheduleShareDto>>() {
            }.getType();
            ArrayList<ScheduleShareDto> Sharers = new Gson().fromJson(list_json, listType);
            for (ScheduleShareDto dto : Sharers) {
                for (int i = 0; i < alldata.size(); i++) {
                    PersonData pd = alldata.get(i);
                    if (dto.getNo() == pd.getUserNo()) {
                        PersonData ob = new PersonData();
                        ob.setUserNo(pd.getUserNo());
                        ob.setFullName(pd.getFullName());
                        ob.setDepartNo(pd.getDepartNo());
                        ob.setmEmail(pd.getmEmail());
                        resultList.add(ob);
                        break;
                    }
                }
            }
        }
    }

    private void initClick() {
        lunar_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IsLunar = "1";
                } else {
                    IsLunar = "0";
                }
            }
        });
        all_day_long_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addNewTime.setVisibility(View.GONE);
                } else {
                    addNewTime.setVisibility(View.VISIBLE);
                }
            }
        });
        add_btn_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrganizationActivity(resultList);
            }
        });
        addNewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrganizationActivity(resultList);
//                Log.e(TAG,"CLICK");
            }
        });
        add_btn_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FilePickerActivity.class);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
                startActivityForResult(i, Statics.FILE_PICKER_SELECT);
            }
        });
        addNewAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FilePickerActivity.class);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
                startActivityForResult(i, Statics.FILE_PICKER_SELECT);
            }
        });
    }

    private void startOrganizationActivity(ArrayList<PersonData> selectedList) {
        Intent i = new Intent(getActivity(), OrganizationActivity.class);
        i.putParcelableArrayListExtra(Statics.BUNDLE_LIST_PERSON, selectedList);
        i.putExtra(Statics.BUNDLE_ORG_DISPLAY_SELECTED_ONLY, false);
        startActivityForResult(i, Statics.ORGANIZATION_TO_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case Statics.FILE_PICKER_SELECT:
                        if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                            // For JellyBean and above
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                ClipData clip = data.getClipData();

                                if (clip != null) {
                                    for (int i = 0; i < clip.getItemCount(); i++) {
                                        Uri uri = clip.getItemAt(i).getUri();
                                        GetFile(uri);
                                        // Do something with the URI
                                        tv_attach_title.setText("" + uri);
                                    }
                                }
                                // For Ice Cream Sandwich
                            } else {
                                ArrayList<String> paths = data.getStringArrayListExtra
                                        (FilePickerActivity.EXTRA_PATHS);

                                if (paths != null) {
                                    for (String path : paths) {
                                        Uri uri = Uri.parse(path);
                                        // Do something with the URI
                                        GetFile(uri);
                                        tv_attach_title.setText("" + uri);
                                    }
                                }
                            }

                        } else {
                            Uri uri = data.getData();
                            GetFile(uri);
                            tv_attach_title.setText("" + uri);
                            // Do something with the URI
                        }
                        break;
                    case Statics.ORGANIZATION_TO_ACTIVITY:
                        resultList = data.getExtras().getParcelableArrayList(Statics.BUNDLE_LIST_PERSON);
                        handleSelectedOrganizationResult();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSelectedOrganizationResult() {
        tv_share_title.setText(ListUtils.getListName(resultList));
    }

    public void GetFile(Uri uri) {
//        String Path = Util.getPathFromURI(uri, getActivity());
//        String fileName = Util.getFileName(uri, getActivity());
//        String fileType = fileName.substring(fileName.lastIndexOf("."));
//        long fileSize = Util.getFileSize(Path);
//        AttachData attachData = new AttachData(fileName, Path, fileSize, fileType, System.currentTimeMillis());
//        mailBoxData.getListAttachMent().add(attachData);
//        final AdapterMailCreateAttachLinear itemView = new AdapterMailCreateAttachLinear(getActivity(), attachData);
//        itemView.setTag(attachData);
//        itemView.getImageButtonDelete().setVisibility(View.VISIBLE);
//        itemView.getImageButtonDelete().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mailBoxData.getListAttachMent().remove(itemView.getTag());
//                linearAttach.removeView(itemView);
//                isUpload--;
//                Util.printLogs("Còn mấy file: " + mailBoxData.getListAttachMent().size());
//            }
//        });
//        itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                return false;
//            }
//        });
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MailHelper.OpenFile(getActivity(), (AttachData) itemView.getTag());
//            }
//        });
//        linearAttach.addView(itemView);
//        new Upload().execute(attachData.getPath(), attachData.getFileName());
    }

    TabHost tab;
    int size_tab = 13, CurTab = 0;
    RadioButton month_rd1, month_rd2, month_rd3, y_rd1, y_rd2, y_rd3, special_rd1, special_rd2, special_rd3;
    LinearLayout daily_layout_fromday, week_layout_fromday, month_layout_fromday, year_layout_fromday,
            daily_layout_today, week_layout_today, month_layout_today, year_layout_today,
            daily_layout_fromhour, week_layout_fromhour, month_layout_fromhour, year_layout_fromhour,
            daily_layout_tohour, week_layout_tohour, month_layout_tohour, year_layout_tohour,
            special_layout_fromday, special_layout_today, special_layout_fromhour, special_layout_tohour, layoutholiday,
            daily_layouttime, week_layouttime, month_layouttime, year_layouttime, special_layouttime;
    CheckBox daily_infiniteloop, week_infiniteloop, month_infiniteloop, year_infiniteloop,
            daily_alldaylong, week_alldaylong, month_alldaylong, year_alldaylong, special_alldaylong,
            week_mon, week_tue, week_wed, week_thu, week_fri, week_sat, week_sun, year_lunar;
    View cl_daily, cl_week, cl_month, cl_year, cl_specialday, cl_specialhour, special_startday, special_starthour,
            daily_startday, week_startday, month_startday, year_startday,
            daily_starthour, week_starthour, month_starthour, year_starthour;
    TextView daily_fromday, week_fromday, month_fromday, year_fromday,
            daily_today, week_today, month_today, year_today,
            daily_fromhour, week_fromhour, month_fromhour, year_fromhour,
            daily_tohour, week_tohour, month_tohour, year_tohour,
            specialday_fromday, specialday_today, specialday_fromhour, specialday_tohour;
    Spinner daily_spinner, week_spinner, month_spinner, spinner2, spinnerHoliday, y_spinnerHoliday, alarm_spinner;
    ArrayList<String> arr_daily, arr_week, arr_month, arr_month_day, arr_holiday, arr_holiday_y, arr_alarm;
    ArrayAdapter<String> adapter_daily, adapter_week, adapter_month, adapter_month_day, adapter_holiday, adapter_holiday_y, alarm_adapter;

    public void loadTabs(View v) {
        if (RepeatContent.length() == 0) {
            CurTab = 0;
        } else {
            if (RepeatContent.contains("Days")) CurTab = 1;
            if (RepeatContent.contains("Weeks")) CurTab = 2;
            if (RepeatContent.contains("Months")) CurTab = 3;
            if (RepeatContent.contains("Year")) CurTab = 4;
        }

        tab = (TabHost) v.findViewById(R.id.tabhost_day);
        tab.setup();
        TabHost.TabSpec spec;

        spec = tab.newTabSpec("t1");
        spec.setContent(R.id.linearLayout);
        spec.setIndicator(getResources().getString(R.string.special_day));
        tab.addTab(spec);

        spec = tab.newTabSpec("t2");
        spec.setContent(R.id.linearLayout2);
        spec.setIndicator(getResources().getString(R.string.daily_day));
        tab.addTab(spec);

        spec = tab.newTabSpec("t3");
        spec.setContent(R.id.linearLayout3);
        spec.setIndicator(getResources().getString(R.string.weekly_day));
        tab.addTab(spec);

        spec = tab.newTabSpec("t4");
        spec.setContent(R.id.linearLayout4);
        spec.setIndicator(getResources().getString(R.string.monthly_day));
        tab.addTab(spec);

        spec = tab.newTabSpec("t5");
        spec.setContent(R.id.linearLayout5);
        spec.setIndicator(getResources().getString(R.string.annualy_day));
        tab.addTab(spec);
        tab.setCurrentTab(CurTab);
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String arg0) {
                for (int i = 0; i < tab.getTabWidget().getChildCount(); i++) {
                    tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.border_tabhost2); // unselected
                }
                tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundResource(R.drawable.border_tabhost); // selected
            }
        });
        int totalTabs = tab.getTabWidget().getChildCount();
        for (int i = 0; i < totalTabs; i++) {
            if (i == CurTab) {
                tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.border_tabhost);
            } else {
                tab.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.border_tabhost2);
            }
        }

        CustomTitle(0);
        CustomTitle(1);
        CustomTitle(2);
        CustomTitle(3);
        CustomTitle(4);

        arr_daily = new ArrayList<String>();
        arr_week = new ArrayList<String>();
        arr_month = new ArrayList<String>();
        arr_month_day = new ArrayList<String>();
        arr_holiday = new ArrayList<String>();
        arr_holiday_y = new ArrayList<String>();
        arr_daily.add("Daily");
        for (int i = 2; i < 32; i++) {
            arr_daily.add("" + i + " Day");
        }
        arr_daily.add("100 Day");
        arr_daily.add("1000 Day");

        arr_week.add("Weekly");
        for (int i = 2; i < 54; i++) {
            arr_week.add("" + i + " Weeks");
        }
        arr_month.add("Monthly");
        for (int i = 2; i < 12; i++) {
            arr_month.add("" + i + " Months");
        }
        for (int i = 1; i < 32; i++) {
            if (i == 1) {
                arr_month_day.add("" + i + "st");
            } else if (i == 2) {
                arr_month_day.add("" + i + "nd");
            } else if (i == 3) {
                arr_month_day.add("" + i + "rd");
            } else if (i == 31) {
                arr_month_day.add("" + i + "st");
            } else {
                arr_month_day.add("" + i + "th");
            }
        }
        arr_month_day.add("LastDay");
        arr_holiday.add("Right Day");
        arr_holiday.add("Previous Day");
        arr_holiday.add("Next Day");

        arr_holiday_y.add("Right Day");
        arr_holiday_y.add("Previous Day");
        arr_holiday_y.add("Next Day");
        cl_specialday = v.findViewById(R.id.cl_specialday);
        special_startday = cl_specialday.findViewById(R.id.startday);
        special_starthour = cl_specialday.findViewById(R.id.starthour);

        cl_daily = v.findViewById(R.id.cl_daily);
        cl_week = v.findViewById(R.id.cl_week);
        cl_month = v.findViewById(R.id.cl_month);
        cl_year = v.findViewById(R.id.cl_year);

        daily_startday = cl_daily.findViewById(R.id.startday);
        week_startday = cl_week.findViewById(R.id.startday);
        month_startday = cl_month.findViewById(R.id.startday);
        year_startday = cl_year.findViewById(R.id.startday);

        adapter_daily = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr_daily);
        daily_spinner = (Spinner) cl_daily.findViewById(R.id.spinner);
        adapter_daily.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daily_spinner.setAdapter(adapter_daily);
        daily_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 31) {
                    RepeatCount_day = position + 1;
                } else if (position == 31) {
                    RepeatCount_day = 100;
                } else {
                    RepeatCount_day = 1000;
                }
//                Log.e(TAG, "RepeatCount: " + RepeatCount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter_week = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr_week);
        week_spinner = (Spinner) cl_week.findViewById(R.id.spinner);
        adapter_week.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        week_spinner.setAdapter(adapter_week);
        week_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RepeatCount_week = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2 = (Spinner) cl_month.findViewById(R.id.spinner2);
        adapter_month_day = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr_month_day);
        adapter_month_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter_month_day);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RepeatDay_month = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter_month = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr_month);
        month_spinner = (Spinner) cl_month.findViewById(R.id.spinner);
        adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(adapter_month);
        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RepeatCount_month = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerHoliday = (Spinner) cl_month.findViewById(R.id.spinnerHoliday);
        adapter_holiday = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr_holiday);
        adapter_holiday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHoliday.setAdapter(adapter_holiday);
        spinnerHoliday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if_holiday = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        y_spinnerHoliday = (Spinner) cl_year.findViewById(R.id.spinnerHoliday);
        adapter_holiday_y = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arr_holiday_y);
        adapter_holiday_y.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        y_spinnerHoliday.setAdapter(adapter_holiday_y);
        y_spinnerHoliday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if_holiday_y = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        daily_starthour = cl_daily.findViewById(R.id.starthour);
        week_starthour = cl_week.findViewById(R.id.starthour);
        month_starthour = cl_month.findViewById(R.id.starthour);
        year_starthour = cl_year.findViewById(R.id.starthour);

        special_layout_fromday = (LinearLayout) special_startday.findViewById(R.id.layout_fromday);
        special_layout_today = (LinearLayout) special_startday.findViewById(R.id.layout_today);
        special_layout_fromhour = (LinearLayout) special_starthour.findViewById(R.id.layout_fromhour);
        special_layout_tohour = (LinearLayout) special_starthour.findViewById(R.id.layout_tohour);

        daily_layout_fromday = (LinearLayout) daily_startday.findViewById(R.id.layout_fromday);
        week_layout_fromday = (LinearLayout) week_startday.findViewById(R.id.layout_fromday);
        month_layout_fromday = (LinearLayout) month_startday.findViewById(R.id.layout_fromday);
        year_layout_fromday = (LinearLayout) year_startday.findViewById(R.id.layout_fromday);

        daily_layout_today = (LinearLayout) daily_startday.findViewById(R.id.layout_today);
        week_layout_today = (LinearLayout) week_startday.findViewById(R.id.layout_today);
        month_layout_today = (LinearLayout) month_startday.findViewById(R.id.layout_today);
        year_layout_today = (LinearLayout) year_startday.findViewById(R.id.layout_today);
        setCheckLoop(daily_layout_today);
        setCheckLoop(week_layout_today);
        setCheckLoop(month_layout_today);
        setCheckLoop(year_layout_today);

        daily_layout_fromhour = (LinearLayout) daily_starthour.findViewById(R.id.layout_fromhour);
        week_layout_fromhour = (LinearLayout) week_starthour.findViewById(R.id.layout_fromhour);
        month_layout_fromhour = (LinearLayout) month_starthour.findViewById(R.id.layout_fromhour);
        year_layout_fromhour = (LinearLayout) year_starthour.findViewById(R.id.layout_fromhour);

        daily_layout_tohour = (LinearLayout) daily_starthour.findViewById(R.id.layout_tohour);
        week_layout_tohour = (LinearLayout) week_starthour.findViewById(R.id.layout_tohour);
        month_layout_tohour = (LinearLayout) month_starthour.findViewById(R.id.layout_tohour);
        year_layout_tohour = (LinearLayout) year_starthour.findViewById(R.id.layout_tohour);
        layoutholiday = (LinearLayout) cl_month.findViewById(R.id.layoutholiday);

        daily_fromday = (TextView) daily_startday.findViewById(R.id.fromday);
        week_fromday = (TextView) week_startday.findViewById(R.id.fromday);
        month_fromday = (TextView) month_startday.findViewById(R.id.fromday);
        year_fromday = (TextView) year_startday.findViewById(R.id.fromday);
        daily_today = (TextView) daily_startday.findViewById(R.id.today);
        week_today = (TextView) week_startday.findViewById(R.id.today);
        month_today = (TextView) month_startday.findViewById(R.id.today);
        year_today = (TextView) year_startday.findViewById(R.id.today);

        specialday_fromday = (TextView) special_startday.findViewById(R.id.fromday);
        specialday_today = (TextView) special_startday.findViewById(R.id.today);
        specialday_fromhour = (TextView) special_starthour.findViewById(R.id.fromhour);
        specialday_tohour = (TextView) special_starthour.findViewById(R.id.tohour);

        final Calendar c = Calendar.getInstance();
        int mnam = Integer.parseInt(fromday.split("-")[0]);
        int mthang = Integer.parseInt(fromday.split("-")[1]);
        int mngay = Integer.parseInt(fromday.split("-")[2]);
        c.set(mnam, mthang, mngay);
        mHour = Integer.parseInt(startHour.split(":")[0]);
        mMinute = Integer.parseInt(startHour.split(":")[1]);
        int DOF = c.get(Calendar.DAY_OF_WEEK);
        month_rd1 = (RadioButton) cl_month.findViewById(R.id.rd1);
        month_rd2 = (RadioButton) cl_month.findViewById(R.id.rd2);
        month_rd3 = (RadioButton) cl_month.findViewById(R.id.rd3);

        y_rd1 = (RadioButton) cl_year.findViewById(R.id.rd1);
        y_rd2 = (RadioButton) cl_year.findViewById(R.id.rd2);
        y_rd3 = (RadioButton) cl_year.findViewById(R.id.rd3);

        special_rd1 = (RadioButton) cl_specialday.findViewById(R.id.rd1);
        special_rd2 = (RadioButton) cl_specialday.findViewById(R.id.rd2);
        special_rd3 = (RadioButton) cl_specialday.findViewById(R.id.rd3);

        month_rd1.setOnClickListener(this);
        month_rd2.setOnClickListener(this);
        month_rd3.setOnClickListener(this);
        y_rd1.setOnClickListener(this);
        y_rd2.setOnClickListener(this);
        y_rd3.setOnClickListener(this);
        specialday_fromday.setText("" + fromday);
        specialday_today.setText("" + today);
        daily_fromday.setText("" + fromday);
        week_fromday.setText("" + fromday);
        month_fromday.setText("" + fromday);
        year_fromday.setText("" + fromday);
        daily_today.setText("" + today);
        week_today.setText("" + today);
        month_today.setText("" + today);
        year_today.setText("" + today);
        startTime = fromday;
        endTime = today;
        Calendar now = Calendar.getInstance();
        now.set(mnam, mthang - 1, mngay);
        getDayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        if (getDayOfWeek == 1) getDOW = "Sun";
        if (getDayOfWeek == 2) getDOW = "Mon";
        if (getDayOfWeek == 3) getDOW = "Tue";
        if (getDayOfWeek == 4) getDOW = "Wed";
        if (getDayOfWeek == 5) getDOW = "Thu";
        if (getDayOfWeek == 6) getDOW = "Fri";
        if (getDayOfWeek == 7) getDOW = "Sat";
        getWeekOfMonth = now.get(Calendar.WEEK_OF_MONTH);
        y_rd1.setText("" + getThang(mthang) + " " + getNgay(mngay));
        month_rd3.setText("Last " + getDOW + " Month");
        month_rd2.setText("" + getWeekOfMonth + " " + getDOW + " Month");
        y_rd3.setText("Last " + getDOW + " Month");
        y_rd2.setText("" + getWeekOfMonth + " " + getDOW + " Month");
        RepeatDay_month = mngay - 1;
        spinner2.setSelection(RepeatDay_month);

        daily_fromhour = (TextView) daily_starthour.findViewById(R.id.fromhour);
        week_fromhour = (TextView) week_starthour.findViewById(R.id.fromhour);
        month_fromhour = (TextView) month_starthour.findViewById(R.id.fromhour);
        year_fromhour = (TextView) year_starthour.findViewById(R.id.fromhour);

        daily_tohour = (TextView) daily_starthour.findViewById(R.id.tohour);
        week_tohour = (TextView) week_starthour.findViewById(R.id.tohour);
        month_tohour = (TextView) month_starthour.findViewById(R.id.tohour);
        year_tohour = (TextView) year_starthour.findViewById(R.id.tohour);

        String _hour = startHour;
        start_h = _hour + ":00";
        end_h = endHour + ":00";
        daily_fromhour.setText(_hour);
        week_fromhour.setText(_hour);
        month_fromhour.setText(_hour);
        year_fromhour.setText(_hour);
        daily_tohour.setText(endHour);
        week_tohour.setText(endHour);
        month_tohour.setText(endHour);
        year_tohour.setText(endHour);

        specialday_fromhour.setText("" + _hour);
        specialday_tohour.setText("" + _hour);

        special_layouttime = (LinearLayout) cl_specialday.findViewById(R.id.layouttime);
        daily_layouttime = (LinearLayout) cl_daily.findViewById(R.id.layouttime);
        week_layouttime = (LinearLayout) cl_week.findViewById(R.id.layouttime);
        month_layouttime = (LinearLayout) cl_month.findViewById(R.id.layouttime);
        year_layouttime = (LinearLayout) cl_year.findViewById(R.id.layouttime);

        daily_infiniteloop = (CheckBox) cl_daily.findViewById(R.id.infiniteloop);
        week_infiniteloop = (CheckBox) cl_week.findViewById(R.id.infiniteloop);
        month_infiniteloop = (CheckBox) cl_month.findViewById(R.id.infiniteloop);
        year_infiniteloop = (CheckBox) cl_year.findViewById(R.id.infiniteloop);
        year_lunar = (CheckBox) cl_year.findViewById(R.id.lunar);
        special_alldaylong = (CheckBox) cl_specialday.findViewById(R.id.alldaylong);
        daily_alldaylong = (CheckBox) cl_daily.findViewById(R.id.alldaylong);
        week_alldaylong = (CheckBox) cl_week.findViewById(R.id.alldaylong);
        month_alldaylong = (CheckBox) cl_month.findViewById(R.id.alldaylong);
        year_alldaylong = (CheckBox) cl_year.findViewById(R.id.alldaylong);

        week_mon = (CheckBox) cl_week.findViewById(R.id.mon);
        week_tue = (CheckBox) cl_week.findViewById(R.id.tue);
        week_wed = (CheckBox) cl_week.findViewById(R.id.wed);
        week_thu = (CheckBox) cl_week.findViewById(R.id.thu);
        week_fri = (CheckBox) cl_week.findViewById(R.id.fri);
        week_sat = (CheckBox) cl_week.findViewById(R.id.sat);
        week_sun = (CheckBox) cl_week.findViewById(R.id.sun);
        week_mon.setChecked(false);
        week_tue.setChecked(false);
        week_wed.setChecked(false);
        week_thu.setChecked(false);
        week_fri.setChecked(false);
        week_sat.setChecked(false);
        week_sun.setChecked(false);
        if (DOF == 1) {
            mDayOfWeek = "Sun";
        } else if (DOF == 2) {
            mDayOfWeek = "Mon";
        } else if (DOF == 3) {
            mDayOfWeek = "Tue";
        } else if (DOF == 4) {
            mDayOfWeek = "Wed";
        } else if (DOF == 5) {
            mDayOfWeek = "Thu";
        } else if (DOF == 6) {
            mDayOfWeek = "Fri";
        } else if (DOF == 7) {
            mDayOfWeek = "Sat";
        }
        if (RepeatContent.length() > 0) {
            if (RepeatContent.contains("Days")) {
                int index = Integer.parseInt(RepeatContent.split(" ")[0].trim());
                RepeatCount_day = index;
                daily_spinner.setSelection(index - 1);
            }
            if (RepeatContent.contains("Weeks")) {
                int index = Integer.parseInt(RepeatContent.split(" ")[1].trim());
                RepeatCount_week = index;
                week_spinner.setSelection(index - 1);
                if (RepeatContent.contains("Sun")) week_sun.setChecked(true);
                if (RepeatContent.contains("Mon")) week_mon.setChecked(true);
                if (RepeatContent.contains("Tue")) week_tue.setChecked(true);
                if (RepeatContent.contains("Wed")) week_wed.setChecked(true);
                if (RepeatContent.contains("Thu")) week_thu.setChecked(true);
                if (RepeatContent.contains("Fri")) week_fri.setChecked(true);
                if (RepeatContent.contains("Sat")) week_sat.setChecked(true);
            }
            if (RepeatContent.contains("Months")) {
                int index = Integer.parseInt(RepeatContent.split(" ")[1].trim().replace("{0}", ""));
                RepeatCount_month = index;
                month_spinner.setSelection(index - 1);
                if (RepeatContent.contains("Daily")) {
                    month_rd1.setChecked(true);
                    month_rd2.setChecked(false);
                    month_rd3.setChecked(false);
                    int day = Integer.parseInt(RepeatContent.split(" ")[2].trim().replace("Daily", ""));
                    RepeatDay_month = day;
                    spinner2.setSelection(day - 1);
                } else if (RepeatContent.contains("Last")) {
                    month_rd1.setChecked(false);
                    month_rd2.setChecked(false);
                    month_rd3.setChecked(true);
                } else {
                    month_rd1.setChecked(false);
                    month_rd2.setChecked(true);
                    month_rd3.setChecked(false);
                }
            }
            if (RepeatContent.contains("Year")) {
                if (RepeatContent.contains("Last")) {
                    y_rd1.setChecked(false);
                    y_rd2.setChecked(false);
                    y_rd3.setChecked(true);
                } else {
                    if (RepeatContent.contains("Daily") && RepeatContent.contains("in Week")) {
                        y_rd1.setChecked(false);
                        y_rd2.setChecked(true);
                        y_rd3.setChecked(false);
                    } else {
                        y_rd1.setChecked(true);
                        y_rd2.setChecked(false);
                        y_rd3.setChecked(false);
                    }
                }
            }

        }
        daily_infiniteloop.setOnCheckedChangeListener(this);
        week_infiniteloop.setOnCheckedChangeListener(this);
        month_infiniteloop.setOnCheckedChangeListener(this);
        year_infiniteloop.setOnCheckedChangeListener(this);

        special_alldaylong.setOnCheckedChangeListener(this);
        daily_alldaylong.setOnCheckedChangeListener(this);
        week_alldaylong.setOnCheckedChangeListener(this);
        month_alldaylong.setOnCheckedChangeListener(this);
        year_alldaylong.setOnCheckedChangeListener(this);

        week_mon.setOnCheckedChangeListener(this);
        week_tue.setOnCheckedChangeListener(this);
        week_wed.setOnCheckedChangeListener(this);
        week_thu.setOnCheckedChangeListener(this);
        week_fri.setOnCheckedChangeListener(this);
        week_sat.setOnCheckedChangeListener(this);
        week_sun.setOnCheckedChangeListener(this);

        special_layout_fromday.setOnClickListener(this);
        special_layout_today.setOnClickListener(this);
        special_layout_fromhour.setOnClickListener(this);
        special_layout_tohour.setOnClickListener(this);

        daily_layout_fromday.setOnClickListener(this);
        week_layout_fromday.setOnClickListener(this);
        month_layout_fromday.setOnClickListener(this);
        year_layout_fromday.setOnClickListener(this);

        daily_layout_fromhour.setOnClickListener(this);
        week_layout_fromhour.setOnClickListener(this);
        month_layout_fromhour.setOnClickListener(this);
        year_layout_fromhour.setOnClickListener(this);

        daily_layout_tohour.setOnClickListener(this);
        week_layout_tohour.setOnClickListener(this);
        month_layout_tohour.setOnClickListener(this);
        year_layout_tohour.setOnClickListener(this);

        daily_layout_today.setOnClickListener(this);
        week_layout_today.setOnClickListener(this);
        month_layout_today.setOnClickListener(this);
        year_layout_today.setOnClickListener(this);

    }

    public void CustomTitle(int index) {
        TextView x = (TextView) tab.getTabWidget().getChildAt(index).findViewById(android.R.id.title);
        x.setAllCaps(false);
        x.setTextSize(size_tab);
    }

    private int mYear = 0, mMonth = 0, mDay = 0, mHour, mMinute;
    private String mDayOfWeek = "";

    public void ShowTimerDialogEnd(final TextView txtTime, final TextView tv2) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay <= Integer.parseInt(tv2.getText().toString().trim().split(":")[0])) {
                            if (hourOfDay < Integer.parseInt(tv2.getText().toString().trim().split(":")[0])) {
                                txtTime.setText("" + tv2.getText().toString());
                                end_h = start_h;
                            } else {
                                if (minute <= Integer.parseInt(tv2.getText().toString().trim().split(":")[1])) {
                                    txtTime.setText("" + tv2.getText().toString());
                                    end_h = start_h;
                                } else {
                                    String h = "", m = "";
                                    if (hourOfDay < 10) {
                                        h = "0" + hourOfDay;
                                    } else {
                                        h = "" + hourOfDay;
                                    }
                                    if (minute < 10) {
                                        m = "0" + minute;
                                    } else {
                                        m = "" + minute;
                                    }
                                    txtTime.setText("" + h + ":" + m);
                                    end_h = "" + h + ":" + m + ":00";
                                    mHour = hourOfDay;
                                    mMinute = minute;
                                }
                            }
                        } else {
                            String h = "", m = "";
                            if (hourOfDay < 10) {
                                h = "0" + hourOfDay;
                            } else {
                                h = "" + hourOfDay;
                            }
                            if (minute < 10) {
                                m = "0" + minute;
                            } else {
                                m = "" + minute;
                            }
                            txtTime.setText("" + h + ":" + m);
                            end_h = "" + h + ":" + m + ":00";
                            mHour = hourOfDay;
                            mMinute = minute;
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void ShowTimerDialog(final TextView txtTime, final TextView tv2) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String h = "", m = "";
                        if (hourOfDay < 10) {
                            h = "0" + hourOfDay;
                        } else {
                            h = "" + hourOfDay;
                        }
                        if (minute < 10) {
                            m = "0" + minute;
                        } else {
                            m = "" + minute;
                        }
                        txtTime.setText("" + h + ":" + m);
                        start_h = "" + h + ":" + m + ":00";
                        mHour = hourOfDay;
                        mMinute = minute;
                        if (hourOfDay >= Integer.parseInt(tv2.getText().toString().trim().split(":")[0])) {
                            if (hourOfDay > Integer.parseInt(tv2.getText().toString().trim().split(":")[0])) {
                                tv2.setText("" + h + ":" + m);
                                end_h = start_h;
                            } else {
                                if (minute >= Integer.parseInt(tv2.getText().toString().trim().split(":")[1]))
                                    tv2.setText("" + h + ":" + m);
                                end_h = start_h;
                            }
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void ShowDateDialogEnd(final TextView tv, final TextView tv2) {
        final Calendar c = Calendar.getInstance();
        if (mYear == 0) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String day = "", month = "";
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = "" + dayOfMonth;
                        }
                        monthOfYear += 1;
                        if (monthOfYear < 10) {
                            month = "0" + monthOfYear;
                        } else {
                            month = "" + monthOfYear;
                        }
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = sdf.parse(tv2.getText().toString().trim());
                            Date date2 = sdf.parse("" + year + "-" + month + "-" + day);
                            if (date1.before(date2)) {
                                tv.setText("" + year + "-" + month + "-" + day);
                                endTime = "" + year + "-" + month + "-" + day;
                                mYear = year;
                                mMonth = monthOfYear - 1;
                                mDay = dayOfMonth;
                            } else {
                                tv.setText("" + tv2.getText().toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void ShowDateDialogYear(final TextView tv, final TextView tv2) {
        final Calendar c = Calendar.getInstance();
        if (mYear == 0) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String day = "", month = "";
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = "" + dayOfMonth;
                        }
                        monthOfYear += 1;
                        if (monthOfYear < 10) {
                            month = "0" + monthOfYear;
                        } else {
                            month = "" + monthOfYear;
                        }
                        tv.setText("" + year + "-" + month + "-" + day);
                        startTime = "" + year + "-" + month + "-" + day;
                        y_rd1.setText("" + getThang(monthOfYear) + " " + getNgay(dayOfMonth));
                        mYear = year;
                        mMonth = monthOfYear - 1;
                        mDay = dayOfMonth;
//                        Log.e(TAG, "startTime: " + startTime);
                        Calendar now = Calendar.getInstance();
                        now.set(year, monthOfYear - 1, dayOfMonth);
                        getDayOfWeek = now.get(Calendar.DAY_OF_WEEK);
                        if (getDayOfWeek == 1) getDOW = "Sun";
                        if (getDayOfWeek == 2) getDOW = "Mon";
                        if (getDayOfWeek == 3) getDOW = "Tue";
                        if (getDayOfWeek == 4) getDOW = "Wed";
                        if (getDayOfWeek == 5) getDOW = "Thu";
                        if (getDayOfWeek == 6) getDOW = "Fri";
                        if (getDayOfWeek == 7) getDOW = "Sat";
                        getWeekOfMonth = now.get(Calendar.WEEK_OF_MONTH);
                        y_rd3.setText("Last " + getDOW + " Month");
                        y_rd2.setText("" + getWeekOfMonth + " " + getDOW + " Month");
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = sdf.parse(tv.getText().toString().trim());
                            Date date2 = sdf.parse(tv2.getText().toString().trim());
                            if (date1.after(date2)) {
                                tv2.setText("" + year + "-" + month + "-" + day);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void ShowDateDialogMonth(final TextView tv, final TextView tv2) {
        final Calendar c = Calendar.getInstance();
        if (mYear == 0) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String day = "", month = "";
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = "" + dayOfMonth;
                        }
                        monthOfYear += 1;
                        if (monthOfYear < 10) {
                            month = "0" + monthOfYear;
                        } else {
                            month = "" + monthOfYear;
                        }
                        tv.setText("" + year + "-" + month + "-" + day);
                        startTime = "" + year + "-" + month + "-" + day;
                        mYear = year;
                        mMonth = monthOfYear - 1;
                        mDay = dayOfMonth;
//                        Log.e(TAG, "startTime: " + startTime);
                        Calendar now = Calendar.getInstance();
                        now.set(year, monthOfYear - 1, dayOfMonth);
                        getDayOfWeek = now.get(Calendar.DAY_OF_WEEK);
                        if (getDayOfWeek == 1) getDOW = "Sun";
                        if (getDayOfWeek == 2) getDOW = "Mon";
                        if (getDayOfWeek == 3) getDOW = "Tue";
                        if (getDayOfWeek == 4) getDOW = "Wed";
                        if (getDayOfWeek == 5) getDOW = "Thu";
                        if (getDayOfWeek == 6) getDOW = "Fri";
                        if (getDayOfWeek == 7) getDOW = "Sat";
                        getWeekOfMonth = now.get(Calendar.WEEK_OF_MONTH);
                        month_rd3.setText("Last " + getDOW + " Month");
                        month_rd2.setText("" + getWeekOfMonth + " " + getDOW + " Month");
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = sdf.parse(tv.getText().toString().trim());
                            Date date2 = sdf.parse(tv2.getText().toString().trim());
                            if (date1.after(date2)) {
                                tv2.setText("" + year + "-" + month + "-" + day);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void ShowDateDialog(final TextView tv, final TextView tv2) {
        final Calendar c = Calendar.getInstance();
        if (mYear == 0) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String day = "", month = "";
                        if (dayOfMonth < 10) {
                            day = "0" + dayOfMonth;
                        } else {
                            day = "" + dayOfMonth;
                        }
                        monthOfYear += 1;
                        if (monthOfYear < 10) {
                            month = "0" + monthOfYear;
                        } else {
                            month = "" + monthOfYear;
                        }
                        tv.setText("" + year + "-" + month + "-" + day);
                        startTime = "" + year + "-" + month + "-" + day;
                        mYear = year;
                        mMonth = monthOfYear - 1;
                        mDay = dayOfMonth;
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date1 = sdf.parse(tv.getText().toString().trim());
                            Date date2 = sdf.parse(tv2.getText().toString().trim());
                            if (date1.after(date2)) {
                                tv2.setText("" + year + "-" + month + "-" + day);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    @Override
    public void onClick(View v) {
        if (v == daily_layout_fromday) {
            ShowDateDialog(daily_fromday, daily_today);
        } else if (v == week_layout_fromday) {
            ShowDateDialog(week_fromday, week_today);
        } else if (v == month_layout_fromday) {
            ShowDateDialogMonth(month_fromday, month_today);
        } else if (v == year_layout_fromday) {
            ShowDateDialogYear(year_fromday, year_today);
        } else if (v == daily_layout_today) {
            ShowDateDialogEnd(daily_today, daily_fromday);
        } else if (v == week_layout_today) {
            ShowDateDialogEnd(week_today, week_fromday);
        } else if (v == month_layout_today) {
            ShowDateDialogEnd(month_today, month_fromday);
        } else if (v == year_layout_today) {
            ShowDateDialogEnd(year_today, year_fromday);
        } else if (v == daily_layout_fromhour) {
            ShowTimerDialog(daily_fromhour, daily_tohour);
        } else if (v == week_layout_fromhour) {
            ShowTimerDialog(week_fromhour, week_tohour);
        } else if (v == month_layout_fromhour) {
            ShowTimerDialog(month_fromhour, month_tohour);
        } else if (v == year_layout_fromhour) {
            ShowTimerDialog(year_fromhour, year_tohour);
        } else if (v == daily_layout_tohour) {
            ShowTimerDialogEnd(daily_tohour, daily_fromhour);
        } else if (v == week_layout_tohour) {
            ShowTimerDialogEnd(week_tohour, week_fromhour);
        } else if (v == month_layout_tohour) {
            ShowTimerDialogEnd(month_tohour, month_fromhour);
        } else if (v == year_layout_tohour) {
            ShowTimerDialogEnd(year_tohour, year_fromhour);
        } else if (v == month_rd1) {
            layoutholiday.setVisibility(View.VISIBLE);
        } else if (v == month_rd2) {
            layoutholiday.setVisibility(View.GONE);
        } else if (v == month_rd3) {
            layoutholiday.setVisibility(View.GONE);
        } else if (v == special_layout_fromday) {
            ShowDateDialog(specialday_fromday, specialday_today);
        } else if (v == special_layout_today) {
            ShowDateDialogEnd(specialday_today, specialday_fromday);
        } else if (v == special_layout_fromhour) {
            ShowTimerDialog(specialday_fromhour, specialday_tohour);
        } else if (v == special_layout_tohour) {
            ShowTimerDialogEnd(specialday_tohour, specialday_fromhour);
        } else if (v == y_rd1) {

        } else if (v == y_rd2) {

        } else if (v == y_rd3) {

        }
    }

    private boolean infiniteloop = false;

    public void setCheckLoop(LinearLayout ln) {
        ln.setEnabled(false);
        ln.setAlpha(0.3f);
    }

    public void setUnCheckLoop(LinearLayout ln) {
        ln.setEnabled(true);
        ln.setAlpha(1);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == daily_infiniteloop) {
            if (isChecked) {
                setCheckLoop(daily_layout_today);
            } else {
                setUnCheckLoop(daily_layout_today);
            }
        } else if (buttonView == week_infiniteloop) {
            if (isChecked) {
                setCheckLoop(week_layout_today);
            } else {
                setUnCheckLoop(week_layout_today);
            }
        } else if (buttonView == month_infiniteloop) {
            if (isChecked) {
                setCheckLoop(month_layout_today);
            } else {
                setUnCheckLoop(month_layout_today);
            }
        } else if (buttonView == year_infiniteloop) {
            if (isChecked) {
                setCheckLoop(year_layout_today);
            } else {
                setUnCheckLoop(year_layout_today);
            }
        } else if (buttonView == special_alldaylong) {
            if (isChecked) {
                special_layouttime.setVisibility(View.GONE);
            } else {
                special_layouttime.setVisibility(View.VISIBLE);
            }
        } else if (buttonView == daily_alldaylong) {
            if (isChecked) {
                daily_layouttime.setVisibility(View.GONE);
            } else {
                daily_layouttime.setVisibility(View.VISIBLE);
            }
        } else if (buttonView == week_alldaylong) {
            if (isChecked) {
                week_layouttime.setVisibility(View.GONE);
            } else {
                week_layouttime.setVisibility(View.VISIBLE);
            }
        } else if (buttonView == month_alldaylong) {
            if (isChecked) {
                month_layouttime.setVisibility(View.GONE);
            } else {
                month_layouttime.setVisibility(View.VISIBLE);
            }
        } else if (buttonView == year_alldaylong) {
            if (isChecked) {
                year_layouttime.setVisibility(View.GONE);
            } else {
                year_layouttime.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setDataPost() {
        if (alarm_cb1.isChecked() || alarm_cb2.isChecked() || alarm_cb3.isChecked() || alarm_cb4.isChecked()) {
            NotiTimeType = NotiTimeType_count;
            if (alarm_cb2.isChecked()) {
                IsNotiMail = true;
            } else {
                IsNotiMail = false;
            }
            if (alarm_cb4.isChecked()) {
                IsNotiPopup = true;
            } else {
                IsNotiPopup = false;
            }
        }

        int index = tab.getCurrentTab();
        if (index == 0) {
            if (special_rd2.isChecked()) {
                IsLunar = "1";
            } else {
                IsLunar = "0";
            }
            RepeatCount = 0;
            RepeatDOWs = 0;
            RepeatType = 0;
            startTime = specialday_fromday.getText().toString();
            endTime = specialday_today.getText().toString();
            start_h = specialday_fromhour.getText().toString();
            end_h = specialday_tohour.getText().toString();
            if (special_alldaylong.isChecked()) {
                start_h = "00:00:00";
                end_h = "00:00:00";
            }
            infiniteloop = false;
            IsAllDay = false;
        } else if (index == 1) {
            RepeatCount = RepeatCount_day;
            RepeatDOWs = 0;
            RepeatType = 1;
            startTime = daily_fromday.getText().toString();
            endTime = daily_today.getText().toString();
            start_h = daily_fromhour.getText().toString();
            end_h = daily_tohour.getText().toString();
            if (daily_alldaylong.isChecked()) {
                start_h = "00:00:00";
                end_h = "00:00:00";
            }
            if (daily_infiniteloop.isChecked()) {
                infiniteloop = true;
            } else {
                infiniteloop = false;
            }
            if (daily_alldaylong.isChecked()) {
                IsAllDay = true;
            } else {
                IsAllDay = false;
            }
        } else if (index == 2) {
            if (week_sun.isChecked()) RepeatDOWs |= 1;
            if (week_mon.isChecked()) RepeatDOWs |= 2;
            if (week_tue.isChecked()) RepeatDOWs |= 4;
            if (week_wed.isChecked()) RepeatDOWs |= 8;
            if (week_thu.isChecked()) RepeatDOWs |= 16;
            if (week_fri.isChecked()) RepeatDOWs |= 32;
            if (week_sat.isChecked()) RepeatDOWs |= 64;
            RepeatCount = RepeatCount_week;
            RepeatType = 2;
            startTime = week_fromday.getText().toString();
            endTime = week_today.getText().toString();
            start_h = week_fromhour.getText().toString();
            end_h = week_tohour.getText().toString();
            if (week_alldaylong.isChecked()) {
                start_h = "00:00:00";
                end_h = "00:00:00";
            }
            if (week_infiniteloop.isChecked()) {
                infiniteloop = true;
            } else {
                infiniteloop = false;
            }
            if (week_alldaylong.isChecked()) {
                IsAllDay = true;
            } else {
                IsAllDay = false;
            }
        } else if (index == 3) {
            RepeatMonth = 0;
            RepeatCount = RepeatCount_month;
            if (month_rd1.isChecked()) {
                RepeatWeek = 0;
                RepeatType = 3;
                RepeatDOWs = 0;
                if (RepeatDay_month > 30) {
                    RepeatDay = 0;
                    IsLastDay = "1";
                } else {
                    RepeatDay = RepeatDay_month;
                }
                if (if_holiday == 0) {
                    IsHoliday = 0;
                } else if (if_holiday == 1) {
                    IsHoliday = -1;
                } else if (if_holiday == 3) {
                    IsHoliday = 1;
                }
            } else if (month_rd2.isChecked()) {
                RepeatType = 4;
                RepeatWeek = getWeekOfMonth;
                RepeatDOWs = (int) Math.pow(2, getDayOfWeek - 1);
            } else if (month_rd3.isChecked()) {
                RepeatType = 5;
                RepeatWeek = 0;
                Log.e(TAG, "getDayOfWeek:" + getDayOfWeek);
                RepeatDOWs = (int) Math.pow(2, getDayOfWeek - 1);
            }
            startTime = month_fromday.getText().toString();
            endTime = month_today.getText().toString();
            start_h = month_fromhour.getText().toString();
            end_h = month_tohour.getText().toString();
            if (month_alldaylong.isChecked()) {
                start_h = "00:00:00";
                end_h = "00:00:00";
            }
            if (month_infiniteloop.isChecked()) {
                infiniteloop = true;
            } else {
                infiniteloop = false;
            }
            if (month_alldaylong.isChecked()) {
                IsAllDay = true;
            } else {
                IsAllDay = false;
            }
        } else if (index == 4) {
            String arr[] = year_fromday.getText().toString().split("-");
            RepeatMonth = Integer.parseInt(arr[1]);
            if (y_rd1.isChecked()) {
                RepeatType = 6;
                if (year_lunar.isChecked()) {
                    IsLunar = "1";
                } else {
                    IsLunar = "0";
                }
//                String arr[] = year_fromday.getText().toString().split("-");
//                RepeatMonth = Integer.parseInt(arr[1]);
                RepeatDay = Integer.parseInt(arr[2]);
            } else if (y_rd2.isChecked()) {
                Calendar now = Calendar.getInstance();
                now.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[2]));
                int getDayOfWeek = now.get(Calendar.DAY_OF_WEEK);
                RepeatWeek = now.get(Calendar.WEEK_OF_MONTH);
                RepeatDOWs = (int) Math.pow(2, getDayOfWeek - 1);
                RepeatType = 7;
            } else if (y_rd3.isChecked()) {
                Calendar now = Calendar.getInstance();
                now.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) - 1, Integer.parseInt(arr[2]));
                int getDayOfWeek = now.get(Calendar.DAY_OF_WEEK);
                RepeatDOWs = (int) Math.pow(2, getDayOfWeek - 1);
                RepeatType = 8;
            }
            RepeatCount = 0;
            startTime = year_fromday.getText().toString();
            endTime = year_today.getText().toString();
            start_h = year_fromhour.getText().toString();
            end_h = year_tohour.getText().toString();
            if (year_alldaylong.isChecked()) {
                start_h = "00:00:00";
                end_h = "00:00:00";
            }
            if (year_infiniteloop.isChecked()) {
                infiniteloop = true;
            } else {
                infiniteloop = false;
            }
            if (year_alldaylong.isChecked()) {
                IsAllDay = true;
            } else {
                IsAllDay = false;
            }
        }
    }

    public static int convertDpToPixels(int dp, Context c) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
        return px;
    }

    public String getNgay(int date) {
        String ngay = "";
        for (int i = 1; i < 32; i++) {
            if (i == date) {
                if (i == 1) ngay = "" + i + "st";
                else if (i == 2) ngay = "" + i + "nd";
                else if (i == 3) ngay = "" + i + "rd";
                else if (i == 31) ngay = "" + i + "st";
                else ngay = "" + i + "th";
                break;
            }
        }
        return ngay;
    }

    public String getThang(int monthOfYear) {
        String thang = "";
        if (monthOfYear == 1) thang = "January";
        else if (monthOfYear == 2) thang = "February";
        else if (monthOfYear == 3) thang = "March";
        else if (monthOfYear == 4) thang = "April";
        else if (monthOfYear == 5) thang = "May";
        else if (monthOfYear == 6) thang = "June";
        else if (monthOfYear == 7) thang = "July";
        else if (monthOfYear == 8) thang = "August";
        else if (monthOfYear == 9) thang = "September";
        else if (monthOfYear == 0) thang = "October";
        else if (monthOfYear == 11) thang = "November";
        else if (monthOfYear == 12) thang = "December";
        return thang;
    }
}
