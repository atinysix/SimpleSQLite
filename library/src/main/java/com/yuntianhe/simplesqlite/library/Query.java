package com.yuntianhe.simplesqlite.library;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述:
 * 作者: daiwj on 2019/2/22 14:09
 */
public class Query {

    public static final String TAG = Query.class.getSimpleName();

    private String tableName;

    private String[] columns;

    private String groupBy;

    private String orderBy;

    private String having;

    private String limit;

    private StringBuilder selectionBuilder;

    private List<Object> selectionArgsBuilder;

    private Query(String tableName) {
        this.tableName = tableName;
        selectionBuilder = new StringBuilder();
        selectionArgsBuilder = new ArrayList<>();
    }

    public static final Query from(String tableName) {
        return new Query(tableName);
    }

    /**
     * 注意：查询指定列的数据，在解析Cursor时，需要手动判断Cursor是否包含此列
     *
     * @param columns
     * @return
     */
    public Query column(String... columns) {
        this.columns = columns;
        return this;
    }

    public Query and() {
        selectionBuilder.append(" and ");
        return this;
    }

    public Query or() {
        selectionBuilder.append(" or ");
        return this;
    }

    public Query like(String column, Object selectionArg) {
        selectionBuilder.append(column + " like ?");
        selectionArgsBuilder.add("%" + selectionArg + "%");
        return this;
    }

    public Query in(String column, List<Object> selectionArgs) {
        if (selectionArgs != null && selectionArgs.size() != 0) {
            return in(column, selectionArgs.toArray());
        }
        return this;
    }

    public Query in(String column, Object... selectionArgs) {
        if (selectionArgs != null && selectionArgs.length != 0) {
            selectionBuilder.append(column + " in(");
            for (int i = 0, length = selectionArgs.length; i < length; i++) {
                Object arg = selectionArgs[i];
                selectionBuilder.append("?");
                if (i != length - 1) {
                    selectionBuilder.append(", ");
                } else {
                    selectionBuilder.append(")");
                }
                selectionArgsBuilder.add(arg);
            }
        }
        return this;
    }

    /**
     * greater than
     *
     * @return
     */
    public Query gt(String column, Object selectionArg) {
        selectionBuilder.append(column + " > ?");
        selectionArgsBuilder.add(selectionArg);
        return this;
    }

    /**
     * less than
     *
     * @return
     */
    public Query lt(String column, Object selectionArg) {
        selectionBuilder.append(column + " < ?");
        selectionArgsBuilder.add(selectionArg);
        return this;
    }

    /**
     * equals to
     *
     * @return
     */
    public Query equal(String column, Object selectionArg) {
        selectionBuilder.append(column + " = ?");
        selectionArgsBuilder.add(selectionArg);
        return this;
    }

    /**
     * greater and equal
     *
     * @return
     */
    public Query ge(String column, Object selectionArg) {
        selectionBuilder.append(column + " >= ?");
        selectionArgsBuilder.add(selectionArg);
        return this;
    }

    /**
     * less and equal
     *
     * @return
     */
    public Query le(String column, Object selectionArg) {
        selectionBuilder.append(column + " <= ?");
        selectionArgsBuilder.add(selectionArg);
        return this;
    }

    public Query between(String column, Object selectionArg1, Object selectionArg2) {
        selectionBuilder.append(column + " between ? and ?");
        selectionArgsBuilder.add(selectionArg1);
        selectionArgsBuilder.add(selectionArg2);
        return this;
    }

    public Query leftJoin() {
        return this;
    }

    public String getTableName() {
        return tableName == null ? "" : tableName;
    }

    public String[] getColumns() {
        return columns;
    }

    public String getSelection() {
        return selectionBuilder.toString();
    }

    public String[] getSelectionArgs() {
        final int size = selectionArgsBuilder.size();
        String[] args = new String[size];
        for (int i = 0; i < size; i++) {
            args[i] = selectionArgsBuilder.get(i).toString();
        }
        return args;
    }

    public String getGroupBy() {
        return groupBy == null ? "" : groupBy;
    }

    public Query groupBy(String groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public String getOrderBy() {
        return orderBy == null ? "" : orderBy;
    }

    public Query orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public String getHaving() {
        return having == null ? "" : having;
    }

    public Query having(String having) {
        this.having = having;
        return this;
    }

    public String getLimit() {
        return limit == null ? "" : limit;
    }

    public Query limit(int limit) {
        return limit(0, limit);
    }

    public Query limit(int offset, int limit) {
        StringBuilder sb = new StringBuilder();
        sb.append(offset).append(", ").append(limit);
        this.limit = sb.toString();
        return this;
    }

    public Query page(int pageCount, int page) {
        StringBuilder sb = new StringBuilder();
        sb.append(pageCount * (page - 1)).append(", ").append(pageCount);
        this.limit = sb.toString();
        return this;
    }

    public Query clearSelection() {
        selectionBuilder.delete(0, selectionBuilder.length());
        selectionArgsBuilder.clear();
        return this;
    }

    public Query log() {
        Log.w(TAG, toString());
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("selection: ").append(selectionBuilder.toString()).append("   ");
        sb.append("selectionArgs: ").append(Arrays.toString(getSelectionArgs()));
        return sb.toString();
    }
}
