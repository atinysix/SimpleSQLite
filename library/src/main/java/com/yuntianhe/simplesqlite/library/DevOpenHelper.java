package com.yuntianhe.simplesqlite.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 11:43
 */
public abstract class DevOpenHelper extends SQLiteOpenHelper implements IDatabaseOperator {

    public DevOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
        onCreate(new Database(db));
    }

    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(new Database(db), oldVersion, newVersion);
    }

    public abstract void onCreate(Database db);

    public abstract void onUpgrade(Database db, int oldVersion, int newVersion);

    @Override
    public <T extends ITableEntity<T>> long add(T t) {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(t.getTableName(), null, t.in(t));
    }

    @Override
    public <T extends ITableEntity<T>> List<Long> addAll(List<T> list) {
        SQLiteDatabase db = getWritableDatabase();
        List<Long> idList = new ArrayList<>();
        try {
            db.beginTransaction();
            for (T t : list) {
                long id = db.insert(t.getTableName(), null, t.in(t));
                idList.add(id);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
        return idList;
    }

    @Override
    public <T extends ITableEntity<T>> long replace(T t) {
        SQLiteDatabase db = getWritableDatabase();
        return db.replace(t.getTableName(), null, t.in(t));
    }

    @Override
    public <T extends ITableEntity<T>> long replaceAll(List<T> list) {
        if (list == null) {
            return -1;
        }

        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            for (T t : list) {
                db.replace(t.getTableName(), null, t.in(t));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return -1;
        } finally {
            db.endTransaction();
        }
        return list.size();
    }

    @Override
    public int delete(Query query) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(query.getTableName(), query.getSelection(), query.getSelectionArgs());
    }

    @Override
    public int deleteAll(Query query) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(query.getTableName(), query.getSelection(), query.getSelectionArgs());
    }

    @Override
    public <T extends ITableEntity<T>> int update(Query query, T t) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(t.getTableName(), t.in(t), query.getSelection(), query.getSelectionArgs());
    }

    @Override
    public <T extends ITableEntity<T>> int updateAll(Query query, List<T> list) {
        if (list == null) {
            return 0;
        }

        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            for (T t : list) {
                db.update(t.getTableName(), t.in(t), query.getSelection(), query.getSelectionArgs());
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return 0;
        } finally {
            db.endTransaction();
        }
        return list.size();
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
            T entity = t.out(c);
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
            while (c.moveToNext()) {
                T entity = t.out(c);
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
            T entity = t.out(c);
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
            while (c.moveToNext()) {
                T entity = t.out(c);
                list.add(entity);
            }
            c.close();
        }
        return list;
    }

}
