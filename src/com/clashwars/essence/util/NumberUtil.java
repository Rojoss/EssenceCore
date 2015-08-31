package com.clashwars.essence.util;

import java.math.BigDecimal;

public class NumberUtil {

    /**
     * Convert a string like '1' to a int. Returns -1 if it's invalid.
     * @param str
     * @return int
     */
    public static int getInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
        }
        return -1;
    }

    /**
     * Convert a string like '1.5' to a double. Returns -1 if it's invalid.
     * @param str
     * @return double
     */
    public static double getDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
        }
        return -1;
    }

    /**
     * Convert a string like '1.12' to a float. Returns -1 if it's invalid.
     * @param str
     * @return float
     */
    public static float getFloat(String str) {
        if (str != null && str != "") {
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException e) {
            }
        }
        return -1;
    }

    /**
     * Round a double value.
     * @param val
     * @return rounded double
     */
    public static double roundDouble(double val) {
        val = val * 100;
        val = Math.round(val);
        val = val / 100;
        return val;
    }

    /**
     * Round a float value with a specific number of decimals.
     */
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * Get percentage based on 2 ints.
     * For example small=10 big = 50 will return 10/50*100 = 20.0%
     * @param smallInt
     * @param bigInt
     * @return Percentage as double
     */
    public static double getPercentage(int smallInt, int bigInt) {
        return ((double) (smallInt) / bigInt) * 100;
    }
}
