package com.indigo.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.indigo.iam.entity.Department;
import com.indigo.iam.entity.User;
import com.indigo.iam.entity.UserDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户部门关联Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface UserDepartmentMapper extends BaseMapper<UserDepartment> {

    /**
     * 查询用户所在的所有部门
     *
     * @param userId 用户ID
     * @return 部门列表
     */
    @Select("SELECT d.* FROM sys_department d " +
            "INNER JOIN sys_user_department ud ON d.id = ud.department_id " +
            "WHERE ud.user_id = #{userId}")
    List<Department> selectUserDepartments(@Param("userId") Long userId);

    /**
     * 查询部门下的所有用户（分页）
     *
     * @param page         分页对象
     * @param departmentId 部门ID
     * @return 用户分页列表
     */
    @Select("SELECT u.* FROM sys_user u " +
            "INNER JOIN sys_user_department ud ON u.id = ud.user_id " +
            "WHERE ud.department_id = #{departmentId}")
    Page<User> selectDepartmentUsers(Page<User> page, @Param("departmentId") Long departmentId);
}
