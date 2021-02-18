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
public final class RealOpenHelper extends SQLiteOpenHelper implements IOpenHelper {

    private IDatabaseEntry mEntry;

    public RealOpenHelper(Context context, String name, int version, IDatabaseEntry entry) {
        super(context, name, null, version);
        mEntry = entry;
        if (entry != null) {
            Logger.d(entry.getClass().getSimpleName() + " init success(name: " + name + ", version: " + version + ")");
            entry.onInit(context, name, version);
        }
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
        if (mEntry != null) {
            mEntry.onCreate(new DatabaseDelegate(db));
            Logger.d(mEntry.getClass().getSimpleName() + " create success");
        }
    }

    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (mEntry != null) {
            mEntry.onUpgrade(new DatabaseDelegate(db), oldVersion, newVersion);
            Logger.d(mEntry.getClass().getSimpleName() + " upgrade success(oldVersion: " + oldVersion + ", newVersion: " + newVersion + ")");
        }
    }

    @Override
    public <T extends ITableEntity<T>> long add(String table, T t) {
        if (t != null) {
            Logger.d(table + " add: " + t.toString());
            SQLiteDatabase db = getWritableDatabase();
            return db.insert(table, null, t.in(t));
        }
        return -1;
    }

    @Override
    public <T extends ITableEntity<T>> List<Long> addAll(String table, List<T> list) {
        SQLiteDatabase db = getWritableDatabase();
        List<Long> idList = new ArrayList<>();
        if (list != null) {
            try {
                db.beginTransaction();
                for (T t : list) {
                    Logger.d(table + " add: " + t.toString());
                    long rowId = db.insert(table, null, t.in(t));
                    idList.add(rowId);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                db.endTransaction();
            }
        }
        return idList;
    }

    @Override
    public <T extends ITableEntity<T>> long replace(String table, T t) {
        if (t != null) {
            Logger.d(table + " replace: " + t.toString());
            SQLiteDatabase db = getWritableDatabase();
            return db.replace(table, null, t.in(t));
        }
        return -1;
    }

    @Override
    public <T extends ITableEntity<T>> List<Long> replaceAll(String table, List<T> list) {
        SQLiteDatabase db = getWritableDatabase();
        List<Long> idList = new ArrayList<>();
        if (list != null) {
            try {
                db.beginTransaction();
                for (T t : list) {
                    Logger.d(table + " replace: " + t.toString());
                    long rowId = db.replace(table, null, t.in(t));
                    idList.add(rowId);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                db.endTransaction();
            }
        }
        return idList;
    }

    @Override
    public long delete(Query query) {
        if (query != null) {
            Logger.d(query.getTableName() + " delete: condition: " + query.toString());
            SQLiteDatabase db = getWritableDatabase();
            return db.delete(query.getTableName(), query.getSelection(), query.getSelectionArgs());
        }
        return 0;
    }

    @Override
    public <T extends ITableEntity<T>> long update(Query query, T t) {
        if (query != null && t != null) {
            Logger.d(query.getTableName() + " update: condition: " + query.toString() + ", data: " + t.toString());
            SQLiteDatabase db = getWritableDatabase();
            return db.update(query.getTableName(), t.in(t), query.getSelection(), query.getSelectionArgs());
        }
        return 0;
    }

    @Override
    public <T extends ITableEntity<T>> long update(Query query, T t, HashMap<String, Object> map) {
        if (query != null && t != null) {
            Logger.d(query.getTableName() + " update: condition: " + query.toString() + ", data: " + map.toString());
            SQLiteDatabase db = getWritableDatabase();
            return db.update(query.getTableName(), t.in(map), query.getSelection(), query.getSelectionArgs());
        }
        return 0;
    }

    @Override
    public <T extends ITableEntity<T>> long updateAll(Query query, List<T> list) {
        SQLiteDatabase db = getWritableDatabase();
        long rows = 0;
        if (list != null) {
            try {
                db.beginTransaction();
                for (T t : list) {
                    Logger.d(query.getTableName() + " update: condition: " + query.toString() + ", data: " + t.toString());
                    rows += db.update(query.getTableName(), t.in(t), query.getSelection(), query.getSelectionArgs());
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
            } finally {
                db.endTransaction();
            }
        }
        return rows;
    }

    @Override
    public <T extends ITableEntity<T>> T query(Query query, T t) {
        if (query != null) {
            Logger.d(query.getTableName() + " query: condition: " + query.toString());
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
        }
        return t;
    }

    @Override
    public <T extends ITableEntity<T>> List<T> queryAll(Query query, T t) {
        List<T> list = new ArrayList<>();
        if (query != null) {
            Logger.d(query.getTableName() + " query: condition: " + query.toString());
            SQLiteDatabase db = getReadableDatabase();
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
        }
        return list;
    }

    @Override
    public <T extends ITableEntity<T>> T rawQuery(RawQuery rawQuery, T t) {
        if (rawQuery != null) {
            Logger.d("rawQuery: sql: " + rawQuery.getSql());
            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery(rawQuery.getSql(), null);
            if (c != null && c.moveToFirst()) {
                CursorWrapper wrapper = new CursorWrapper(c);
                T entity = t.out(wrapper);
                c.close();
                return entity;
            }
        }
        return t;
    }

    @Override
    public <T extends ITableEntity<T>> List<T> rawQueryAll(RawQuery rawQuery, T t) {
        List<T> list = new ArrayList<>();
        if (rawQuery != null) {
            Logger.d("rawQuery: sql: " + rawQuery.getSql());
            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery(rawQuery.getSql(), null);
            if (c != null) {
                CursorWrapper wrapper = new CursorWrapper(c);
                while (c.moveToNext()) {
                    T entity = t.out(wrapper);
                    list.add(entity);
                }
                c.close();
            }
        }
        return list;
    }

}
