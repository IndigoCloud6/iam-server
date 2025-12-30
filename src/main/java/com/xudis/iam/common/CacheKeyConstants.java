package com.xudis.iam.common;

/**
 * 缓存Key常量类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public class CacheKeyConstants {

    /**
     * 用户权限缓存
     */
    public static final String USER_PERMISSIONS = "user:permissions";

    /**
     * 用户菜单缓存
     */
    public static final String USER_MENUS = "user:menus";

    /**
     * 用户按钮权限缓存
     */
    public static final String USER_BUTTONS = "user:buttons";

    /**
     * 用户API权限缓存
     */
    public static final String USER_APIS = "user:apis";

    /**
     * 角色权限缓存
     */
    public static final String ROLE_PERMISSIONS = "role:permissions";

    /**
     * 用户角色缓存
     */
    public static final String USER_ROLES = "user:roles";

    /**
     * 部门树缓存
     */
    public static final String DEPARTMENT_TREE = "department:tree";

    /**
     * 权限树缓存
     */
    public static final String PERMISSION_TREE = "permission:tree";

    /**
     * 组织树缓存
     */
    public static final String ORGANIZATION_TREE = "organization:tree";

    /**
     * 字典项缓存
     */
    public static final String DICT_ITEMS = "dict:items";

    /**
     * 字典项树缓存
     */
    public static final String DICT_TREE = "dict:tree";

    /**
     * 配置缓存
     */
    public static final String CONFIG = "config";

    /**
     * 统计概览缓存
     */
    public static final String STATISTICS_OVERVIEW = "statistics:overview";

    /**
     * 用户策略缓存
     */
    public static final String USER_POLICIES = "user:policies";

    /**
     * 角色策略缓存
     */
    public static final String ROLE_POLICIES = "role:policies";

    private CacheKeyConstants() {
        // 工具类不允许实例化
    }
}
