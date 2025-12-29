package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.dto.UserDepartmentAssignDTO;
import com.xudis.iam.dto.UserDepartmentTransferDTO;
import com.xudis.iam.entity.Department;
import com.xudis.iam.entity.User;
import com.xudis.iam.entity.UserDepartment;

import java.util.List;

/**
 * 用户部门服务接口
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public interface UserDepartmentService extends IService<UserDepartment> {

    /**
     * 分配用户到部门
     *
     * @param dto 用户部门分配DTO
     * @return 是否成功
     */
    boolean assignDepartment(UserDepartmentAssignDTO dto);

    /**
     * 设置用户主部门
     *
     * @param userId       用户ID
     * @param departmentId 部门ID
     * @return 是否成功
     */
    boolean setPrimaryDepartment(Long userId, Long departmentId);

    /**
     * 从部门移除用户
     *
     * @param userId       用户ID
     * @param departmentId 部门ID
     * @return 是否成功
     */
    boolean removeDepartment(Long userId, Long departmentId);

    /**
     * 查询用户所在的所有部门
     *
     * @param userId 用户ID
     * @return 部门列表
     */
    List<Department> getUserDepartments(Long userId);

    /**
     * 查询部门下的所有用户（分页）
     *
     * @param page         分页对象
     * @param departmentId 部门ID
     * @return 用户分页列表
     */
    Page<User> getDepartmentUsers(Page<User> page, Long departmentId);

    /**
     * 用户部门调动
     *
     * @param dto 用户部门调动DTO
     * @return 是否成功
     */
    boolean transferDepartment(UserDepartmentTransferDTO dto);
}
