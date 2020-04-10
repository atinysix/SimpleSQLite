package com.yuntianhe.simplesqlite.entity;

import android.content.ContentValues;

import com.yuntianhe.simplesqlite.library.BaseTableEntity;
import com.yuntianhe.simplesqlite.library.CursorWrapper;
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
    public ContentValues in(TestData entity) {
        ContentValues cv = new ContentValues();
        cv.put(TestTable.TEXT, entity.getText());
        cv.put(TestTable.DURATION, entity.getDuration());
        return cv;
    }

    @Override
    public TestData out(CursorWrapper cursor) {
        TestData data = new TestData();
        data.setId(cursor.getLong(TestTable._ID));
        data.setText(cursor.getString(TestTable.TEXT));
        data.setDuration(cursor.getInt(TestTable.DURATION));
        return data;
    }
}
