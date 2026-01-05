package com.indigo.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.indigo.iam.entity.OperationLog;
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
