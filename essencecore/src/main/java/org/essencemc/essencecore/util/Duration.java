package org.essencemc.essencecore.util;

import org.essencemc.essencecore.message.EText;

public class Duration {

    private String string;
    private Long time;

    private boolean success = false;
    private EText error;

    /**
     * Parses the given duration string to time in milliseconds.
     * The string supports days[d], hours[h], minutes[m], seconds[s] and miliseconds[ms].
     * Some valid input strings are:
     * 1d10h, 5h10m3s, 500ms, 10m
     * @param string The string that needs to be parsed.
     */
    public Duration(String string) {
        this.string = string;
        //TODO: Format string to miliseconds.
        success = true;
    }

    /**
     * Parses the given time to a duration string.
     * The duration string will look like 1d10h5m
     * @param time The time in milliseconds that needs to be parsed.
     */
    public Duration(Long time) {
        this.time = time;
        //TODO: Format time to string.
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
        //TODO: Strip time of duration based on depth.
        return string;
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
