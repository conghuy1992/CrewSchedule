package com.dazone.crewschedule.Constant;

/**
 * Created by david on 12/18/15.
 */
public interface Statics {


//    preft key

    String ORGANIZATION_TREE = "organization_tree";

//    end preft key

    //    bundle key
    String BUNDLE_LIST_PERSON = "listPerson";
    String BUNDLE_ORG_DISPLAY_SELECTED_ONLY = "isDisplaySelectedOnly";
    String BUNDLE_POSITION = "position";
//    end bundle key

    int REQUEST_TIMEOUT_MS = 15000;

    String TAG = "CrewScheduleLogs";
    String TAG_DAVID = "David_tags";
    String PREFS_KEY_SESSION_ERROR = "session_error";

    //    database
    int DATABASE_VERSION = 1;
    String DATABASE_NAME = "crewchat.db";


    //main version
    int MAIN_ACTIVITY_TAB_COUNT = 4;

    //format date
    String DATE_FORMAT_YY_MM_DD_DD = "yy-MM-dd-EEEEEEE";
    String DATE_FORMAT_YY_MM_DD_DD_H_M = "yy-MM-dd-EEEEEEE hh:mm aa";
    String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    String DATE_FORMAT_DD = "dd";
    String DATE_FORMAT_DD_CHAR = "EEEEEEE";
    String DATE_FORMAT_YY_MM_DD_H_M = "yy-MM-dd hh:mm aa";
    String DATE_TOOLBAR_FORMAT_YY_MM = "yyyy-MM";
    String DATE_TOOLBAR_FORMAT_DD = "DD";
    String DATE_FORMAT_MONTH = "MM";
    String DATE_FORMAT_YEAR = "yyyy";
    String DATE_FORMAT_MONTH_NAME = "MMM";
    String DATE_FORMAT_HH_MM_AA = "hh:mm aa";
    //for intent
    int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    int IMAGE_PICKER_SELECT = 101;

    String DIALOG_DATE = "dialog_date";


    int DRAWER_MENU_ID_AGENDA = 0;
    int DRAWER_MENU_ID_DAY = 1;
    int DRAWER_MENU_ID_WEEK = 2;
    int DRAWER_MENU_ID_MONTH = 3;
    int DRAWER_MENU_ID_ALL_CALENDAR = 4;
    int DRAWER_MENU_ID_PUBLIC_CALENDAR = 5; //type = 1;
    int DRAWER_MENU_ID_PRIVATE_CALENDAR = 6;//type = 2;
    int DRAWER_MENU_ID_SHARE_CALENDAR = 7;//type = 3;
//    int DRAWER_MENU_ID_DDAY = 8;
    //

    int FILE_PICKER_SELECT = 100;
    int ORGANIZATION_TO_ACTIVITY = 300;
    int ORGANIZATION_DISPLAY_SELECTED_ACTIVITY = 303;

    //file type
    String IMAGE_JPG = ".jpg";
    String IMAGE_JPEG = ".jpeg";
    String IMAGE_PNG = ".png";
    String IMAGE_GIF = ".gif";
    String AUDIO_MP3 = ".mp3";
    String AUDIO_WMA = ".wma";
    String AUDIO_AMR = ".amr";
    String VIDEO_MP4 = ".mp4";
    String FILE_PDF = ".pdf";
    String FILE_DOCX = ".docx";
    String FILE_DOC = ".doc";
    String FILE_XLS = ".xls";
    String FILE_XLSX = ".xlsx";
    String FILE_PPTX = ".pptx";
    String FILE_PPT = ".ppt";
    String FILE_ZIP = ".zip";
    String FILE_RAR = ".rar";
    String FILE_APK = ".apk";
    String MIME_TYPE_AUDIO = "audio/*";
    /*    public static final String MIME_TYPE_TEXT = "application/vnd.google-apps.file";*/
    String MIME_TYPE_IMAGE = "image/*";
    String MIME_TYPE_VIDEO = "video/*";
    String MIME_TYPE_APP = "file/*";
    String MIME_TYPE_TEXT = "text/*";
    String MIME_TYPE_ALL = "*/*";
    String MIME_TYPE_PDF = "application/pdf";
    String MIME_TYPE_ZIP = "application/zip";
    String MIME_TYPE_RAR = "application/x-rar-compressed";
    String MIME_TYPE_DOC = "application/doc";
    String MIME_TYPE_XLSX = "application/xls";
    String MIME_TYPE_PPTX = "application/ppt";
    String MIME_TYPE_APK = "application/vnd.android.package-archive";
}
