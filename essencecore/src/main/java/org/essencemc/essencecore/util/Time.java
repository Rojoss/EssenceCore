package org.essencemc.essencecore.util;

import java.sql.Timestamp;

public class Time {

    public static final int MS_IN_DAY = 86400000;
    public static final int MS_IN_HOUR = 3600000;
    public static final int MS_IN_MIN = 60000;
    public static final int MS_IN_SEC = 1000;

    private int days = 0;
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    private int ms = 0;

    private Long time;

    /**
     * Creates a new Time instance based on the Long time in milliseconds.
     * @param time milliseconds to covert.
     */
    public Time(Long time) {
        this.time = time;

        days = (int)(long)time / MS_IN_DAY;
        time = time - days * MS_IN_DAY;

        hours = (int)(long)time / MS_IN_HOUR;
        time = time - hours * MS_IN_HOUR;

        minutes = (int)(long)time / MS_IN_MIN;
        time = time - minutes * MS_IN_MIN;

        seconds = (int)(long)time / MS_IN_SEC;
        time = time - seconds * MS_IN_SEC;

        ms = (int)(long)time;
    }

    /**
     * Creates a new Time instance based on the time values.
     * @param days The amount of days.
     * @param hours The amount of hours.
     * @param minutes The amount of minutes.
     * @param seconds The amount of seconds.
     * @param ms The milliseconds.
     */
    public Time(int days, int hours, int minutes, int seconds, int ms) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.ms = ms;
        this.time = (long)days * MS_IN_DAY + hours * MS_IN_HOUR + minutes * MS_IN_MIN + seconds * MS_IN_SEC + ms;
    }

    /**
     * Create a new Time instance based on the sql Timestamp.
     * @param timestamp Timestamp to convert.
     */
    public Time(Timestamp timestamp) {
        this(timestamp.getTime());
    }

    /**
     * Create a new Time instance based on the Duration.
     * If the duration isn't valid the time will be 0!
     * @param duration Duration to convert.
     */
    public Time(Duration duration) {
        this(duration.getMS());
    }

    /**
     * Get the days value.
     * @return days
     */
    public int getDays() {
        return days;
    }

    /**
     * Get the hours value.
     * @return hours
     */
    public int getHours() {
        return hours;
    }

    /**
     * Get the minutes value.
     * @return minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Get the seconds value.
     * @return seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Get the milliseconds value.
     * @return milliseconds
     */
    public int getMs() {
        return ms;
    }

    /**
     * Get the full time in milliseconds.
     * @return Time in milliseconds.
     */
    public Long getTime() {
        return time;
    }

    /**
     * Format a timestamp to a string with days/hours/mins/secs/ms.
     * For example: '%Dd %H:%M:%S' will be replaced with something like '1d 23:12:52'
     * The possible options in the syntax are:
     * %D = Days
     * %H = Hours
     * %M = Minutes
     * %S = Seconds
     * %MS = MilliSeconds
     * %% Remainder percentage of seconds with 1 decimal like '%S.%%s' could be '34.1s'
     * %%% Remainder percentage of seconds with 2 decimals like '%S.%%%s' could be '34.13s'
     * @param syntax The string with the above options which will be replaced with the time.
     * @param excludeZeroValues When set to true it won't display values that are 0.
     * @param extraZeros When set to true an extra zero will be added to hours,minutes and days when the value is less than 10 like 05 etc.
     * @return Formatted time string.
     */
    public String format(String syntax, boolean excludeZeroValues, boolean extraZeros) {
        syntax = syntax.replace("%%%", "" + (excludeZeroValues && ms <= 0 ? "" : (ms/10)));
        syntax = syntax.replace("%%", "" + (excludeZeroValues && ms <= 0 ? "" : (ms/100)));
        syntax = syntax.replace("%MS", "" + (excludeZeroValues && ms <= 0 ? "" : ms));
        syntax = syntax.replace("%S", "" + (excludeZeroValues && seconds <= 0 ? "" : (seconds < 10 && extraZeros ? "0"+seconds : seconds)));
        syntax = syntax.replace("%M", "" + (excludeZeroValues && minutes <= 0 ? "" : (minutes < 10 && extraZeros ? "0"+minutes : minutes)));
        syntax = syntax.replace("%H", "" + (excludeZeroValues && hours <= 0 ? "" : (hours < 10 && extraZeros ? "0"+hours : hours)));
        syntax = syntax.replace("%D", "" + (excludeZeroValues && days <= 0 ? "" : days));
        return syntax;
    }
}
