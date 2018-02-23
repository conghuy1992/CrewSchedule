package com.dazone.crewschedule.RCVViewHolders;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.AddNewScheduleActivity;
import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Activities.DailyScheduleActivity;
import com.dazone.crewschedule.Activities.ScheduleDetailActivity;
import com.dazone.crewschedule.Class.ScheduleViewItem;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.ImageUtils;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.Calendar;

/**
 * Created by david on 12/25/15.
 */
public class DailyGridViewHolder extends ItemViewHolder<CalendarDto> {
    String TAG = "DailyGridViewHolder";
    TextView title, luna_title, tv_num;
    public LinearLayout lnl_daily_main, schedule_lnl, lnl_daily_bound;

    public DailyGridViewHolder(View v) {
        super(v);
    }

    @Override
    protected void setup(View v) {
        Log.d(TAG, "setup");
        title = (TextView) v.findViewById(R.id.title);
        tv_num = (TextView) v.findViewById(R.id.tv_num);
        lnl_daily_main = (LinearLayout) v.findViewById(R.id.lnl_daily_main);
        lnl_daily_bound = (LinearLayout) v.findViewById(R.id.lnl_daily_bound);
        schedule_lnl = (LinearLayout) v.findViewById(R.id.schedule_lnl);
    }

    private int currentMonth = 0;

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    @Override
    public void bindData(final CalendarDto dto) {

        Log.d(TAG, "bindData: ");
        title.setText(TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD));
//        Log.e(TAG,"date: "+TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_YYYY_MM_DD));
        if (TimeUtils.isSunday(dto.getTimeInMillis()) || TimeUtils.isSaturday(dto.getTimeInMillis())) {
            if (TimeUtils.isSaturday(dto.getTimeInMillis())) {
                title.setTextColor(ImageUtils.getColor(title.getContext(), R.color.tv_sat_color));
            } else {
                title.setTextColor(ImageUtils.getColor(title.getContext(), R.color.tv_sun_color));
            }
        } else {
            title.setTextColor(Color.BLACK);
        }

        if (TimeUtils.isSameDate(dto.getTimeInMillis(), Calendar.getInstance().getTimeInMillis())) {
            lnl_daily_bound.setBackgroundColor(ImageUtils.
                    getColor(lnl_daily_bound.getContext(), R.color.current_day_border_color));
        } else {
            lnl_daily_bound.setBackgroundColor(ImageUtils.
                    getColor(lnl_daily_bound.getContext(), R.color.normal_day_border_color));
        }

        if (dto.getCurrentMonth() != currentMonth) {
            // background color for other month
            lnl_daily_main.setBackgroundColor(ImageUtils.getColor(title.getContext(), R.color.bg_not_in_month));
        } else {
            if (TimeUtils.isSameDate(dto.getTimeInMillis(), Calendar.getInstance().getTimeInMillis())) {
                // background color for current day
                lnl_daily_main.setBackgroundColor(ImageUtils.
                        getColor(lnl_daily_bound.getContext(), R.color.current_day_bg_color));
            } else {
                lnl_daily_main.setBackgroundColor(Color.WHITE);
            }
        }
//        Log.e(TAG,"dto.getScheduleDtos().size():"+dto.getScheduleDtos().size());
        if (dto.getScheduleDtos() != null && dto.getScheduleDtos().size() != 0) {
//            if (dto.getScheduleDtos().size() <= 3) {
            for (ScheduleDto dto1 : dto.getScheduleDtos()) {
                if (dto1 != null && DrawerMenuViewHolder.ARR_TYPE.size() == 0) {
                    if (dto.getScheduleDtos().size() <= 3) {
                        ScheduleViewItem item = new ScheduleViewItem(schedule_lnl.getContext(), dto1, dto);
                        item.addToView(schedule_lnl);
                    } else {
                        int sum = dto.getScheduleDtos().size() - 3;
                        tv_num.setText("+" + sum);
                        tv_num.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(lnl_daily_main.getContext(), DailyScheduleActivity.class);
                                i.putExtra(ParamKeys.KEY_TIME_MILLISECONDS, dto.getTimeInMillis());
                                BaseActivity.Instance.startActivity(i);
                            }
                        });
                        ScheduleViewItem item = null;
                        item = new ScheduleViewItem(schedule_lnl.getContext(), dto.getScheduleDtos().get(0), dto);
                        item.addToView(schedule_lnl);
                        item = new ScheduleViewItem(schedule_lnl.getContext(), dto.getScheduleDtos().get(1), dto);
                        item.addToView(schedule_lnl);
                        item = new ScheduleViewItem(schedule_lnl.getContext(), dto.getScheduleDtos().get(2), dto);
                        item.addToView(schedule_lnl);
                    }
                } else if (dto1 != null && DrawerMenuViewHolder.ARR_TYPE.size() > 0) {
                    if (DrawerMenuViewHolder.instance.check_contain("" + dto1.getCalendarNo())) {
                        if (dto.getScheduleDtos().size() <= 3) {
                            ScheduleViewItem item = new ScheduleViewItem(schedule_lnl.getContext(), dto1, dto);
                            item.addToView(schedule_lnl);
                        } else {
                            int sum = dto.getScheduleDtos().size() - 3;
                            tv_num.setText("+" + sum);
                            tv_num.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(lnl_daily_main.getContext(), DailyScheduleActivity.class);
                                    i.putExtra(ParamKeys.KEY_TIME_MILLISECONDS, dto.getTimeInMillis());
                                    BaseActivity.Instance.startActivity(i);
                                }
                            });
                            ScheduleViewItem item = null;
                            item = new ScheduleViewItem(schedule_lnl.getContext(), dto.getScheduleDtos().get(0), dto);
                            item.addToView(schedule_lnl);
                            item = new ScheduleViewItem(schedule_lnl.getContext(), dto.getScheduleDtos().get(1), dto);
                            item.addToView(schedule_lnl);
                            item = new ScheduleViewItem(schedule_lnl.getContext(), dto.getScheduleDtos().get(2), dto);
                            item.addToView(schedule_lnl);
                        }
                    }
                }
            }
//            }
//            else {
//                int sum = dto.getScheduleDtos().size() - 3;
//                tv_num.setText("+" + sum);
//                ScheduleViewItem item = null;
//                item = new ScheduleViewItem(schedule_lnl.getContext(), dto.getScheduleDtos().get(0));
//                item.addToView(schedule_lnl);
//                item = new ScheduleViewItem(schedule_lnl.getContext(), dto.getScheduleDtos().get(1));
//                item.addToView(schedule_lnl);
//                item = new ScheduleViewItem(schedule_lnl.getContext(), dto.getScheduleDtos().get(2));
//                item.addToView(schedule_lnl);
//            }
        }
        initClickAction(dto);
    }

    public static String date_current = "";
    public static int date_current_int = 0;

    private void initClickAction(final CalendarDto dto) {
        lnl_daily_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("CLICK","from adapter");
//                if (dto.getScheduleDtos() != null && dto.getScheduleDtos().size() != 0) {
//                    if (dto.getScheduleDtos().size() == 1) {
//                        Intent i = new Intent(lnl_daily_main.getContext(), ScheduleDetailActivity.class);
//                        i.putExtra(ParamKeys.KEY_SCHEDULE_NO, dto.getScheduleDtos().get(0).getScheduleNo());
//                        BaseActivity.Instance.startActivity(i);
//                    } else {
//                        Intent i = new Intent(lnl_daily_main.getContext(), DailyScheduleActivity.class);
//                        i.putExtra(ParamKeys.KEY_TIME_MILLISECONDS, dto.getTimeInMillis());
//                        BaseActivity.Instance.startActivity(i);
//                    }
//                } else {
                date_current_int = 1;
                date_current = TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_YYYY_MM_DD);
                BaseActivity.Instance.callActivity(AddNewScheduleActivity.class);
//                }
            }
        });
    }
}