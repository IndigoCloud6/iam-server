package com.xudis.iam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.common.BusinessException;
import com.xudis.iam.dto.CreateDepartmentRequest;
import com.xudis.iam.dto.UpdateDepartmentRequest;
import com.xudis.iam.entity.Department;
import com.xudis.iam.entity.Organization;
import com.xudis.iam.mapper.DepartmentMapper;
import com.xudis.iam.service.DepartmentService;
import com.xudis.iam.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 部门服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    private final OrganizationService organizationService;

    @Override
    public Department getByDeptCode(String deptCode) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Department::getDeptCode, deptCode);
        return this.getOne(wrapper);
    }

    @Override
    public List<Department> getDepartmentTree(Long orgId) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        if (orgId != null) {
            wrapper.eq(Department::getOrgId, orgId);
        }
        wrapper.orderByAsc(Department::getOrderNum);
        return this.list(wrapper);
    }

    @Override
    public Page<Department> pageDepartments(Page<Department> page, String deptCode, String deptName) {
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(deptCode)) {
            wrapper.like(Department::getDeptCode, deptCode);
        }
        if (StringUtils.hasText(deptName)) {
            wrapper.like(Department::getDeptName, deptName);
        }
        wrapper.orderByAsc(Department::getOrderNum);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Department createDepartment(CreateDepartmentRequest request) {
        // 检查组织是否存在
        Organization organization = organizationService.getById(request.getOrgId());
        if (organization == null) {
            throw new BusinessException("组织不存在: " + request.getOrgId());
        }

        // 检查部门编码是否已存在
        Department existingDept = getByDeptCode(request.getDeptCode());
        if (existingDept != null) {
            throw new BusinessException("部门编码已存在: " + request.getDeptCode());
        }

        // 创建部门实体
        Department department = new Department();
        BeanUtil.copyProperties(request, department);

        // 设置默认值
        if (department.getStatus() == null) {
            department.setStatus(1); // 正常状态
        }
        if (department.getDeptType() == null) {
            department.setDeptType(2); // 部门
        }
        if (department.getParentId() == null) {
            department.setParentId(0L);
        }
        if (department.getDeptLevel() == null) {
            department.setDeptLevel(1);
        }
        if (department.getOrderNum() == null) {
            department.setOrderNum(0);
        }

        // 计算祖级列表和路径
        calculateAncestorsAndPath(department);

        // 保存部门
        boolean saved = this.save(department);
        if (!saved) {
            throw new BusinessException("创建部门失败");
        }

        return department;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDepartment(UpdateDepartmentRequest request) {
        // 检查部门是否存在
        Department existingDept = this.getById(request.getId());
        if (existingDept == null) {
            throw new BusinessException("部门不存在: " + request.getId());
        }

        // 复制属性
        Department department = new Department();
        BeanUtil.copyProperties(request, department);

        // 更新部门
        return this.updateById(department);
    }

    /**
     * 计算祖级列表和路径
     */
    private void calculateAncestorsAndPath(Department department) {
        Long parentId = department.getParentId();
        if (parentId == null || parentId == 0) {
            // 根节点
            department.setAncestors("0");
            department.setDeptPath("/" + department.getDeptCode());
            department.setDeptLevel(1);
        } else {
            // 子节点
            Department parent = this.getById(parentId);
            if (parent == null) {
                throw new BusinessException("父部门不存在: " + parentId);
            }

            // 计算祖级列表
            String ancestors = parent.getAncestors() + "," + parent.getId();
            department.setAncestors(ancestors);

            // 计算路径
            String deptPath = parent.getDeptPath() + "/" + department.getDeptCode();
            department.setDeptPath(deptPath);

            // 设置层级
            department.setDeptLevel(parent.getDeptLevel() + 1);
        }
    }
}
