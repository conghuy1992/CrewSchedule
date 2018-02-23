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

/**
 * Created by HUYNHCONGHUY on 4/3/2016.
 */
public class ScheduleDivAdapter extends BaseAdapter {
    Context context;
    String color[];
    String data[];

    public ScheduleDivAdapter(Context c, String data[], String color[]) {
        this.context = c;
        this.data = data;
        this.color = color;
    }

    @Override
    public int getCount() {
        return data.length;
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
        ImageView color_imv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder vh;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.schedule_div_layout_adapter, parent, false);
            vh = new Viewholder();
            vh.content = (TextView) convertView.findViewById(R.id.content);
            vh.color_imv = (ImageView) convertView.findViewById(R.id.color_imv);
            convertView.setTag(vh);
        } else {
            vh = (Viewholder) convertView.getTag();
        }
        vh.content.setText(data[position]);
        vh.color_imv.setBackgroundColor(Color.parseColor(color[position]));
        return convertView;
    }
}
