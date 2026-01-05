package com.indigo.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.indigo.iam.entity.Permission;
import com.indigo.iam.entity.Role;
import com.indigo.iam.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色权限关联Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 查询角色的所有权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    @Select("SELECT p.* FROM sys_permission p " +
            "INNER JOIN sys_role_permission rp ON p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId}")
    List<Permission> selectRolePermissions(@Param("roleId") Long roleId);

    /**
     * 查询权限被哪些角色使用
     *
     * @param permissionId 权限ID
     * @return 角色列表
     */
    @Select("SELECT r.* FROM sys_role r " +
            "INNER JOIN sys_role_permission rp ON r.id = rp.role_id " +
            "WHERE rp.permission_id = #{permissionId}")
    List<Role> selectPermissionRoles(@Param("permissionId") Long permissionId);
}
