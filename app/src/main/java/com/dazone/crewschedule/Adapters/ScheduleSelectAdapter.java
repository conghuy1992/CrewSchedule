package com.dazone.crewschedule.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazone.crewschedule.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CONGHUY on 4/4/2016.
 */
public class ScheduleSelectAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> arr;

    public ScheduleSelectAdapter(Context c,ArrayList<String> arr) {
        this.context = c;

        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class Viewholder {
        TextView content;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder vh;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.schedule_select_layout_adapter, parent, false);
            vh = new Viewholder();
            vh.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(vh);
        } else {
            vh = (Viewholder) convertView.getTag();
        }
        vh.content.setText(arr.get(position));
        return convertView;
    }
}
