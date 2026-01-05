package com.indigo.iam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 组织树形VO
 *
 * @author MaxYun
 * @since 2025/12/31
 */
@Data
public class OrganizationTreeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织ID
     */
    private Long id;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 组织简称
     */
    private String orgShortName;

    /**
     * 组织类型:1-总公司,2-分公司,3-子公司,4-部门,5-项目部,6-虚拟组织,7-合作伙伴
     */
    private Integer orgType;

    /**
     * 组织层级
     */
    private Integer orgLevel;

    /**
     * 父组织ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 组织路径
     */
    private String orgPath;

    /**
     * 根组织ID
     */
    private Long rootOrgId;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 组织状态:1-正常,2-停用,3-注销,4-筹备中
     */
    private Integer orgStatus;

    /**
     * 是否为叶子节点
     */
    private Integer isLeaf;

    /**
     * 员工数量
     */
    private Integer employeeCount;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 组织Logo
     */
    private String logoUrl;

    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate establishmentDate;

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

    /**
     * 组织描述
     */
    private String description;

    /**
     * 子组织列表
     */
    private List<OrganizationTreeVO> children;
}
