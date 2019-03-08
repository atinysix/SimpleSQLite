package com.yuntianhe.simplesqlite.library;

import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 10:16
 */
public interface ITableOperator<T extends ITableEntity<T>> {

    long add(T t);

    List<Long> addAll(List<T> list);

    int delete(Query query);

    int delete(String column, String value);

    int deleteAll(Query query);

    int deleteAll(String column, List<String> values);

    long replace(T t);

    long replaceAll(List<T> t);

    int update(Query query, T t);

    int updateAll(Query query, List<T> list);

    T query(Query query);

    T query(String column, String value);

    List<T> queryAll(Query query);

    List<T> queryAll(String column, List<String> values);

    T rawQuery(RawQuery rawQuery);

    List<T> rawQueryAll(RawQuery rawQuery);



}
