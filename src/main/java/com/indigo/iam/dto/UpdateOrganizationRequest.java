package com.indigo.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 更新组织 DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "更新组织请求")
public class UpdateOrganizationRequest {

    /**
     * 组织ID
     */
    @NotNull(message = "组织ID不能为空")
    @Schema(description = "组织ID", example = "1")
    private Long id;

    /**
     * 组织名称
     */
    @Size(max = 200, message = "组织名称长度不能超过200")
    @Schema(description = "组织名称", example = "XX科技公司")
    private String orgName;

    /**
     * 组织简称
     */
    @Size(max = 100, message = "组织简称长度不能超过100")
    @Schema(description = "组织简称", example = "XX科技")
    private String orgShortName;

    /**
     * 英文名称
     */
    @Size(max = 200, message = "英文名称长度不能超过200")
    @Schema(description = "英文名称", example = "XX Technology")
    private String orgEnName;

    /**
     * 组织状态:1-正常,2-停用,3-注销,4-筹备中
     */
    @Schema(description = "组织状态", example = "1")
    private Integer orgStatus;

    /**
     * 联系人
     */
    @Size(max = 100, message = "联系人长度不能超过100")
    @Schema(description = "联系人", example = "张三")
    private String contactPerson;

    /**
     * 联系电话
     */
    @Size(max = 20, message = "联系电话长度不能超过20")
    @Schema(description = "联系电话", example = "13800138000")
    private String contactPhone;

    /**
     * 联系邮箱
     */
    @Size(max = 128, message = "联系邮箱长度不能超过128")
    @Schema(description = "联系邮箱", example = "contact@xx.com")
    private String contactEmail;

    /**
     * 详细地址
     */
    @Size(max = 500, message = "地址长度不能超过500")
    @Schema(description = "详细地址", example = "北京市朝阳区XX路XX号")
    private String address;

    /**
     * 备注
     */
    @Schema(description = "备注信息")
    private String remark;
}
