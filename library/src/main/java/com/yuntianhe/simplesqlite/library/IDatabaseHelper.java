package com.yuntianhe.simplesqlite.library;

import android.content.Context;

/**
 * desc:
 * author: daiwj on 2020-04-11 22:03
 */
public interface IDatabaseHelper {

    void onInit(Context context, String name, int version);

    void onCreate(DatabaseDelegate db);

    void onUpgrade(DatabaseDelegate db, int oldVersion, int newVersion);

}
