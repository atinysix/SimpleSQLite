package com.yuntianhe.simplesqlite.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.yuntianhe.simplesqlite.library.BaseTableEntity;
import com.yuntianhe.simplesqlite.table.TestTable;

/**
 * 描述:
 * 作者: daiwj on 2019/3/8 12:35
 */
public class TestData extends BaseTableEntity<TestData> {

    private long id;
    private String text;
    private int duration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text == null ? "" : text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String getTableName() {
        return TestTable.TABLE_NAME;
    }

    @Override
    public TestData out(Cursor cursor) {
        TestData data = new TestData();
        data.setId(cursor.getLong(cursor.getColumnIndex(TestTable._ID)));
        data.setText(cursor.getString(cursor.getColumnIndex(TestTable.TEXT)));
        data.setDuration(cursor.getInt(cursor.getColumnIndex(TestTable.DURATION)));
        return data;
    }

    @Override
    public ContentValues in(TestData entity) {
        ContentValues cv = new ContentValues();
        cv.put(TestTable.TEXT, entity.getText());
        cv.put(TestTable.DURATION, entity.getDuration());
        return cv;
    }
}
