package com.dazone.crewschedule.Class;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dazone.crewschedule.Activities.Base.BaseActivity;
import com.dazone.crewschedule.Activities.DailyScheduleActivity;
import com.dazone.crewschedule.Activities.ScheduleDetailActivity;
import com.dazone.crewschedule.Constant.ParamKeys;
import com.dazone.crewschedule.Dtos.CalendarDto;
import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.R;

/**
 * Created by david on 1/5/16.
 */
public class ScheduleViewItem extends BaseViewClass {

    ScheduleDto dto = null;
    TextView schedule_tv = null;
    CalendarDto cd;

    public ScheduleViewItem(Context context, ScheduleDto dto, CalendarDto cd) {
        super(context);
        this.dto = dto;
        this.cd = cd;
        setupView();
    }

    int count = 0;

    public ScheduleViewItem(Context context, int count) {
        super(context);
        this.count = count;
        setupView();
    }

    @Override
    protected void setupView() {
        currentView = inflater.inflate(R.layout.schedule_item_row, null);
        schedule_tv = (TextView) currentView.findViewById(R.id.schedule_tv);

        binData();
    }

    private void binData() {
        if (dto == null) {
            if (count == 0)
                return;
            schedule_tv.setTextColor(Color.BLACK);
            schedule_tv.setText("+" + count);
        } else {
            schedule_tv.setText(dto.getTitle());
            schedule_tv.setTextColor(Color.WHITE);
            schedule_tv.setBackgroundColor(Color.parseColor("#" + dto.getCalendarColor()));
            schedule_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cd.getScheduleDtos() != null && cd.getScheduleDtos().size() != 0) {
                        if (cd.getScheduleDtos().size() == 1) {
                            Intent i = new Intent(schedule_tv.getContext(), ScheduleDetailActivity.class);
                            i.putExtra(ParamKeys.KEY_SCHEDULE_NO, cd.getScheduleDtos().get(0).getScheduleNo());
                            BaseActivity.Instance.startActivity(i);
                        } else {
                            Intent i = new Intent(schedule_tv.getContext(), DailyScheduleActivity.class);
                            i.putExtra(ParamKeys.KEY_TIME_MILLISECONDS, cd.getTimeInMillis());
                            BaseActivity.Instance.startActivity(i);
                        }
                    }
                }
            });
        }
    }
}
