package com.dazone.crewschedule.Utils;

import com.dazone.crewschedule.Interfaces.IOrgDto;

import java.util.List;

/**
 * Created by david on 12/23/15.
 */
public class DaZoneTextUtils {
    public static String getFirstLetter(String string){
        if(!android.text.TextUtils.isEmpty(string)){
            String[] temp = string.split(" ");
            if(temp.length>1)
            {
                return temp[0].substring(0, 1).toUpperCase()+temp[temp.length-1].substring(0, 1).toUpperCase();
            }
            else {
                return temp[0].substring(0, 1).toUpperCase();
            }
        }else{
            return "";
        }
    }
    public static String getName(List<IOrgDto> list)
    {
        String temp = "";
        if(list==null|| list.size()==0)
        {
            return "";
        }
        for(IOrgDto dto: list)
        {
            temp += dto.getName()+",";
        }
        return temp.substring(0,temp.length()-1);
    }
}
