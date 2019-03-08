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

    /**
     * 注意：查询指定列的数据，在解析Cursor时，需要手动判断Cursor是否包含此列
     *
     * @param cursor
     * @return
     */
    T out(CursorWrapper cursor);

    ContentValues in(T entity);

    ContentValues in(HashMap<String, Object> map);

}
