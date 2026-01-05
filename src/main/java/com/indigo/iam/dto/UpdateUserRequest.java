package com.indigo.iam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * 更新用户DTO
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@Schema(description = "更新用户请求")
public class UpdateUserRequest {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID", example = "1")
    private Long id;

    /**
     * 真实姓名
     */
    @Size(max = 50, message = "真实姓名长度不能超过50")
    @Schema(description = "真实姓名", example = "张三")
    private String realName;

    /**
     * 昵称
     */
    @Size(max = 50, message = "昵称长度不能超过50")
    @Schema(description = "昵称", example = "小张")
    private String nickname;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100")
    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;

    /**
     * 手机号
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号", example = "13800138000")
    private String mobile;

    /**
     * 头像地址
     */
    @Size(max = 500, message = "头像地址长度不能超过500")
    @Schema(description = "头像地址", example = "https://example.com/avatar.jpg")
    private String avatar;

    /**
     * 性别:0-未知,1-男,2-女
     */
    @Schema(description = "性别:0-未知,1-男,2-女", example = "1")
    private Integer gender;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "生日", example = "1990-01-01")
    private LocalDate birthday;

    /**
     * 所属组织ID
     */
    @Schema(description = "所属组织ID", example = "1")
    private Long orgId;

    /**
     * 主部门ID
     */
    @Schema(description = "主部门ID", example = "1")
    private Long departmentId;

    /**
     * 职位
     */
    @Size(max = 100, message = "职位长度不能超过100")
    @Schema(description = "职位", example = "软件工程师")
    private String position;

    /**
     * 员工编号
     */
    @Size(max = 50, message = "员工编号长度不能超过50")
    @Schema(description = "员工编号", example = "EMP001")
    private String employeeId;

    /**
     * 账户状态:1-正常,2-锁定,3-禁用,4-过期
     */
    @Schema(description = "账户状态:1-正常,2-锁定,3-禁用,4-过期", example = "1")
    private Integer accountStatus;
}
