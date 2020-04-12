package com.yuntianhe.simplesqlite.library;

import android.content.Context;

/**
 * desc:
 * author: daiwj on 2020-04-11 22:03
 */
public interface IDatabaseFactory {

    void onInit(Context context);

    void onCreate(DatabaseWrapper db);

    void onUpgrade(DatabaseWrapper db, int oldVersion, int newVersion);

}
