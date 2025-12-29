package com.xudis.iam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 批量分配角色DTO
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
public class BatchRoleAssignDTO {

    /**
     * 用户ID列表
     */
    @NotEmpty(message = "用户ID列表不能为空")
    private List<Long> userIds;

    /**
     * 角色ID
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /**
     * 所属部门ID(用于部门角色)
     */
    private Long departmentId;

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
