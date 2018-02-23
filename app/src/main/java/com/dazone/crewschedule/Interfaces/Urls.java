package com.dazone.crewschedule.Interfaces;

public interface Urls {
    String URL_ROOT = "/UI/MobileSchedule/MobileDataService.asmx/";
    String URL_ROOT_SERVICE = "/UI/WebService/WebServiceCenter.asmx/";


    String URL_GET_MONTH_SCHEDULE = URL_ROOT + "GetMonthSchedules";
    String URL_GET_PERIOD_SCHEDULE = URL_ROOT + "GetPeriodSchedules";
    String URL_GET_DAY_SCHEDULE = URL_ROOT + "GetDaySchedules";//sessionId,languageCode,timeZoneOffset,currDate,scheduleType
    String URL_GET_CALENDAR = URL_ROOT + "GetCalendar";
    String URL_GET_SCHEDULE = URL_ROOT + "GetSchedule";
    String URL_GET_VIEW_CALENDAR = URL_ROOT + "GetViewCalendars";
    String URL_INSERT_CALENDAR = URL_ROOT + "InsertCalendar";
    String URL_DELETE_CALENDAR = URL_ROOT + "DeleteCalendar";
    String URL_UPDATE_CALENDAR = URL_ROOT + "UpdateCalendar";
    String URL_GET_CALENDARS = URL_ROOT + "GetCalendars";
    String URL_TEST_FUNCTION = URL_ROOT + "TestFunction";
    String URL_INSERT_SCHEDULE = URL_ROOT + "InsertSchedule";
    String URL_DELETE_SCHEDULE = URL_ROOT + "DeleteSchedule";
    String URL_UPDATE_SCHEDULE = URL_ROOT + "UpdateSchedule";

    String URL_GET_USER = URL_ROOT_SERVICE + "GetUser";
    String URL_GET_ALL_USER = URL_ROOT_SERVICE + "GetAllOfUsers";
    String URL_GET_DEPARTMENT = URL_ROOT_SERVICE + "GetDepartments";
    String URL_DOWNLOAD_ATTACH = "UI/MobileSchedule/MobileFileDownload.ashx?";

}