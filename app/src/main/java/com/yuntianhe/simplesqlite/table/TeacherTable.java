package com.yuntianhe.simplesqlite.table;

import com.yuntianhe.simplesqlite.db.DBManagerSingleton;
import com.yuntianhe.simplesqlite.entity.Teacher;
import com.yuntianhe.simplesqlite.library.Table;

/**
 * 描述:
 * 作者: daiwj on 2019/2/25 17:40
 */
public class TeacherTable extends Table<Teacher> {

    public TeacherTable() {
        super(DBManagerSingleton.getInstance().getDatabaseOpenHelper());
    }

    @Override
    protected String[] onCreateColumns() {
        return new String[0];
    }

    @Override
    protected Teacher getTableEntity() {
        return new Teacher();
    }
}
