package com.android.onehuman.cleanlauncher.utils;

public class Utils {

    public static boolean isHeader(String label, String previous) {
        return label.charAt(0) != previous.charAt(0);
    }

}