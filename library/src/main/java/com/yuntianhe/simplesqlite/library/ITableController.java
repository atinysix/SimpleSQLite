package com.yuntianhe.simplesqlite.library;

/**
 * 描述:
 * 作者: daiwj on 2019/2/26 14:26
 */
public interface ITableController {

    void create(Table table);

    void delete(String tableName);
}
