package com.dazone.crewschedule.Dtos;

import com.dazone.crewschedule.Utils.Utils;

/**
 * Created by nguyentiendat on 1/19/16.
 */
public class DrawerDto extends DataDto {

    private String Name;
    private boolean isView = false;
    private int Type = 0;
    private int iconId = 0;
    private int CalendarNo;
    private String Color;
    private boolean isRegYN = false;

    public DrawerDto(String name,int iconId) {
        Name = name;
        this.iconId = iconId;
    }
    public DrawerDto(int name,int iconId,int id) {
        Name = Utils.getString(name);
        this.iconId = iconId;
        this.id = id;
    }
    public DrawerDto(int name,int id) {
        Name = Utils.getString(name);
        this.id = id;
    }

    public boolean isRegYN() {
        return isRegYN;
    }

    public void setIsRegYN(boolean isRegYN) {
        this.isRegYN = isRegYN;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isView() {
        return isView;
    }

    public void setIsView(boolean isView) {
        this.isView = isView;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getCalendarNo() {
        return CalendarNo;
    }

    public void setCalendarNo(int calendarNo) {
        CalendarNo = calendarNo;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
