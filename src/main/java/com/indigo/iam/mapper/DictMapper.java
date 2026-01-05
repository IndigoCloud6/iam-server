package com.indigo.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.indigo.iam.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典 Mapper
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}
