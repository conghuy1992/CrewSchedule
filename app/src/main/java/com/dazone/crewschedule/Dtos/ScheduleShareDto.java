package com.dazone.crewschedule.Dtos;

import com.dazone.crewschedule.Interfaces.IOrgDto;

/**
 * Created by david on 12/23/15.
 */
public class ScheduleShareDto extends DataDto implements IOrgDto {
    String Type;
    int No;
    String Name;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
