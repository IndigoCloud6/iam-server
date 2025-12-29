package com.xudis.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudis.iam.entity.UserDepartment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户部门关联Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface UserDepartmentMapper extends BaseMapper<UserDepartment> {
}
