package com.yuntianhe.simplesqlite.library;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 10:14
 */
public abstract class Table<T extends ITableEntity<T>> implements ITableOperator<T> {

    public static final String TAG = Table.class.getSimpleName();

    private IDatabaseOperator mDatabaseOperator;

    public Table(DevOpenHelper helper) {
        mDatabaseOperator = helper;
    }

    public String getTableName() {
        return getClass().getSimpleName();
    }

    protected abstract String[] onCreateColumns();

    protected abstract T getTableEntity();

    @Override
    public long add(T t) {
        return mDatabaseOperator.add(t);
    }

    @Override
    public List<Long> addAll(List<T> list) {
        return mDatabaseOperator.addAll(list);
    }

    @Override
    public int delete(Query query) {
        return mDatabaseOperator.delete(query);
    }

    @Override
    public int delete(String column, String value) {
        Query query = Query.from(getTableName()).equal(column, value).log();
        return mDatabaseOperator.delete(query);
    }

    @Override
    public int deleteAll(Query query) {
        if (query == null) {
            query = Query.from(getTableName());
        }
        return mDatabaseOperator.deleteAll(query);
    }

    @Override
    public int deleteAll(String column, List<String> values) {
        Query query = Query.from(getTableName()).in(column, values).log();
        return mDatabaseOperator.deleteAll(query);
    }

    @Override
    public long replace(T t) {
        return mDatabaseOperator.replace(t);
    }

    @Override
    public long replaceAll(List<T> list) {
        return mDatabaseOperator.replaceAll(list);
    }

    @Override
    public int update(Query query, T t) {
        return mDatabaseOperator.update(query, t);
    }

    @Override
    public int update(Query query, T t, HashMap<String, Object> map) {
        return mDatabaseOperator.update(query, t, map);
    }

    @Override
    public int updateAll(Query query, List<T> list) {
        return mDatabaseOperator.updateAll(query, list);
    }

    @Override
    public T query(Query query) {
        return mDatabaseOperator.query(query, getTableEntity());
    }

    @Override
    public T query(String column, String value) {
        Query query = Query.from(getTableName()).equal(column, value).log();
        return mDatabaseOperator.query(query, getTableEntity());
    }

    @Override
    public List<T> queryAll(Query query) {
        if (query == null) {
            query = Query.from(getTableName());
        }
        return mDatabaseOperator.queryAll(query, getTableEntity());
    }

    @Override
    public List<T> queryAll(String column, List<String> values) {
        Query query = Query.from(getTableName()).in(column, values).log();
        return mDatabaseOperator.queryAll(query, getTableEntity());
    }

    @Override
    public T rawQuery(RawQuery rawQuery) {
        return mDatabaseOperator.rawQuery(rawQuery, getTableEntity());
    }

    @Override
    public List<T> rawQueryAll(RawQuery rawQuery) {
        return mDatabaseOperator.rawQueryAll(rawQuery, getTableEntity());
    }
}
