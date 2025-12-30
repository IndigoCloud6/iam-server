package com.xudis.iam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.common.BusinessException;
import com.xudis.iam.dto.CreateOrganizationRequest;
import com.xudis.iam.dto.UpdateOrganizationRequest;
import com.xudis.iam.entity.Organization;
import com.xudis.iam.mapper.OrganizationMapper;
import com.xudis.iam.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 组织服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Override
    public Organization getByOrgCode(String orgCode) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Organization::getOrgCode, orgCode);
        return this.getOne(wrapper);
    }

    @Override
    public List<Organization> getOrganizationTree() {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Organization::getOrderNum);
        return this.list(wrapper);
    }

    @Override
    public Page<Organization> pageOrganizations(Page<Organization> page, String orgCode, String orgName) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(orgCode)) {
            wrapper.like(Organization::getOrgCode, orgCode);
        }
        if (StringUtils.hasText(orgName)) {
            wrapper.like(Organization::getOrgName, orgName);
        }
        wrapper.orderByAsc(Organization::getOrderNum);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Organization createOrganization(CreateOrganizationRequest request) {
        // 检查组织编码是否已存在
        Organization existingOrg = getByOrgCode(request.getOrgCode());
        if (existingOrg != null) {
            throw new BusinessException("组织编码已存在: " + request.getOrgCode());
        }

        // 创建组织实体
        Organization organization = new Organization();
        BeanUtil.copyProperties(request, organization);

        // 设置默认值
        if (organization.getOrgStatus() == null) {
            organization.setOrgStatus(1); // 正常状态
        }
        if (organization.getOrgLevel() == null) {
            organization.setOrgLevel(1);
        }
        if (organization.getParentId() == null) {
            organization.setParentId(0L);
        }
        if (organization.getIsIndependent() == null) {
            organization.setIsIndependent(1);
        }
        if (organization.getIsVirtual() == null) {
            organization.setIsVirtual(0);
        }
        if (organization.getOrderNum() == null) {
            organization.setOrderNum(0);
        }
        if (organization.getSortWeight() == null) {
            organization.setSortWeight(1000);
        }
        if (organization.getDataIsolationLevel() == null) {
            organization.setDataIsolationLevel(1);
        }
        if (organization.getEmployeeCount() == null) {
            organization.setEmployeeCount(0);
        }
        if (organization.getDepartmentCount() == null) {
            organization.setDepartmentCount(0);
        }
        if (organization.getSubsidiaryCount() == null) {
            organization.setSubsidiaryCount(0);
        }

        // 计算祖级列表和路径
        calculateAncestorsAndPath(organization);

        // 保存组织
        boolean saved = this.save(organization);
        if (!saved) {
            throw new BusinessException("创建组织失败");
        }

        return organization;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrganization(UpdateOrganizationRequest request) {
        // 检查组织是否存在
        Organization existingOrg = this.getById(request.getId());
        if (existingOrg == null) {
            throw new BusinessException("组织不存在: " + request.getId());
        }

        // 复制属性
        Organization organization = new Organization();
        BeanUtil.copyProperties(request, organization);

        // 更新组织
        return this.updateById(organization);
    }

    /**
     * 计算祖级列表和路径
     */
    private void calculateAncestorsAndPath(Organization organization) {
        Long parentId = organization.getParentId();
        if (parentId == null || parentId == 0) {
            // 根节点
            organization.setAncestors("0");
            organization.setOrgPath("/" + organization.getOrgCode());
            organization.setRootOrgId(organization.getId());
            organization.setOrgLevel(1);
            organization.setIsLeaf(1);
        } else {
            // 子节点
            Organization parent = this.getById(parentId);
            if (parent == null) {
                throw new BusinessException("父组织不存在: " + parentId);
            }

            // 计算祖级列表
            String ancestors = parent.getAncestors() + "," + parent.getId();
            organization.setAncestors(ancestors);

            // 计算路径
            String orgPath = parent.getOrgPath() + "/" + organization.getOrgCode();
            organization.setOrgPath(orgPath);

            // 设置根组织ID
            organization.setRootOrgId(parent.getRootOrgId());

            // 设置层级
            organization.setOrgLevel(parent.getOrgLevel() + 1);

            // 更新父节点的 isLeaf 状态
            parent.setIsLeaf(0);
            this.updateById(parent);

            // 设置当前节点为叶子节点
            organization.setIsLeaf(1);
        }
    }
}
