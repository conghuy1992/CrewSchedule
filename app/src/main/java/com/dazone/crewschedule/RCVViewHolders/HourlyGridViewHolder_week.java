package com.dazone.crewschedule.RCVViewHolders;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.ImageUtils;
import com.dazone.crewschedule.Utils.TimeUtils;

import java.util.Calendar;

/**
 * Created by maidinh on 29/3/2016.
 */
public class HourlyGridViewHolder_week extends ItemViewHolder<CalendarDto> {
    String TAG = "HourlyGridViewHolder";
    TextView title;
    public LinearLayout lnl_daily_bound, ln_schedule;


    public HourlyGridViewHolder_week(View v) {
        super(v);
    }

    @Override
    protected void setup(View v) {
        title = (TextView) v.findViewById(R.id.title);
        lnl_daily_bound = (LinearLayout) v.findViewById(R.id.lnl_daily_bound);

    }

    @Override
    public void bindData(CalendarDto dto) {
        if (dto.getTimeInMillis() != -1) {
            title.setText(TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD));
            if (TimeUtils.isSameDate(dto.getTimeInMillis(), Calendar.getInstance().getTimeInMillis())) {
                title.setBackgroundResource(R.drawable.round_tv);
            }
//            title.setTextColor(ImageUtils.getColor(title.getContext(), R.color.textColorPrimary));
//            Log.e(TAG, TimeUtils.showTime(dto.getTimeInMillis(), Statics.DATE_FORMAT_DD));
//            if (TimeUtils.isSunday(dto.getTimeInMillis()) || TimeUtils.isSaturday(dto.getTimeInMillis())) {
//                if (TimeUtils.isSaturday(dto.getTimeInMillis())) {
//                    title.setTextColor(ImageUtils.getColor(title.getContext(), R.color.tv_sat_color));
//                } else {
//                    title.setTextColor(ImageUtils.getColor(title.getContext(), R.color.tv_sun_color));
//                }
//            } else {
//                title.setTextColor(Color.BLACK);
//            }
        }


    }
}
