package com.yuntianhe.simplesqlite.table;

import android.content.ContentValues;

import com.yuntianhe.simplesqlite.annotations.Column;
import com.yuntianhe.simplesqlite.annotations.Table;
import com.yuntianhe.simplesqlite.library.BaseTableEntity;
import com.yuntianhe.simplesqlite.library.CursorWrapper;
import com.yuntianhe.simplesqlite.processor.TestTable;

/**
 * 描述: 客服消息表
 * 作者: daiwj on 2019/2/20 14:39
 */
@Table(tableName = "test_table", databaseName = "test_database")
public class Test extends BaseTableEntity<Test> {

    @Column(name = "price", defaultValue = "1.0")
    public float price;

    @Column(name = "count")
    public int count;

    @Column(name = "count_limit")
    public int limit;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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
        sb.append("price: " + price);
        sb.append(", count: " + count);
        sb.append(", limit: " + limit);
        return sb.toString();
    }
}
