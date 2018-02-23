package com.dazone.crewschedule.Class;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.Fragment.DailyListItemFragment;
import com.dazone.crewschedule.R;

/**
 * Created by david on 1/5/16.
 */
public class ScheduleDay extends BaseViewClass {

    ScheduleDto dto = null;
    TextView schedule_tv = null;
    TextView divider = null;
    LinearLayout root = null;
    int size, c, m;

    public ScheduleDay(Context context, ScheduleDto dto, int size, int c, int m) {
        super(context);
        this.dto = dto;
        this.size = size;
        this.c = c;
        this.m = m;
        setupView();
    }

    int count = 0;

    public ScheduleDay(Context context, int count) {
        super(context);
        this.count = count;
        setupView();
    }

    @Override
    protected void setupView() {
        currentView = inflater.inflate(R.layout.schedule_item_day, null);
        schedule_tv = (TextView) currentView.findViewById(R.id.schedule_tv);
        divider = (TextView) currentView.findViewById(R.id.divider);
        root = (LinearLayout) currentView.findViewById(R.id.root);
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, DailyListItemFragment.lineHeight);
            root.setLayoutParams(params);
            root.setBackgroundColor(Color.parseColor("#" + dto.getCalendarColor()));
            if (m == 0) {
                LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(size, DailyListItemFragment.lineHeight / 10);
                root.setLayoutParams(pr);
                root.setBackgroundColor(Color.parseColor("#" + dto.getCalendarColor()));
            }
            if (c == 0) {
                root.setBackgroundColor(Color.parseColor("#ffffff"));
                divider.setVisibility(View.GONE);
            }
        }
    }
}
