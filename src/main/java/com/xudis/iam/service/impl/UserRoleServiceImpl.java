package com.xudis.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.common.BusinessException;
import com.xudis.iam.dto.BatchRoleAssignDTO;
import com.xudis.iam.dto.UpdateRoleValidityDTO;
import com.xudis.iam.dto.UserRoleAssignDTO;
import com.xudis.iam.entity.Role;
import com.xudis.iam.entity.User;
import com.xudis.iam.entity.UserRole;
import com.xudis.iam.mapper.RoleMapper;
import com.xudis.iam.mapper.UserMapper;
import com.xudis.iam.mapper.UserRoleMapper;
import com.xudis.iam.service.CacheService;
import com.xudis.iam.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private CacheService cacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignRole(UserRoleAssignDTO dto) {
        // 验证用户是否存在
        User user = userMapper.selectById(dto.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证角色是否存在
        Role role = roleMapper.selectById(dto.getRoleId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 检查是否已分配
        if (hasRole(dto.getUserId(), dto.getRoleId(), dto.getDepartmentId())) {
            throw new BusinessException("用户已分配该角色");
        }

        // 创建用户角色关联
        UserRole userRole = new UserRole();
        userRole.setUserId(dto.getUserId());
        userRole.setRoleId(dto.getRoleId());
        userRole.setDepartmentId(dto.getDepartmentId());
        userRole.setEffectiveFrom(dto.getEffectiveFrom());
        userRole.setEffectiveTo(dto.getEffectiveTo());
        userRole.setIsActive(1);
        userRole.setAssignmentType(1); // 手动分配
        userRole.setCreatedAt(LocalDateTime.now());
        userRole.setUpdatedAt(LocalDateTime.now());

        boolean result = save(userRole);

        // 清除用户权限缓存
        if (result) {
            cacheService.clearUserPermissionCache(dto.getUserId());
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchAssignRole(BatchRoleAssignDTO dto) {
        // 验证角色是否存在
        Role role = roleMapper.selectById(dto.getRoleId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 收集待保存的用户角色关联
        List<UserRole> userRolesToSave = new ArrayList<>();

        // 批量分配
        for (Long userId : dto.getUserIds()) {
            // 验证用户是否存在
            User user = userMapper.selectById(userId);
            if (user == null) {
                continue; // 跳过不存在的用户
            }

            // 检查是否已分配
            if (hasRole(userId, dto.getRoleId(), dto.getDepartmentId())) {
                continue; // 跳过已分配的
            }

            // 创建用户角色关联
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(dto.getRoleId());
            userRole.setDepartmentId(dto.getDepartmentId());
            userRole.setEffectiveFrom(dto.getEffectiveFrom());
            userRole.setEffectiveTo(dto.getEffectiveTo());
            userRole.setIsActive(1);
            userRole.setAssignmentType(1); // 手动分配
            userRole.setCreatedAt(LocalDateTime.now());
            userRole.setUpdatedAt(LocalDateTime.now());

            userRolesToSave.add(userRole);
        }

        // 批量保存
        if (!userRolesToSave.isEmpty()) {
            saveBatch(userRolesToSave);
            
            // 清除所有用户的权限缓存
            for (UserRole userRole : userRolesToSave) {
                cacheService.clearUserPermissionCache(userRole.getUserId());
            }
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean revokeRole(Long userId, Long roleId, Long departmentId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId)
               .eq(UserRole::getRoleId, roleId);

        if (departmentId != null) {
            wrapper.eq(UserRole::getDepartmentId, departmentId);
        }

        boolean result = remove(wrapper);

        // 清除用户权限缓存
        if (result) {
            cacheService.clearUserPermissionCache(userId);
        }

        return result;
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        return baseMapper.selectUserRoles(userId);
    }

    @Override
    public Page<User> getRoleUsers(Page<User> page, Long roleId) {
        return baseMapper.selectRoleUsers(page, roleId);
    }

    @Override
    public List<Role> getEffectiveRoles(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return baseMapper.selectEffectiveRoles(userId, now);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateValidity(UpdateRoleValidityDTO dto) {
        UserRole userRole = getById(dto.getId());
        if (userRole == null) {
            throw new BusinessException("用户角色关联不存在");
        }

        userRole.setEffectiveFrom(dto.getEffectiveFrom());
        userRole.setEffectiveTo(dto.getEffectiveTo());
        userRole.setUpdatedAt(LocalDateTime.now());

        boolean result = updateById(userRole);

        // 清除用户权限缓存
        if (result) {
            cacheService.clearUserPermissionCache(userRole.getUserId());
        }

        return result;
    }

    @Override
    public boolean hasRole(Long userId, Long roleId, Long departmentId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId)
               .eq(UserRole::getRoleId, roleId);

        if (departmentId != null) {
            wrapper.eq(UserRole::getDepartmentId, departmentId);
        }

        return count(wrapper) > 0;
    }
}
