package com.xudis.iam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 操作日志实体
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_operation_log")
public class OperationLog extends BaseEntity {

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型: LOGIN-登录, LOGOUT-登出, CREATE-新增, UPDATE-更新, DELETE-删除,
     * QUERY-查询, EXPORT-导出, IMPORT-导入, OTHER-其他
     */
    private String operationType;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应结果
     */
    private String responseData;

    /**
     * 操作状态: 1-成功, 0-失败
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 执行时长(毫秒)
     */
    private Long duration;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作IP
     */
    private String operatorIp;

    /**
     * 操作地点
     */
    private String operatorLocation;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 跟踪ID（用于分布式追踪）
     */
    private String traceId;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationTime;
}
