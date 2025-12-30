package com.xudis.iam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.common.BusinessException;
import com.xudis.iam.dto.BatchUserPermissionAssignDTO;
import com.xudis.iam.dto.UserPermissionAssignDTO;
import com.xudis.iam.entity.Permission;
import com.xudis.iam.entity.Role;
import com.xudis.iam.entity.UserPermission;
import com.xudis.iam.mapper.UserPermissionMapper;
import com.xudis.iam.service.PermissionService;
import com.xudis.iam.service.RolePermissionService;
import com.xudis.iam.service.UserService;
import com.xudis.iam.service.UserPermissionService;
import com.xudis.iam.service.UserRoleService;
import com.xudis.iam.vo.UserAllPermissionsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户权限服务实现类
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Service
@RequiredArgsConstructor
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermission> implements UserPermissionService {

    private final UserRoleService userRoleService;
    private final RolePermissionService rolePermissionService;
    private final PermissionService permissionService;
    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignPermission(UserPermissionAssignDTO dto) {
        // 检查用户是否存在
        com.xudis.iam.entity.User user = userService.getById(dto.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在: " + dto.getUserId());
        }

        // 检查权限是否存在
        Permission permission = permissionService.getById(dto.getPermissionId());
        if (permission == null) {
            throw new BusinessException("权限不存在: " + dto.getPermissionId());
        }

        // 检查是否已经分配过该权限
        LambdaQueryWrapper<UserPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPermission::getUserId, dto.getUserId())
                .eq(UserPermission::getPermissionId, dto.getPermissionId());
        UserPermission existing = this.getOne(wrapper);
        if (existing != null) {
            throw new BusinessException("用户已拥有该权限，无需重复分配");
        }

        // 创建用户权限关联
        UserPermission userPermission = new UserPermission();
        BeanUtil.copyProperties(dto, userPermission);

        // 设置默认值
        if (userPermission.getGrantType() == null) {
            userPermission.setGrantType(1); // 直接授予
        }
        if (userPermission.getIsEnabled() == null) {
            userPermission.setIsEnabled(1); // 启用
        }

        return this.save(userPermission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchAssignPermission(BatchUserPermissionAssignDTO dto) {
        // 检查用户是否存在
        com.xudis.iam.entity.User user = userService.getById(dto.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在: " + dto.getUserId());
        }

        // 检查权限是否存在
        List<Permission> permissions = permissionService.listByIds(dto.getPermissionIds());
        if (permissions.size() != dto.getPermissionIds().size()) {
            throw new BusinessException("部分权限不存在");
        }

        // 查询用户已有的权限
        LambdaQueryWrapper<UserPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPermission::getUserId, dto.getUserId());
        List<UserPermission> existingPermissions = this.list(wrapper);
        Set<Long> existingPermissionIds = existingPermissions.stream()
                .map(UserPermission::getPermissionId)
                .collect(Collectors.toSet());

        // 过滤掉已存在的权限
        List<Long> newPermissionIds = dto.getPermissionIds().stream()
                .filter(id -> !existingPermissionIds.contains(id))
                .collect(Collectors.toList());

        if (newPermissionIds.isEmpty()) {
            return true;
        }

        // 批量创建用户权限关联
        List<UserPermission> userPermissions = new ArrayList<>();
        for (Long permissionId : newPermissionIds) {
            UserPermission userPermission = new UserPermission();
            userPermission.setUserId(dto.getUserId());
            userPermission.setPermissionId(permissionId);
            userPermission.setGrantType(dto.getGrantType() != null ? dto.getGrantType() : 1);
            userPermission.setGrantReason(dto.getGrantReason());
            userPermission.setIsEnabled(1);
            userPermissions.add(userPermission);
        }

        return this.saveBatch(userPermissions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean revokePermission(Long userId, Long permissionId) {
        LambdaQueryWrapper<UserPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPermission::getUserId, userId)
                .eq(UserPermission::getPermissionId, permissionId);
        return this.remove(wrapper);
    }

    @Override
    public List<Permission> getUserDirectPermissions(Long userId) {
        // 查询用户的直接权限关联
        LambdaQueryWrapper<UserPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPermission::getUserId, userId)
                .eq(UserPermission::getIsEnabled, 1);
        List<UserPermission> userPermissions = this.list(wrapper);

        if (userPermissions.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取权限ID列表
        List<Long> permissionIds = userPermissions.stream()
                .map(UserPermission::getPermissionId)
                .collect(Collectors.toList());

        // 查询权限详情
        return permissionService.listByIds(permissionIds);
    }

    @Override
    public UserAllPermissionsVO getUserAllPermissions(Long userId) {
        UserAllPermissionsVO result = new UserAllPermissionsVO();
        result.setUserId(userId);

        // 1. 获取用户的直接权限
        List<Permission> directPermissions = getUserDirectPermissions(userId);
        List<UserAllPermissionsVO.PermissionSourceVO> directPermissionVOs = directPermissions.stream()
                .map(p -> {
                    UserAllPermissionsVO.PermissionSourceVO vo = new UserAllPermissionsVO.PermissionSourceVO();
                    vo.setPermissionId(p.getId());
                    vo.setPermCode(p.getPermCode());
                    vo.setPermName(p.getPermName());
                    vo.setPermType(p.getPermType());
                    vo.setSource("DIRECT");
                    return vo;
                })
                .collect(Collectors.toList());
        result.setDirectPermissions(directPermissionVOs);

        // 2. 获取用户的角色及其权限
        List<Role> userRoles = userRoleService.getUserRoles(userId);
        List<UserAllPermissionsVO.RolePermissionSourceVO> rolePermissionVOs = userRoles.stream()
                .map(role -> {
                    UserAllPermissionsVO.RolePermissionSourceVO roleVO = new UserAllPermissionsVO.RolePermissionSourceVO();
                    roleVO.setRoleId(role.getId());
                    roleVO.setRoleName(role.getRoleName());
                    roleVO.setRoleCode(role.getRoleCode());

                    // 获取该角色的权限列表
                    List<Permission> rolePermissions = rolePermissionService.getRolePermissions(role.getId());
                    List<UserAllPermissionsVO.PermissionSourceVO> permVOs = rolePermissions.stream()
                            .map(p -> {
                                UserAllPermissionsVO.PermissionSourceVO permVO = new UserAllPermissionsVO.PermissionSourceVO();
                                permVO.setPermissionId(p.getId());
                                permVO.setPermCode(p.getPermCode());
                                permVO.setPermName(p.getPermName());
                                permVO.setPermType(p.getPermType());
                                permVO.setSource("ROLE");
                                permVO.setRoleId(role.getId());
                                permVO.setRoleName(role.getRoleName());
                                return permVO;
                            })
                            .collect(Collectors.toList());

                    roleVO.setPermissions(permVOs);
                    return roleVO;
                })
                .collect(Collectors.toList());
        result.setRolePermissions(rolePermissionVOs);

        // 3. 合并所有权限（去重）
        Set<Long> permissionIdSet = new HashSet<>();
        List<UserAllPermissionsVO.PermissionSourceVO> allPermissions = new ArrayList<>();

        // 先添加直接权限
        for (UserAllPermissionsVO.PermissionSourceVO vo : directPermissionVOs) {
            if (permissionIdSet.add(vo.getPermissionId())) {
                allPermissions.add(vo);
            }
        }

        // 再添加角色权限（去重）
        for (UserAllPermissionsVO.RolePermissionSourceVO roleVO : rolePermissionVOs) {
            for (UserAllPermissionsVO.PermissionSourceVO permVO : roleVO.getPermissions()) {
                if (permissionIdSet.add(permVO.getPermissionId())) {
                    allPermissions.add(permVO);
                }
            }
        }

        result.setAllPermissions(allPermissions);

        return result;
    }

    @Override
    public boolean hasPermission(Long userId, String permCode) {
        // 检查直接权限
        List<Permission> directPermissions = getUserDirectPermissions(userId);
        boolean hasDirect = directPermissions.stream()
                .anyMatch(p -> p.getPermCode().equals(permCode));
        if (hasDirect) {
            return true;
        }

        // 检查角色权限
        List<Role> userRoles = userRoleService.getUserRoles(userId);
        for (Role role : userRoles) {
            List<Permission> rolePermissions = rolePermissionService.getRolePermissions(role.getId());
            if (rolePermissions.stream().anyMatch(p -> p.getPermCode().equals(permCode))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasPermissionById(Long userId, Long permissionId) {
        // 检查直接权限
        LambdaQueryWrapper<UserPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPermission::getUserId, userId)
                .eq(UserPermission::getPermissionId, permissionId)
                .eq(UserPermission::getIsEnabled, 1);
        UserPermission userPermission = this.getOne(wrapper);
        if (userPermission != null) {
            return true;
        }

        // 检查角色权限
        List<Role> userRoles = userRoleService.getUserRoles(userId);
        for (Role role : userRoles) {
            List<Permission> rolePermissions = rolePermissionService.getRolePermissions(role.getId());
            if (rolePermissions.stream().anyMatch(p -> p.getId().equals(permissionId))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Long> getUserIdsByPermission(Long permissionId) {
        LambdaQueryWrapper<UserPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPermission::getPermissionId, permissionId)
                .eq(UserPermission::getIsEnabled, 1);
        List<UserPermission> userPermissions = this.list(wrapper);

        return userPermissions.stream()
                .map(UserPermission::getUserId)
                .distinct()
                .collect(Collectors.toList());
    }
}
