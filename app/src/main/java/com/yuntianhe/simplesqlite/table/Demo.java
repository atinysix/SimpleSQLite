package com.yuntianhe.simplesqlite.table;

import android.content.ContentValues;

import com.yuntianhe.simplesqlite.annotations.Column;
import com.yuntianhe.simplesqlite.annotations.Table;
import com.yuntianhe.simplesqlite.library.BaseTableEntity;
import com.yuntianhe.simplesqlite.library.CursorWrapper;
import com.yuntianhe.simplesqlite.processor.DemoTable;

/**
 * 描述:
 * 作者: daiwj on 2019/2/20 14:39
 */
@Table(tableName = "demo_table", databaseName = "demo_database")
public class Demo extends BaseTableEntity<Demo> {

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
    public ContentValues in(Demo entity) {
        return DemoTable.in(entity);
    }

    @Override
    public Demo out(CursorWrapper cursor) {
        return DemoTable.out(cursor);
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
