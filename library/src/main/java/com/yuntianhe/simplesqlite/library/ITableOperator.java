package com.yuntianhe.simplesqlite.library;

import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 10:16
 */
public interface ITableOperator<T extends ITableEntity<T>> {

    long add(T t);

    List<Long> addAll(List<T> list);

    int delete(long id);

    int deleteAll(List<Long> ids);

    long replace(T t);

    long replaceAll(List<T> t);

    int update(long id, T t);

    int updateAll(List<Long> ids, List<T> t);

    T query(long id);

    List<T> queryAll(List<Long> ids);

    T rawQuery(RawQuery rawQuery);

    List<T> rawQueryAll(RawQuery rawQuery);

}
