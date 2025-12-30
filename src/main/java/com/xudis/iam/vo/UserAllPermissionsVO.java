package com.xudis.iam.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用户所有权限VO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "用户所有权限")
public class UserAllPermissionsVO {

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 直接授予的权限列表
     */
    @Schema(description = "直接授予的权限列表")
    private List<PermissionSourceVO> directPermissions;

    /**
     * 通过角色获得的权限列表
     */
    @Schema(description = "通过角色获得的权限列表")
    private List<RolePermissionSourceVO> rolePermissions;

    /**
     * 所有权限（去重后的完整列表）
     */
    @Schema(description = "所有权限（去重后）")
    private List<PermissionSourceVO> allPermissions;

    /**
     * 权限来源
     */
    @Data
    @Schema(description = "权限来源")
    public static class PermissionSourceVO {

        /**
         * 权限ID
         */
        @Schema(description = "权限ID")
        private Long permissionId;

        /**
         * 权限编码
         */
        @Schema(description = "权限编码")
        private String permCode;

        /**
         * 权限名称
         */
        @Schema(description = "权限名称")
        private String permName;

        /**
         * 权限类型
         */
        @Schema(description = "权限类型:1-菜单,2-按钮,3-API接口,4-数据权限,5-文件权限")
        private Integer permType;

        /**
         * 来源: DIRECT-直接授予, ROLE-来自角色
         */
        @Schema(description = "来源")
        private String source;

        /**
         * 角色ID（当来源为ROLE时有值）
         */
        @Schema(description = "角色ID")
        private Long roleId;

        /**
         * 角色名称（当来源为ROLE时有值）
         */
        @Schema(description = "角色名称")
        private String roleName;
    }

    /**
     * 角色权限来源
     */
    @Data
    @Schema(description = "角色权限来源")
    public static class RolePermissionSourceVO {

        /**
         * 角色ID
         */
        @Schema(description = "角色ID")
        private Long roleId;

        /**
         * 角色名称
         */
        @Schema(description = "角色名称")
        private String roleName;

        /**
         * 角色编码
         */
        @Schema(description = "角色编码")
        private String roleCode;

        /**
         * 该角色的权限列表
         */
        @Schema(description = "该角色的权限列表")
        private List<PermissionSourceVO> permissions;
    }
}
