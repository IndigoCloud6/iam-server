package com.xudis.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.common.BusinessException;
import com.xudis.iam.dto.UserDepartmentAssignDTO;
import com.xudis.iam.dto.UserDepartmentTransferDTO;
import com.xudis.iam.entity.Department;
import com.xudis.iam.entity.User;
import com.xudis.iam.entity.UserDepartment;
import com.xudis.iam.mapper.DepartmentMapper;
import com.xudis.iam.mapper.UserDepartmentMapper;
import com.xudis.iam.mapper.UserMapper;
import com.xudis.iam.service.UserDepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户部门服务实现类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
@RequiredArgsConstructor
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment>
        implements UserDepartmentService {

    private final UserMapper userMapper;

    private final DepartmentMapper departmentMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean assignDepartment(UserDepartmentAssignDTO dto) {
        // 验证用户是否存在
        User user = userMapper.selectById(dto.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证部门是否存在
        Department department = departmentMapper.selectById(dto.getDepartmentId());
        if (department == null) {
            throw new BusinessException("部门不存在");
        }

        // 检查是否已分配
        LambdaQueryWrapper<UserDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDepartment::getUserId, dto.getUserId())
                .eq(UserDepartment::getDepartmentId, dto.getDepartmentId());
        if (count(wrapper) > 0) {
            throw new BusinessException("用户已在该部门");
        }

        // 如果设置为主部门，先取消其他主部门
        if (dto.getIsPrimary() != null && dto.getIsPrimary() == 1) {
            LambdaUpdateWrapper<UserDepartment> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(UserDepartment::getUserId, dto.getUserId())
                    .set(UserDepartment::getIsPrimary, 0);
            update(updateWrapper);

            // 更新用户表的主部门ID
            user.setDepartmentId(dto.getDepartmentId());
            userMapper.updateById(user);
        }

        // 创建用户部门关联
        UserDepartment userDepartment = new UserDepartment();
        userDepartment.setUserId(dto.getUserId());
        userDepartment.setDepartmentId(dto.getDepartmentId());
        userDepartment.setIsPrimary(dto.getIsPrimary() != null ? dto.getIsPrimary() : 0);
        userDepartment.setPosition(dto.getPosition());
        userDepartment.setJobLevel(dto.getJobLevel());
        userDepartment.setEffectiveFrom(dto.getEffectiveFrom());
        userDepartment.setEffectiveTo(dto.getEffectiveTo());
        userDepartment.setIsActive(1);
        userDepartment.setCreatedAt(LocalDateTime.now());
        userDepartment.setUpdatedAt(LocalDateTime.now());

        return save(userDepartment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setPrimaryDepartment(Long userId, Long departmentId) {
        // 验证用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证部门是否存在
        Department department = departmentMapper.selectById(departmentId);
        if (department == null) {
            throw new BusinessException("部门不存在");
        }

        // 验证用户是否已在该部门
        LambdaQueryWrapper<UserDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDepartment::getUserId, userId)
                .eq(UserDepartment::getDepartmentId, departmentId);
        UserDepartment userDepartment = getOne(wrapper);
        if (userDepartment == null) {
            throw new BusinessException("用户不在该部门");
        }

        // 取消其他主部门
        LambdaUpdateWrapper<UserDepartment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserDepartment::getUserId, userId)
                .set(UserDepartment::getIsPrimary, 0);
        update(updateWrapper);

        // 设置为主部门
        userDepartment.setIsPrimary(1);
        userDepartment.setUpdatedAt(LocalDateTime.now());
        updateById(userDepartment);

        // 更新用户表的主部门ID
        user.setDepartmentId(departmentId);
        return userMapper.updateById(user) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeDepartment(Long userId, Long departmentId) {
        // 检查是否为主部门
        LambdaQueryWrapper<UserDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDepartment::getUserId, userId)
                .eq(UserDepartment::getDepartmentId, departmentId);
        UserDepartment userDepartment = getOne(wrapper);

        if (userDepartment == null) {
            throw new BusinessException("用户不在该部门");
        }

        if (userDepartment.getIsPrimary() == 1) {
            // 如果是主部门，清除用户表的主部门ID
            User user = userMapper.selectById(userId);
            if (user != null) {
                user.setDepartmentId(null);
                userMapper.updateById(user);
            }
        }

        return removeById(userDepartment.getId());
    }

    @Override
    public List<Department> getUserDepartments(Long userId) {
        return baseMapper.selectUserDepartments(userId);
    }

    @Override
    public Page<User> getDepartmentUsers(Page<User> page, Long departmentId) {
        return baseMapper.selectDepartmentUsers(page, departmentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferDepartment(UserDepartmentTransferDTO dto) {
        // 验证用户是否存在
        User user = userMapper.selectById(dto.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证原部门和目标部门是否存在
        Department fromDept = departmentMapper.selectById(dto.getFromDepartmentId());
        Department toDept = departmentMapper.selectById(dto.getToDepartmentId());
        if (fromDept == null || toDept == null) {
            throw new BusinessException("部门不存在");
        }

        // 检查用户是否在原部门
        LambdaQueryWrapper<UserDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDepartment::getUserId, dto.getUserId())
                .eq(UserDepartment::getDepartmentId, dto.getFromDepartmentId());
        UserDepartment fromUserDept = getOne(wrapper);
        if (fromUserDept == null) {
            throw new BusinessException("用户不在原部门");
        }

        boolean isPrimary = fromUserDept.getIsPrimary() == 1;

        // 从原部门移除
        removeById(fromUserDept.getId());

        // 检查是否已在目标部门
        LambdaQueryWrapper<UserDepartment> toWrapper = new LambdaQueryWrapper<>();
        toWrapper.eq(UserDepartment::getUserId, dto.getUserId())
                .eq(UserDepartment::getDepartmentId, dto.getToDepartmentId());
        if (count(toWrapper) == 0) {
            // 添加到目标部门
            boolean shouldBePrimary = (dto.getTransferPrimary() != null && dto.getTransferPrimary() && isPrimary);

            UserDepartment toUserDept = new UserDepartment();
            toUserDept.setUserId(dto.getUserId());
            toUserDept.setDepartmentId(dto.getToDepartmentId());
            toUserDept.setIsPrimary(shouldBePrimary ? 1 : 0);
            toUserDept.setIsActive(1);
            toUserDept.setCreatedAt(LocalDateTime.now());
            toUserDept.setUpdatedAt(LocalDateTime.now());
            save(toUserDept);
        }

        // 如果是主部门调动，更新用户表
        if (dto.getTransferPrimary() != null && dto.getTransferPrimary() && isPrimary) {
            user.setDepartmentId(dto.getToDepartmentId());
            userMapper.updateById(user);
        }

        return true;
    }
}
