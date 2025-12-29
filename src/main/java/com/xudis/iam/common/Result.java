package com.xudis.iam.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用响应结果类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功标识
     */
    private Boolean success;

    /**
     * 成功返回
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null, true);
    }

    /**
     * 成功返回(带数据)
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data, true);
    }

    /**
     * 成功返回(带消息和数据)
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data, true);
    }

    /**
     * 失败返回
     */
    public static <T> Result<T> error() {
        return new Result<>(500, "操作失败", null, false);
    }

    /**
     * 失败返回(带消息)
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null, false);
    }

    /**
     * 失败返回(带状态码和消息)
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null, false);
    }
}
