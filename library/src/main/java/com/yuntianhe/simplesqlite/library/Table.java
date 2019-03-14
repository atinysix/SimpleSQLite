package com.yuntianhe.simplesqlite.library;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    public List<Long> addAllAsync(final List<T> list, final AsyncCallback<List<Long>> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<List<Long>>() {
                @Override
                public void subscribe(ObservableEmitter<List<Long>> e) {
                    e.onNext(addAll(list));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<Long>>() {
                        @Override
                        public void accept(List<Long> longs) {
                            callback.onResult(longs);
                        }
                    });
        }
        return null;
    }

    @Override
    public long replace(T t) {
        return mDatabaseOperator.replace(t);
    }

    @Override
    public List<Long> replaceAll(List<T> list) {
        return mDatabaseOperator.replaceAll(list);
    }

    @Override
    public void replaceAllAsync(final List<T> list, final AsyncCallback<List<Long>> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<List<Long>>() {
                @Override
                public void subscribe(ObservableEmitter<List<Long>> e) {
                    e.onNext(replaceAll(list));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<Long>>() {
                        @Override
                        public void accept(List<Long> longs) {
                            callback.onResult(longs);
                        }
                    });
        }
    }

    @Override
    public int delete(Query query) {
        return mDatabaseOperator.delete(query);
    }

    @Override
    public int deleteAsync(final Query query, final AsyncCallback<Integer> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e) {
                    e.onNext(delete(query));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) {
                            callback.onResult(integer);
                        }
                    });
        }
        return 0;
    }

    @Override
    public int clear(Query query) {
        query.clearSelection().equal("1", "1");
        return mDatabaseOperator.delete(query);
    }

    @Override
    public int clearAsync(final Query query, final AsyncCallback<Integer> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e) {
                    e.onNext(clear(query));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) {
                            callback.onResult(integer);
                        }
                    });
        }
        return 0;
    }

    @Override
    public int update(Query query, T t) {
        return mDatabaseOperator.update(query, t);
    }

    @Override
    public int updateAsync(final Query query, final T t, final AsyncCallback<Integer> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e) {
                    e.onNext(update(query, t));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) {
                            callback.onResult(integer);
                        }
                    });
        }
        return 0;
    }

    @Override
    public int update(Query query, T t, HashMap<String, Object> map) {
        return mDatabaseOperator.update(query, t, map);
    }

    @Override
    public int updateAsync(final Query query, final T t, final HashMap<String, Object> map, final AsyncCallback<Integer> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e) {
                    e.onNext(update(query, t, map));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) {
                            callback.onResult(integer);
                        }
                    });
        }
        return 0;
    }

    @Override
    public T query(Query query) {
        return mDatabaseOperator.query(query, getTableEntity());
    }

    @Override
    public T query(String column, Object value) {
        Query query = Query.from(getTableName()).equal(column, value).log();
        return mDatabaseOperator.query(query, getTableEntity());
    }

    @Override
    public List<T> queryAll(Query query) {
        return mDatabaseOperator.queryAll(query, getTableEntity());
    }

    @Override
    public void queryAllAsync(final Query query, final AsyncCallback<List<T>> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<List<T>>() {
                @Override
                public void subscribe(ObservableEmitter<List<T>> e) {
                    e.onNext(queryAll(query));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<T>>() {
                        @Override
                        public void accept(List<T> integer) {
                            callback.onResult(integer);
                        }
                    });
        }
    }

    @Override
    public List<T> queryAll(String column, Object value) {
        Query query = Query.from(getTableName()).equal(column, value).log();
        return mDatabaseOperator.queryAll(query, getTableEntity());
    }

    @Override
    public void queryAllAsync(final String column, final Object value, final AsyncCallback<List<T>> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<List<T>>() {
                @Override
                public void subscribe(ObservableEmitter<List<T>> e) {
                    e.onNext(queryAll(column, value));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<T>>() {
                        @Override
                        public void accept(List<T> integer) {
                            callback.onResult(integer);
                        }
                    });
        }
    }

    @Override
    public T rawQuery(RawQuery rawQuery) {
        return mDatabaseOperator.rawQuery(rawQuery, getTableEntity());
    }

    @Override
    public List<T> rawQueryAll(RawQuery rawQuery) {
        return mDatabaseOperator.rawQueryAll(rawQuery, getTableEntity());
    }

    @Override
    public void rawQueryAllAsync(final RawQuery rawQuery, T t, final AsyncCallback<List<T>> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<List<T>>() {
                @Override
                public void subscribe(ObservableEmitter<List<T>> e) {
                    e.onNext(rawQueryAll(rawQuery));
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<T>>() {
                        @Override
                        public void accept(List<T> integer) {
                            callback.onResult(integer);
                        }
                    });
        }
    }
}
