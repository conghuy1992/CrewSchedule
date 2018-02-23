package com.dazone.crewschedule.Interfaces;

public interface OAUTHUrls {
    String URL_ROOT = "/UI/WebService/WebServiceCenter.asmx/";
    String URL_DEFAULT_API = "http://dazone.crewcloud.net/";
    String URL_GET_LOGIN = URL_ROOT + "Login_v2";
    String URL_INSERT_PHONE_TOKEN = URL_DEFAULT_API + "/UI/WebService/WebServiceCenter.asmx/AddPhoneTokens";
    String URL_CHECK_SESSION = URL_ROOT + "CheckSessionUser_v2";
    String URL_CHECK_DEVICE_TOKEN = URL_DEFAULT_API + "/UI/WebService/WebServiceCenter.asmx/CheckPhoneToken";
    String URL_LOG_OUT = URL_ROOT + "LogOutUser";
    String URL_GET_DOMAIN = URL_DEFAULT_API + "/UI/WebService/WebServiceCenter.asmx/GetListDomain";

//    String CHECK_VERSION_LINK = "http://www.bizsw.co.kr:8080/Attachments/AppVn/Version/";
//    String DOWNLOAD_LINK = "http://www.bizsw.co.kr:8080/Attachments/AppVn/File/";

    String CHECK_VERSION_LINK = URL_DEFAULT_API + "Android/Version/";
    String DOWNLOAD_LINK = URL_DEFAULT_API + "Android/Package/";

}