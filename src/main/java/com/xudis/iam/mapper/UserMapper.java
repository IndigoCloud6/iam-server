package com.xudis.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xudis.iam.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
