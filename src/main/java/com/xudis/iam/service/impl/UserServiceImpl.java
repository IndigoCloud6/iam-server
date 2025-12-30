package com.xudis.iam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.dto.CreateUserRequest;
import com.xudis.iam.dto.UpdateUserRequest;
import com.xudis.iam.entity.User;
import com.xudis.iam.exception.BusinessException;
import com.xudis.iam.mapper.UserMapper;
import com.xudis.iam.service.UserService;
import com.xudis.iam.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public Page<User> pageUsers(Page<User> page, String username, String realName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(username)) {
            wrapper.like(User::getUsername, username);
        }
        if (StringUtils.hasText(realName)) {
            wrapper.like(User::getRealName, realName);
        }
        wrapper.orderByDesc(User::getCreatedAt);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(CreateUserRequest request) {
        // 检查用户名是否已存在
        User existingUser = getByUsername(request.getUsername());
        if (existingUser != null) {
            throw new BusinessException("用户名已存在: " + request.getUsername());
        }

        // 创建用户实体
        User user = new User();
        BeanUtil.copyProperties(request, user);

        // 加密密码
        String encodedPassword = PasswordEncoder.encode(request.getPassword());
        user.setPasswordHash(encodedPassword);

        // 设置默认值
        user.setAccountStatus(1); // 正常状态
        user.setLoginFailCount(0);
        user.setTwoFactorEnabled(0);
        user.setPasswordChangedAt(LocalDateTime.now());

        // 保存用户
        boolean saved = this.save(user);
        if (!saved) {
            throw new BusinessException("创建用户失败");
        }

        // 返回创建的用户（不包含密码信息）
        user.setPasswordHash(null);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UpdateUserRequest request) {
        // 检查用户是否存在
        User existingUser = this.getById(request.getId());
        if (existingUser == null) {
            throw new BusinessException("用户不存在: " + request.getId());
        }

        // 复制属性
        User user = new User();
        BeanUtil.copyProperties(request, user);

        // 更新用户
        return this.updateById(user);
    }
}
