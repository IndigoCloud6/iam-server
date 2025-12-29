package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.entity.Permission;

import java.util.List;

/**
 * 权限服务接口
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据权限编码查询权限
     *
     * @param permCode 权限编码
     * @return 权限信息
     */
    Permission getByPermCode(String permCode);

    /**
     * 查询权限树
     *
     * @return 权限树列表
     */
    List<Permission> getPermissionTree();

    /**
     * 分页查询权限
     *
     * @param page     分页对象
     * @param permCode 权限编码
     * @param permName 权限名称
     * @return 分页结果
     */
    Page<Permission> pagePermissions(Page<Permission> page, String permCode, String permName);
}
