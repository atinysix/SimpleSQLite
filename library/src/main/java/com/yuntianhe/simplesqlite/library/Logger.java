package com.yuntianhe.simplesqlite.library;

import android.util.Log;

/**
 * desc:
 * author: daiwj on 2020-04-12 21:20
 */
public class Logger {

    public static final String TAG = "SimpleSQLite";

    public static void d(String message) {
        Log.d(TAG, message);
    }

    public static void w(String message) {
        Log.w(TAG, message);
    }

    public static void e(String message) {
        Log.e(TAG, message);
    }
}
