package com.indigo.iam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统配置实体
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Data
@TableName("sys_config")
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 配置类型:STRING,NUMBER,BOOLEAN,JSON
     */
    private String configType;

    /**
     * 作用域:GLOBAL,TENANT,DEPARTMENT
     */
    private String scope;

    /**
     * 作用域ID
     */
    private Long scopeId;

    /**
     * 配置版本
     */
    private Integer version;

    /**
     * 前一个值
     */
    private String previousValue;

    /**
     * 配置描述
     */
    private String description;

    /**
     * 是否敏感配置
     */
    private Integer isSensitive;

    /**
     * 是否系统配置
     */
    private Integer isSystem;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
