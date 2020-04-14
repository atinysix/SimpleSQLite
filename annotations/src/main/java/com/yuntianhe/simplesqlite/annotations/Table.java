package com.yuntianhe.simplesqlite.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc:
 * author: daiwj on 2020-04-08 17:32
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Table {

    String tableName() default "";

    String databaseName();

}
