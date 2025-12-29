package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.entity.Organization;

import java.util.List;

/**
 * 组织服务接口
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public interface OrganizationService extends IService<Organization> {

    /**
     * 根据组织编码查询组织
     *
     * @param orgCode 组织编码
     * @return 组织信息
     */
    Organization getByOrgCode(String orgCode);

    /**
     * 查询组织树
     *
     * @return 组织树列表
     */
    List<Organization> getOrganizationTree();

    /**
     * 分页查询组织
     *
     * @param page    分页对象
     * @param orgCode 组织编码
     * @param orgName 组织名称
     * @return 分页结果
     */
    Page<Organization> pageOrganizations(Page<Organization> page, String orgCode, String orgName);
}
