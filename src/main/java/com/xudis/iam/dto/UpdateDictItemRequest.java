package com.xudis.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新字典项DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "更新字典项请求")
public class UpdateDictItemRequest {

    /**
     * 字典项ID
     */
    @NotNull(message = "字典项ID不能为空")
    @Schema(description = "字典项ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 字典项编码
     */
    @Schema(description = "字典项编码", example = "active")
    private String itemCode;

    /**
     * 字典项名称
     */
    @Schema(description = "字典项名称", example = "正常")
    private String itemName;

    /**
     * 字典项值
     */
    @Schema(description = "字典项值", example = "1")
    private String itemValue;

    /**
     * 父项ID
     */
    @Schema(description = "父项ID", example = "0")
    private Long parentId;

    /**
     * 排序号
     */
    @Schema(description = "排序号", example = "1")
    private Integer orderNum;

    /**
     * 状态:1-启用,2-停用
     */
    @Schema(description = "状态:1-启用,2-停用", example = "1")
    private Integer status;
}
