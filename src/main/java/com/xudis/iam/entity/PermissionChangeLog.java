package com.xudis.iam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限变更日志实体
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@TableName("sys_permission_change_log")
public class PermissionChangeLog {

    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 变更类型: GRANT-授权, REVOKE-撤销, UPDATE-修改
     */
    private String changeType;

    /**
     * 目标类型: USER-用户, ROLE-角色, GROUP-用户组
     */
    private String targetType;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 权限ID
     */
    private Long permissionId;

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
     * 变更原因
     */
    private String changeReason;

    /**
     * 工单ID
     */
    private String ticketId;

    /**
     * 变更时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime changeTime;
}
