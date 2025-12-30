package com.xudis.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudis.iam.entity.UserPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户权限关联 Mapper
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Mapper
public interface UserPermissionMapper extends BaseMapper<UserPermission> {
}
