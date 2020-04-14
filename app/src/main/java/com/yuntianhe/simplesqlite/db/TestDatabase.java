package com.yuntianhe.simplesqlite.db;

import android.content.Context;

import com.yuntianhe.simplesqlite.annotation.Database;
import com.yuntianhe.simplesqlite.library.DatabaseDelegate;
import com.yuntianhe.simplesqlite.library.IDatabaseEntry;
import com.yuntianhe.simplesqlite.processor.TestTable;

/**
 * 描述:
 * 作者: daiwj on 2019/2/25 17:33
 */
@Database(name = "test_database", version = 1)
public class TestDatabase implements IDatabaseEntry {

    @Override
    public void onInit(Context context, String name, int version) {
    }

    @Override
    public void onCreate(DatabaseDelegate db) {
        db.createTable(new TestTable());
    }

    @Override
    public void onUpgrade(DatabaseDelegate db, int oldVersion, int newVersion) {

    }
}
