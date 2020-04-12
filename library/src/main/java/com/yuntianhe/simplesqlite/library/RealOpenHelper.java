package com.yuntianhe.simplesqlite.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 11:43
 */
public class RealOpenHelper extends SQLiteOpenHelper implements IOpenHelper {

    private IDatabaseEntry mFactory;

    public RealOpenHelper(Context context, String name, int version, IDatabaseEntry factory) {
        super(context, name, null, version);
        mFactory = factory;
        if (factory != null) {
            Logger.d(factory.getClass().getSimpleName() + " init success(name: " + name + ", version: " + version + ")");
            factory.onInit(context, name, version);
        }
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
        if (mFactory != null) {
            mFactory.onCreate(new DatabaseDelegate(db));
            Logger.d(mFactory.getClass().getSimpleName() + " create success");
        }
    }

    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (mFactory != null) {
            mFactory.onUpgrade(new DatabaseDelegate(db), oldVersion, newVersion);
            Logger.d(mFactory.getClass().getSimpleName() + " upgrade success");
        }
    }

    @Override
    public <T extends ITableEntity<T>> long add(String table, T t) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(table, null, t.in(t));
    }

    @Override
    public <T extends ITableEntity<T>> List<Long> addAll(String table, List<T> list) {
        SQLiteDatabase db = getWritableDatabase();
        List<Long> idList = new ArrayList<>();
        try {
            db.beginTransaction();
            for (T t : list) {
                long rowId = db.insert(table, null, t.in(t));
                idList.add(rowId);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        return idList;
    }

    @Override
    public <T extends ITableEntity<T>> long replace(String table, T t) {
        SQLiteDatabase db = getWritableDatabase();
        return db.replace(table, null, t.in(t));
    }

    @Override
    public <T extends ITableEntity<T>> List<Long> replaceAll(String table, List<T> list) {
        SQLiteDatabase db = getWritableDatabase();
        List<Long> idList = new ArrayList<>();
        try {
            db.beginTransaction();
            for (T t : list) {
                long rowId = db.replace(table, null, t.in(t));
                idList.add(rowId);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        return idList;
    }

    @Override
    public long delete(Query query) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(query.getTableName(), query.getSelection(), query.getSelectionArgs());
    }

    @Override
    public <T extends ITableEntity<T>> long update(Query query, T t) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(query.getTableName(), t.in(t), query.getSelection(), query.getSelectionArgs());
    }

    @Override
    public <T extends ITableEntity<T>> long update(Query query, T t, HashMap<String, Object> map) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(query.getTableName(), t.in(map), query.getSelection(), query.getSelectionArgs());
    }

    @Override
    public <T extends ITableEntity<T>> long updateAll(Query query, List<T> list) {
        SQLiteDatabase db = getWritableDatabase();
        long rows = 0;
        try {
            db.beginTransaction();
            for (T t : list) {
                rows += db.update(query.getTableName(), t.in(t), query.getSelection(), query.getSelectionArgs());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        return rows;
    }

    @Override
    public <T extends ITableEntity<T>> T query(Query query, T t) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(query.getTableName(),
                query.getColumns(),
                query.getSelection(),
                query.getSelectionArgs(),
                query.getGroupBy(),
                query.getHaving(),
                query.getOrderBy());
        if (c != null && c.moveToFirst()) {
            CursorWrapper wrapper = new CursorWrapper(c);
            T entity = t.out(wrapper);
            c.close();
            return entity;
        }
        return t;
    }

    @Override
    public <T extends ITableEntity<T>> List<T> queryAll(Query query, T t) {
        SQLiteDatabase db = getReadableDatabase();
        List<T> list = new ArrayList<>();
        Cursor c = db.query(query.getTableName(),
                query.getColumns(),
                query.getSelection(),
                query.getSelectionArgs(),
                query.getGroupBy(),
                query.getHaving(),
                query.getOrderBy(),
                query.getLimit());
        if (c != null) {
            CursorWrapper wrapper = new CursorWrapper(c);
            while (c.moveToNext()) {
                T entity = t.out(wrapper);
                list.add(entity);
            }
            c.close();
        }
        return list;
    }

    @Override
    public <T extends ITableEntity<T>> T rawQuery(RawQuery rawQuery, T t) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(rawQuery.getSql(), null);
        if (c != null && c.moveToFirst()) {
            CursorWrapper wrapper = new CursorWrapper(c);
            T entity = t.out(wrapper);
            c.close();
            return entity;
        }
        return t;
    }

    @Override
    public <T extends ITableEntity<T>> List<T> rawQueryAll(RawQuery rawQuery, T t) {
        SQLiteDatabase db = getReadableDatabase();
        List<T> list = new ArrayList<>();
        Cursor c = db.rawQuery(rawQuery.getSql(), null);
        if (c != null) {
            CursorWrapper wrapper = new CursorWrapper(c);
            while (c.moveToNext()) {
                T entity = t.out(wrapper);
                list.add(entity);
            }
            c.close();
        }
        return list;
    }

}
