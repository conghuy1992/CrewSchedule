package com.dazone.crewschedule.RCVViewHolders;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.Activity_finish;
import com.dazone.crewschedule.Activities.AddNewScheduleActivity;
import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Activities.Base.BaseDrawerActivity;
import com.dazone.crewschedule.Activities.HomeActivity;
import com.dazone.crewschedule.Adapters.DrawerMenuAdapter;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.DrawerDto;
import com.dazone.crewschedule.Dtos.MainFragmentDto;
import com.dazone.crewschedule.Fragment.BaseMonthPagerFragment;
import com.dazone.crewschedule.Fragment.MainPageFragment;
import com.dazone.crewschedule.Fragment.MonthlyFragment;
import com.dazone.crewschedule.Interfaces.DrawerTrigger;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.TimeUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DrawerMenuViewHolder extends ItemViewHolder<DrawerDto> {
    public static DrawerMenuViewHolder instance = null;
    String TAG = "DrawerMenuViewHolder";
    TextView title, content, tv_bg;
    FrameLayout frcontent;
    CheckBox cb_drawer;
    ImageView icon_drawer;
    View divider;
    LinearLayout child_lnl;
    List<DrawerDto> childList;
    private CheckedCallback callback;

    public DrawerMenuViewHolder(View v) {
        super(v);
    }

    public void setCallback(CheckedCallback callback) {
        this.callback = callback;
    }

    DrawerTrigger trigger;

    public void setTrigger(DrawerTrigger trigger) {
        this.trigger = trigger;
    }

    @Override
    protected void setup(View v) {
        instance = this;
        title = (TextView) v.findViewById(R.id.title);
        tv_bg = (TextView) v.findViewById(R.id.tv_bg);
        content = (TextView) v.findViewById(R.id.content);
        frcontent = (FrameLayout) v.findViewById(R.id.frcontent);
        icon_drawer = (ImageView) v.findViewById(R.id.icon_drawer);
        cb_drawer = (CheckBox) v.findViewById(R.id.cb_drawer);
        divider = v.findViewById(R.id.divider);
        child_lnl = (LinearLayout) v.findViewById(R.id.child_lnl);
    }

    public boolean check_contain(String a) {
        for (int i = 0; i < ARR_TYPE.size(); i++) {
            if (a.equals(ARR_TYPE.get(i)))
                return true;
        }
        return false;
    }

    public boolean check_temp(ArrayList<String> str, String a) {
        for (int i = 0; i < str.size(); i++) {
            if (a.equals(str.get(i)))
                return true;
        }
        return false;
    }

    DrawerDto dto;
    ViewGroup viewParent = null;

    @Override
    public void bindData(DrawerDto dto) {
        this.viewParent = null;
        bindData(dto, null);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getAdapterPosition() == 0 || getAdapterPosition() == 1 || getAdapterPosition() == 2 || getAdapterPosition() == 3) {
                    trigger.onItemSelect(getAdapterPosition());
                }
            }
        });
    }

    private void bindData(DrawerDto dto, ViewGroup parent, int dif) {
        this.viewParent = parent;
        bindData(dto, null);
    }

    public void bindData(final DrawerDto dto, List<DrawerDto> childList) {

        if (childList != null) {
            this.viewParent = null;
        }
        this.dto = dto;
        this.childList = childList;
        title.setText(dto.getName());
        if (!TextUtils.isEmpty(dto.getColor())) {
            title.setBackgroundColor(Color.parseColor("#" + dto.getColor()));
        }
        if (dto.getIconId() != 0) {
            icon_drawer.setVisibility(View.VISIBLE);
            cb_drawer.setVisibility(View.GONE);
            icon_drawer.setImageResource(dto.getIconId());
            divider.setVisibility(View.VISIBLE);
            child_lnl.setVisibility(View.GONE);
        } else {
            if (dto.getType() == 0) {
                icon_drawer.setVisibility(View.GONE);
                if (dto.getId() != Statics.DRAWER_MENU_ID_ALL_CALENDAR) {
                    if (childList != null && childList.size() != 0) {
                        child_lnl.setVisibility(View.VISIBLE);
                        View v;
                        child_lnl.removeAllViews();
                        for (DrawerDto dto1 : childList) {
                            v = LayoutInflater.from(child_lnl.getContext()).inflate(R.layout.row_drawer_menu, null);
                            child_lnl.addView(v);
                            DrawerMenuViewHolder viewHolder = new DrawerMenuViewHolder(v);
                            viewHolder.bindData(dto1, child_lnl, 0);
                        }
                    } else {
                        child_lnl.setVisibility(View.GONE);
                    }
                } else {
                    childList = null;
                    child_lnl.removeAllViews();
                    child_lnl.setVisibility(View.GONE);
                }
            } else {
                icon_drawer.setVisibility(View.INVISIBLE);
                child_lnl.setVisibility(View.GONE);
            }
            title.setVisibility(View.GONE);
            frcontent.setVisibility(View.VISIBLE);
            cb_drawer.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(dto.getColor())) {
                tv_bg.setBackgroundColor(Color.parseColor("#" + dto.getColor()));
            }
            content.setText(dto.getName());
            if (!check_childList(dto.getName()))
                content.setTextColor(Color.WHITE);
//            Log.e(TAG, "isView:" + dto.isView() + " name:" + dto.getName());
            cb_drawer.setChecked(checkAllChecked(child_lnl, dto.isView()));

//            for (int i = 0; i < BaseDrawerActivity.drawer_rcl.getChildCount(); i++) {
//                View view = BaseDrawerActivity.drawer_rcl.getChildAt(i);
//                CheckBox chk = (CheckBox) view.findViewById(R.id.cb_drawer);
//                TextView tv = (TextView) view.findViewById(R.id.title);
//                if (tv.getText().toString().equals(BaseActivity.Instance.all)) {
//                    chk.setEnabled(false);
//                }
//            }

            divider.setVisibility(View.GONE);
            cb_drawer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (dto.getId() != Statics.DRAWER_MENU_ID_ALL_CALENDAR) {
                        if (isChecked) {
                            handleChecked();
                        } else {
                            handleUnChecked();
                        }
                    }
                    if (callback != null) {
//                        callback.callback(isChecked, dto.getId());
                    }
                }
            });

            cb_drawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    if (dto.getId() != Statics.DRAWER_MENU_ID_ALL_CALENDAR) {
                        setCheckAll(cb.isChecked());
                        if (dto.getId() == Statics.DRAWER_MENU_ID_PUBLIC_CALENDAR) {
                            check_p(cb.isChecked(), Statics.DRAWER_MENU_ID_PUBLIC_CALENDAR);
                        } else if (dto.getId() == Statics.DRAWER_MENU_ID_PRIVATE_CALENDAR) {
                            check_p(cb.isChecked(), Statics.DRAWER_MENU_ID_PRIVATE_CALENDAR);
                        } else if (dto.getId() == Statics.DRAWER_MENU_ID_SHARE_CALENDAR) {
                            if (DrawerMenuAdapter.childList_3.size() == 0) {
                                cb.setChecked(true);
                                cb.setEnabled(false);
                            } else {
                                check_p(cb.isChecked(), Statics.DRAWER_MENU_ID_SHARE_CALENDAR);
                            }
                        }
                        ARR_TYPE.clear();
                        ARR_TYPE_EXCEPTION.clear();
                        int CALENDAR_NO = dto.getCalendarNo();
                        if (CALENDAR_NO != 0) {
                            String str = "" + CALENDAR_NO;
                            for (int i = 0; i < DrawerMenuAdapter.finalChildList.size(); i++) {
                                if (str.equals("" + DrawerMenuAdapter.finalChildList.get(i).getCalendarNo())) {
                                    if (DrawerMenuAdapter.finalChildList.get(i).isView()) {
                                        DrawerMenuAdapter.finalChildList.get(i).setIsView(false);
                                    } else {
                                        DrawerMenuAdapter.finalChildList.get(i).setIsView(true);
                                    }
                                }
                            }
                        } else {
                            if (dto.getId() == Statics.DRAWER_MENU_ID_PUBLIC_CALENDAR) {
                                check_parent(cb.isChecked(), 1);
                            } else if (dto.getId() == Statics.DRAWER_MENU_ID_PRIVATE_CALENDAR) {
                                check_parent(cb.isChecked(), 2);
                            } else if (dto.getId() == Statics.DRAWER_MENU_ID_SHARE_CALENDAR) {
                                check_parent(cb.isChecked(), 3);
                            }
                        }
                        for (DrawerDto dto1 : DrawerMenuAdapter.finalChildList) {
                            if (dto1.isView()) {
                                ARR_TYPE.add("" + dto1.getCalendarNo());
                            } else {
                                ARR_TYPE_EXCEPTION.add("" + dto1.getCalendarNo());
                            }
                        }
                        if (ARR_TYPE_EXCEPTION.size() == 0) {
                            for (int i = 0; i < BaseDrawerActivity.drawer_rcl.getChildCount(); i++) {
                                View view = BaseDrawerActivity.drawer_rcl.getChildAt(i);
                                TextView tv = (TextView) view.findViewById(R.id.title);
                                CheckBox chk = (CheckBox) view.findViewById(R.id.cb_drawer);
                                if (tv.getText().toString().equals(BaseActivity.Instance.all))
                                    chk.setChecked(true);
                            }
                            for (int i = 4; i < DrawerMenuAdapter.dataSet_current.size() - 1; i++) {
                                DrawerMenuAdapter.dataSet_current.get(i).setIsView(true);
                            }
                        } else {
                            for (int i = 0; i < BaseDrawerActivity.drawer_rcl.getChildCount(); i++) {
                                View view = BaseDrawerActivity.drawer_rcl.getChildAt(i);
                                TextView tv = (TextView) view.findViewById(R.id.title);
                                CheckBox chk = (CheckBox) view.findViewById(R.id.cb_drawer);
                                if (tv.getText().toString().equals(BaseActivity.Instance.all))
                                    chk.setChecked(false);
                            }
                            for (int i = 0; i < DrawerMenuAdapter.finalChildList.size(); i++) {
                                if (check_temp(ARR_TYPE_EXCEPTION, "" + DrawerMenuAdapter.finalChildList.get(i).getCalendarNo())) {
                                    int type = DrawerMenuAdapter.finalChildList.get(i).getType();
                                    if (type == 1) {
                                        DrawerMenuAdapter.dataSet_current.get(Statics.DRAWER_MENU_ID_PUBLIC_CALENDAR).setIsView(false);
                                    } else if (type == 2) {
                                        DrawerMenuAdapter.dataSet_current.get(Statics.DRAWER_MENU_ID_PRIVATE_CALENDAR).setIsView(false);
                                    } else if (type == 3) {
                                        DrawerMenuAdapter.dataSet_current.get(Statics.DRAWER_MENU_ID_SHARE_CALENDAR).setIsView(false);
                                    }
                                }
                            }
                            DrawerMenuAdapter.dataSet_current.get(Statics.DRAWER_MENU_ID_ALL_CALENDAR).setIsView(false);
                        }
                        if (ARR_TYPE.size() == 0) ARR_TYPE.add("1");
                        MonthlyFragment.mf.update_adapter();
                        BaseActivity.Instance.callActivity(Activity_finish.class);

//                        Calendar cal = Calendar.getInstance();
//                        cal.setTimeInMillis(HomeActivity.tml);
//                        Log.e(TAG, "month:" + cal.get(Calendar.MONTH));
//                        int position = TimeUtils.getPositionForMonth(cal);
//                        long time = TimeUtils.getMonthForPosition(position).getTimeInMillis();
//                        List<CalendarDto> dataSet = TimeUtils.getDatesFromTime(time, 42);
//                        for (CalendarDto dt : dataSet) {
//                            Log.e(TAG, "date: " + TimeUtils.showTime(dt.getTimeInMillis(), Statics.DATE_FORMAT_YYYY_MM_DD));
//                        }

                    } else if (dto.getId() == Statics.DRAWER_MENU_ID_ALL_CALENDAR) {
                        if (cb.isChecked()) {
                            if (ARR_TYPE.size() > 0) {
                                if (!ARR_TYPE.get(0).equals("1")) {
                                    ARR_UNCHECK = new ArrayList<String>(ARR_TYPE);
                                    dataSet_current.clear();
                                    for (DrawerDto dt : DrawerMenuAdapter.dataSet_current) {
                                        if (!dt.isView())
                                            dataSet_current.add(dt.getName());
                                    }
                                }
                            }
                            for (int i = 0; i < DrawerMenuAdapter.finalChildList.size(); i++) {
                                DrawerMenuAdapter.finalChildList.get(i).setIsView(true);
                            }
                            ARR_TYPE.clear();
                            for (int i = 0; i < DrawerMenuAdapter.dataSet_current.size(); i++) {
                                if (i != DrawerMenuAdapter.dataSet_current.size() - 1)
                                    DrawerMenuAdapter.dataSet_current.get(i).setIsView(true);
                            }
                        } else {
                            ARR_TYPE.clear();
                            if (ARR_UNCHECK.size() == DrawerMenuAdapter.finalChildList.size() || ARR_UNCHECK.size() == 0) {
                                ARR_TYPE.add("1");
                                for (int i = 4; i < DrawerMenuAdapter.dataSet_current.size() - 1; i++) {
                                    DrawerMenuAdapter.dataSet_current.get(i).setIsView(false);
                                }
                            } else {
                                DrawerMenuAdapter.dataSet_current.get(Statics.DRAWER_MENU_ID_ALL_CALENDAR).setIsView(false);
                                ARR_TYPE = new ArrayList<String>(ARR_UNCHECK);
                                for (int i = 5; i < DrawerMenuAdapter.dataSet_current.size() - 1; i++) {
                                    if (check_temp(dataSet_current, DrawerMenuAdapter.dataSet_current.get(i).getName()))
                                        DrawerMenuAdapter.dataSet_current.get(i).setIsView(false);
                                    else
                                        DrawerMenuAdapter.dataSet_current.get(i).setIsView(true);
                                }
                            }
                            for (int i = 0; i < DrawerMenuAdapter.finalChildList.size(); i++) {
                                DrawerMenuAdapter.finalChildList.get(i).setIsView(false);
                            }
                            for (int i = 0; i < DrawerMenuAdapter.finalChildList.size(); i++) {
                                if (check_temp(ARR_UNCHECK, "" + DrawerMenuAdapter.finalChildList.get(i).getCalendarNo()))
                                    DrawerMenuAdapter.finalChildList.get(i).setIsView(true);
                            }
                        }
                        DrawerMenuAdapter.dataSet_current.get(Statics.DRAWER_MENU_ID_ALL_CALENDAR).setIsView(cb.isChecked());
                        HomeActivity.mDrawerMenuAdapter.notifyDataSetChanged();
                        MonthlyFragment.mf.update_adapter();
                        BaseActivity.Instance.callActivity(Activity_finish.class);

                    }
//                    Log.e(TAG, "size:" + ARR_TYPE.size());
//                    for (int i = 0; i < ARR_TYPE.size(); i++)
//                        Log.e(TAG, "ARR_TYPE :" + ARR_TYPE.get(i));
                }
            });
        }
    }

    public void check_p(boolean cb, int i) {
        if (cb)
            DrawerMenuAdapter.dataSet_current.get(i).setIsView(cb);
        else
            DrawerMenuAdapter.dataSet_current.get(i).setIsView(cb);
    }

    public void check_parent(boolean cb, int n) {
        for (int i = 0; i < DrawerMenuAdapter.finalChildList.size(); i++) {
            if (DrawerMenuAdapter.finalChildList.get(i).getType() == n)
                if (cb)
                    DrawerMenuAdapter.finalChildList.get(i).setIsView(true);
                else
                    DrawerMenuAdapter.finalChildList.get(i).setIsView(false);
        }
    }

    String name_temp = "";
    ArrayList<String> dataSet_current = new ArrayList<String>();
    public ArrayList<String> ARR_UNCHECK = new ArrayList<String>();
    public static ArrayList<String> ARR_TYPE = new ArrayList<String>();
    public ArrayList<String> ARR_TYPE_EXCEPTION = new ArrayList<String>();

    private void setCheckAll(boolean isCheck) {
        if (child_lnl.getChildCount() != 0) {
            for (int i = 0; i < child_lnl.getChildCount(); i++) {
                View vChild = child_lnl.getChildAt(i);
                if (vChild != null) {
                    CheckBox cb = (CheckBox) vChild.findViewById(R.id.cb_drawer);
                    cb.setChecked(isCheck);
                }
            }
        }
    }

    private void handleChecked() {
        if (viewParent == null)
            return;
        ViewGroup parent = (ViewGroup) viewParent.getParent();
        if (parent != null) {
            CheckBox parentCheckBox = (CheckBox) parent.findViewById(R.id.cb_drawer);
            if (parentCheckBox != null) {
                parentCheckBox.setChecked(checkAllChecked(viewParent, parentCheckBox.isChecked()));
            }
        }
    }

    private void handleUnChecked() {
        if (viewParent == null)
            return;
        ViewGroup parent = (ViewGroup) viewParent.getParent();
        if (parent != null) {
            CheckBox parentCheckBox = (CheckBox) parent.findViewById(R.id.cb_drawer);
            if (parentCheckBox != null) {
                parentCheckBox.setChecked(false);
            }
        }
    }

    private boolean checkAllChecked(ViewGroup v, boolean current) {
        if (v.getChildCount() != 0) {
            for (int i = 0; i < v.getChildCount(); i++) {
                View vChild = v.getChildAt(i);
                if (vChild != null) {
                    CheckBox cb = (CheckBox) vChild.findViewById(R.id.cb_drawer);
                    if (!cb.isChecked())
                        return false;
                }
            }
            return true;
        }
        return current;
    }

    public interface CheckedCallback {
        public void callback(boolean callback, int itemID);
    }

    public boolean check_childList(String name) {
        for (int i = 0; i < DrawerMenuAdapter.finalChildList.size(); i++) {
            if (name.equals(DrawerMenuAdapter.finalChildList.get(i).getName()))
                return false;
        }
        return true;
    }
}
