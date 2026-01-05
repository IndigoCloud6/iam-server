-- =====================================================
-- XX集团 组织架构及部门结构 DML
-- 生成时间: 2025-12-31
-- 说明: 包含集团总公司、分公司、子公司及下属多级部门结构
-- =====================================================

-- =====================================================
-- 1. 组织表数据 (sys_organization)
-- =====================================================

INSERT INTO sys_organization (
    id, org_code, org_name, org_short_name, org_type, org_level, parent_id, ancestors,
    org_path, root_org_id, contact_person, contact_phone, contact_email, address,
    org_status, is_leaf, employee_count, order_num, created_by, updated_by, created_at, updated_at
) VALUES
-- 1. XX集团总公司 (根节点)
(1, 'GROUP_HQ', 'XX集团总公司', 'XX集团', 1, 1, 0, '0', '/GROUP_HQ', 1,
 '张总', '010-12345678', 'zhangzong@xxgroup.com', '北京市朝阳区XX路1号',
 1, 0, 500, 1, 1, 1, NOW(), NOW()),

-- 2. 北京分公司
(2, 'BJ_BRANCH', '北京分公司', '北京分', 2, 2, 1, '0,1', '/GROUP_HQ/BJ_BRANCH', 1,
 '李总', '010-87654321', 'lizong@xxgroup.com', '北京市海淀区XX路100号',
 1, 0, 150, 2, 1, 1, NOW(), NOW()),

-- 3. 上海子公司
(3, 'SH_SUB', '上海子公司', '上海子', 3, 2, 1, '0,1', '/GROUP_HQ/SH_SUB', 1,
 '王总', '021-12345678', 'wangzong@xxgroup.com', '上海市浦东新区XX路200号',
 1, 0, 120, 3, 1, 1, NOW(), NOW()),

-- 4. 深圳技术中心
(4, 'SZ_TECH', '深圳技术中心', '深圳技术', 6, 2, 1, '0,1', '/GROUP_HQ/SZ_TECH', 1,
 '赵总', '0755-98765432', 'zhaogong@xxgroup.com', '深圳市南山区XX路300号',
 1, 0, 80, 4, 1, 1, NOW(), NOW()),

-- 5. 成都办事处
(5, 'CD_OFFICE', '成都办事处', '成都办', 5, 2, 1, '0,1', '/GROUP_HQ/CD_OFFICE', 1,
 '刘经理', '028-11112222', 'liujingli@xxgroup.com', '成都市武侯区XX路400号',
 1, 1, 30, 5, 1, 1, NOW(), NOW());

-- =====================================================
-- 2. 部门表数据 (sys_department)
-- =====================================================

-- =====================================================
-- 2.1 XX集团总公司部门 (orgId = 1)
-- =====================================================
INSERT INTO sys_department (
    id, org_id, dept_code, dept_name, parent_id, ancestors, dept_path, dept_level,
    leader_id, order_num, phone, email, dept_type, status, created_by, updated_by, created_at, updated_at
) VALUES
-- 总经办 (一级部门)
(1, 1, 'HQ_GM_OFFICE', '总经办', 0, '0', '/HQ_GM_OFFICE', 1,
 NULL, 1, '010-12345678', 'gmoffice@xxgroup.com', 1, 1, 1, 1, NOW(), NOW()),

-- 人力资源部 (一级部门)
(2, 1, 'HQ_HR_DEPT', '人力资源部', 0, '0', '/HQ_HR_DEPT', 1,
 NULL, 2, '010-12345679', 'hr@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 财务部 (一级部门)
(3, 1, 'HQ_FINANCE_DEPT', '财务部', 0, '0', '/HQ_FINANCE_DEPT', 1,
 NULL, 3, '010-12345680', 'finance@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 技术部 (一级部门)
(4, 1, 'HQ_TECH_DEPT', '技术部', 0, '0', '/HQ_TECH_DEPT', 1,
 NULL, 4, '010-12345681', 'tech@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 市场部 (一级部门)
(5, 1, 'HQ_MARKETING_DEPT', '市场部', 0, '0', '/HQ_MARKETING_DEPT', 1,
 NULL, 5, '010-12345682', 'marketing@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 人力资源部-招聘组 (二级部门)
(6, 1, 'HQ_HR_RECRUIT', '招聘组', 2, '0,2', '/HQ_HR_DEPT/HQ_HR_RECRUIT', 2,
 NULL, 1, '010-12345683', 'recruit@xxgroup.com', 3, 1, 1, 1, NOW(), NOW()),

-- 人力资源部-培训组 (二级部门)
(7, 1, 'HQ_HR_TRAIN', '培训组', 2, '0,2', '/HQ_HR_DEPT/HQ_HR_TRAIN', 2,
 NULL, 2, '010-12345684', 'train@xxgroup.com', 3, 1, 1, 1, NOW(), NOW()),

-- 技术部-研发中心 (二级部门)
(8, 1, 'HQ_TECH_R&D', '研发中心', 4, '0,4', '/HQ_TECH_DEPT/HQ_TECH_R&D', 2,
 NULL, 1, '010-12345685', 'rd@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 技术部-运维组 (二级部门)
(9, 1, 'HQ_TECH_OPS', '运维组', 4, '0,4', '/HQ_TECH_DEPT/HQ_TECH_OPS', 2,
 NULL, 2, '010-12345686', 'ops@xxgroup.com', 3, 1, 1, 1, NOW(), NOW()),

-- 技术部-测试组 (二级部门)
(10, 1, 'HQ_TECH_QA', '测试组', 4, '0,4', '/HQ_TECH_DEPT/HQ_TECH_QA', 2,
 NULL, 3, '010-12345687', 'qa@xxgroup.com', 3, 1, 1, 1, NOW(), NOW()),

-- 研发中心-后端组 (三级部门)
(11, 1, 'HQ_TECH_BACKEND', '后端组', 8, '0,4,8', '/HQ_TECH_DEPT/HQ_TECH_R&D/HQ_TECH_BACKEND', 3,
 NULL, 1, '010-12345688', 'backend@xxgroup.com', 3, 1, 1, 1, NOW(), NOW()),

-- 研发中心-前端组 (三级部门)
(12, 1, 'HQ_TECH_FRONTEND', '前端组', 8, '0,4,8', '/HQ_TECH_DEPT/HQ_TECH_R&D/HQ_TECH_FRONTEND', 3,
 NULL, 2, '010-12345689', 'frontend@xxgroup.com', 3, 1, 1, 1, NOW(), NOW()),

-- 研发中心-移动端组 (三级部门)
(13, 1, 'HQ_TECH_MOBILE', '移动端组', 8, '0,4,8', '/HQ_TECH_DEPT/HQ_TECH_R&D/HQ_TECH_MOBILE', 3,
 NULL, 3, '010-12345690', 'mobile@xxgroup.com', 3, 1, 1, 1, NOW(), NOW());

-- =====================================================
-- 2.2 北京分公司部门 (orgId = 2)
-- =====================================================
INSERT INTO sys_department (
    id, org_id, dept_code, dept_name, parent_id, ancestors, dept_path, dept_level,
    leader_id, order_num, phone, email, dept_type, status, created_by, updated_by, created_at, updated_at
) VALUES
-- 总经办
(14, 2, 'BJ_GM_OFFICE', '总经办', 0, '0', '/BJ_GM_OFFICE', 1,
 NULL, 1, '010-87654321', 'bjgm@xxgroup.com', 1, 1, 1, 1, NOW(), NOW()),

-- 市场部
(15, 2, 'BJ_MARKETING_DEPT', '市场部', 0, '0', '/BJ_MARKETING_DEPT', 1,
 NULL, 2, '010-87654322', 'bjmarket@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 销售部
(16, 2, 'BJ_SALES_DEPT', '销售部', 0, '0', '/BJ_SALES_DEPT', 1,
 NULL, 3, '010-87654323', 'bjsales@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 客服部
(17, 2, 'BJ_SERVICE_DEPT', '客服部', 0, '0', '/BJ_SERVICE_DEPT', 1,
 NULL, 4, '010-87654324', 'bjservice@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 销售部-销售一组
(18, 2, 'BJ_SALES_TEAM1', '销售一组', 16, '0,16', '/BJ_SALES_DEPT/BJ_SALES_TEAM1', 2,
 NULL, 1, '010-87654325', 'bjsales1@xxgroup.com', 3, 1, 1, 1, NOW(), NOW()),

-- 销售部-销售二组
(19, 2, 'BJ_SALES_TEAM2', '销售二组', 16, '0,16', '/BJ_SALES_DEPT/BJ_SALES_TEAM2', 2,
 NULL, 2, '010-87654326', 'bjsales2@xxgroup.com', 3, 1, 1, 1, NOW(), NOW());

-- =====================================================
-- 2.3 上海子公司部门 (orgId = 3)
-- =====================================================
INSERT INTO sys_department (
    id, org_id, dept_code, dept_name, parent_id, ancestors, dept_path, dept_level,
    leader_id, order_num, phone, email, dept_type, status, created_by, updated_by, created_at, updated_at
) VALUES
-- 总经办
(20, 3, 'SH_GM_OFFICE', '总经办', 0, '0', '/SH_GM_OFFICE', 1,
 NULL, 1, '021-12345678', 'shgm@xxgroup.com', 1, 1, 1, 1, NOW(), NOW()),

-- 研发部
(21, 3, 'SH_RD_DEPT', '研发部', 0, '0', '/SH_RD_DEPT', 1,
 NULL, 2, '021-12345679', 'shrd@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 产品部
(22, 3, 'SH_PRODUCT_DEPT', '产品部', 0, '0', '/SH_PRODUCT_DEPT', 1,
 NULL, 3, '021-12345680', 'shproduct@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 运营部
(23, 3, 'SH_OPERATION_DEPT', '运营部', 0, '0', '/SH_OPERATION_DEPT', 1,
 NULL, 4, '021-12345681', 'shoperation@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 研发部-算法组
(24, 3, 'SH_RD_ALGO', '算法组', 21, '0,21', '/SH_RD_DEPT/SH_RD_ALGO', 2,
 NULL, 1, '021-12345682', 'shalgo@xxgroup.com', 3, 1, 1, 1, NOW(), NOW()),

-- 研发部-大数据组
(25, 3, 'SH_RD_BIGDATA', '大数据组', 21, '0,21', '/SH_RD_DEPT/SH_RD_BIGDATA', 2,
 NULL, 2, '021-12345683', 'shbigdata@xxgroup.com', 3, 1, 1, 1, NOW(), NOW());

-- =====================================================
-- 2.4 深圳技术中心部门 (orgId = 4)
-- =====================================================
INSERT INTO sys_department (
    id, org_id, dept_code, dept_name, parent_id, ancestors, dept_path, dept_level,
    leader_id, order_num, phone, email, dept_type, status, created_by, updated_by, created_at, updated_at
) VALUES
-- 总经办
(26, 4, 'SZ_GM_OFFICE', '总经办', 0, '0', '/SZ_GM_OFFICE', 1,
 NULL, 1, '0755-98765432', 'szgm@xxgroup.com', 1, 1, 1, 1, NOW(), NOW()),

-- 研发一部
(27, 4, 'SZ_RD1_DEPT', '研发一部', 0, '0', '/SZ_RD1_DEPT', 1,
 NULL, 2, '0755-98765433', 'szrd1@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 研发二部
(28, 4, 'SZ_RD2_DEPT', '研发二部', 0, '0', '/SZ_RD2_DEPT', 1,
 NULL, 3, '0755-98765434', 'szrd2@xxgroup.com', 2, 1, 1, 1, NOW(), NOW()),

-- 研发一部-AI实验室
(29, 4, 'SZ_RD1_AI', 'AI实验室', 27, '0,27', '/SZ_RD1_DEPT/SZ_RD1_AI', 2,
 NULL, 1, '0755-98765435', 'szai@xxgroup.com', 3, 1, 1, 1, NOW(), NOW()),

-- 研发一部-云计算组
(30, 4, 'SZ_RD1_CLOUD', '云计算组', 27, '0,27', '/SZ_RD1_DEPT/SZ_RD1_CLOUD', 2,
 NULL, 2, '0755-98765436', 'szcloud@xxgroup.com', 3, 1, 1, 1, NOW(), NOW());

-- =====================================================
-- 2.5 成都办事处部门 (orgId = 5)
-- =====================================================
INSERT INTO sys_department (
    id, org_id, dept_code, dept_name, parent_id, ancestors, dept_path, dept_level,
    leader_id, order_num, phone, email, dept_type, status, created_by, updated_by, created_at, updated_at
) VALUES
-- 办事处负责人
(31, 5, 'CD_OFFICE_LEAD', '办事处', 0, '0', '/CD_OFFICE_LEAD', 1,
 NULL, 1, '028-11112222', 'cdoffice@xxgroup.com', 2, 1, 1, 1, NOW()),

-- 业务组
(32, 5, 'CD_SALES_TEAM', '业务组', 31, '0,31', '/CD_OFFICE_LEAD/CD_SALES_TEAM', 2,
 NULL, 1, '028-11112223', 'cdsales@xxgroup.com', 3, 1, 1, 1, NOW()),

-- 支持组
(33, 5, 'CD_SUPPORT_TEAM', '支持组', 31, '0,31', '/CD_OFFICE_LEAD/CD_SUPPORT_TEAM', 2,
 NULL, 2, '028-11112224', 'cdsupport@xxgroup.com', 3, 1, 1, 1, NOW(), NOW());

-- =====================================================
-- 3. 数据统计信息
-- =====================================================
-- 组织总数: 5个
--   - 总公司: 1个
--   - 分公司: 1个
--   - 子公司: 1个
--   - 技术中心: 1个
--   - 办事处: 1个
--
-- 部门总数: 33个
--   - 集团总部部门: 13个 (3级结构)
--   - 北京分公司部门: 6个 (2级结构)
--   - 上海子公司部门: 6个 (2级结构)
--   - 深圳技术中心部门: 5个 (2级结构)
--   - 成都办事处部门: 3个 (2级结构)
--
-- 层级说明:
--   orgLevel: 组织层级 (1=根节点, 2=二级组织)
--   deptLevel: 部门层级 (1=一级部门, 2=二级部门, 3=三级部门)
--   ancestors: 祖级列表，逗号分隔的ID序列
--   deptPath: 部门路径，用斜杠分隔的编码路径
--
-- 部门类型 (dept_type):
--   1 - 公司/法人实体
--   2 - 部门
--   3 - 小组/团队
--
-- 组织状态 (org_status):
--   1 - 正常
--   2 - 停用
--   3 - 注销
--   4 - 筹备中
--
-- 部门状态 (status):
--   1 - 正常
--   2 - 停用
-- =====================================================

-- =====================================================
-- 4. 验证查询语句
-- =====================================================

-- 查询所有组织
-- SELECT id, org_code, org_name, org_type, org_level, parent_id, ancestors FROM sys_organization ORDER BY order_num;

-- 查询所有部门
-- SELECT id, org_id, dept_code, dept_name, parent_id, ancestors, dept_level FROM sys_department ORDER BY org_id, order_num;

-- 查询集团总部部门树
-- SELECT id, dept_code, dept_name, parent_id, ancestors, dept_level FROM sys_department WHERE org_id = 1 ORDER BY order_num;

-- 统计每个组织的部门数量
-- SELECT o.org_name, COUNT(d.id) as dept_count FROM sys_organization o LEFT JOIN sys_department d ON o.id = d.org_id GROUP BY o.id, o.org_name ORDER BY o.order_num;

-- =====================================================
-- 说明：
-- 1. 所有ID从1开始自增，避免冲突
-- 2. parent_id = 0 表示根节点
-- 3. ancestors记录了从根到当前节点的路径
-- 4. dept_path使用编码表示的路径
-- 5. order_num控制同级节点的排序
-- 6. leader_id设置为NULL，需要后续关联用户表
-- 7. 所有时间戳使用NOW()函数
-- =====================================================
