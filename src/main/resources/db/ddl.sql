SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置键',
  `config_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '配置值',
  `config_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'STRING' COMMENT '配置类型:STRING,NUMBER,BOOLEAN,JSON',
  `scope` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'GLOBAL' COMMENT '作用域:GLOBAL,TENANT,DEPARTMENT',
  `scope_id` bigint(20) NULL DEFAULT NULL COMMENT '作用域ID',
  `version` int(11) NULL DEFAULT 1 COMMENT '配置版本',
  `previous_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '前一个值',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置描述',
  `is_sensitive` tinyint(1) NULL DEFAULT 0 COMMENT '是否敏感配置',
  `is_system` tinyint(1) NULL DEFAULT 0 COMMENT '是否系统配置',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `config_key`(`config_key`) USING BTREE,
  INDEX `idx_config_key`(`config_key`) USING BTREE,
  INDEX `idx_scope`(`scope`, `scope_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `org_id` bigint(20) NOT NULL COMMENT '所属组织ID',
  `dept_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '部门编码',
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门ID',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '祖级列表(逗号分隔)',
  `dept_path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '部门路径',
  `dept_level` int(11) NULL DEFAULT 1 COMMENT '部门层级',
  `leader_id` bigint(20) NULL DEFAULT NULL COMMENT '负责人ID',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `dept_type` tinyint(4) NULL DEFAULT 1 COMMENT '部门类型:1-公司,2-部门,3-小组',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态:1-正常,2-停用',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '描述',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dept_code`(`dept_code`) USING BTREE,
  UNIQUE INDEX `uk_dept_path`(`dept_path`(255)) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE,
  INDEX `idx_ancestors`(`ancestors`(255)) USING BTREE,
  INDEX `idx_dept_code`(`dept_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dict_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `dict_type` tinyint(4) NULL DEFAULT 1 COMMENT '字典类型:1-系统字典,2-业务字典',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态:1-启用,2-停用',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_dict_code`(`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典项ID',
  `dict_id` bigint(20) NOT NULL COMMENT '字典ID',
  `item_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典项编码',
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典项名称',
  `item_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典项值',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父项ID',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '祖级列表',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '排序号',
  `extra_info` json NULL COMMENT '扩展信息',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态:1-启用,2-停用',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_dict_item`(`dict_id`, `item_code`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE,
  CONSTRAINT `sys_dict_item_ibfk_1` FOREIGN KEY (`dict_id`) REFERENCES `sys_dict` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_group_member
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_member`;
CREATE TABLE `sys_group_member`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '成员ID',
  `group_id` bigint(20) NOT NULL COMMENT '用户组ID',
  `member_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '成员类型:USER,ROLE,DEPARTMENT,GROUP',
  `member_id` bigint(20) NOT NULL COMMENT '成员ID',
  `effective_from` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `effective_to` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_member`(`group_id`, `member_type`, `member_id`) USING BTREE,
  INDEX `idx_member`(`member_type`, `member_id`) USING BTREE,
  CONSTRAINT `sys_group_member_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `sys_user_group` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组成员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `login_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录方式:PASSWORD,SSO,LDAP,OAUTH2',
  `login_status` tinyint(4) NULL DEFAULT 1 COMMENT '登录状态:1-成功,2-失败,3-锁定',
  `failure_reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失败原因',
  `device_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备类型',
  `device_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备ID',
  `os_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地理位置',
  `isp` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网络运营商',
  `session_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会话ID',
  `mfa_used` tinyint(1) NULL DEFAULT 0 COMMENT '是否使用MFA',
  `login_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_login_time`(`login_time`) USING BTREE,
  INDEX `idx_ip`(`ip_address`) USING BTREE,
  INDEX `idx_status`(`login_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请求跟踪ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '操作用户ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `department_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `operation_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作类型',
  `operation_module` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作模块',
  `operation_target` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作目标',
  `operation_desc` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作描述',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请求方法',
  `request_uri` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '请求URI',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '请求参数',
  `request_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '请求体',
  `response_status` int(11) NULL DEFAULT NULL COMMENT '响应状态码',
  `response_body` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '响应体',
  `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '错误信息',
  `client_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '客户端IP',
  `user_agent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户代理',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '登录地点',
  `execute_time` bigint(20) NULL DEFAULT NULL COMMENT '执行时间(毫秒)',
  `memory_used` bigint(20) NULL DEFAULT NULL COMMENT '内存使用(字节)',
  `operation_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_operation_time`(`operation_time`) USING BTREE,
  INDEX `idx_operation_type`(`operation_type`) USING BTREE,
  INDEX `idx_trace_id`(`trace_id`) USING BTREE,
  INDEX `idx_module`(`operation_module`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组织ID',
  `org_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织编码(唯一标识)',
  `org_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '组织名称',
  `org_short_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组织简称',
  `org_en_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '英文名称',
  `org_type` tinyint(4) NOT NULL COMMENT '组织类型:1-总公司,2-分公司,3-子公司,4-部门,5-项目部,6-虚拟组织,7-合作伙伴',
  `org_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组织类别',
  `org_level` tinyint(4) NULL DEFAULT 1 COMMENT '组织层级',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父组织ID',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '祖级列表(逗号分隔)',
  `org_path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组织路径(用于快速查询)',
  `tree_path` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '树形路径',
  `root_org_id` bigint(20) NULL DEFAULT NULL COMMENT '根组织ID',
  `contact_person` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  `fax` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '传真',
  `website` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '网站',
  `country_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '国家代码',
  `province_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省份代码',
  `city_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市代码',
  `district_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区县代码',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细地址',
  `postal_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `longitude` decimal(10, 7) NULL DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10, 7) NULL DEFAULT NULL COMMENT '纬度',
  `legal_representative` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '法定代表人',
  `unified_social_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '统一社会信用代码',
  `business_license_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '营业执照号',
  `business_scope` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '经营范围',
  `registered_capital` decimal(15, 2) NULL DEFAULT NULL COMMENT '注册资本(万元)',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '货币代码',
  `establishment_date` date NULL DEFAULT NULL COMMENT '成立日期',
  `operating_period_start` date NULL DEFAULT NULL COMMENT '经营期限开始',
  `operating_period_end` date NULL DEFAULT NULL COMMENT '经营期限结束',
  `org_status` tinyint(4) NULL DEFAULT 1 COMMENT '组织状态:1-正常,2-停用,3-注销,4-筹备中',
  `is_independent` tinyint(1) NULL DEFAULT 1 COMMENT '是否独立核算',
  `is_virtual` tinyint(1) NULL DEFAULT 0 COMMENT '是否为虚拟组织',
  `is_leaf` tinyint(1) NULL DEFAULT 0 COMMENT '是否为叶子节点',
  `employee_count` int(11) NULL DEFAULT 0 COMMENT '员工数量',
  `manager_count` int(11) NULL DEFAULT 0 COMMENT '管理人员数量',
  `department_count` int(11) NULL DEFAULT 0 COMMENT '下级部门数量',
  `subsidiary_count` int(11) NULL DEFAULT 0 COMMENT '子公司数量',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `sort_weight` int(11) NULL DEFAULT 1000 COMMENT '排序权重',
  `logo_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组织Logo',
  `theme_config` json NULL COMMENT '主题配置',
  `timezone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'Asia/Shanghai' COMMENT '时区',
  `language_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'zh-CN' COMMENT '语言代码',
  `currency_symbol` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '¥' COMMENT '货币符号',
  `date_format` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'YYYY-MM-DD' COMMENT '日期格式',
  `time_format` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'HH:mm:ss' COMMENT '时间格式',
  `data_isolation_level` tinyint(4) NULL DEFAULT 1 COMMENT '数据隔离级别:1-完全隔离,2-部分共享,3-完全共享',
  `permission_template_id` bigint(20) NULL DEFAULT NULL COMMENT '权限模板ID',
  `role_template_id` bigint(20) NULL DEFAULT NULL COMMENT '角色模板ID',
  `industry_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '行业代码',
  `org_nature` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组织性质(国企/民企/外企等)',
  `org_scale` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组织规模(大型/中型/小型)',
  `credit_rating` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '信用评级',
  `tax_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '税务登记号',
  `bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '开户银行',
  `bank_account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '银行账号',
  `account_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账户名称',
  `invoice_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `taxpayer_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '纳税人类型',
  `approval_workflow_id` bigint(20) NULL DEFAULT NULL COMMENT '审批流程ID',
  `require_approval` tinyint(1) NULL DEFAULT 0 COMMENT '是否需要审批',
  `external_system_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '外部系统ID',
  `sync_status` tinyint(4) NULL DEFAULT 0 COMMENT '同步状态:0-未同步,1-已同步,2-同步失败',
  `last_sync_time` datetime(0) NULL DEFAULT NULL COMMENT '最后同步时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `version` int(11) NULL DEFAULT 1 COMMENT '乐观锁版本号',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标记',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '组织描述',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `org_code`(`org_code`) USING BTREE,
  INDEX `idx_org_code`(`org_code`) USING BTREE,
  INDEX `idx_org_name`(`org_name`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE,
  INDEX `idx_ancestors`(`ancestors`(255)) USING BTREE,
  INDEX `idx_root_org`(`root_org_id`) USING BTREE,
  INDEX `idx_org_type`(`org_type`) USING BTREE,
  INDEX `idx_org_status`(`org_status`) USING BTREE,
  INDEX `idx_created_at`(`created_at`) USING BTREE,
  FULLTEXT INDEX `idx_fulltext_search`(`org_name`, `org_short_name`, `contact_person`, `address`) COMMENT '全文搜索索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '组织/公司表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_organization_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_admin`;
CREATE TABLE `sys_organization_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `org_id` bigint(20) NOT NULL COMMENT '组织ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `admin_type` tinyint(4) NOT NULL COMMENT '管理员类型:1-超级管理员,2-系统管理员,3-业务管理员,4-审计管理员',
  `admin_level` int(11) NULL DEFAULT 1 COMMENT '管理级别',
  `scope_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'ALL' COMMENT '管理范围:ALL-全部,DEPARTMENT-指定部门,PROJECT-指定项目',
  `scope_ids` json NULL COMMENT '范围ID列表',
  `effective_from` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `effective_to` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '是否生效',
  `can_manage_user` tinyint(1) NULL DEFAULT 0 COMMENT '能否管理用户',
  `can_manage_role` tinyint(1) NULL DEFAULT 0 COMMENT '能否管理角色',
  `can_manage_dept` tinyint(1) NULL DEFAULT 0 COMMENT '能否管理部门',
  `can_manage_permission` tinyint(1) NULL DEFAULT 0 COMMENT '能否管理权限',
  `can_manage_config` tinyint(1) NULL DEFAULT 0 COMMENT '能否管理配置',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_org_admin`(`org_id`, `user_id`, `admin_type`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE,
  INDEX `idx_active`(`is_active`, `effective_from`, `effective_to`) USING BTREE,
  CONSTRAINT `sys_organization_admin_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `sys_organization` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_organization_admin_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父权限ID',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '祖级列表(逗号分隔)',
  `perm_type` tinyint(4) NOT NULL COMMENT '权限类型:1-菜单,2-按钮,3-API接口,4-数据权限,5-文件权限',
  `perm_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限标识符(唯一)',
  `perm_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '权限名称',
  `perm_level` int(11) NULL DEFAULT 1 COMMENT '权限层级',
  `menu_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `component` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '前端组件',
  `redirect` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `is_external` tinyint(1) NULL DEFAULT 0 COMMENT '是否外链',
  `external_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '外链地址',
  `is_cache` tinyint(1) NULL DEFAULT 1 COMMENT '是否缓存',
  `is_visible` tinyint(1) NULL DEFAULT 1 COMMENT '是否显示',
  `is_breadcrumb` tinyint(1) NULL DEFAULT 1 COMMENT '是否显示面包屑',
  `api_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'API路径(支持Ant风格)',
  `api_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'HTTP方法(GET,POST,PUT,DELETE等)',
  `data_scope_rule` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '数据权限规则(JSON格式)',
  `field_scope` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '字段权限规则',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态:1-启用,2-停用',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '排序号',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_perm_code`(`perm_code`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE,
  INDEX `idx_perm_type`(`perm_type`) USING BTREE,
  INDEX `idx_api_path`(`api_path`(200)) USING BTREE,
  INDEX `idx_menu_path`(`menu_path`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '权限资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission_change_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_change_log`;
CREATE TABLE `sys_permission_change_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `change_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '变更类型:GRANT,REVOKE,UPDATE',
  `target_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目标类型:USER,ROLE,GROUP',
  `target_id` bigint(20) NOT NULL COMMENT '目标ID',
  `permission_id` bigint(20) NULL DEFAULT NULL COMMENT '权限ID',
  `permission_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限编码',
  `old_value` json NULL COMMENT '变更前值',
  `new_value` json NULL COMMENT '变更后值',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '操作人ID',
  `operator_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人姓名',
  `change_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更原因',
  `ticket_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单ID',
  `change_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_target`(`target_type`, `target_id`) USING BTREE,
  INDEX `idx_change_time`(`change_time`) USING BTREE,
  INDEX `idx_permission`(`permission_id`) USING BTREE,
  INDEX `idx_operator`(`operator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限变更日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_policy
-- ----------------------------
DROP TABLE IF EXISTS `sys_policy`;
CREATE TABLE `sys_policy`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '策略ID',
  `policy_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略编码',
  `policy_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '策略名称',
  `policy_type` tinyint(4) NULL DEFAULT 1 COMMENT '策略类型:1-访问控制,2-数据过滤,3-审批流程',
  `effect` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'ALLOW' COMMENT '效果:ALLOW,DENY',
  `conditions` json NULL COMMENT '生效条件',
  `actions` json NULL COMMENT '允许的操作列表',
  `resources` json NULL COMMENT '资源列表',
  `priority` int(11) NULL DEFAULT 1000 COMMENT '优先级(数字越小优先级越高)',
  `context_rules` json NULL COMMENT '上下文规则',
  `ip_ranges` json NULL COMMENT 'IP范围限制',
  `time_ranges` json NULL COMMENT '时间范围限制',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态:1-启用,2-停用',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `policy_code`(`policy_code`) USING BTREE,
  INDEX `idx_policy_code`(`policy_code`) USING BTREE,
  INDEX `idx_policy_type`(`policy_type`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限策略表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_policy_binding
-- ----------------------------
DROP TABLE IF EXISTS `sys_policy_binding`;
CREATE TABLE `sys_policy_binding`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '绑定ID',
  `policy_id` bigint(20) NOT NULL COMMENT '策略ID',
  `target_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目标类型:USER,ROLE,GROUP,DEPARTMENT',
  `target_id` bigint(20) NOT NULL COMMENT '目标ID',
  `scope_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'GLOBAL' COMMENT '作用范围:GLOBAL,DEPARTMENT,PROJECT',
  `scope_id` bigint(20) NULL DEFAULT NULL COMMENT '范围ID',
  `effective_from` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `effective_to` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_policy_target`(`policy_id`, `target_type`, `target_id`, `scope_type`, `scope_id`) USING BTREE,
  INDEX `idx_target`(`target_type`, `target_id`) USING BTREE,
  INDEX `idx_scope`(`scope_type`, `scope_id`) USING BTREE,
  CONSTRAINT `sys_policy_binding_ibfk_1` FOREIGN KEY (`policy_id`) REFERENCES `sys_policy` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '策略绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `position_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `position_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `department_id` bigint(20) NULL DEFAULT NULL COMMENT '所属部门ID',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '上级岗位ID',
  `position_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位级别',
  `job_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位类别',
  `responsibility` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '岗位职责',
  `requirement` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '任职要求',
  `default_role_ids` json NULL COMMENT '默认角色ID列表',
  `required_permissions` json NULL COMMENT '必需权限列表',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态:1-启用,2-停用',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '排序号',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `position_code`(`position_code`) USING BTREE,
  INDEX `idx_department`(`department_id`) USING BTREE,
  INDEX `idx_position_code`(`position_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色编码',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `role_type` tinyint(4) NULL DEFAULT 1 COMMENT '角色类型:1-系统角色,2-业务角色,3-自定义角色',
  `role_level` int(11) NULL DEFAULT 99 COMMENT '角色级别(数字越小级别越高)',
  `data_scope` tinyint(4) NULL DEFAULT 1 COMMENT '数据权限范围:1-全部数据,2-本部门及以下,3-本部门,4-仅本人,5-自定义',
  `is_permanent` tinyint(1) NULL DEFAULT 1 COMMENT '是否永久有效',
  `effective_from` datetime(0) NULL DEFAULT NULL COMMENT '生效时间',
  `effective_to` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态:1-启用,2-停用',
  `max_user_count` int(11) NULL DEFAULT NULL COMMENT '最大用户数限制',
  `current_user_count` int(11) NULL DEFAULT 0 COMMENT '当前用户数',
  `is_system` tinyint(1) NULL DEFAULT 0 COMMENT '是否为系统内置角色',
  `is_default` tinyint(1) NULL DEFAULT 0 COMMENT '是否为默认角色',
  `require_mfa` tinyint(1) NULL DEFAULT 0 COMMENT '是否要求MFA',
  `ip_whitelist` json NULL COMMENT 'IP白名单',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '角色描述',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_code`(`role_code`) USING BTREE,
  INDEX `idx_role_code`(`role_code`) USING BTREE,
  INDEX `idx_role_type`(`role_type`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE,
  INDEX `idx_created_at`(`created_at`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `operation_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作类型(当权限为数据权限时使用)',
  `condition_expression` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '条件表达式(JSON格式)',
  `field_filter` json NULL COMMENT '字段过滤规则',
  `row_filter` json NULL COMMENT '行过滤规则',
  `effective_from` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `effective_to` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `is_inheritable` tinyint(1) NULL DEFAULT 1 COMMENT '是否可被继承',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_permission`(`role_id`, `permission_id`, `operation_type`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_permission_id`(`permission_id`) USING BTREE,
  INDEX `idx_effective`(`effective_from`, `effective_to`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码哈希值',
  `password_salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码盐值',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '真实姓名',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '登录名',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像地址',
  `gender` tinyint(4) NULL DEFAULT 0 COMMENT '性别:0-未知,1-男,2-女',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '所属组织ID',
  `department_id` bigint(20) NULL DEFAULT NULL COMMENT '主部门ID',
  `position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职位',
  `employee_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '员工编号',
  `account_status` tinyint(4) NULL DEFAULT 1 COMMENT '账户状态:1-正常,2-锁定,3-禁用,4-过期',
  `account_expired_at` datetime(0) NULL DEFAULT NULL COMMENT '账户过期时间',
  `credentials_expired_at` datetime(0) NULL DEFAULT NULL COMMENT '凭证过期时间',
  `account_locked_until` datetime(0) NULL DEFAULT NULL COMMENT '账户锁定至',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `login_fail_count` tinyint(4) NULL DEFAULT 0 COMMENT '连续登录失败次数',
  `password_changed_at` datetime(0) NULL DEFAULT NULL COMMENT '密码最后修改时间',
  `password_expire_days` int(11) NULL DEFAULT 90 COMMENT '密码过期天数',
  `two_factor_enabled` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用双因素认证',
  `two_factor_secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '双因素密钥',
  `two_factor_backup_codes` json NULL COMMENT '备用验证码',
  `api_token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'API访问令牌',
  `api_token_expires_at` datetime(0) NULL DEFAULT NULL COMMENT 'API令牌过期时间',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `version` int(11) NULL DEFAULT 1 COMMENT '乐观锁版本号',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标记',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `nickname`(`nickname`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE,
  INDEX `idx_email`(`email`) USING BTREE,
  INDEX `idx_mobile`(`mobile`) USING BTREE,
  INDEX `idx_department`(`department_id`) USING BTREE,
  INDEX `idx_status`(`account_status`) USING BTREE,
  INDEX `idx_created_at`(`created_at`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `department_id` bigint(20) NOT NULL COMMENT '部门ID',
  `is_primary` tinyint(1) NULL DEFAULT 0 COMMENT '是否主部门',
  `position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '部门内职位',
  `job_level` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '职级',
  `effective_from` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `effective_to` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '是否生效',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_department_active`(`user_id`, `department_id`, `is_active`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_department_id`(`department_id`) USING BTREE,
  INDEX `idx_primary`(`is_primary`) USING BTREE,
  INDEX `idx_effective`(`effective_from`, `effective_to`) USING BTREE,
  CONSTRAINT `sys_user_department_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_user_department_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户组ID',
  `group_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户组编码',
  `group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户组名称',
  `group_type` tinyint(4) NULL DEFAULT 1 COMMENT '组类型:1-静态组,2-动态组',
  `dynamic_rule` json NULL COMMENT '动态组规则(JSON格式)',
  `last_sync_time` datetime(0) NULL DEFAULT NULL COMMENT '最后同步时间',
  `sync_interval` int(11) NULL DEFAULT 3600 COMMENT '同步间隔(秒)',
  `parent_group_id` bigint(20) NULL DEFAULT NULL COMMENT '父用户组ID',
  `inherit_permissions` tinyint(1) NULL DEFAULT 1 COMMENT '是否继承父组权限',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态:1-启用,2-停用',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `updated_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `group_code`(`group_code`) USING BTREE,
  INDEX `idx_group_code`(`group_code`) USING BTREE,
  INDEX `idx_group_type`(`group_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_position
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_position`;
CREATE TABLE `sys_user_position`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `position_id` bigint(20) NOT NULL COMMENT '岗位ID',
  `department_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `is_primary` tinyint(1) NULL DEFAULT 0 COMMENT '是否主岗位',
  `effective_from` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `effective_to` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '是否生效',
  `created_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_position_active`(`user_id`, `position_id`, `is_active`) USING BTREE,
  INDEX `position_id`(`position_id`) USING BTREE,
  CONSTRAINT `sys_user_position_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_user_position_ibfk_2` FOREIGN KEY (`position_id`) REFERENCES `sys_position` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `department_id` bigint(20) NULL DEFAULT NULL COMMENT '所属部门ID(用于部门角色)',
  `effective_from` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `effective_to` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '是否生效',
  `assigned_by` bigint(20) NULL DEFAULT NULL COMMENT '分配人',
  `assignment_type` tinyint(4) NULL DEFAULT 1 COMMENT '分配类型:1-手动分配,2-自动分配,3-继承分配',
  `source_id` bigint(20) NULL DEFAULT NULL COMMENT '来源ID(如岗位ID)',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_role_dept`(`user_id`, `role_id`, `department_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_department`(`department_id`) USING BTREE,
  INDEX `idx_effective`(`effective_from`, `effective_to`, `is_active`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_user_role_ibfk_3` FOREIGN KEY (`department_id`) REFERENCES `sys_department` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
