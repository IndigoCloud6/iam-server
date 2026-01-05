package com.indigo.iam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限树形VO
 *
 * @author MaxYun
 * @since 2025/12/31
 */
@Data
public class PermissionTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 父权限ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 权限类型:1-菜单,2-按钮,3-API接口,4-数据权限,5-文件权限
     */
    private Integer permType;

    /**
     * 权限标识符
     */
    private String permCode;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限层级
     */
    private Integer permLevel;

    /**
     * 菜单路径
     */
    private String menuPath;

    /**
     * 前端组件
     */
    private String component;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否外链
     */
    private Integer isExternal;

    /**
     * 外链地址
     */
    private String externalUrl;

    /**
     * 是否缓存
     */
    private Integer isCache;

    /**
     * 是否显示
     */
    private Integer isVisible;

    /**
     * API路径
     */
    private String apiPath;

    /**
     * HTTP方法
     */
    private String apiMethod;

    /**
     * 状态:1-启用,2-停用
     */
    private Integer status;

    /**
     * 排序号
     */
    private Integer orderNum;

    /**
     * 备注
     */
    private String remark;

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

    /**
     * 子权限列表
     */
    private List<PermissionTreeVO> children;
}
