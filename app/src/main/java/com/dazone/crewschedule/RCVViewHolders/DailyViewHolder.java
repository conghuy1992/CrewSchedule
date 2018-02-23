package com.dazone.crewschedule.RCVViewHolders;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Activities.ScheduleDetailActivity;
import com.dazone.crewschedule.Class.ScheduleViewItem;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.R;

/**
 * Created by david on 12/25/15.
 */
public class DailyViewHolder extends ItemViewHolder<ScheduleDto> {
    String TAG = "DailyViewHolder";
    TextView tv_schedule_name, tv_schedule_time;
    public LinearLayout lnl_info;

    public DailyViewHolder(View v) {
        super(v);
    }

    @Override
    protected void setup(View v) {
        tv_schedule_name = (TextView) v.findViewById(R.id.tv_schedule_name);
        tv_schedule_time = (TextView) v.findViewById(R.id.tv_schedule_time);
        lnl_info = (LinearLayout) v.findViewById(R.id.lnl_info);
    }

    @Override
    public void bindData(final ScheduleDto dto) {
        Log.d(TAG, "bindData");
        tv_schedule_name.setText(dto.getTitle());
        if (dto.getStartTime().equals("00:00") && dto.getEndTime().equals("00:00")) {
            tv_schedule_time.setText(R.string.string_all_day_long);
        } else {
            tv_schedule_time.setText(dto.getStartTime() + " - " + dto.getEndTime());
        }
        lnl_info.setBackgroundColor(Color.parseColor("#" + dto.getCalendarColor()));

        lnl_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(lnl_info.getContext(), ScheduleDetailActivity.class);
                i.putExtra(ParamKeys.KEY_SCHEDULE_NO, dto.getScheduleNo());
                BaseActivity.Instance.startActivity(i);
            }
        });
    }

}
