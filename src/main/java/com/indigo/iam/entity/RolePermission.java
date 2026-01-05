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
 * 角色权限关联实体
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
@TableName(value = "sys_role_permission", autoResultMap = true)
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID
     */
    private Long permissionId;

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
     * 是否可被继承
     */
    private Integer isInheritable;

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
