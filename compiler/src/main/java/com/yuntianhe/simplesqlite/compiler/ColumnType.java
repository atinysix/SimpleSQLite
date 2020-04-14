package com.yuntianhe.simplesqlite.compiler;

/**
 * desc:
 * author: daiwj on 2020-04-08 21:05
 */
public enum ColumnType {

    BYTE("integer", "byte", "Byte"),
    SHORT("integer", "short", "Short"),
    INT("integer", "int", "Integer"),
    LONG("integer", "long", "Long"),
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
