package com.yuntianhe.simplesqlite.library;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 13:50
 */
public interface ITableEntity<T> {

    String getTableName();

    ContentValues in(T entity);

    ContentValues in(HashMap<String, Object> map);

    T out(CursorWrapper cursor);

}
