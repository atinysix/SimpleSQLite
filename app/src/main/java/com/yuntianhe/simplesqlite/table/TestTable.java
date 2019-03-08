package com.yuntianhe.simplesqlite.table;

import com.yuntianhe.simplesqlite.db.DBManagerSingleton;
import com.yuntianhe.simplesqlite.entity.TestData;
import com.yuntianhe.simplesqlite.library.Table;

/**
 * 描述: 客服消息表
 * 作者: daiwj on 2019/2/20 14:39
 */
public class TestTable extends Table<TestData> {

    public static final String TABLE_NAME = "table_gim_message";

    public static final String _ID = "id";
    public static final String TEXT = "text";
    public static final String DURATION = "duration";

    public TestTable() {
        super(DBManagerSingleton.getInstance().getDatabaseOpenHelper());
    }

    @Override
    protected String[] onCreateColumns() {
        return new String[]{
                _ID + " integer primary key autoincrement",
                TEXT + " text",
                DURATION + " integer default 0",
        };
    }

    @Override
    protected TestData getTableEntity() {
        return new TestData();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

}
