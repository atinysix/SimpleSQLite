package com.yuntianhe.simplesqlite.library;

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

    private IDatabaseOperator getDatabaseOperator() {
        return mDatabaseOperator;
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
    public int delete(long id) {
        Query query = Query.from(getTableName()).equal("id", String.valueOf(id));
        return mDatabaseOperator.delete(query);
    }

    @Override
    public int deleteAll(List<Long> ids) {
        Query query = Query.from(getTableName()).in("id", ids).log();
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
    public int update(long id, T t) {
        Query query = Query.from(getTableName()).equal("id", id).log();
        return mDatabaseOperator.update(query, t);
    }

    @Override
    public int updateAll(List<Long> ids, List<T> t) {
        Query query = Query.from(getTableName()).in("id", ids).log();
        return mDatabaseOperator.updateAll(query, t);
    }

    @Override
    public T query(long id) {
        Query query = Query.from(getTableName()).equal("id", id).log();
        return mDatabaseOperator.query(query, getTableEntity());
    }

    @Override
    public List<T> queryAll(List<Long> ids) {
        Query query = Query.from(getTableName()).in("id", ids).log();
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
