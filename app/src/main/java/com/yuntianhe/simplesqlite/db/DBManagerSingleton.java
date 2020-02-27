package com.yuntianhe.simplesqlite.db;


import com.yuntianhe.simplesqlite.App;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 17:17
 */
public class DBManagerSingleton {

    public static final String TAG = DBManagerSingleton.class.getSimpleName();

    public static final int DB_VERSION = 1;

    private static volatile DBManagerSingleton sGIMDBManager;

    private MyOpenHelper mOpenHelper;

    private DBManagerSingleton() {
        mOpenHelper = new MyOpenHelper(App.getApp(), "test.db", DB_VERSION);
    }

    public static final DBManagerSingleton getInstance() {
        if (sGIMDBManager == null) {
            synchronized (DBManagerSingleton.class) {
                if (sGIMDBManager == null) {
                    sGIMDBManager = new DBManagerSingleton();
                }
            }
        }
        return sGIMDBManager;
    }

    public MyOpenHelper getOpenHelper() {
        return mOpenHelper;
    }

    public void init() {

    }

}
