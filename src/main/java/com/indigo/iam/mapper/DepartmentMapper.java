package com.indigo.iam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.indigo.iam.entity.Department;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门Mapper
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
}
