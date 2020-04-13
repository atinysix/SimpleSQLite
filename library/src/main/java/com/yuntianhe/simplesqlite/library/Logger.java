package com.yuntianhe.simplesqlite.library;

import android.util.Log;

/**
 * desc:
 * author: daiwj on 2020-04-12 21:20
 */
public class Logger {

    public static boolean sLoggable = true;

    public static final String TAG = "SimpleSQLite";

    public static void setLoggable(boolean loggable) {
        sLoggable = loggable;
    }

    public static void d(String message) {
        if (sLoggable) {
            Log.d(TAG, message);
        }
    }

    public static void w(String message) {
        if (sLoggable) {
            Log.w(TAG, message);
        }
    }

    public static void e(String message) {
        if (sLoggable) {
            Log.e(TAG, message);
        }
    }
}
