package com.xudis.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.entity.Organization;
import com.xudis.iam.mapper.OrganizationMapper;
import com.xudis.iam.service.OrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 组织服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
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
}
