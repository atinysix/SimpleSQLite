package com.yuntianhe.simplesqlite.library;

/**
 * desc:
 * author: daiwj on 2020-04-08 21:05
 */
public enum FieldType {

    INTEGER("integer", byte.class, Byte.class,
            short.class, Short.class,
            int.class, Integer.class,
            long.class, Long.class),

    FLOAT("float", float.class, Float.class),

    DOUBLE("double", double.class, Double.class),

    TEXT("text", String.class);

    private String fieldType;
    private Class<?>[] classType;

    FieldType(String fieldType, Class<?>... classType) {
        this.fieldType = fieldType;
        this.classType = classType;
    }

    public String getFieldType() {
        return fieldType == null ? "" : fieldType;
    }

    public Class<?>[] getClassType() {
        return classType;
    }

    public static String getType(Class<?> c) {
        final FieldType[] fieldTypes = FieldType.values();
        for (FieldType type : fieldTypes) {
            Class<?>[] classes = type.getClassType();
            for (Class<?> origin : classes) {
                if (origin == c) {
                    return type.getFieldType();
                }
            }
        }
        return TEXT.getFieldType();
    }

}
