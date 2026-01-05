package com.indigo.iam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.indigo.iam.common.BusinessException;
import com.indigo.iam.dto.CreatePermissionRequest;
import com.indigo.iam.dto.UpdatePermissionRequest;
import com.indigo.iam.entity.Permission;
import com.indigo.iam.mapper.PermissionMapper;
import com.indigo.iam.service.PermissionService;
import com.indigo.iam.util.TreeBuilder;
import com.indigo.iam.vo.PermissionTreeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
@RequiredArgsConstructor
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
    public List<PermissionTreeVO> getPermissionTreeVO() {
        // 查询所有权限
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Permission::getOrderNum);
        List<Permission> permissions = this.list(wrapper);

        // 转换为VO
        List<PermissionTreeVO> permVOs = permissions.stream()
                .map(perm -> {
                    PermissionTreeVO vo = new PermissionTreeVO();
                    BeanUtil.copyProperties(perm, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        // 构建树形结构
        return TreeBuilder.buildTree(
                permVOs,
                0L,
                PermissionTreeVO::getId,
                PermissionTreeVO::getParentId,
                PermissionTreeVO::setChildren
        );
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Permission createPermission(CreatePermissionRequest request) {
        // 检查权限编码是否已存在
        Permission existingPerm = getByPermCode(request.getPermCode());
        if (existingPerm != null) {
            throw new BusinessException("权限编码已存在: " + request.getPermCode());
        }

        // 创建权限实体
        Permission permission = new Permission();
        BeanUtil.copyProperties(request, permission);

        // 设置默认值
        if (permission.getParentId() == null) {
            permission.setParentId(0L);
        }
        if (permission.getStatus() == null) {
            permission.setStatus(1); // 启用状态
        }
        if (permission.getPermLevel() == null) {
            permission.setPermLevel(1);
        }
        if (permission.getIsExternal() == null) {
            permission.setIsExternal(0);
        }
        if (permission.getIsCache() == null) {
            permission.setIsCache(1);
        }
        if (permission.getIsVisible() == null) {
            permission.setIsVisible(1);
        }
        if (permission.getIsBreadcrumb() == null) {
            permission.setIsBreadcrumb(1);
        }
        if (permission.getOrderNum() == null) {
            permission.setOrderNum(0);
        }

        // 计算祖级列表
        calculateAncestors(permission);

        // 保存权限
        boolean saved = this.save(permission);
        if (!saved) {
            throw new BusinessException("创建权限失败");
        }

        return permission;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePermission(UpdatePermissionRequest request) {
        // 检查权限是否存在
        Permission existingPerm = this.getById(request.getId());
        if (existingPerm == null) {
            throw new BusinessException("权限不存在: " + request.getId());
        }

        // 如果修改了权限编码，检查新编码是否已被使用
        if (StringUtils.hasText(request.getPermCode()) &&
            !request.getPermCode().equals(existingPerm.getPermCode())) {
            Permission permWithSameCode = getByPermCode(request.getPermCode());
            if (permWithSameCode != null) {
                throw new BusinessException("权限编码已被使用: " + request.getPermCode());
            }
        }

        // 复制属性
        Permission permission = new Permission();
        BeanUtil.copyProperties(request, permission);

        // 如果修改了父级，重新计算祖级列表
        if (request.getParentId() != null && !request.getParentId().equals(existingPerm.getParentId())) {
            calculateAncestors(permission);
        }

        // 更新权限
        return this.updateById(permission);
    }

    /**
     * 计算祖级列表
     */
    private void calculateAncestors(Permission permission) {
        Long parentId = permission.getParentId();
        if (parentId == null || parentId == 0) {
            // 根节点
            permission.setAncestors("0");
            permission.setPermLevel(1);
        } else {
            // 子节点
            Permission parent = this.getById(parentId);
            if (parent == null) {
                throw new BusinessException("父权限不存在: " + parentId);
            }

            // 计算祖级列表
            String ancestors = parent.getAncestors() + "," + parent.getId();
            permission.setAncestors(ancestors);

            // 设置层级
            permission.setPermLevel(parent.getPermLevel() + 1);
        }
    }
}
