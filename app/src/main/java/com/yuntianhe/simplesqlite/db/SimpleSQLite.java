package com.yuntianhe.simplesqlite.db;

import android.content.Context;

import com.yuntianhe.simplesqlite.library.IOpenHelper;
import com.yuntianhe.simplesqlite.library.RealOpenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * desc:
 * author: daiwj on 2020-04-11 21:57
 */
public final class SimpleSQLite {

    private static final HashMap<String, IOpenHelper> sContainer = new HashMap<>();

    public static void init(Context context) {
        sContainer.put("test_database", new RealOpenHelper(context, "test_name", 1, new TestDatabase()));
    }

    public static IOpenHelper getOpenHelper(String databaseName) {
        for (Map.Entry<String, IOpenHelper> entry : sContainer.entrySet()) {
            if (entry.getKey().equals(databaseName)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
