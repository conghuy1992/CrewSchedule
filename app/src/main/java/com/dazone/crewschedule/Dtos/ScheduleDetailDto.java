package com.dazone.crewschedule.Dtos;

import java.util.List;

/**
 * Created by nguyentiendat on 1/12/16.
 */
public class ScheduleDetailDto extends DataDto {
    private int ScheduleNo;
    private int RegUserNo;


    private String UserName;
    private String PositionName;
    private String RegDate;
    private String Title;
    private String Content;
    private String DayContent;
    private String TimeContent;
    private String RepeatContent;

    List<ScheduleShareDto> Sharers;
    List<FileDto> Files;

    public List<FileDto> getFiles() {
        return Files;
    }

    public void setFiles(List<FileDto> files) {
        Files = files;
    }


    public ScheduleDetailDto() {
    }

    public int getScheduleNo() {
        return ScheduleNo;
    }

    public void setScheduleNo(int scheduleNo) {
        ScheduleNo = scheduleNo;
    }

    public int getRegUserNo() {
        return RegUserNo;
    }

    public void setRegUserNo(int regUserNo) {
        RegUserNo = regUserNo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
    }

    @Override
    public String getTitle() {
        return Title;
    }

    @Override
    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public String getContent() {
        return Content;
    }

    @Override
    public void setContent(String content) {
        Content = content;
    }

    //    public String getDayContent() {
//        return DayContent.split("From")[0];
//    }
    public String getDayContent() {
        return DayContent;
    }

    public void setDayContent(String dayContent) {
        DayContent = dayContent;
    }

    public String getTimeContent() {
        return TimeContent;
    }

    public void setTimeContent(String timeContent) {
        TimeContent = timeContent;
    }

    public String getRepeatContent() {
        return RepeatContent;
    }

    public void setRepeatContent(String repeatContent) {
        RepeatContent = repeatContent;
    }

    public List<ScheduleShareDto> getSharers() {
        return Sharers;
    }

    public void setSharers(List<ScheduleShareDto> sharers) {
        Sharers = sharers;
    }
}
