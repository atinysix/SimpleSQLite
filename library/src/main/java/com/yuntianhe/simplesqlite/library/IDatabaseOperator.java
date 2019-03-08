package com.yuntianhe.simplesqlite.library;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 13:47
 */
public interface IDatabaseOperator {

    <T extends ITableEntity<T>> long add(T t);

    <T extends ITableEntity<T>> List<Long> addAll(List<T> list);

    <T extends ITableEntity<T>> long replace(T t);

    <T extends ITableEntity<T>> long replaceAll(List<T> list);

    int delete(Query query);

    int deleteAll(Query query);

    <T extends ITableEntity<T>> int update(Query query, T t);

    <T extends ITableEntity<T>> int update(Query query, T t, HashMap<String, Object> map);

    <T extends ITableEntity<T>> int updateAll(Query query, List<T> list);

    <T extends ITableEntity<T>> T query(Query query, T t);

    <T extends ITableEntity<T>> List<T> queryAll(Query query, T t);

    <T extends ITableEntity<T>> T rawQuery(RawQuery rawQuery, T t);

    <T extends ITableEntity<T>> List<T> rawQueryAll(RawQuery rawQuery, T t);
}
