package com.dazone.crewschedule.Dtos;

/**
 * Created by nguyentiendat on 1/19/16.
 */
public class FileDto extends DataDto {

    private String FileName;
    private int AttachNo = 0;
    private int FileLength = 0;
    private String FileLengthToString;

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public int getAttachNo() {
        return AttachNo;
    }

    public void setAttachNo(int attachNo) {
        AttachNo = attachNo;
    }

    public int getFileLength() {
        return FileLength;
    }

    public void setFileLength(int fileLength) {
        FileLength = fileLength;
    }

    public String getFileLengthToString() {
        return FileLengthToString;
    }

    public void setFileLengthToString(String fileLengthToString) {
        FileLengthToString = fileLengthToString;
    }
}
