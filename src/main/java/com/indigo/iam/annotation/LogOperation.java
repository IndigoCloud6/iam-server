package com.indigo.iam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogOperation {

    /**
     * 操作模块
     */
    String module() default "";

    /**
     * 操作类型: LOGIN-登录, LOGOUT-登出, CREATE-新增, UPDATE-更新, DELETE-删除,
     * QUERY-查询, EXPORT-导出, IMPORT-导入, OTHER-其他
     */
    String operationType() default "OTHER";

    /**
     * 操作描述
     */
    String description() default "";

    /**
     * 是否保存请求参数
     */
    boolean saveParams() default true;

    /**
     * 是否保存响应结果
     */
    boolean saveResponse() default false;
}
