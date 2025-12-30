package com.xudis.iam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 权限变更日志实体
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission_change_log")
public class PermissionChangeLog extends BaseEntity {

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 变更类型: GRANT-授权, REVOKE-撤销, MODIFY-修改
     */
    private String changeType;

    /**
     * 目标类型: USER-用户, ROLE-角色, DEPARTMENT-部门
     */
    private String targetType;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 目标名称
     */
    private String targetName;

    /**
     * 权限ID
     */
    private Long permissionId;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 变更前值(JSON格式)
     */
    private String oldValue;

    /**
     * 变更后值(JSON格式)
     */
    private String newValue;

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
     * 变更原因
     */
    private String changeReason;

    /**
     * 变更时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime changeTime;

    /**
     * 是否生效: 1-是, 0-否
     */
    private Integer effective;
}
