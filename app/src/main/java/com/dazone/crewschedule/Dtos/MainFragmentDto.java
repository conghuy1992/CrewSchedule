package com.dazone.crewschedule.Dtos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nguyentiendat on 1/13/16.
 */
public class MainFragmentDto extends DataDto implements Parcelable {
    long timeInMilliSecond = 0;
    int scheduleType = 0;

    public MainFragmentDto() {
    }

    public long getTimeInMilliSecond() {
        return timeInMilliSecond;
    }

    public void setTimeInMilliSecond(long timeInMilliSecond) {
        this.timeInMilliSecond = timeInMilliSecond;
    }

    public int getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(int scheduleType) {
        this.scheduleType = scheduleType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.id);
        dest.writeInt(this.scheduleType);
        dest.writeLong(this.timeInMilliSecond);
    }

    protected MainFragmentDto(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.id = in.readInt();
        this.scheduleType = in.readInt();
        this.timeInMilliSecond = in.readLong();
    }
    public static final Parcelable.Creator<MainFragmentDto> CREATOR = new Parcelable.Creator<MainFragmentDto>() {
        public MainFragmentDto createFromParcel(Parcel source) {
            return new MainFragmentDto(source);
        }

        public MainFragmentDto[] newArray(int size) {
            return new MainFragmentDto[size];
        }
    };
}
