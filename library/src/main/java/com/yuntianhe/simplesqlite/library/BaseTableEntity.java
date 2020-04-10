package com.yuntianhe.simplesqlite.library;

import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 作者: daiwj on 2019/3/8 15:52
 */
public class BaseTableEntity<T> implements ITableEntity<T> {

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public ContentValues in(T entity) {
        return null;
    }

    @Override
    public final ContentValues in(HashMap<String, Object> map) {
        ContentValues cv = new ContentValues();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                cv.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return cv;
    }

    @Override
    public T out(CursorWrapper cursor) {
        return null;
    }
}
