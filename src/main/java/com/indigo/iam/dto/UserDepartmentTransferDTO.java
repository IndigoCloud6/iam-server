package com.indigo.iam.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户部门调动DTO
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
public class UserDepartmentTransferDTO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 原部门ID
     */
    @NotNull(message = "原部门ID不能为空")
    private Long fromDepartmentId;

    /**
     * 目标部门ID
     */
    @NotNull(message = "目标部门ID不能为空")
    private Long toDepartmentId;

    /**
     * 是否转移主部门
     */
    private Boolean transferPrimary;
}
