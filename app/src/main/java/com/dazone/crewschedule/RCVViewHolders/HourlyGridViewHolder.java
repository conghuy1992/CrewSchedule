package com.dazone.crewschedule.RCVViewHolders;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Class.ScheduleDay;
import com.dazone.crewschedule.Class.ScheduleViewItem;
import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.Fragment.WeeklyListItemFragment;
import com.dazone.crewschedule.Fragment.WeeklyPagerFragment;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.ImageUtils;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by david on 12/25/15.
 */
public class HourlyGridViewHolder extends ItemViewHolder<CalendarDto> {
    String TAG = "HourlyGridViewHolder";
    TextView title;
    public LinearLayout lnl_daily_bound, ln_schedule;


    public HourlyGridViewHolder(View v) {
        super(v);
    }

    @Override
    protected void setup(View v) {
        title = (TextView) v.findViewById(R.id.title);
        lnl_daily_bound = (LinearLayout) v.findViewById(R.id.lnl_daily_bound);
        ln_schedule = (LinearLayout) v.findViewById(R.id.ln_schedule);
    }

    @Override
    public void bindData(CalendarDto dto) {
//        if (dto.getTimeInMillis() != -1) {
//            title.setText(TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD));
//            Log.e(TAG,TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD));
//            if (TimeUtils.isSunday(dto.getTimeInMillis()) || TimeUtils.isSaturday(dto.getTimeInMillis())) {
//                if (TimeUtils.isSaturday(dto.getTimeInMillis())) {
//                    title.setTextColor(ImageUtils.getColor(title.getContext(), R.color.tv_sat_color));
//                } else {
//                    title.setTextColor(ImageUtils.getColor(title.getContext(), R.color.tv_sun_color));
//                }
//            } else {
//                title.setTextColor(Color.BLACK);
//            }

//            if (dto.getScheduleDtos() != null && dto.getScheduleDtos().size() != 0) {
//                for (ScheduleDto dto1 : dto.getScheduleDtos()) {
//                    if (dto1 != null && DrawerMenuViewHolder.ARR_TYPE.size() == 0) {
//                        ScheduleViewItem item = new ScheduleViewItem(ln_schedule.getContext(), dto1);
//                        item.addToView(ln_schedule);
//                    } else if (dto1 != null && DrawerMenuViewHolder.ARR_TYPE.size() > 0) {
//                        if (DrawerMenuViewHolder.instance.check_contain("" + dto1.getCalendarNo())) {
//                            ScheduleViewItem item = new ScheduleViewItem(ln_schedule.getContext(), dto1);
//                            item.addToView(ln_schedule);
//                        }
//                    }
//                }
//            }
//        }

    }
}
