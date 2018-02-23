package com.dazone.crewschedule.Class;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazone.crewschedule.Dtos.ScheduleDto;
import com.dazone.crewschedule.Fragment.WeeklyListItemFragment;
import com.dazone.crewschedule.R;

/**
 * Created by david on 1/5/16.
 */
public class ScheduleWeeks extends BaseViewClass {

    ScheduleDto dto = null;
    TextView schedule_tv = null;
    TextView divider = null;
    TextView divider_bottom = null;
    FrameLayout root = null;
    int size, c, start, setOrientation, num, set_text;

    public ScheduleWeeks(Context context, ScheduleDto dto, int size, int c, int start, int setOrientation, int num, int set_text) {
        super(context);
        this.dto = dto;
        this.size = size;
        this.c = c;
        this.start = start;
        this.setOrientation = setOrientation;
        this.num = num;
        this.set_text = set_text;
        setupView();
    }

    int count = 0;

    public ScheduleWeeks(Context context, int count) {
        super(context);
        this.count = count;
        setupView();
    }

    @Override
    protected void setupView() {
        currentView = inflater.inflate(R.layout.schedule_item_week, null);
        schedule_tv = (TextView) currentView.findViewById(R.id.schedule_tv);
        divider = (TextView) currentView.findViewById(R.id.divider);
        divider_bottom = (TextView) currentView.findViewById(R.id.divider_bottom);
        root = (FrameLayout) currentView.findViewById(R.id.root);
        binData();
    }

    private void binData() {
        if (dto == null) {
            return;
        } else {
            if (set_text == 1)
                schedule_tv.setText(dto.getTitle());
            schedule_tv.setTextColor(Color.WHITE);
            int setOr = WeeklyListItemFragment.lineHeight;
            if (setOrientation == 1) {
                if (num > 3) num = 3;
                setOr = WeeklyListItemFragment.lineHeight / num;
                divider_bottom.setVisibility(View.VISIBLE);
                divider.setVisibility(View.GONE);
                size = 200;
//                schedule_tv.setText("");
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, setOr);
            root.setLayoutParams(params);
            root.setBackgroundColor(Color.parseColor("#" + dto.getCalendarColor()));
            if (start == 0) {
                LinearLayout.LayoutParams pr = new LinearLayout.LayoutParams(size, WeeklyListItemFragment.lineHeight / 10);
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
