package com.xudis.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 批量用户权限分配DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "批量用户权限分配请求")
public class BatchUserPermissionAssignDTO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    /**
     * 权限ID列表
     */
    @NotEmpty(message = "权限ID列表不能为空")
    @Schema(description = "权限ID列表", example = "[1, 2, 3]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> permissionIds;

    /**
     * 授予类型:1-直接授予,2-继承,3-临时授权
     */
    @Schema(description = "授予类型:1-直接授予,2-继承,3-临时授权", example = "1")
    private Integer grantType;

    /**
     * 授予原因说明
     */
    @Schema(description = "授予原因说明", example = "批量授权")
    private String grantReason;
}
