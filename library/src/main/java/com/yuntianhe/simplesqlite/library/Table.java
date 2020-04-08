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
public abstract class Table<T extends ITableEntity> implements ITableOperator<T> {

    public static final String TAG = Table.class.getSimpleName();

    protected abstract DevOpenHelper getOpenHelper();

    public String getTableName() {
        return getClass().getSimpleName();
    }

    protected abstract String[] onCreateColumns();

    protected abstract T getTableEntity();

    @Override
    public long add(T t) {
        return getOpenHelper().add(t);
    }

    @Override
    public List<Long> addAll(List<T> list) {
        return getOpenHelper().addAll(list);
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
        return getOpenHelper().replace(t);
    }

    @Override
    public List<Long> replaceAll(List<T> list) {
        return getOpenHelper().replaceAll(list);
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
        return getOpenHelper().update(query, getTableEntity(), map);
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
        return getOpenHelper().query(query, getTableEntity());
    }

    @Override
    public T query(String column, Object value) {
        Query query = Query.from(getTableName()).equal(column, value).log();
        return getOpenHelper().query(query, getTableEntity());
    }

    @Override
    public List<T> queryAll(Query query) {
        return getOpenHelper().queryAll(query, getTableEntity());
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
        return getOpenHelper().queryAll(query, getTableEntity());
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
        return getOpenHelper().rawQuery(rawQuery, getTableEntity());
    }

    @Override
    public List<T> rawQueryAll(RawQuery rawQuery) {
        return getOpenHelper().rawQueryAll(rawQuery, getTableEntity());
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
