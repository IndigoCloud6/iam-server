package com.indigo.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.indigo.iam.dto.CreateOrganizationRequest;
import com.indigo.iam.dto.UpdateOrganizationRequest;
import com.indigo.iam.entity.Organization;
import com.indigo.iam.vo.OrganizationTreeVO;

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
     * 查询组织树VO
     *
     * @return 组织树VO列表
     */
    List<OrganizationTreeVO> getOrganizationTreeVO();

    /**
     * 分页查询组织
     *
     * @param page    分页对象
     * @param orgCode 组织编码
     * @param orgName 组织名称
     * @return 分页结果
     */
    Page<Organization> pageOrganizations(Page<Organization> page, String orgCode, String orgName);

    /**
     * 创建组织
     *
     * @param request 创建组织请求
     * @return 创建的组织信息
     */
    Organization createOrganization(CreateOrganizationRequest request);

    /**
     * 更新组织
     *
     * @param request 更新组织请求
     * @return 是否更新成功
     */
    boolean updateOrganization(UpdateOrganizationRequest request);
}
