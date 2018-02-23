package com.dazone.crewschedule.Utils;

import com.dazone.crewschedule.Constant.Statics;

/**
 * Created by David on 2/3/16.
 */
public class FileUtils {
    public static int getTypeFile(String typeFile)
    {
        int type = 0;
        switch (typeFile) {
            case Statics.IMAGE_GIF:
            case Statics.IMAGE_JPEG:
            case Statics.IMAGE_JPG:
            case Statics.IMAGE_PNG:
                type = 1;
                break;
            case Statics.VIDEO_MP4:
                type = 2;
                break;
            case Statics.AUDIO_MP3:
            case Statics.AUDIO_AMR:
            case Statics.AUDIO_WMA:
                type = 3;
                break;
            case Statics.FILE_DOC:
            case Statics.FILE_DOCX:
                type = 4;
                break;
            case Statics.FILE_XLS:
            case Statics.FILE_XLSX:
                type = 5;
                break;
            case Statics.FILE_PDF:
                type = 6;
                break;
            case Statics.FILE_PPT:
            case Statics.FILE_PPTX:
                type = 7;
                break;
            case Statics.FILE_ZIP:
                type = 8;
                break;
            case Statics.FILE_RAR:
                type = 9;
                break;
            case Statics.FILE_APK:
                type = 10;
                break;
            default:
                type = 11;
                break;
        }
        return type;
    }
}

