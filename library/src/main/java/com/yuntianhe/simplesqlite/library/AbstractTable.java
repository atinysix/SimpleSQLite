package com.yuntianhe.simplesqlite.library;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 10:14
 */
public abstract class AbstractTable<T extends ITableEntity<T>> implements ITableOperator<T> {

    protected abstract RealOpenHelper getOpenHelper();

    public abstract String getDatabaseName();

    public String getTableName() {
        return getClass().getSimpleName();
    }

    public abstract String onCreateInitSql();

    protected abstract T onCreateTableEntity();

    @Override
    public long add(T t) {
        return getOpenHelper().add(getTableName(), t);
    }

    @Override
    public List<Long> addAll(List<T> list) {
        return getOpenHelper().addAll(getTableName(), list);
    }

    @Override
    public void addAllAsync(final List<T> list, final AsyncCallback<List<Long>> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<List<Long>>() {
                @Override
                public void run() {
                    List<Long> idList = addAll(list);
                    post(callback, idList);
                }
            });
        }
    }

    @Override
    public long replace(T t) {
        return getOpenHelper().replace(getTableName(), t);
    }

    @Override
    public List<Long> replaceAll(List<T> list) {
        return getOpenHelper().replaceAll(getTableName(), list);
    }

    @Override
    public void replaceAllAsync(final List<T> list, final AsyncCallback<List<Long>> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<List<Long>>() {
                @Override
                public void run() {
                    List<Long> idList = replaceAll(list);
                    post(callback, idList);
                }
            });
        }
    }

    @Override
    public long delete(Query query) {
        return getOpenHelper().delete(query);
    }

    @Override
    public void deleteAsync(final Query query, final AsyncCallback<Long> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<Long>() {
                @Override
                public void run() {
                    post(callback, delete(query));
                }
            });
        }
    }

    @Override
    public long clear(Query query) {
        query.clearSelection().equal("1", "1");
        return getOpenHelper().delete(query);
    }

    @Override
    public void clearAsync(final Query query, final AsyncCallback<Long> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<Long>() {
                @Override
                public void run() {
                    post(callback, clear(query));
                }
            });
        }
    }

    @Override
    public long update(Query query, T t) {
        return getOpenHelper().update(query, t);
    }

    @Override
    public void updateAsync(final Query query, final T t, final AsyncCallback<Long> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<Long>() {
                @Override
                public void run() {
                    post(callback, update(query, t));
                }
            });
        }
    }

    @Override
    public long updateAll(Query query, List<T> list) {
        return getOpenHelper().updateAll(query, list);
    }

    @Override
    public void updateAllAsync(final Query query, final List<T> list, final AsyncCallback<Long> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<Long>() {
                @Override
                public void run() {
                    post(callback, updateAll(query, list));
                }
            });
        }
    }

    @Override
    public long update(Query query, HashMap<String, Object> map) {
        return getOpenHelper().update(query, onCreateTableEntity(), map);
    }

    @Override
    public void updateAsync(final Query query, final HashMap<String, Object> map, final AsyncCallback<Long> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<Long>() {
                @Override
                public void run() {
                    post(callback, update(query, map));
                }
            });
        }
    }

    @Override
    public T query(Query query) {
        return getOpenHelper().query(query, onCreateTableEntity());
    }

    @Override
    public T query(String column, Object value) {
        Query query = Query.from(getTableName()).equal(column, value).build();
        return getOpenHelper().query(query, onCreateTableEntity());
    }

    @Override
    public List<T> queryAll(Query query) {
        return getOpenHelper().queryAll(query, onCreateTableEntity());
    }

    @Override
    public void queryAllAsync(final Query query, final AsyncCallback<List<T>> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<List<T>>() {
                @Override
                public void run() {
                    post(callback, queryAll(query));
                }
            });
        }
    }

    @Override
    public List<T> queryAll(String column, Object value) {
        Query query = Query.from(getTableName()).equal(column, value).build();
        return getOpenHelper().queryAll(query, onCreateTableEntity());
    }

    @Override
    public void queryAllAsync(final String column, final Object value, final AsyncCallback<List<T>> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<List<T>>() {
                @Override
                public void run() {
                    post(callback, queryAll(column, value));
                }
            });
        }
    }

    @Override
    public T rawQuery(RawQuery rawQuery) {
        return getOpenHelper().rawQuery(rawQuery, onCreateTableEntity());
    }

    @Override
    public List<T> rawQueryAll(RawQuery rawQuery) {
        return getOpenHelper().rawQueryAll(rawQuery, onCreateTableEntity());
    }

    @Override
    public void rawQueryAllAsync(final RawQuery rawQuery, final AsyncCallback<List<T>> callback) {
        if (callback != null) {
            AsyncExecutor.runBackground(new AsyncExecutor.Task<List<T>>() {
                @Override
                public void run() {
                    post(callback, rawQueryAll(rawQuery));
                }
            });
        }
    }
}
