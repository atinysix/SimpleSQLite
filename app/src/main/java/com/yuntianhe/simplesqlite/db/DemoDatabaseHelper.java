package com.yuntianhe.simplesqlite.db;

import android.content.Context;

import com.yuntianhe.simplesqlite.annotation.Database;
import com.yuntianhe.simplesqlite.library.DatabaseDelegate;
import com.yuntianhe.simplesqlite.library.IDatabaseHelper;
import com.yuntianhe.simplesqlite.compiler.DemoTable;

/**
 * 描述:
 * 作者: daiwj on 2019/2/25 17:33
 */
@Database(name = "demo_database", version = 1)
public class DemoDatabaseHelper implements IDatabaseHelper {

    @Override
    public void onInit(Context context, String name, int version) {

    }

    @Override
    public void onCreate(DatabaseDelegate db) {
        db.createTable(new DemoTable());
    }

    @Override
    public void onUpgrade(DatabaseDelegate db, int oldVersion, int newVersion) {

    }
}
