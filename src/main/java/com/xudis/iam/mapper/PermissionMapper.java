package com.xudis.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudis.iam.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
