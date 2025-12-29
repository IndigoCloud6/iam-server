package com.xudis.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.entity.Department;
import com.xudis.iam.mapper.DepartmentMapper;
import com.xudis.iam.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 部门服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

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
}
