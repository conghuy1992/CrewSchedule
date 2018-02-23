package com.dazone.crewschedule.Class;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Activities.ScheduleDetailActivity;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.R;

/**
 * Created by david on 1/5/16.
 */
public class ScheduleListViewItem extends BaseViewClass {

    ScheduleDto dto = null;
    TextView tv_schedule_name, tv_schedule_time;
    LinearLayout lnl_bound;

    public ScheduleListViewItem(Context context, ScheduleDto dto) {
        super(context);
        this.dto = dto;
        setupView();
    }

    @Override
    protected void setupView() {
        currentView = inflater.inflate(R.layout.row_daily_list_schedule_layout, null);
        lnl_bound = (LinearLayout) currentView.findViewById(R.id.lnl_bound);
        tv_schedule_name = (TextView) currentView.findViewById(R.id.tv_schedule_name);
        tv_schedule_time = (TextView) currentView.findViewById(R.id.tv_schedule_time);
        binData();
    }

    private void binData() {
        if (dto == null) {
            return;
        } else {
            tv_schedule_name.setText(dto.getTitle());
            if (dto.getStartTime().equals("00:00") && dto.getEndTime().equals("00:00")) {
                tv_schedule_time.setText(R.string.string_all_day_long);
            } else {
                tv_schedule_time.setText(dto.getStartTime() + " - " + dto.getEndTime());
            }
            lnl_bound.setBackgroundColor(Color.parseColor("#" + dto.getCalendarColor()));
        }
        lnl_bound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(lnl_bound.getContext(), ScheduleDetailActivity.class);
                i.putExtra(ParamKeys.KEY_SCHEDULE_NO, dto.getScheduleNo());
                BaseActivity.Instance.startActivity(i);
            }
        });
    }
}
