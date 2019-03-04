package com.yuntianhe.simplesqlite.library;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 13:50
 */
public interface ITableEntity<T> {

    String getTableName();

    T out(Cursor cursor);

    ContentValues in(T entity);

}
