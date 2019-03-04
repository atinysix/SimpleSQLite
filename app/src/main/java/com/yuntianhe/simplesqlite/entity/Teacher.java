package com.yuntianhe.simplesqlite.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.yuntianhe.simplesqlite.library.ITableEntity;

/**
 * 描述:
 * 作者: daiwj on 2019/2/25 17:39
 */
public class Teacher implements ITableEntity<Teacher> {

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public Teacher out(Cursor cursor) {
        return null;
    }

    @Override
    public ContentValues in(Teacher entity) {
        return null;
    }
}
