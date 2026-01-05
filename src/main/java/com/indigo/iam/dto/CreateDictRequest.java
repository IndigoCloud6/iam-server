package com.indigo.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建字典DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "创建数据字典请求")
public class CreateDictRequest {

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    @Schema(description = "字典编码", example = "user_status", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictCode;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Schema(description = "字典名称", example = "用户状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dictName;

    /**
     * 字典类型:1-系统字典,2-业务字典
     */
    @Schema(description = "字典类型:1-系统字典,2-业务字典", example = "1")
    private Integer dictType;

    /**
     * 描述
     */
    @Schema(description = "描述", example = "用户状态字典")
    private String description;

    /**
     * 状态:1-启用,2-停用
     */
    @Schema(description = "状态:1-启用,2-停用", example = "1")
    private Integer status;
}
