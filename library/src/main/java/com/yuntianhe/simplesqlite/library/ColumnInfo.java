package com.yuntianhe.simplesqlite.library;

import java.lang.reflect.Field;
import java.util.List;

/**
 * desc:
 * author: daiwj on 2020-04-08 17:43
 */
public class ColumnInfo {

    private Field mField;

    private String mFieldName;

    private String mFieldType = FieldType.TEXT.getFieldType();

    private boolean primaryKey = false;

    private boolean autoIncrement = false;

    private boolean notNull;

    private String defaultValue;

    private List<Long> foreignKey;

    public Field getField() {
        return mField;
    }

    public void setField(Field field) {
        mField = field;
    }

    public String getFieldType() {
        return mFieldType == null ? "" : mFieldType;
    }

    public void setFieldType(Class<?> classType) {
        this.mFieldType = FieldType.getType(classType);
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getFieldName() {
        return mFieldName == null ? "" : mFieldName;
    }

    public void setFieldName(String fieldName) {
        mFieldName = fieldName;
    }

    public void setFieldType(String fieldType) {
        mFieldType = fieldType;
    }

    public boolean isAutoincrement() {
        return autoIncrement;
    }

    public void setAutoincrement(boolean autoincrement) {
        this.autoIncrement = autoincrement;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public List<Long> getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(List<Long> foreignKey) {
        this.foreignKey = foreignKey;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFieldName() + " ")
                .append(getFieldType() + " ")
                .append(isPrimaryKey() ? "primary key " : "")
                .append(isAutoincrement() ? "autoincrement" : "");
        return sb.toString();
    }
}
