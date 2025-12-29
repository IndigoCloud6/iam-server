package com.xudis.iam.common;

/**
 * 缓存Key常量类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public class CacheKeyConstants {

    /**
     * 用户权限缓存 - user:permissions:{userId}
     */
    public static final String USER_PERMISSIONS = "user:permissions:";

    /**
     * 用户菜单缓存 - user:menus:{userId}
     */
    public static final String USER_MENUS = "user:menus:";

    /**
     * 用户按钮权限缓存 - user:buttons:{userId}
     */
    public static final String USER_BUTTONS = "user:buttons:";

    /**
     * 用户API权限缓存 - user:apis:{userId}
     */
    public static final String USER_APIS = "user:apis:";

    /**
     * 角色权限缓存 - role:permissions:{roleId}
     */
    public static final String ROLE_PERMISSIONS = "role:permissions:";

    /**
     * 用户角色缓存 - user:roles:{userId}
     */
    public static final String USER_ROLES = "user:roles:";

    /**
     * 部门树缓存 - department:tree:{orgId}
     */
    public static final String DEPARTMENT_TREE = "department:tree:";

    /**
     * 权限树缓存 - permission:tree
     */
    public static final String PERMISSION_TREE = "permission:tree";

    /**
     * 组织树缓存 - organization:tree
     */
    public static final String ORGANIZATION_TREE = "organization:tree";

    /**
     * 字典项缓存 - dict:items:{dictId}
     */
    public static final String DICT_ITEMS = "dict:items:";

    /**
     * 字典项树缓存 - dict:tree:{dictId}
     */
    public static final String DICT_TREE = "dict:tree:";

    /**
     * 配置缓存 - config:{configKey}
     */
    public static final String CONFIG = "config:";

    /**
     * 统计概览缓存 - statistics:overview
     */
    public static final String STATISTICS_OVERVIEW = "statistics:overview";

    /**
     * 用户策略缓存 - user:policies:{userId}
     */
    public static final String USER_POLICIES = "user:policies:";

    /**
     * 角色策略缓存 - role:policies:{roleId}
     */
    public static final String ROLE_POLICIES = "role:policies:";

    private CacheKeyConstants() {
        // 工具类不允许实例化
    }
}
