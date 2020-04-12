package com.yuntianhe.simplesqlite.library;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 13:47
 */
public interface IOpenHelper {

    /**
     * 插入数据
     *
     * @param t   数据源
     * @param <T>
     * @return 受作用行id
     */
    <T extends ITableEntity<T>>

    long add(String table, T t);

    /**
     * 插入数据
     *
     * @param t   数据源
     * @param <T>
     * @return 受作用行id列表
     */
    <T extends ITableEntity<T>> List<Long> addAll(String table, List<T> t);

    /**
     * 无则插入，有则更新数据
     *
     * @param t   数据源
     * @param <T>
     * @return 受作用行id
     */
    <T extends ITableEntity<T>> long replace(String table, T t);

    /**
     * 无则插入，有则更新数据
     *
     * @param list 数据源
     * @param <T>
     * @return 受作用行id列表
     */
    <T extends ITableEntity<T>> List<Long> replaceAll(String table, List<T> list);

    /**
     * 根据条件删除指定数据
     *
     * @param query
     * @return 受作用行数
     */
    int delete(Query query);

    /**
     * 更新已有的数据
     *
     * @param query
     * @param t     实体类数据源
     * @param <T>
     * @return 受作用行数
     */
    <T extends ITableEntity<T>> int update(Query query, T t);

    /**
     * 更新已有的数据
     *
     * @param query
     * @param t     实体类
     * @param map   数据源
     * @param <T>
     * @return 受作用行数
     */
    <T extends ITableEntity<T>> int update(Query query, T t, HashMap<String, Object> map);

    /**
     * 更新已有的数据
     *
     * @param query
     * @param mapList 实体类数据源
     * @param <T>
     * @return 受作用行数
     */
    <T extends ITableEntity<T>> List<Long> updateAll(Query query, List<T> mapList);

    /**
     * 查询数据
     *
     * @param query
     * @param t     实体类
     * @param <T>
     * @return 实体类
     */
    <T extends ITableEntity<T>> T query(Query query, T t);

    /**
     * 查询数据
     *
     * @param query
     * @param t     实体类
     * @param <T>
     * @return 实体类列表
     */
    <T extends ITableEntity<T>> List<T> queryAll(Query query, T t);

    /**
     * sql直接查询
     *
     * @param rawQuery
     * @param t        实体类
     * @param <T>
     * @return 实体类
     */
    <T extends ITableEntity<T>> T rawQuery(RawQuery rawQuery, T t);

    /**
     * sql直接查询
     *
     * @param rawQuery
     * @param t        实体类
     * @param <T>
     * @return 实体类列表
     */
    <T extends ITableEntity<T>> List<T> rawQueryAll(RawQuery rawQuery, T t);

}
