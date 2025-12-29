package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.entity.Department;

import java.util.List;

/**
 * 部门服务接口
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 根据部门编码查询部门
     *
     * @param deptCode 部门编码
     * @return 部门信息
     */
    Department getByDeptCode(String deptCode);

    /**
     * 查询部门树
     *
     * @param orgId 组织ID
     * @return 部门树列表
     */
    List<Department> getDepartmentTree(Long orgId);

    /**
     * 分页查询部门
     *
     * @param page     分页对象
     * @param deptCode 部门编码
     * @param deptName 部门名称
     * @return 分页结果
     */
    Page<Department> pageDepartments(Page<Department> page, String deptCode, String deptName);
}
