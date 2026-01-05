package com.indigo.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.indigo.iam.entity.Config;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置 Mapper
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {
}
