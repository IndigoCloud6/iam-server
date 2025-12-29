package com.xudis.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudis.iam.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
