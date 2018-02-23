package com.dazone.crewschedule.Dtos;

/**
 * Created by nguyentiendat on 1/12/16.
 */
public class ScheduleDto extends DataDto {
    private int ScheduleNo;
    private int CalendarType;
    private int CalendarNo;

    private String Title;
    private String CalendarColor;

    private String DivisionNo;

    private String StartTime;
    private String EndTime;

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getDivisionNo() {
        return DivisionNo;
    }

    public void setDivisionNo(String divisionNo) {
        DivisionNo = divisionNo;
    }

    public int getScheduleNo() {
        return ScheduleNo;
    }

    public void setScheduleNo(int scheduleNo) {
        ScheduleNo = scheduleNo;
    }

    public int getCalendarType() {
        return CalendarType;
    }

    public void setCalendarType(int calendarType) {
        CalendarType = calendarType;
    }

    public int getCalendarNo() {
        return CalendarNo;
    }

    public void setCalendarNo(int calendarNo) {
        CalendarNo = calendarNo;
    }

    @Override
    public String getTitle() {
        return Title;
    }

    @Override
    public void setTitle(String title) {
        Title = title;
    }

    public String getCalendarColor() {
        return CalendarColor;
    }

    public void setCalendarColor(String calendarColor) {
        CalendarColor = calendarColor;
    }
}
