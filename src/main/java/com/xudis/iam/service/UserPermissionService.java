package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.dto.BatchUserPermissionAssignDTO;
import com.xudis.iam.dto.UserPermissionAssignDTO;
import com.xudis.iam.entity.Permission;
import com.xudis.iam.entity.UserPermission;
import com.xudis.iam.vo.UserAllPermissionsVO;

import java.util.List;

/**
 * 用户权限服务接口
 *
 * @author MaxYun
 * @since 2025/12/30
 */
public interface UserPermissionService extends IService<UserPermission> {

    /**
     * 给用户分配权限
     *
     * @param dto 用户权限分配DTO
     * @return 是否成功
     */
    boolean assignPermission(UserPermissionAssignDTO dto);

    /**
     * 批量给用户分配权限
     *
     * @param dto 批量分配DTO
     * @return 是否成功
     */
    boolean batchAssignPermission(BatchUserPermissionAssignDTO dto);

    /**
     * 撤销用户权限
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     * @return 是否成功
     */
    boolean revokePermission(Long userId, Long permissionId);

    /**
     * 查询用户的直接权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Permission> getUserDirectPermissions(Long userId);

    /**
     * 查询用户的所有权限（直接权限 + 角色权限）
     *
     * @param userId 用户ID
     * @return 用户所有权限信息
     */
    UserAllPermissionsVO getUserAllPermissions(Long userId);

    /**
     * 检查用户是否拥有指定权限
     *
     * @param userId    用户ID
     * @param permCode 权限编码
     * @return 是否拥有
     */
    boolean hasPermission(Long userId, String permCode);

    /**
     * 检查用户是否拥有指定权限（通过权限ID）
     *
     * @param userId       用户ID
     * @param permissionId 权限ID
     * @return 是否拥有
     */
    boolean hasPermissionById(Long userId, Long permissionId);

    /**
     * 查询拥有指定权限的用户列表
     *
     * @param permissionId 权限ID
     * @return 用户ID列表
     */
    List<Long> getUserIdsByPermission(Long permissionId);
}
