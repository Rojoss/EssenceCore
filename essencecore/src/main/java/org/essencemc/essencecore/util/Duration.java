package org.essencemc.essencecore.util;

import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Duration {

    private String string = "";
    private Long time = 0l;

    private boolean success = false;
    private EText error;

    private final String[] patterns = new String[] {"\\d+ms", "\\d+s", "\\d+m", "\\d+h", "\\d+d"};

    /**
     * Parses the given duration string to time in milliseconds.
     * The string supports days[d], hours[h], minutes[m], seconds[s] and miliseconds[ms].
     * Some valid input strings are:
     * 1d10h, 5h10m3s, 500ms, 10m
     * @param string The string that needs to be parsed.
     */
    public Duration(String string) {
        this.string = string;
        success = true;

        int[] multipliers = new int[] {1, Time.MS_IN_SEC, Time.MS_IN_MIN, Time.MS_IN_HOUR, Time.MS_IN_DAY};
        Long ms = 0l;

        for (int i = 0; i < patterns.length; i++) {
            Pattern pattern = Pattern.compile(patterns[i]);
            Matcher matcher = pattern.matcher(string);
            if (matcher.find()) {
                string = string.replaceAll(matcher.group(0), "");
                Integer val = NumberUtil.getInt(matcher.group(0).replaceAll("[^\\d.]", ""));
                if (val != null) {
                    ms += val * multipliers[i];
                } else {
                    success = false;
                    error = Message.INVALID_DURATION.msg().parseArgs(string, matcher.group(0));
                }
            }
        }

        if (ms <= 0) {
            error = Message.INVALID_DURATION_NOT_ZERO.msg().parseArgs(string);
            success = false;
        }
        this.time = ms;
    }

    /**
     * Parses the given time to a duration string.
     * The duration string will look like 1d10h5m
     * @param ms The time in milliseconds that needs to be parsed.
     */
    public Duration(Long ms) {
        this.time = ms;
        Time time = new Time(ms);
        String str = "";
        if (time.getDays() > 0)
            str += time.getDays() + "d";
        if (time.getHours() > 0)
            str += time.getHours() + "h";
        if (time.getMinutes() > 0)
            str += time.getMinutes() + "m";
        if (time.getSeconds() > 0)
            str += time.getSeconds() + "s";
        if (time.getMs() > 0)
            str += time.getMs() + "ms";
        this.string = str;
        success = true;
    }

    /**
     * Get the formated duration string.
     * @return String with proper duration formatting like 1d10h5m10s100ms
     */
    public String getString() {
        return string;
    }

    /**
     * Get the formated duration string.
     * @param depth When greater than 0 it will strip things at the bottom.
     *              For example when the depth is 1 and the string has miliseconds they won't be added to the return string.
     *              And if the depth is 3 it will only show days and hours.
     * @return String with proper duration formatting like 1d10h5m10s100ms
     */
    public String getString(int depth) {
        if (depth > 0) {
            for (int i = 0; i < depth; i++) {
                string = string.replaceAll(patterns[i], "");
            }
        }
        return string;
    }

    /**
     * Get the depth of the duration string.
     * ms=1 s=2 m=3 h=4 d=5
     * If the string is 1ms the depth would 1 and when it's 1h the depth would be 4
     * If the string has multiple times the highest depth will be returned.
     * So a string like 10m5s100ms would return depth 3 for minutes.
     * @return The depth of the duration.
     */
    public int getDepth() {
        //TODO: Get depth of string.
        return 0;
    }

    /**
     * Gets the duration in milliseconds.
     * @return Duration in milliseconds.
     */
    public Long getMS() {
        return time;
    }

    /**
     * Checks if the parsing was successful or not.
     * If this returns false you should call getError() and send it to the user.
     * @return Wether the parsing was successful or not.
     */
    public boolean isValid() {
        return success && error == null;
    }

    /**
     * If the parsing wasn't successful this will return the error message.
     * @return EText with the error if there is any. Otherwise null.
     */
    public EText getError() {
        return error;
    }
}
