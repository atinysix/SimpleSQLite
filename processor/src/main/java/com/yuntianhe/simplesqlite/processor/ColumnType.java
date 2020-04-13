package com.yuntianhe.simplesqlite.processor;

/**
 * desc:
 * author: daiwj on 2020-04-08 21:05
 */
public enum ColumnType {

    BYTE("byte", "byte", "Byte"),
    SHORT("short", "short", "Short"),
    INTEGER("integer", "int", "Integer"),
    LONG("long", "long", "Long"),
    FLOAT("float", "float", "Float"),
    DOUBLE("double", "double", "Double"),
    TEXT("text", "String");

    private String columnType;
    private String[] fieldType;

    ColumnType(String columnType, String... fieldType) {
        this.columnType = columnType;
        this.fieldType = fieldType;
    }

    public String[] getFieldType() {
        return fieldType;
    }

    public String getColumnType() {
        return columnType == null ? "" : columnType;
    }

    public static String get(String fieldType) {
        final ColumnType[] fieldTypes = ColumnType.values();
        for (ColumnType type : fieldTypes) {
            String[] types = type.getFieldType();
            for (String source : types) {
                if (source.equals(fieldType)) {
                    return type.getColumnType();
                }
            }
        }
        return TEXT.getColumnType();
    }
}
