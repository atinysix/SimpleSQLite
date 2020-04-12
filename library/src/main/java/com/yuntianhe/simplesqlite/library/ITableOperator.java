package com.yuntianhe.simplesqlite.library;

import java.util.HashMap;
import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 10:16
 */
public interface ITableOperator<T> {

    /**
     * 插入一条数据
     *
     * @param t
     * @return 受作用行id
     */
    long add(T t);

    /**
     * 插入多条数据
     *
     * @param list
     * @return 受作用行id列表
     */
    List<Long> addAll(List<T> list);

    /**
     * 异步操作
     * <p>
     * 插入多条数据
     *
     * @param list     数据源
     * @param callback 异步回调
     * @return 受作用行id列表
     */
    void addAllAsync(List<T> list, AsyncCallback<List<Long>> callback);

    /**
     * 无则插入，有则更新数据
     *
     * @param t 数据源
     * @return 受作用行id
     */
    long replace(T t);

    /**
     * 无则插入，有则更新数据
     *
     * @param t 数据源
     * @return 受作用行id列表
     */
    List<Long> replaceAll(List<T> t);

    /**
     * 异步操作
     * <p>
     * 无则插入，有则更新多条数据
     *
     * @param list     数据源
     * @param callback 异步回调
     * @return 受作用行id列表
     */
    void replaceAllAsync(final List<T> list, final AsyncCallback<List<Long>> callback);

    /**
     * 删除数据
     *
     * @param query
     * @return 受作用行数
     */
    long delete(Query query);

    /**
     * 异步操作
     * <p>
     * 删除数据
     *
     * @param query
     * @param callback 异步回调
     * @return 受作用行数
     */
    void deleteAsync(final Query query, final AsyncCallback<Long> callback);

    /**
     * 清空数据
     *
     * @param query
     * @return 受作用行数
     */
    long clear(final Query query);

    /**
     * 异步操作
     * <p>
     * 清空数据
     *
     * @param query
     * @param callback 异步回调
     * @return 受作用行数
     */
    void clearAsync(final Query query, final AsyncCallback<Long> callback);

    /**
     * 更新数据
     *
     * @param query
     * @param t     实体类数据源
     * @return 受作用行数
     */
    long update(Query query, T t);

    /**
     * 异步操作
     * <p>
     * 更新已有的数据
     *
     * @param query
     * @param t        实体类数据源
     * @param callback 异步回调
     * @return 受作用行数
     */
    void updateAsync(final Query query, T t, final AsyncCallback<Long> callback);

    /**
     * 更新数据
     *
     * @param query
     * @param list   数据源
     * @return 受作用行数
     */
    long updateAll(Query query, List<T> list);

    void updateAllAsync(Query query, List<T> list, AsyncCallback<Long> callback);

    /**
     * 更新数据
     *
     * @param query
     * @param map   数据源
     * @return 受作用行数
     */
    long update(Query query, HashMap<String, Object> map);

    /**
     * 异步操作
     * <p>
     * 更新已有的数据
     *
     * @param query
     * @param map      数据源
     * @param callback 异步回调
     * @return 受作用行数
     */
    void updateAsync(final Query query, final HashMap<String, Object> map, final AsyncCallback<Long> callback);

    /**
     * 查询数据
     *
     * @param query
     * @return 实体类
     * @see #query(String, Object)
     */
    T query(Query query);

    /**
     * 查询数据
     *
     * @param column {@link Query#equal(String, Object)}
     * @param value  {@link Query#equal(String, Object)}
     * @return 实体类
     * @see #query(Query)
     */
    T query(String column, Object value);

    /**
     * 查询数据
     *
     * @param query
     * @return 实体类
     * @see #queryAll(String, Object)
     */
    List<T> queryAll(Query query);

    /**
     * 异步操作
     * <p>
     * 查询数据
     *
     * @param query
     * @param callback 异步回调
     * @return 实体类列表
     * @see #queryAllAsync(String, Object, AsyncCallback)
     */
    void queryAllAsync(final Query query, final AsyncCallback<List<T>> callback);

    /**
     * 查询数据
     *
     * @param column {@link Query#equal(String, Object)}
     * @param value  {@link Query#equal(String, Object)}
     * @return 实体类
     * @see #queryAll(Query)
     */
    List<T> queryAll(String column, Object value);

    /**
     * 异步操作
     * <p>
     * 查询数据
     *
     * @param column   {@link Query#equal(String, Object)}
     * @param value    {@link Query#equal(String, Object)}
     * @param callback 异步回调
     * @return 实体类列表
     * @see #queryAllAsync(Query, AsyncCallback)
     */
    void queryAllAsync(final String column, final Object value, final AsyncCallback<List<T>> callback);

    /**
     * sql直接查询
     *
     * @param rawQuery
     * @return 实体类
     */
    T rawQuery(RawQuery rawQuery);

    /**
     * sql直接查询
     *
     * @param rawQuery
     * @return 实体类
     */
    List<T> rawQueryAll(RawQuery rawQuery);

    /**
     * 异步操作
     * <p>
     * sql 直接查询多条数据
     *
     * @param rawQuery
     * @param t        实体类
     * @param callback 异步回调
     * @return 实体类列表
     */
    void rawQueryAllAsync(RawQuery rawQuery, AsyncCallback<List<T>> callback);


}
