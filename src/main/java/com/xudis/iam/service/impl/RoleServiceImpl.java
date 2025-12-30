package com.xudis.iam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.common.BusinessException;
import com.xudis.iam.dto.CreateRoleRequest;
import com.xudis.iam.dto.UpdateRoleRequest;
import com.xudis.iam.entity.Role;
import com.xudis.iam.mapper.RoleMapper;
import com.xudis.iam.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 角色服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Role getByRoleCode(String roleCode) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleCode, roleCode);
        return this.getOne(wrapper);
    }

    @Override
    public Page<Role> pageRoles(Page<Role> page, String roleCode, String roleName) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(roleCode)) {
            wrapper.like(Role::getRoleCode, roleCode);
        }
        if (StringUtils.hasText(roleName)) {
            wrapper.like(Role::getRoleName, roleName);
        }
        wrapper.orderByDesc(Role::getCreatedAt);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role createRole(CreateRoleRequest request) {
        // 检查角色编码是否已存在
        Role existingRole = getByRoleCode(request.getRoleCode());
        if (existingRole != null) {
            throw new BusinessException("角色编码已存在: " + request.getRoleCode());
        }

        // 创建角色实体
        Role role = new Role();
        BeanUtil.copyProperties(request, role);

        // 设置默认值
        if (role.getRoleType() == null) {
            role.setRoleType(3); // 自定义角色
        }
        if (role.getRoleLevel() == null) {
            role.setRoleLevel(99);
        }
        if (role.getDataScope() == null) {
            role.setDataScope(1); // 全部数据
        }
        if (role.getIsPermanent() == null) {
            role.setIsPermanent(1); // 永久有效
        }
        if (role.getStatus() == null) {
            role.setStatus(1); // 启用状态
        }
        if (role.getMaxUserCount() == null) {
            role.setMaxUserCount(0); // 不限制
        }
        if (role.getCurrentUserCount() == null) {
            role.setCurrentUserCount(0);
        }
        if (role.getIsSystem() == null) {
            role.setIsSystem(0); // 非系统角色
        }
        if (role.getIsDefault() == null) {
            role.setIsDefault(0); // 非默认角色
        }
        if (role.getRequireMfa() == null) {
            role.setRequireMfa(0); // 不要求MFA
        }

        // 保存角色
        boolean saved = this.save(role);
        if (!saved) {
            throw new BusinessException("创建角色失败");
        }

        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(UpdateRoleRequest request) {
        // 检查角色是否存在
        Role existingRole = this.getById(request.getId());
        if (existingRole == null) {
            throw new BusinessException("角色不存在: " + request.getId());
        }

        // 系统内置角色不能修改角色编码
        if (existingRole.getIsSystem() == 1 && StringUtils.hasText(request.getRoleCode()) &&
            !request.getRoleCode().equals(existingRole.getRoleCode())) {
            throw new BusinessException("系统内置角色不能修改角色编码");
        }

        // 如果修改了角色编码，检查新编码是否已被使用
        if (StringUtils.hasText(request.getRoleCode()) &&
            !request.getRoleCode().equals(existingRole.getRoleCode())) {
            Role roleWithSameCode = getByRoleCode(request.getRoleCode());
            if (roleWithSameCode != null) {
                throw new BusinessException("角色编码已被使用: " + request.getRoleCode());
            }
        }

        // 复制属性
        Role role = new Role();
        BeanUtil.copyProperties(request, role);

        // 更新角色
        return this.updateById(role);
    }
}
