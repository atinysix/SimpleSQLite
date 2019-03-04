package com.yuntianhe.simplesqlite;

import android.app.Application;

import com.yuntianhe.simplesqlite.db.DBManagerSingleton;

/**
 * 描述:
 * 作者: daiwj on 2019/2/20 14:56
 */
public class App extends Application {

    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

        DBManagerSingleton.getInstance().init();
    }

    public static App getApp() {
        return mApp;
    }
}
