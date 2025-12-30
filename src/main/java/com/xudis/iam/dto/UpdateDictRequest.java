package com.xudis.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新字典DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "更新数据字典请求")
public class UpdateDictRequest {

    /**
     * 字典ID
     */
    @NotNull(message = "字典ID不能为空")
    @Schema(description = "字典ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码", example = "user_status")
    private String dictCode;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称", example = "用户状态")
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
