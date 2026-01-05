package com.indigo.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 更新角色DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "更新角色请求")
public class UpdateRoleRequest {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID", example = "1")
    private Long id;

    /**
     * 角色编码
     */
    @Size(max = 50, message = "角色编码长度不能超过50")
    @Schema(description = "角色编码", example = "ROLE_ADMIN")
    private String roleCode;

    /**
     * 角色名称
     */
    @Size(max = 50, message = "角色名称长度不能超过50")
    @Schema(description = "角色名称", example = "系统管理员")
    private String roleName;

    /**
     * 角色类型:1-系统角色,2-业务角色,3-自定义角色
     */
    @Schema(description = "角色类型:1-系统角色,2-业务角色,3-自定义角色", example = "1")
    private Integer roleType;

    /**
     * 角色级别(数字越小级别越高)
     */
    @Schema(description = "角色级别", example = "1")
    private Integer roleLevel;

    /**
     * 数据权限范围:1-全部数据,2-本部门及以下,3-本部门,4-仅本人,5-自定义
     */
    @Schema(description = "数据权限范围:1-全部数据,2-本部门及以下,3-本部门,4-仅本人,5-自定义", example = "1")
    private Integer dataScope;

    /**
     * 是否永久有效:0-否,1-是
     */
    @Schema(description = "是否永久有效:0-否,1-是", example = "1")
    private Integer isPermanent;

    /**
     * 生效时间
     */
    @Schema(description = "生效时间（支持各种时区，自动转换为上海时区）", example = "2025-01-01T00:00:00Z")
    private LocalDateTime effectiveFrom;

    /**
     * 失效时间
     */
    @Schema(description = "失效时间（支持各种时区，自动转换为上海时区）", example = "2025-12-31T23:59:59Z")
    private LocalDateTime effectiveTo;

    /**
     * 状态:1-启用,2-停用
     */
    @Schema(description = "状态:1-启用,2-停用", example = "1")
    private Integer status;

    /**
     * 最大用户数限制
     */
    @Schema(description = "最大用户数限制", example = "100")
    private Integer maxUserCount;

    /**
     * 是否为系统内置角色:0-否,1-是
     */
    @Schema(description = "是否为系统内置角色:0-否,1-是", example = "0")
    private Integer isSystem;

    /**
     * 是否为默认角色:0-否,1-是
     */
    @Schema(description = "是否为默认角色:0-否,1-是", example = "0")
    private Integer isDefault;

    /**
     * 是否要求MFA:0-否,1-是
     */
    @Schema(description = "是否要求MFA:0-否,1-是", example = "0")
    private Integer requireMfa;

    /**
     * IP白名单
     */
    @Schema(description = "IP白名单", example = "[\"192.168.1.1\", \"192.168.1.2\"]")
    private List<String> ipWhitelist;

    /**
     * 角色描述
     */
    @Size(max = 500, message = "角色描述不能超过500")
    @Schema(description = "角色描述", example = "系统管理员角色，拥有所有权限")
    private String description;
}
