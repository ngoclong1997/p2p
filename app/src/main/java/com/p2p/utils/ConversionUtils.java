package com.p2p.utils;

import java.text.DecimalFormat;

public class ConversionUtils {
    public static String doubleToString(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        return decimalFormat.format(num);
    }
    public static String doubleToString(double num, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(num);
    }
    public static String intToString(int num) {
        return String.valueOf(num);
    }
    public static int stringToInt(String str, int defNum) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            return defNum;
        }
    }
}
