package com.indigo.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.indigo.iam.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
