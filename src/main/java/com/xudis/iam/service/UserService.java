package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.dto.CreateUserRequest;
import com.xudis.iam.dto.UpdateUserRequest;
import com.xudis.iam.entity.User;

/**
 * 用户服务接口
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 分页查询用户
     *
     * @param page     分页对象
     * @param username 用户名
     * @param realName 真实姓名
     * @return 分页结果
     */
    Page<User> pageUsers(Page<User> page, String username, String realName);

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 创建的用户信息
     */
    User createUser(CreateUserRequest request);

    /**
     * 更新用户
     *
     * @param request 更新用户请求
     * @return 是否更新成功
     */
    boolean updateUser(UpdateUserRequest request);
}
