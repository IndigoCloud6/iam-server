package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.dto.BatchPermissionAssignDTO;
import com.xudis.iam.dto.RolePermissionAssignDTO;
import com.xudis.iam.entity.Permission;
import com.xudis.iam.entity.Role;
import com.xudis.iam.entity.RolePermission;
import com.xudis.iam.vo.RolePermissionTreeVO;

import java.util.List;

/**
 * 角色权限服务接口
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 给角色分配权限
     *
     * @param dto 角色权限分配DTO
     * @return 是否成功
     */
    boolean assignPermission(RolePermissionAssignDTO dto);

    /**
     * 批量分配权限
     *
     * @param dto 批量分配DTO
     * @return 是否成功
     */
    boolean batchAssignPermission(BatchPermissionAssignDTO dto);

    /**
     * 撤销角色权限
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return 是否成功
     */
    boolean revokePermission(Long roleId, Long permissionId);

    /**
     * 查询角色的所有权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Permission> getRolePermissions(Long roleId);

    /**
     * 查询权限被哪些角色使用
     *
     * @param permissionId 权限ID
     * @return 角色列表
     */
    List<Role> getPermissionRoles(Long permissionId);

    /**
     * 获取角色权限树
     *
     * @param roleId 角色ID
     * @return 权限树
     */
    List<RolePermissionTreeVO> getPermissionTree(Long roleId);
}
