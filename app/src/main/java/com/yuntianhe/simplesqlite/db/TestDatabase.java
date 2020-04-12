package com.yuntianhe.simplesqlite.db;

import android.content.Context;

import com.yuntianhe.simplesqlite.annotations.Database;
import com.yuntianhe.simplesqlite.library.DatabaseWrapper;
import com.yuntianhe.simplesqlite.library.IDatabaseFactory;
import com.yuntianhe.simplesqlite.processor.TestTable;

/**
 * 描述:
 * 作者: daiwj on 2019/2/25 17:33
 */
@Database(name = "test_database", version = 1)
public class TestDatabase implements IDatabaseFactory {

    @Override
    public void onInit(Context context) {

    }

    @Override
    public void onCreate(DatabaseWrapper db) {
        db.createTable(new TestTable());
    }

    @Override
    public void onUpgrade(DatabaseWrapper db, int oldVersion, int newVersion) {

    }
}
