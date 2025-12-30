package com.xudis.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudis.iam.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志 Mapper
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
