package com.yuntianhe.simplesqlite.table;

import android.content.ContentValues;

import com.yuntianhe.simplesqlite.annotation.Column;
import com.yuntianhe.simplesqlite.annotation.Table;
import com.yuntianhe.simplesqlite.library.BaseTableEntity;
import com.yuntianhe.simplesqlite.library.CursorWrapper;
import com.yuntianhe.simplesqlite.compiler.TestTable;

/**
 * 描述:
 * 作者: daiwj on 2019/2/20 14:39
 */
@Table(tableName = "test_table", databaseName = "test_database")
public class Test extends BaseTableEntity<Test> {

    @Column(name = "id")
    public long id;

    @Column(name = "text")
    public String text;

    @Column(name = "duration_new")
    public int duration;

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
    public ContentValues in(Test entity) {
        return TestTable.in(entity);
    }

    @Override
    public Test out(CursorWrapper cursor) {
        return TestTable.out(cursor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("id: " + id);
        sb.append(", text: " + text);
        sb.append(", duration: " + duration);
        sb.append("}");
        return sb.toString();
    }
}
