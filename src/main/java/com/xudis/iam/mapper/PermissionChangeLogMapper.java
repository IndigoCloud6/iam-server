package com.xudis.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudis.iam.entity.PermissionChangeLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限变更日志 Mapper
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Mapper
public interface PermissionChangeLogMapper extends BaseMapper<PermissionChangeLog> {
}
