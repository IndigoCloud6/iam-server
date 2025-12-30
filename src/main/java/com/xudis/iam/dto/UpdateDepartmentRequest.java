package com.xudis.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 更新部门 DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "更新部门请求")
public class UpdateDepartmentRequest {

    /**
     * 部门ID
     */
    @NotNull(message = "部门ID不能为空")
    @Schema(description = "部门ID", example = "1")
    private Long id;

    /**
     * 部门名称
     */
    @Size(max = 100, message = "部门名称长度不能超过100")
    @Schema(description = "部门名称", example = "研发部")
    private String deptName;

    /**
     * 负责人ID
     */
    @Schema(description = "负责人ID", example = "1")
    private Long leaderId;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序", example = "1")
    private Integer orderNum;

    /**
     * 联系电话
     */
    @Size(max = 20, message = "联系电话长度不能超过20")
    @Schema(description = "联系电话", example = "010-12345678")
    private String phone;

    /**
     * 邮箱
     */
    @Size(max = 128, message = "邮箱长度不能超过128")
    @Schema(description = "邮箱", example = "dept@xx.com")
    private String email;

    /**
     * 地址
     */
    @Size(max = 500, message = "地址长度不能超过500")
    @Schema(description = "地址", example = "北京市朝阳区XX大厦")
    private String address;

    /**
     * 状态:1-正常,2-停用
     */
    @Schema(description = "状态", example = "1")
    private Integer status;

    /**
     * 描述
     */
    @Schema(description = "部门描述")
    private String description;
}
