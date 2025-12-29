package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.entity.Role;

/**
 * 角色服务接口
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色信息
     */
    Role getByRoleCode(String roleCode);

    /**
     * 分页查询角色
     *
     * @param page     分页对象
     * @param roleCode 角色编码
     * @param roleName 角色名称
     * @return 分页结果
     */
    Page<Role> pageRoles(Page<Role> page, String roleCode, String roleName);
}
