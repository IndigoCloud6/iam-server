package com.indigo.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.indigo.iam.entity.Organization;
import org.apache.ibatis.annotations.Mapper;

/**
 * 组织Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {
}
