package com.indigo.iam.vo;

import lombok.Data;

import java.util.List;

/**
 * 角色权限树VO
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Data
public class RolePermissionTreeVO {

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限编码
     */
    private String permCode;

    /**
     * 权限名称
     */
    private String permName;

    /**
     * 权限类型:1-菜单,2-按钮,3-API接口,4-数据权限
     */
    private Integer permType;

    /**
     * 父权限ID
     */
    private Long parentId;

    /**
     * 是否已选中
     */
    private Boolean checked;

    /**
     * 子权限列表
     */
    private List<RolePermissionTreeVO> children;
}
