package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.dto.BatchRoleAssignDTO;
import com.xudis.iam.dto.UpdateRoleValidityDTO;
import com.xudis.iam.dto.UserRoleAssignDTO;
import com.xudis.iam.entity.Role;
import com.xudis.iam.entity.User;
import com.xudis.iam.entity.UserRole;

import java.util.List;

/**
 * 用户角色服务接口
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 给用户分配角色
     *
     * @param dto 用户角色分配DTO
     * @return 是否成功
     */
    boolean assignRole(UserRoleAssignDTO dto);

    /**
     * 批量分配角色给多个用户
     *
     * @param dto 批量分配DTO
     * @return 是否成功
     */
    boolean batchAssignRole(BatchRoleAssignDTO dto);

    /**
     * 撤销用户角色
     *
     * @param userId       用户ID
     * @param roleId       角色ID
     * @param departmentId 部门ID（可选）
     * @return 是否成功
     */
    boolean revokeRole(Long userId, Long roleId, Long departmentId);

    /**
     * 查询用户的所有角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> getUserRoles(Long userId);

    /**
     * 查询角色下的所有用户（分页）
     *
     * @param page   分页对象
     * @param roleId 角色ID
     * @return 用户分页列表
     */
    Page<User> getRoleUsers(Page<User> page, Long roleId);

    /**
     * 查询用户的生效角色
     *
     * @param userId 用户ID
     * @return 生效的角色列表
     */
    List<Role> getEffectiveRoles(Long userId);

    /**
     * 更新角色有效期
     *
     * @param dto 更新有效期DTO
     * @return 是否成功
     */
    boolean updateValidity(UpdateRoleValidityDTO dto);

    /**
     * 检查用户是否已分配指定角色
     *
     * @param userId       用户ID
     * @param roleId       角色ID
     * @param departmentId 部门ID（可选）
     * @return 是否已分配
     */
    boolean hasRole(Long userId, Long roleId, Long departmentId);
}
