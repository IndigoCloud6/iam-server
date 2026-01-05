package com.indigo.iam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户实体
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user", autoResultMap = true)
public class User extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码哈希值
     */
    private String passwordHash;

    /**
     * 密码盐值
     */
    private String passwordSalt;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 性别:0-未知,1-男,2-女
     */
    private Integer gender;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    /**
     * 所属组织ID
     */
    private Long orgId;

    /**
     * 主部门ID
     */
    private Long departmentId;

    /**
     * 职位
     */
    private String position;

    /**
     * 员工编号
     */
    private String employeeId;

    /**
     * 账户状态:1-正常,2-锁定,3-禁用,4-过期
     */
    private Integer accountStatus;

    /**
     * 账户过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accountExpiredAt;

    /**
     * 凭证过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime credentialsExpiredAt;

    /**
     * 账户锁定至
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime accountLockedUntil;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 连续登录失败次数
     */
    private Integer loginFailCount;

    /**
     * 密码最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime passwordChangedAt;

    /**
     * 密码过期天数
     */
    private Integer passwordExpireDays;

    /**
     * 是否启用双因素认证
     */
    private Integer twoFactorEnabled;

    /**
     * 双因素密钥
     */
    private String twoFactorSecret;

    /**
     * 备用验证码
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> twoFactorBackupCodes;

    /**
     * API访问令牌
     */
    private String apiToken;

    /**
     * API令牌过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime apiTokenExpiresAt;
}
