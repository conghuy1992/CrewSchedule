package com.dazone.crewschedule.RCVViewHolders;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Class.ScheduleListViewItem;
import com.dazone.crewschedule.Class.ScheduleViewItem;
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
public class DailyListMonthlyViewHolder extends ItemViewHolder<CalendarDto> {
    TextView day_number, day_char;
    public LinearLayout schedule_lnl;

    public DailyListMonthlyViewHolder(View v) {
        super(v);
    }

    @Override
    protected void setup(View v) {
        day_number = (TextView) v.findViewById(R.id.day_number);
        day_char = (TextView) v.findViewById(R.id.day_char);
        schedule_lnl = (LinearLayout) v.findViewById(R.id.schedule_lnl);
    }

    private int currentMonth = 0;

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    @Override
    public void bindData(CalendarDto dto) {
        if (TimeUtils.isSameDate(dto.getTimeInMillis(), Calendar.getInstance().getTimeInMillis())) {
            day_number.setTextColor(ImageUtils.getColor(day_number.getContext(), R.color.colorPrimary));
            day_char.setTextColor(ImageUtils.getColor(day_char.getContext(), R.color.colorPrimary));
        } else {
            day_number.setTextColor(Color.BLACK);
            day_char.setTextColor(ImageUtils.getColor(day_char.getContext(), R.color.daily_list_schedule_color));
        }
//        day_number.setText(TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD));
//        day_char.setText(TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD_CHAR));
        schedule_lnl.removeAllViews();
        for (ScheduleDto dto1 : dto.getScheduleDtos()) {
            if (DrawerMenuViewHolder.ARR_TYPE.size() == 0) {
                day_number.setText(TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD));
                day_char.setText(TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD_CHAR));
                day_number.setVisibility(View.VISIBLE);
                day_char.setVisibility(View.VISIBLE);
                ScheduleListViewItem item = new ScheduleListViewItem(schedule_lnl.getContext(), dto1);
                item.addToView(schedule_lnl);
            } else {
                if (DrawerMenuViewHolder.instance.check_contain("" + dto1.getCalendarNo())) {
                    day_number.setText(TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD));
                    day_char.setText(TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD_CHAR));
                    day_number.setVisibility(View.VISIBLE);
                    day_char.setVisibility(View.VISIBLE);
                    ScheduleListViewItem item = new ScheduleListViewItem(schedule_lnl.getContext(), dto1);
                    item.addToView(schedule_lnl);
                }
            }

        }
        initClickAction(dto);
    }

    private void initClickAction(final CalendarDto dto) {
//        lnl_daily_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(dto.getScheduleDtos()!=null&&dto.getScheduleDtos().size()!=0)
//                {
//                    Intent i = new Intent(lnl_daily_main.getContext(), DailyScheduleActivity.class);
//                    i.putExtra(ParamKeys.KEY_TIME_MILLISECONDS,dto.getTimeInMillis());
//                    BaseActivity.Instance.startActivity(i);
//                }
//                else
//                {
//                    BaseActivity.Instance.callActivity(AddNewScheduleActivity.class);
//                }
//            }
//        });
    }
}
