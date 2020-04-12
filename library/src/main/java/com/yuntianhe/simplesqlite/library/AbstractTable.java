package com.yuntianhe.simplesqlite.library;

import android.support.annotation.CallSuper;

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
public abstract class AbstractTable<T extends ITableEntity<T>> implements ITableOperator<T> {

    public static final String TAG = AbstractTable.class.getSimpleName();

    public abstract RealOpenHelper getOpenHelper();

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
        return getOpenHelper().delete(query);
    }

    @Override
    public void deleteAsync(final Query query, final AsyncCallback<Integer> callback) {
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
    }

    @Override
    public int clear(Query query) {
        query.clearSelection().equal("1", "1");
        return getOpenHelper().delete(query);
    }

    @Override
    public void clearAsync(final Query query, final AsyncCallback<Integer> callback) {
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
    }

    @Override
    public int update(Query query, T t) {
        return getOpenHelper().update(query, t);
    }

    @Override
    public List<Long> updateAll(Query query, List<T> list) {
        return getOpenHelper().updateAll(query, list);
    }

    @Override
    public void updateAsync(final Query query, final T t, final AsyncCallback<Integer> callback) {
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
    }

    @Override
    public int update(Query query, HashMap<String, Object> map) {
        return getOpenHelper().update(query, onCreateTableEntity(), map);
    }

    @Override
    public void updateAsync(final Query query, final HashMap<String, Object> map, final AsyncCallback<Integer> callback) {
        if (callback != null) {
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e) {
                    e.onNext(update(query, map));
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
        Query query = Query.from(getTableName()).equal(column, value).build();
        return getOpenHelper().queryAll(query, onCreateTableEntity());
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
        return getOpenHelper().rawQuery(rawQuery, onCreateTableEntity());
    }

    @Override
    public List<T> rawQueryAll(RawQuery rawQuery) {
        return getOpenHelper().rawQueryAll(rawQuery, onCreateTableEntity());
    }

    @Override
    public void rawQueryAllAsync(final RawQuery rawQuery, final AsyncCallback<List<T>> callback) {
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
