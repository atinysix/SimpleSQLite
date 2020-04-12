package com.yuntianhe.simplesqlite.library;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 11:39
 */
public class DatabaseWrapper {

    private SQLiteDatabase mDatabase;

    DatabaseWrapper(SQLiteDatabase db) {
        mDatabase = db;
    }

    public final void createTable(AbstractTable table) {
        final String sql = table.onCreateInitSql();
        Log.w("SimpleSQLite", "execute sql: " + sql);
        mDatabase.execSQL(table.onCreateInitSql());
    }

    public final void deleteTable(AbstractTable table) {

    }
}
