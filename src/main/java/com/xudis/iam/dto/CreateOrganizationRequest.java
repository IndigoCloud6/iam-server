package com.xudis.iam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 创建组织 DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "创建组织请求")
public class CreateOrganizationRequest {

    /**
     * 组织编码(唯一标识)
     */
    @NotBlank(message = "组织编码不能为空")
    @Size(max = 50, message = "组织编码长度不能超过50")
    @Schema(description = "组织编码", example = "ORG001")
    private String orgCode;

    /**
     * 组织名称
     */
    @NotBlank(message = "组织名称不能为空")
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
     * 组织类型:1-总公司,2-分公司,3-子公司,4-部门,5-项目部,6-虚拟组织,7-合作伙伴
     */
    @NotNull(message = "组织类型不能为空")
    @Schema(description = "组织类型", example = "1")
    private Integer orgType;

    /**
     * 组织类别
     */
    @Size(max = 50, message = "组织类别长度不能超过50")
    @Schema(description = "组织类别", example = "科技公司")
    private String orgCategory;

    /**
     * 父组织ID
     */
    @Schema(description = "父组织ID", example = "1")
    private Long parentId;

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
     * 法定代表人
     */
    @Size(max = 100, message = "法定代表人长度不能超过100")
    @Schema(description = "法定代表人", example = "李四")
    private String legalRepresentative;

    /**
     * 统一社会信用代码
     */
    @Size(max = 50, message = "统一社会信用代码长度不能超过50")
    @Schema(description = "统一社会信用代码", example = "91110000123456789X")
    private String unifiedSocialCode;

    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "成立日期", example = "2020-01-01")
    private LocalDate establishmentDate;

    /**
     * 组织状态:1-正常,2-停用,3-注销,4-筹备中
     */
    @Schema(description = "组织状态", example = "1")
    private Integer orgStatus;

    /**
     * 备注
     */
    @Schema(description = "备注信息")
    private String remark;
}
