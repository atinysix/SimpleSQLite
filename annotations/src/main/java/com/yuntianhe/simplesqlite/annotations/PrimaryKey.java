package com.yuntianhe.simplesqlite.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @deprecated 不推荐使用自定义主键，SimpleSQLite 会自动帮你创建"_ID"作为自增主键
 * desc:
 * author: daiwj on 2020-04-08 21:24
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
@Deprecated
public @interface PrimaryKey {

    String name() default "";

    boolean autoincrement() default true;

}
