package com.xudis.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.common.BusinessException;
import com.xudis.iam.common.CacheKeyConstants;
import com.xudis.iam.dto.BatchPermissionAssignDTO;
import com.xudis.iam.dto.RolePermissionAssignDTO;
import com.xudis.iam.entity.Permission;
import com.xudis.iam.entity.Role;
import com.xudis.iam.entity.RolePermission;
import com.xudis.iam.mapper.PermissionMapper;
import com.xudis.iam.mapper.RoleMapper;
import com.xudis.iam.mapper.RolePermissionMapper;
import com.xudis.iam.service.CacheService;
import com.xudis.iam.service.RolePermissionService;
import com.xudis.iam.util.TreeBuilder;
import com.xudis.iam.vo.RolePermissionTreeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色权限服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
@RequiredArgsConstructor
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
        implements RolePermissionService {

    private final RoleMapper roleMapper;

    private final PermissionMapper permissionMapper;

    private final CacheService cacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheKeyConstants.ROLE_PERMISSIONS, allEntries = true)
    public boolean assignPermission(RolePermissionAssignDTO dto) {
        // 验证角色是否存在
        Role role = roleMapper.selectById(dto.getRoleId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 验证权限是否存在
        Permission permission = permissionMapper.selectById(dto.getPermissionId());
        if (permission == null) {
            throw new BusinessException("权限不存在");
        }

        // 检查是否已分配
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, dto.getRoleId())
                .eq(RolePermission::getPermissionId, dto.getPermissionId());
        if (count(wrapper) > 0) {
            throw new BusinessException("角色已拥有该权限");
        }

        // 创建角色权限关联
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(dto.getRoleId());
        rolePermission.setPermissionId(dto.getPermissionId());
        rolePermission.setOperationType(dto.getOperationType());
        rolePermission.setFieldFilter(dto.getFieldFilter());
        rolePermission.setRowFilter(dto.getRowFilter());
        rolePermission.setEffectiveFrom(dto.getEffectiveFrom());
        rolePermission.setEffectiveTo(dto.getEffectiveTo());
        rolePermission.setIsInheritable(1);
        rolePermission.setCreatedAt(LocalDateTime.now());
        rolePermission.setUpdatedAt(LocalDateTime.now());

        boolean result = save(rolePermission);

        // 清除角色权限缓存
        if (result) {
            cacheService.clearRolePermissionCache(dto.getRoleId());
            cacheService.clearPermissionTreeCache();
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheKeyConstants.ROLE_PERMISSIONS, allEntries = true)
    public boolean batchAssignPermission(BatchPermissionAssignDTO dto) {
        // 验证角色是否存在
        Role role = roleMapper.selectById(dto.getRoleId());
        if (role == null) {
            throw new BusinessException("角色不存在");
        }

        // 批量分配
        for (Long permissionId : dto.getPermissionIds()) {
            // 验证权限是否存在
            Permission permission = permissionMapper.selectById(permissionId);
            if (permission == null) {
                continue; // 跳过不存在的权限
            }

            // 检查是否已分配
            LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(RolePermission::getRoleId, dto.getRoleId())
                    .eq(RolePermission::getPermissionId, permissionId);
            if (count(wrapper) > 0) {
                continue; // 跳过已分配的
            }

            // 创建角色权限关联
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(dto.getRoleId());
            rolePermission.setPermissionId(permissionId);
            rolePermission.setEffectiveFrom(dto.getEffectiveFrom());
            rolePermission.setEffectiveTo(dto.getEffectiveTo());
            rolePermission.setIsInheritable(1);
            rolePermission.setCreatedAt(LocalDateTime.now());
            rolePermission.setUpdatedAt(LocalDateTime.now());

            save(rolePermission);
        }

        // 清除角色权限缓存
        cacheService.clearRolePermissionCache(dto.getRoleId());
        cacheService.clearPermissionTreeCache();

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheKeyConstants.ROLE_PERMISSIONS, allEntries = true)
    public boolean revokePermission(Long roleId, Long permissionId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId)
                .eq(RolePermission::getPermissionId, permissionId);

        boolean result = remove(wrapper);

        // 清除角色权限缓存
        if (result) {
            cacheService.clearRolePermissionCache(roleId);
            cacheService.clearPermissionTreeCache();
        }

        return result;
    }

    @Override
    @Cacheable(value = CacheKeyConstants.ROLE_PERMISSIONS, key = "#roleId")
    public List<Permission> getRolePermissions(Long roleId) {
        return baseMapper.selectRolePermissions(roleId);
    }

    @Override
    @Cacheable(value = CacheKeyConstants.ROLE_PERMISSIONS, key = "'roles:' + #permissionId")
    public List<Role> getPermissionRoles(Long permissionId) {
        return baseMapper.selectPermissionRoles(permissionId);
    }

    @Override
    @Cacheable(value = CacheKeyConstants.ROLE_PERMISSIONS, key = "'tree:' + #roleId")
    public List<RolePermissionTreeVO> getPermissionTree(Long roleId) {
        // 查询所有权限
        List<Permission> allPermissions = permissionMapper.selectList(null);

        // 查询角色已拥有的权限ID列表
        List<Permission> rolePermissions = getRolePermissions(roleId);
        Set<Long> rolePermissionIds = rolePermissions.stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());

        // 转换为VO
        List<RolePermissionTreeVO> permissionVOs = allPermissions.stream()
                .map(permission -> {
                    RolePermissionTreeVO vo = new RolePermissionTreeVO();
                    BeanUtils.copyProperties(permission, vo);
                    vo.setChecked(rolePermissionIds.contains(permission.getId()));
                    return vo;
                })
                .collect(Collectors.toList());

        // 构建树形结构
        return TreeBuilder.buildTree(
                permissionVOs,
                0L,
                RolePermissionTreeVO::getId,
                RolePermissionTreeVO::getParentId,
                RolePermissionTreeVO::setChildren
        );
    }
}
