package com.xudis.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.entity.Role;
import com.xudis.iam.entity.User;
import com.xudis.iam.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户角色关联Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 查询用户的所有角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Select("SELECT r.* FROM sys_role r " +
            "INNER JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<Role> selectUserRoles(@Param("userId") Long userId);

    /**
     * 查询角色下的所有用户（分页）
     *
     * @param page   分页对象
     * @param roleId 角色ID
     * @return 用户分页列表
     */
    @Select("SELECT u.* FROM sys_user u " +
            "INNER JOIN sys_user_role ur ON u.id = ur.user_id " +
            "WHERE ur.role_id = #{roleId}")
    Page<User> selectRoleUsers(Page<User> page, @Param("roleId") Long roleId);

    /**
     * 查询用户的生效角色
     *
     * @param userId 用户ID
     * @param now    当前时间
     * @return 生效的角色列表
     */
    @Select("SELECT r.* FROM sys_role r " +
            "INNER JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId} " +
            "AND ur.is_active = 1 " +
            "AND (ur.effective_from IS NULL OR ur.effective_from <= #{now}) " +
            "AND (ur.effective_to IS NULL OR ur.effective_to >= #{now})")
    List<Role> selectEffectiveRoles(@Param("userId") Long userId, @Param("now") LocalDateTime now);
}
