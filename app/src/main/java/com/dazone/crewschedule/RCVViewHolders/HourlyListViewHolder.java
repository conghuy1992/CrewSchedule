package com.dazone.crewschedule.RCVViewHolders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.AddNewScheduleActivity;
import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Activities.DailyScheduleActivity;
import com.dazone.crewschedule.Activities.HomeActivity;
import com.dazone.crewschedule.Activities.ScheduleDetailActivity;
import com.dazone.crewschedule.Class.ScheduleDay;
import com.dazone.crewschedule.Class.ScheduleListViewItem;
import com.dazone.crewschedule.Class.ScheduleViewItem;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.Fragment.DailyListItemFragment;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.ImageUtils;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by david on 12/25/15.
 */
public class HourlyListViewHolder extends ItemViewHolder<CalendarDto> {
    String TAG = "HourlyListViewHolder";
    TextView title;
    public LinearLayout lnl_daily_bound, schedule_lnl;

    public HourlyListViewHolder(View v) {
        super(v);
    }

    @Override
    protected void setup(View v) {
        title = (TextView) v.findViewById(R.id.title);
        lnl_daily_bound = (LinearLayout) v.findViewById(R.id.lnl_daily_bound);
        schedule_lnl = (LinearLayout) v.findViewById(R.id.schedule_lnl);

    }

    @Override
    public void bindData(CalendarDto dto) {
        String hour = "0%1$d:00";
        String hours = "%1$d:00";
        if (getAdapterPosition() == 0) {
            title.setText(R.string.string_all_day);
            title.setBackgroundColor(ImageUtils.getColor(lnl_daily_bound.getContext(), R.color.gray_light));
            schedule_lnl.setBackgroundColor(ImageUtils.getColor(lnl_daily_bound.getContext(), R.color.gray_light));
        } else {
            if (getAdapterPosition() < 11) {
                String temp = String.format(hour, getAdapterPosition() - 1);
                title.setText(temp);
            } else {
                String temp = String.format(hours, getAdapterPosition() - 1);
                title.setText(temp);
//                Log.e(TAG, "pos:" + getAdapterPosition());
            }
        }
        schedule_lnl.removeAllViews();
//        if (dto.getScheduleDtos() != null && dto.getScheduleDtos().size() != 0) {
        int size = dto.getScheduleDtos().size();
//        int _width = DailyListItemFragment._width;
        int _width = HomeActivity._width;

        for (ScheduleDto dto1 : dto.getScheduleDtos()) {
            if (dto1 != null && DrawerMenuViewHolder.ARR_TYPE.size() == 0) {
                int START = Integer.parseInt(dto1.getStartTime().split(":")[0]);
                int END = Integer.parseInt(dto1.getEndTime().split(":")[0]);
                if (START == 0 && END == 0) {
                    ScheduleDay item = new ScheduleDay(schedule_lnl.getContext(), dto1, _width / size, 1, 1);
                    item.addToView(schedule_lnl);
                } else {
                    START += 1;
                    END += 1;
                    int check = 1;
                    if (START == END) {
                        END += 1;
                        check = 0;
                    }
                    if (getAdapterPosition() >= START && getAdapterPosition() < END) {
                        ScheduleDay item = new ScheduleDay(schedule_lnl.getContext(), dto1, _width / size, 1, check);
                        item.addToView(schedule_lnl);
                    } else {
                        ScheduleDay item = new ScheduleDay(schedule_lnl.getContext(), dto1, _width / size, 0, check);
                        item.addToView(schedule_lnl);
                    }
                }
            } else if (dto1 != null && DrawerMenuViewHolder.ARR_TYPE.size() > 0) {
                if (DrawerMenuViewHolder.instance.check_contain("" + dto1.getCalendarNo())) {
                    int START = Integer.parseInt(dto1.getStartTime().split(":")[0]);
                    int END = Integer.parseInt(dto1.getEndTime().split(":")[0]);
                    if (START == 0 && END == 0) {
                        ScheduleDay item = new ScheduleDay(schedule_lnl.getContext(), dto1, _width / size, 1, 1);
                        item.addToView(schedule_lnl);
                    } else {
                        START += 1;
                        END += 1;
                        int check = 1;
                        if (START == END) {
                            END += 1;
                            check = 0;
                        }
                        if (getAdapterPosition() >= START && getAdapterPosition() < END) {
                            ScheduleDay item = new ScheduleDay(schedule_lnl.getContext(), dto1, _width / size, 1, check);
                            item.addToView(schedule_lnl);
                        } else {
                            ScheduleDay item = new ScheduleDay(schedule_lnl.getContext(), dto1, _width / size, 0, check);
                            item.addToView(schedule_lnl);
                        }
                    }
                }
            }
        }
//        }

        initClickAction(dto);
    }


    private void initClickAction(final CalendarDto dto) {
        schedule_lnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dto.getScheduleDtos() != null && dto.getScheduleDtos().size() != 0) {
                    if (dto.getScheduleDtos().size() == 1) {
                        Intent i = new Intent(lnl_daily_bound.getContext(), ScheduleDetailActivity.class);
                        i.putExtra(ParamKeys.KEY_SCHEDULE_NO, dto.getScheduleDtos().get(0).getScheduleNo());
                        BaseActivity.Instance.startActivity(i);
                    } else {
                        int year = Integer.parseInt(DailyGridViewHolder.date_current.trim().split("-")[0]);
                        int month = Integer.parseInt(DailyGridViewHolder.date_current.trim().split("-")[1]) - 1;
                        int date = Integer.parseInt(DailyGridViewHolder.date_current.trim().split("-")[2]);
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, date);
                        long tml = cal.getTimeInMillis();
                        Intent i = new Intent(lnl_daily_bound.getContext(), DailyScheduleActivity.class);
                        i.putExtra(ParamKeys.KEY_TIME_MILLISECONDS, tml);
                        BaseActivity.Instance.startActivity(i);
                    }
                }
// else {
//                }
            }
        });
    }
}
