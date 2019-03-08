package com.yuntianhe.simplesqlite.library;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 11:39
 */
public class Database {

    private SQLiteDatabase delegate;

    Database(SQLiteDatabase database) {
        delegate = database;
    }

    public final void createTable(Table table) {
        final String tableName = table.getTableName();
        if (TextUtils.isEmpty(tableName)) {
            return;
        }

        final String[] columns = table.onCreateColumns();
        if (columns == null || columns.length == 0) {
            return;
        }

        final StringBuilder sb = new StringBuilder("create table if not exists " + tableName);
        sb.append("(");
        for (int i = 0, length = columns.length; i < length; i++) {
            String column = columns[i];
            sb.append(column);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");

        Log.w("Database", "db: " + sb.toString());

        delegate.execSQL(sb.toString());
    }

    public final void deleteTable(Table table) {

    }
}
