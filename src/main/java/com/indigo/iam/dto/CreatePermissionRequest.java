package com.indigo.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 创建权限DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "创建权限请求")
public class CreatePermissionRequest {

    /**
     * 父权限ID
     */
    @Schema(description = "父权限ID", example = "0")
    private Long parentId;

    /**
     * 权限类型:1-菜单,2-按钮,3-API接口,4-数据权限,5-文件权限
     */
    @NotNull(message = "权限类型不能为空")
    @Schema(description = "权限类型:1-菜单,2-按钮,3-API接口,4-数据权限,5-文件权限", example = "1")
    private Integer permType;

    /**
     * 权限标识符(唯一)
     */
    @NotBlank(message = "权限标识符不能为空")
    @Size(max = 100, message = "权限标识符长度不能超过100")
    @Schema(description = "权限标识符", example = "system:user:view")
    private String permCode;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Size(max = 50, message = "权限名称长度不能超过50")
    @Schema(description = "权限名称", example = "用户查看")
    private String permName;

    /**
     * 权限层级
     */
    @Schema(description = "权限层级", example = "1")
    private Integer permLevel;

    /**
     * 菜单路径
     */
    @Size(max = 200, message = "菜单路径长度不能超过200")
    @Schema(description = "菜单路径", example = "/system/user")
    private String menuPath;

    /**
     * 前端组件
     */
    @Size(max = 200, message = "前端组件路径不能超过200")
    @Schema(description = "前端组件", example = "system/user/index")
    private String component;

    /**
     * 重定向地址
     */
    @Size(max = 200, message = "重定向地址不能超过200")
    @Schema(description = "重定向地址", example = "/index")
    private String redirect;

    /**
     * 菜单图标
     */
    @Size(max = 100, message = "菜单图标不能超过100")
    @Schema(description = "菜单图标", example = "user")
    private String icon;

    /**
     * 是否外链:0-否,1-是
     */
    @Schema(description = "是否外链:0-否,1-是", example = "0")
    private Integer isExternal;

    /**
     * 外链地址
     */
    @Size(max = 500, message = "外链地址不能超过500")
    @Schema(description = "外链地址", example = "https://example.com")
    private String externalUrl;

    /**
     * 是否缓存:0-否,1-是
     */
    @Schema(description = "是否缓存:0-否,1-是", example = "1")
    private Integer isCache;

    /**
     * 是否显示:0-隐藏,1-显示
     */
    @Schema(description = "是否显示:0-隐藏,1-显示", example = "1")
    private Integer isVisible;

    /**
     * 是否显示面包屑:0-否,1-是
     */
    @Schema(description = "是否显示面包屑:0-否,1-是", example = "1")
    private Integer isBreadcrumb;

    /**
     * API路径(支持Ant风格)
     */
    @Size(max = 200, message = "API路径不能超过200")
    @Schema(description = "API路径", example = "/api/user/**")
    private String apiPath;

    /**
     * HTTP方法(GET,POST,PUT,DELETE等)
     */
    @Size(max = 20, message = "HTTP方法不能超过20")
    @Schema(description = "HTTP方法", example = "GET,POST")
    private String apiMethod;

    /**
     * 数据权限规则(JSON格式)
     */
    @Schema(description = "数据权限规则")
    private String dataScopeRule;

    /**
     * 字段权限规则
     */
    @Schema(description = "字段权限规则")
    private String fieldScope;

    /**
     * 状态:1-启用,2-停用
     */
    @Schema(description = "状态:1-启用,2-停用", example = "1")
    private Integer status;

    /**
     * 排序号
     */
    @Schema(description = "排序号", example = "1")
    private Integer orderNum;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注不能超过500")
    @Schema(description = "备注", example = "用户管理权限")
    private String remark;
}
