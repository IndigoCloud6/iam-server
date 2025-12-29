package com.xudis.iam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 组织/公司实体
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_organization")
public class Organization extends BaseEntity {

    /**
     * 组织ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 组织编码(唯一标识)
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
     * 英文名称
     */
    private String orgEnName;

    /**
     * 组织类型:1-总公司,2-分公司,3-子公司,4-部门,5-项目部,6-虚拟组织,7-合作伙伴
     */
    private Integer orgType;

    /**
     * 组织类别
     */
    private String orgCategory;

    /**
     * 组织层级
     */
    private Integer orgLevel;

    /**
     * 父组织ID
     */
    private Long parentId;

    /**
     * 祖级列表(逗号分隔)
     */
    private String ancestors;

    /**
     * 组织路径(用于快速查询)
     */
    private String orgPath;

    /**
     * 树形路径
     */
    private String treePath;

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
     * 传真
     */
    private String fax;

    /**
     * 网站
     */
    private String website;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 省份代码
     */
    private String provinceCode;

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 区县代码
     */
    private String districtCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 法定代表人
     */
    private String legalRepresentative;

    /**
     * 统一社会信用代码
     */
    private String unifiedSocialCode;

    /**
     * 营业执照号
     */
    private String businessLicenseNo;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 注册资本(万元)
     */
    private BigDecimal registeredCapital;

    /**
     * 货币代码
     */
    private String currencyCode;

    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate establishmentDate;

    /**
     * 经营期限开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate operatingPeriodStart;

    /**
     * 经营期限结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate operatingPeriodEnd;

    /**
     * 组织状态:1-正常,2-停用,3-注销,4-筹备中
     */
    private Integer orgStatus;

    /**
     * 是否独立核算
     */
    private Integer isIndependent;

    /**
     * 是否为虚拟组织
     */
    private Integer isVirtual;

    /**
     * 是否为叶子节点
     */
    private Integer isLeaf;

    /**
     * 员工数量
     */
    private Integer employeeCount;

    /**
     * 管理人员数量
     */
    private Integer managerCount;

    /**
     * 下级部门数量
     */
    private Integer departmentCount;

    /**
     * 子公司数量
     */
    private Integer subsidiaryCount;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 排序权重
     */
    private Integer sortWeight;

    /**
     * 组织Logo
     */
    private String logoUrl;

    /**
     * 主题配置
     */
    private String themeConfig;

    /**
     * 时区
     */
    private String timezone;

    /**
     * 语言代码
     */
    private String languageCode;

    /**
     * 货币符号
     */
    private String currencySymbol;

    /**
     * 日期格式
     */
    private String dateFormat;

    /**
     * 时间格式
     */
    private String timeFormat;

    /**
     * 数据隔离级别:1-完全隔离,2-部分共享,3-完全共享
     */
    private Integer dataIsolationLevel;

    /**
     * 权限模板ID
     */
    private Long permissionTemplateId;

    /**
     * 角色模板ID
     */
    private Long roleTemplateId;

    /**
     * 行业代码
     */
    private String industryCode;

    /**
     * 组织性质(国企/民企/外企等)
     */
    private String orgNature;

    /**
     * 组织规模(大型/中型/小型)
     */
    private String orgScale;

    /**
     * 信用评级
     */
    private String creditRating;

    /**
     * 税务登记号
     */
    private String taxNumber;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 纳税人类型
     */
    private String taxpayerType;

    /**
     * 审批流程ID
     */
    private Long approvalWorkflowId;

    /**
     * 是否需要审批
     */
    private Integer requireApproval;

    /**
     * 外部系统ID
     */
    private String externalSystemId;

    /**
     * 同步状态:0-未同步,1-已同步,2-同步失败
     */
    private Integer syncStatus;

    /**
     * 最后同步时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime lastSyncTime;

    /**
     * 组织描述
     */
    private String description;

    /**
     * 备注信息
     */
    private String remark;
}
