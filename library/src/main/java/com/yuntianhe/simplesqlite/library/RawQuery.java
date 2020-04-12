package com.yuntianhe.simplesqlite.library;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 15:11
 */
public class RawQuery {

    private String sql;

    private RawQuery(String sql) {
        this.sql = sql;
    }

    public static final RawQuery from(String sql) {
        return new RawQuery(sql);
    }

    public String getSql() {
        return sql == null ? "" : sql;
    }


}
