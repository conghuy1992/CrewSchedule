package com.dazone.crewschedule.AddNewViewHolder;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazone.crewschedule.Dtos.DrawerDto;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.Fragment.AddNewScheduleFragment;
import com.dazone.crewschedule.Fragment.UpdateScheduleFragment;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Interfaces.GetCalendarTypeCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.DialogUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class Row_select_calendar_add_new_view_holder extends BaseAddNewViewHolder implements GetCalendarTypeCallBack {
    View v;
    String TAG = "Row_select_calendar_add_new_view_holder";

    public Row_select_calendar_add_new_view_holder(View v) {
        super(v);
    }

    TextView title;
    ImageView icon;

    @Override
    public void setup(View v) {
        this.v = v;
        title = (TextView) v.findViewById(R.id.title);
        icon = (ImageView) v.findViewById(R.id.icon);
        HttpRequest.getInstance().getCalendarType(this);
    }

    DrawerDto dto = null;

    @Override
    public void onGetGetDrawerSuccess(List<DrawerDto> dtos) {
        ArrayList<Integer> arr_remove = new ArrayList<Integer>();
        if (dtos != null && dtos.size() != 0) {
//            title.setText(dtos.get(0).getName());
            for (int i = 0; i < dtos.size(); i++) {
                if (dtos.get(i).getName().equals("내 일정")) {
//                    Log.e(TAG, "" + dtos.get(i).getCalendarNo());
                    AddNewScheduleFragment.CalendarNo = dtos.get(i).getCalendarNo();
                    UpdateScheduleFragment.CalendarNo = dtos.get(i).getCalendarNo();
                }
            }
            dto = dtos.get(0);
            handleSingleChoice(dtos);
        }
    }

    DrawerDto dtos_h = null;
    List<DrawerDto> dtos_handler = null;

    private void handleSingleChoice(final List<DrawerDto> dtos) {
        final List<String> temp = new ArrayList<>();
        dtos_handler = new ArrayList<DrawerDto>();
        for (int i = 0; i < dtos.size(); i++) {
            if (dtos.get(i).isRegYN()) {
                dtos_h = dtos.get(i);
                dtos_handler.add(dtos_h);
            }
        }
        for (int i = 0; i < dtos_handler.size(); i++) {
            temp.add(dtos_handler.get(i).getName());
        }
        AddNewScheduleFragment.CalendarType = dtos_handler.get(0).getType();
        UpdateScheduleFragment.CalendarType = dtos_handler.get(0).getType();
        title.setText(dtos_handler.get(0).getName());
//        Log.e(TAG, "typeAdd:" + AddNewScheduleFragment.CalendarType);
//        Log.e(TAG, "typeUpdate:" + UpdateScheduleFragment.CalendarType);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUtils.displaySingleChoiceList(title.getContext(), temp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dto = dtos_handler.get(which);
                        title.setText(dto.getName());
                        AddNewScheduleFragment.CalendarType = dto.getType();
                        UpdateScheduleFragment.CalendarType = dto.getType();
//                        Log.e(TAG, "typeAdd_CLICK:" + AddNewScheduleFragment.CalendarType);
//                        Log.e(TAG, "typeUpdate_CLICK:" + UpdateScheduleFragment.CalendarType);
                    }
                }, Utils.getString(R.string.string_select_calendar));
            }
        });
    }

    @Override
    public void onGetGetDrawerFail(ErrorDto errorDto) {

    }
}
