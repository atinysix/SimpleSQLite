package com.yuntianhe.simplesqlite.library;

/**
 * 描述:
 * 作者: daiwj on 2019/2/26 13:59
 */
public interface IDataBaseController {

    Database openReadableDatabase();

    Database openWritableDatabase();
}
