package com.yuntianhe.simplesqlite.db;

import android.content.Context;

import com.yuntianhe.simplesqlite.library.Database;
import com.yuntianhe.simplesqlite.library.DevOpenHelper;
import com.yuntianhe.simplesqlite.table.TeacherTable;

/**
 * 描述:
 * 作者: daiwj on 2019/2/25 17:33
 */
public class MyOpenHelper extends DevOpenHelper {

    public MyOpenHelper(Context context, String name, int version) {
        super(context, name, version);
    }

    @Override
    public void onCreate(Database db) {
        db.createTable(new TeacherTable());
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

    }

}
