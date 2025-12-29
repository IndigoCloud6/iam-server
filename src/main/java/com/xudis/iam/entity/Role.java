package com.xudis.iam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色实体
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
@TableName(value = "sys_role", autoResultMap = true)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型:1-系统角色,2-业务角色,3-自定义角色
     */
    private Integer roleType;

    /**
     * 角色级别(数字越小级别越高)
     */
    private Integer roleLevel;

    /**
     * 数据权限范围:1-全部数据,2-本部门及以下,3-本部门,4-仅本人,5-自定义
     */
    private Integer dataScope;

    /**
     * 是否永久有效
     */
    private Integer isPermanent;

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
     * 状态:1-启用,2-停用
     */
    private Integer status;

    /**
     * 最大用户数限制
     */
    private Integer maxUserCount;

    /**
     * 当前用户数
     */
    private Integer currentUserCount;

    /**
     * 是否为系统内置角色
     */
    private Integer isSystem;

    /**
     * 是否为默认角色
     */
    private Integer isDefault;

    /**
     * 是否要求MFA
     */
    private Integer requireMfa;

    /**
     * IP白名单
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> ipWhitelist;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 更新人
     */
    private Long updatedBy;

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
