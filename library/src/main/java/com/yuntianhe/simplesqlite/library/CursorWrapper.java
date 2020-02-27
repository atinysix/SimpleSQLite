package com.yuntianhe.simplesqlite.library;

import android.database.Cursor;

/**
 * 描述: CursorWrapper
 * 作者: daiwj on 2019/3/8 16:20
 */
public class CursorWrapper {

    public static final byte[] INVALID_BLOB = new byte[0];
    public static final long INVALID_LONG = 0L;
    public static final int INVALID_INTEGER = 0;
    public static final short INVALID_SHORT = (short) 0;
    public static final float INVALID_FLOAT = 0.0f;
    public static final double INVALID_DOUBLE = 0.0d;
    public static final String INVALID_STRING = "";

    private Cursor mCursor;

    CursorWrapper(Cursor c) {
        mCursor = c;
    }

    public int getCount() {
        return mCursor.getCount();
    }

    public boolean moveToFirst() {
        return mCursor.moveToFirst();
    }

    public boolean moveToNext() {
        return mCursor.moveToNext();
    }

    public int getColumnIndex(String columnName) {
        return mCursor.getColumnIndex(columnName);
    }

    public int getColumnCount() {
        return mCursor.getColumnCount();
    }

    public byte[] getBlob(String columnName) {
        return getBlob(mCursor.getColumnIndex(columnName));
    }

    public byte[] getBlob(int columnIndex) {
        return columnIndex != -1 ? mCursor.getBlob(columnIndex) : INVALID_BLOB;
    }

    public String getString(String columnName) {
        return getString(mCursor.getColumnIndex(columnName));
    }

    public String getString(int columnIndex) {
        return columnIndex != -1 ? mCursor.getString(columnIndex) : INVALID_STRING;
    }

    public short getShort(String columnName) {
        return getShort(mCursor.getColumnIndex(columnName));
    }

    public short getShort(int columnIndex) {
        return columnIndex != -1 ? mCursor.getShort(columnIndex) : INVALID_SHORT;
    }

    public int getInt(String columnName) {
        return getInt(mCursor.getColumnIndex(columnName));
    }

    public int getInt(int columnIndex) {
        return columnIndex != -1 ? mCursor.getInt(columnIndex) : INVALID_INTEGER;
    }

    public long getLong(String columnName) {
        return getLong(mCursor.getColumnIndex(columnName));
    }

    public long getLong(int columnIndex) {
        return columnIndex != -1 ? mCursor.getLong(columnIndex) : INVALID_LONG;
    }

    public float getFloat(String columnName) {
        return getFloat(mCursor.getColumnIndex(columnName));
    }

    public float getFloat(int columnIndex) {
        return columnIndex != -1 ? mCursor.getFloat(columnIndex) : INVALID_FLOAT;
    }

    public double getDouble(String columnName) {
        return getDouble(mCursor.getColumnIndex(columnName));
    }

    public double getDouble(int columnIndex) {
        return columnIndex != -1 ? mCursor.getDouble(columnIndex) : INVALID_DOUBLE;
    }

    public boolean isNull(int columnIndex) {
        return mCursor.isNull(columnIndex);
    }

    public boolean hasColumn(String column) {
        return mCursor.getColumnIndex(column) != -1;
    }

    public void close() {
        mCursor.close();
    }

    public boolean isClosed() {
        return mCursor.isClosed();
    }
}
