package com.indigo.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建字典项DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "创建字典项请求")
public class CreateDictItemRequest {

    /**
     * 字典ID
     */
    @NotNull(message = "字典ID不能为空")
    @Schema(description = "字典ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long dictId;

    /**
     * 字典项编码
     */
    @NotBlank(message = "字典项编码不能为空")
    @Schema(description = "字典项编码", example = "active", requiredMode = Schema.RequiredMode.REQUIRED)
    private String itemCode;

    /**
     * 字典项名称
     */
    @NotBlank(message = "字典项名称不能为空")
    @Schema(description = "字典项名称", example = "正常", requiredMode = Schema.RequiredMode.REQUIRED)
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
