package com.xudis.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.entity.Permission;
import com.xudis.iam.mapper.PermissionMapper;
import com.xudis.iam.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 权限服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public Permission getByPermCode(String permCode) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPermCode, permCode);
        return this.getOne(wrapper);
    }

    @Override
    public List<Permission> getPermissionTree() {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Permission::getOrderNum);
        return this.list(wrapper);
    }

    @Override
    public Page<Permission> pagePermissions(Page<Permission> page, String permCode, String permName) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(permCode)) {
            wrapper.like(Permission::getPermCode, permCode);
        }
        if (StringUtils.hasText(permName)) {
            wrapper.like(Permission::getPermName, permName);
        }
        wrapper.orderByAsc(Permission::getOrderNum);
        return this.page(page, wrapper);
    }
}
