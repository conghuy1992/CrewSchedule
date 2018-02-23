

package com.dazone.crewschedule.AddNewViewHolder;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazone.crewschedule.Dtos.DrawerDto;
import com.dazone.crewschedule.Dtos.ErrorDto;
import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.Interfaces.GetCalendarTypeCallBack;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.Utils.DialogUtils;
import com.dazone.crewschedule.Utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class Row_select_schedule_div_add_new_view_holder extends BaseAddNewViewHolder {
    View v;

    public Row_select_schedule_div_add_new_view_holder(View v) {
        super(v);
    }

    TextView title;
    ImageView icon;

    @Override
    public void setup(View v) {
        this.v = v;
        title = (TextView) v.findViewById(R.id.title);
        icon = (ImageView) v.findViewById(R.id.icon);
    }

    DrawerDto dto = null;


    private void handleSingleChoice(final List<DrawerDto> dtos) {

        final List<String> temp = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            temp.add(dtos.get(i).getName());
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUtils.displaySingleChoiceList(title.getContext(), temp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dto = dtos.get(which);
                        title.setText(dto.getName());
                    }
                }, Utils.getString(R.string.string_schedule_div));
            }
        });
    }

}
