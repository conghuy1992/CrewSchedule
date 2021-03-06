package com.dazone.crewschedule.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.dazone.crewschedule.Dtos.PersonData;

import java.util.ArrayList;

/**
 * Created by david on 12/18/15.
 */
public class ListUtils {
    //    public static String getListName(ArrayList<PersonData> resultList) {
//        String result = "";
//        if (resultList != null && resultList.size() != 0) {
//            for (PersonData data : resultList) {
//                result += data.getmEmail() + ",";
//            }
//        }
//        if (TextUtils.isEmpty(result)) {
//            return result;
//        } else {
//            return result.substring(0, result.length() - 1);
//        }
//    }
    public static String getListName(ArrayList<PersonData> resultList) {
        String result = "";
        if (resultList != null && resultList.size() != 0) {
            for (int i = 0; i < resultList.size(); i++) {
                PersonData data = resultList.get(i);
                int UserNo = data.getUserNo();
                if (UserNo == 0) {
                    if (!check_root_folder(resultList, data))
                        result += data.getFullName() + ", ";
                } else {
                    if (!check_root_user(resultList, data)) {
                        result += data.getFullName() + ", ";
                    }
                }
            }
//            for (PersonData data : resultList) {
//                result += data.getFullName() + ", ";
//            }
        }
        if (TextUtils.isEmpty(result)) {
            return result;
        } else {
            return result.substring(0, result.length() - 2);
        }
    }

    public static boolean check_root_folder(ArrayList<PersonData> resultList, PersonData data) {
        for (int i = 0; i < resultList.size(); i++) {
            int DepartNo = resultList.get(i).getDepartNo();
            int UserNo = resultList.get(i).getUserNo();
            if (data.getDepartmentParentNo() == DepartNo && UserNo == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean check_root_user(ArrayList<PersonData> resultList, PersonData data) {
        for (int i = 0; i < resultList.size(); i++) {
            int DepartNo = resultList.get(i).getDepartNo();
            int UserNo = resultList.get(i).getUserNo();
            if (data.getDepartNo() == DepartNo && UserNo == 0) {
                return true;
            }
        }
        return false;
    }
}
