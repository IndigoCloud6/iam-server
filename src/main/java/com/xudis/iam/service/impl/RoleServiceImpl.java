package com.xudis.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.entity.Role;
import com.xudis.iam.mapper.RoleMapper;
import com.xudis.iam.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 角色服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
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
}
