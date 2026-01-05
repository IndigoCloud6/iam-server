package com.indigo.iam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户权限关联实体
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@TableName(value = "sys_user_permission", autoResultMap = true)
public class UserPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 权限ID
     */
    private Long permissionId;

    /**
     * 授予类型:1-直接授予,2-继承,3-临时授权
     */
    private Integer grantType;

    /**
     * 授予人ID
     */
    private Long grantedBy;

    /**
     * 授予原因说明
     */
    private String grantReason;

    /**
     * 操作类型(当权限为数据权限时使用)
     */
    private String operationType;

    /**
     * 条件表达式(JSON格式)
     */
    private String conditionExpression;

    /**
     * 字段过滤规则
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> fieldFilter;

    /**
     * 行过滤规则
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> rowFilter;

    /**
     * 生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveFrom;

    /**
     * 失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveTo;

    /**
     * 是否启用:0-禁用,1-启用
     */
    private Integer isEnabled;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
