package com.indigo.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户权限分配DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "用户权限分配请求")
public class UserPermissionAssignDTO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    /**
     * 权限ID
     */
    @NotNull(message = "权限ID不能为空")
    @Schema(description = "权限ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long permissionId;

    /**
     * 授予类型:1-直接授予,2-继承,3-临时授权
     */
    @Schema(description = "授予类型:1-直接授予,2-继承,3-临时授权", example = "1")
    private Integer grantType;

    /**
     * 授予原因说明
     */
    @Schema(description = "授予原因说明", example = "特殊业务需求")
    private String grantReason;

    /**
     * 操作类型(当权限为数据权限时使用)
     */
    @Schema(description = "操作类型", example = "READ,WRITE")
    private String operationType;

    /**
     * 条件表达式(JSON格式)
     */
    @Schema(description = "条件表达式")
    private String conditionExpression;
}
