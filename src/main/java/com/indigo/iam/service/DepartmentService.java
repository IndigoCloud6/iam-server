package com.indigo.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.indigo.iam.dto.CreateDepartmentRequest;
import com.indigo.iam.dto.UpdateDepartmentRequest;
import com.indigo.iam.entity.Department;
import com.indigo.iam.vo.DepartmentTreeVO;

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
     * 查询部门树VO
     *
     * @param orgId 组织ID（可选，不传则查询所有）
     * @return 部门树VO列表
     */
    List<DepartmentTreeVO> getDepartmentTreeVO(Long orgId);

    /**
     * 分页查询部门
     *
     * @param page     分页对象
     * @param deptCode 部门编码
     * @param deptName 部门名称
     * @return 分页结果
     */
    Page<Department> pageDepartments(Page<Department> page, String deptCode, String deptName);

    /**
     * 创建部门
     *
     * @param request 创建部门请求
     * @return 创建的部门信息
     */
    Department createDepartment(CreateDepartmentRequest request);

    /**
     * 更新部门
     *
     * @param request 更新部门请求
     * @return 是否更新成功
     */
    boolean updateDepartment(UpdateDepartmentRequest request);
}
