package com.indigo.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建系统配置DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "创建系统配置请求")
public class CreateConfigRequest {

    /**
     * 配置键
     */
    @NotBlank(message = "配置键不能为空")
    @Schema(description = "配置键", example = "system.title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configKey;

    /**
     * 配置值
     */
    @NotBlank(message = "配置值不能为空")
    @Schema(description = "配置值", example = "IAM管理系统", requiredMode = Schema.RequiredMode.REQUIRED)
    private String configValue;

    /**
     * 配置类型:STRING,NUMBER,BOOLEAN,JSON
     */
    @Schema(description = "配置类型:STRING,NUMBER,BOOLEAN,JSON", example = "STRING")
    private String configType;

    /**
     * 作用域:GLOBAL,TENANT,DEPARTMENT
     */
    @Schema(description = "作用域:GLOBAL,TENANT,DEPARTMENT", example = "GLOBAL")
    private String scope;

    /**
     * 作用域ID
     */
    @Schema(description = "作用域ID", example = "1")
    private Long scopeId;

    /**
     * 配置描述
     */
    @Schema(description = "配置描述", example = "系统标题")
    private String description;

    /**
     * 是否敏感配置
     */
    @Schema(description = "是否敏感配置:0-否,1-是", example = "0")
    private Integer isSensitive;

    /**
     * 是否系统配置
     */
    @Schema(description = "是否系统配置:0-否,1-是", example = "0")
    private Integer isSystem;
}
