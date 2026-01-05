package com.indigo.iam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 角色权限分配DTO
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
public class RolePermissionAssignDTO {

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /**
     * 权限ID
     */
    @NotNull(message = "权限ID不能为空")
    private Long permissionId;

    /**
     * 操作类型(当权限为数据权限时使用)
     */
    private String operationType;

    /**
     * 字段过滤规则
     */
    private Map<String, Object> fieldFilter;

    /**
     * 行过滤规则
     */
    private Map<String, Object> rowFilter;

    /**
     * 生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveFrom;

    /**
     * 失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime effectiveTo;
}
