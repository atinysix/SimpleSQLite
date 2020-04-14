package com.yuntianhe.simplesqlite.library;

import android.database.sqlite.SQLiteDatabase;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 11:39
 */
public class DatabaseDelegate {

    private SQLiteDatabase mDatabase;

    DatabaseDelegate(SQLiteDatabase db) {
        mDatabase = db;
    }

    public final void createTable(AbstractTable table) {
        executeSql(table.onCreateInitSql());
    }

    public final void executeSql(String sql) {
        Logger.d("DatabaseDelegate execute sql: " + sql);
        mDatabase.execSQL(sql);
    }

}
