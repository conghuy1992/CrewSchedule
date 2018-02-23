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
import com.dazone.crewschedule.Activities.HomeActivity;
import com.dazone.crewschedule.Activities.ScheduleDetailActivity;
import com.dazone.crewschedule.Class.ScheduleDay;
import com.dazone.crewschedule.Class.ScheduleViewItem;
import com.dazone.crewschedule.Class.ScheduleWeeks;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.Fragment.WeeklyListItemFragment;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.ImageUtils;
import com.dazone.crewschedule.Utils.TimeUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.ArrayList;

/**
 * Created by david on 12/25/15.
 */
public class HourlyTitleGridViewHolder extends ItemViewHolder<CalendarDto> {
    String TAG = "HourlyGridViewHolder";
    TextView title, tv_num, tv_content;
    public LinearLayout lnl_daily_bound, ln_schedule;
    private int currentMonth = 0;

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public HourlyTitleGridViewHolder(View v) {
        super(v);
    }

    @Override
    protected void setup(View v) {
        title = (TextView) v.findViewById(R.id.title);
        tv_num = (TextView) v.findViewById(R.id.tv_num);
        tv_content = (TextView) v.findViewById(R.id.tv_content);
        lnl_daily_bound = (LinearLayout) v.findViewById(R.id.lnl_daily_bound);
        ln_schedule = (LinearLayout) v.findViewById(R.id.ln_schedule);

    }

    @Override
    public void bindData(CalendarDto dto) {
        String hour = "0%1$d:00";
        String hours = "%1$d:00";
        if (getAdapterPosition() < 8)
            ln_schedule.setBackgroundColor(ImageUtils.getColor(lnl_daily_bound.getContext(), R.color.gray_light));
        if (getAdapterPosition() == 0) {
            title.setVisibility(View.VISIBLE);
            title.setText(R.string.string_all_day);
            title.setBackgroundColor(ImageUtils.getColor(lnl_daily_bound.getContext(), R.color.gray_light));
        } else if (getAdapterPosition() < 88 && getAdapterPosition() % 8 == 0) {
            title.setVisibility(View.VISIBLE);
            String temp = String.format(hour, (getAdapterPosition() - 8) / 8);
            title.setText(temp);
        } else if (getAdapterPosition() >= 88 && getAdapterPosition() % 8 == 0) {
            title.setVisibility(View.VISIBLE);
            String temp = String.format(hours, (getAdapterPosition() - 8) / 8);
            title.setText(temp);
        }

        if (dto.getScheduleDtos() != null && dto.getScheduleDtos().size() != 0) {
            ln_schedule.removeAllViews();
            int size = dto.getScheduleDtos().size();
            for (ScheduleDto dto1 : dto.getScheduleDtos()) {
                if (dto1 != null && DrawerMenuViewHolder.ARR_TYPE.size() == 0) {
                    Draw(size, dto1);
                } else if (dto1 != null && DrawerMenuViewHolder.ARR_TYPE.size() > 0) {
                    if (DrawerMenuViewHolder.instance.check_contain("" + dto1.getCalendarNo())) {
                        Draw(size, dto1);
                    }
                }
            }
        }

        initClickAction(dto);

    }

    private void initClickAction(final CalendarDto dto) {
        lnl_daily_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e(TAG, "pos:" + getAdapterPosition());
                if (dto.getScheduleDtos() != null && dto.getScheduleDtos().size() != 0) {
                    if (dto.getScheduleDtos().size() == 1) {
                        Intent i = new Intent(lnl_daily_bound.getContext(), ScheduleDetailActivity.class);
                        i.putExtra(ParamKeys.KEY_SCHEDULE_NO, dto.getScheduleDtos().get(0).getScheduleNo());
                        BaseActivity.Instance.startActivity(i);
//                        Log.e(TAG, "ScheduleDetailActivity");
                    } else {
//                        Log.e(TAG, "DailyScheduleActivity");
                        Intent i = new Intent(lnl_daily_bound.getContext(), DailyScheduleActivity.class);
                        i.putExtra(ParamKeys.KEY_TIME_MILLISECONDS, dto.getTimeInMillis());
                        BaseActivity.Instance.startActivity(i);
                    }
                } else {
                    Log.e(TAG, "No event");
//                    BaseActivity.Instance.callActivity(AddNewScheduleActivity.class);
                }
            }
        });
    }

    public void Draw(int size, ScheduleDto dto1) {
        int width = HomeActivity._width_week;
        int START = Integer.parseInt(dto1.getStartTime().split(":")[0]);
        int END = Integer.parseInt(dto1.getEndTime().split(":")[0]);
        if (START == 0 && END == 0) {
            if (size > 3) {
                tv_num.setVisibility(View.VISIBLE);
                int num = size - 3;
                tv_num.setText("+" + num);
                tv_num.setHeight(WeeklyListItemFragment.lineHeight / 3);
            }
            ln_schedule.setOrientation(LinearLayout.VERTICAL);
            ScheduleWeeks item = new ScheduleWeeks(ln_schedule.getContext(), dto1, ln_schedule.getWidth() / size, 1, 1, 1, size, size);
            item.addToView(ln_schedule);
        } else {
            START += 1;
            END += 1;
            int check = 1;
            if (START == END) {
                END += 1;
                check = 0;
            }
            if (getAdapterPosition() >= HomeActivity.arr_sun.get(START) && getAdapterPosition() <= HomeActivity.arr_sun.get(END)) {
                ScheduleWeeks item = new ScheduleWeeks(ln_schedule.getContext(), dto1, width / size, 1, check, 0, 1, size);
                item.addToView(ln_schedule);
            } else {

                ScheduleWeeks item = new ScheduleWeeks(ln_schedule.getContext(), dto1, width / size, 0, check, 0, 1, size);
                item.addToView(ln_schedule);
            }
        }
    }
}
