package com.dazone.crewschedule.Dtos;

/**
 * Created by nguyentiendat on 1/12/16.
 */
public class ScheduleTimeDto extends DataDto {
    private long Ticks;
    private long Days;
    private long Hours;
    private long Milliseconds;
    private long Minutes;
    private long Seconds;
    private double TotalDays;
    private double TotalHours;
    private long TotalMilliseconds;
    private long TotalMinutes;
    private long TotalSeconds;

    public long getTicks() {
        return Ticks;
    }

    public void setTicks(long ticks) {
        Ticks = ticks;
    }

    public long getDays() {
        return Days;
    }

    public void setDays(long days) {
        Days = days;
    }

    public long getHours() {
        return Hours;
    }

    public void setHours(long hours) {
        Hours = hours;
    }

    public long getMilliseconds() {
        return Milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        Milliseconds = milliseconds;
    }

    public long getMinutes() {
        return Minutes;
    }

    public void setMinutes(long minutes) {
        Minutes = minutes;
    }

    public long getSeconds() {
        return Seconds;
    }

    public void setSeconds(long seconds) {
        Seconds = seconds;
    }

    public double getTotalDays() {
        return TotalDays;
    }

    public void setTotalDays(double totalDays) {
        TotalDays = totalDays;
    }

    public double getTotalHours() {
        return TotalHours;
    }

    public void setTotalHours(double totalHours) {
        TotalHours = totalHours;
    }

    public long getTotalMilliseconds() {
        return TotalMilliseconds;
    }

    public void setTotalMilliseconds(long totalMilliseconds) {
        TotalMilliseconds = totalMilliseconds;
    }

    public long getTotalMinutes() {
        return TotalMinutes;
    }

    public void setTotalMinutes(long totalMinutes) {
        TotalMinutes = totalMinutes;
    }

    public long getTotalSeconds() {
        return TotalSeconds;
    }

    public void setTotalSeconds(long totalSeconds) {
        TotalSeconds = totalSeconds;
    }
}
