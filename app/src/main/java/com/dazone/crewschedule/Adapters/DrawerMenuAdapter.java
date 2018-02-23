package com.dazone.crewschedule.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazone.crewschedule.Constant.Statics;
import com.dazone.crewschedule.Dtos.DrawerDto;
import com.dazone.crewschedule.Interfaces.DrawerTrigger;
import com.dazone.crewschedule.R;
import com.dazone.crewschedule.RCVViewHolders.DrawerMenuViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 1/5/16.
 */
public class DrawerMenuAdapter extends SelectionAdapter<DrawerDto> implements DrawerMenuViewHolder.CheckedCallback {
    String TAG = "DrawerMenuAdapter";
    int lineHeight = 0;
    int currentMonth = 0;
    List<DrawerDto> childList;
    public static List<DrawerDto> childList_1 = new ArrayList<>();
    public static List<DrawerDto> childList_2 = new ArrayList<>();
    public static List<DrawerDto> childList_3 = new ArrayList<>();
    DrawerTrigger trigger;
    public static List<DrawerDto> finalChildList = null;
    public static List<DrawerDto> dataSet_current = null;

    public DrawerMenuAdapter(List<DrawerDto> dataSet, List<DrawerDto> childList, DrawerTrigger trigger) {
        super(dataSet);
        this.childList = childList;
        this.trigger = trigger;
        getList(childList);
        finalChildList = childList;
        dataSet_current = dataSet;
        for (int i = 0; i < finalChildList.size(); i++) {
            finalChildList.get(i).setIsView(true);
        }
        for (int i = 0; i < dataSet_current.size(); i++) {
            dataSet_current.get(i).setIsView(true);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_drawer_menu, parent, false);
        vh = new DrawerMenuViewHolder(v);
        return vh;
    }

    @Override
    public void callback(boolean callback, int id) {
//        Utils.printLogs(" childList : " + childList);
//        if (childList == null)
//            return;
//        for (DrawerDto dto : dataSet) {
//            if (dto.getId() == id) {
//                dto.setIsView(callback);
//            }
//            if(dto.getId()==Statics.DRAWER_MENU_ID_ALL_CALENDAR&&id != Statics.DRAWER_MENU_ID_ALL_CALENDAR) {
//                if (!callback) {
//                    dto.setIsView(callback);
//                } else {
//                    dto.setIsView(checkChecked());
//                }
//            }
//        }
//        if(id==Statics.DRAWER_MENU_ID_ALL_CALENDAR) {
//            for (DrawerDto dto : childList) {
//                dto.setIsView(callback);
//            }
//            getList(childList);
//            this.notifyDataSetChanged();
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DrawerDto item = dataSet_current.get(position);
//        for (int i = 0; i < dataSet_current.size(); i++) {
//            Log.e(TAG, dataSet_current.get(i).getName() + ":" + dataSet_current.get(i).isView());
//        }
//        Log.e(TAG, "POS:" + item.getName() + ":" + item.isView());
//        Log.e(TAG,"size:"+childList_1.size()+":"+childList_2.size()+":"+childList_3.size());
//        if (childList_1.size() == 0) {
//
//        }
        DrawerMenuViewHolder viewHolder = (DrawerMenuViewHolder) holder;
        viewHolder.setTrigger(trigger);
        if (item.getIconId() == 0) {
            viewHolder.setCallback(this);
        }
        switch (item.getId()) {
            case Statics.DRAWER_MENU_ID_PUBLIC_CALENDAR:
                viewHolder.bindData(item, childList_1);
                break;
            case Statics.DRAWER_MENU_ID_PRIVATE_CALENDAR:
                viewHolder.bindData(item, childList_2);
                break;
            case Statics.DRAWER_MENU_ID_SHARE_CALENDAR:
                viewHolder.bindData(item, childList_3);
                break;
            default:
                viewHolder.bindData(item);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void getList(List<DrawerDto> list) {
        childList_1.clear();
        childList_2.clear();
        childList_3.clear();
        if (list != null && list.size() != 0) {
            for (DrawerDto dto : list) {
                if (dto.getType() == 1) {
                    if (dto.isRegYN())
                        childList_1.add(dto);
                }
                if (dto.getType() == 2) {
                    if (dto.isRegYN())
                        childList_2.add(dto);
                }
                if (dto.getType() == 3) {
                    if (dto.isRegYN())
                        childList_3.add(dto);
                }
            }
        }

    }

    private boolean checkChecked() {
        for (DrawerDto dto : dataSet) {
            if (dto.getId() == Statics.DRAWER_MENU_ID_SHARE_CALENDAR || dto.getId() == Statics.DRAWER_MENU_ID_PUBLIC_CALENDAR ||
                    dto.getId() == Statics.DRAWER_MENU_ID_PRIVATE_CALENDAR) {
                if (!dto.isView()) {
                    return false;
                }
            }

        }
        return true;
    }
}
