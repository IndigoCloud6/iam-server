# IAM Server

Identity and Access Management (IAM) Server - 基于Spring Boot 3.5.8 + JDK 17 + MyBatis-Plus 3.5.10实现的完整IAM服务

## 技术栈

- **Spring Boot**: 3.5.8
- **JDK**: 17
- **MyBatis-Plus**: 3.5.10
- **MySQL**: 8.0+
- **Lombok**: 用于简化Java Bean
- **Hutool**: 5.8.31 Java工具类库

## 项目结构

```
src/main/java/com/xudis/
├── Main.java                           # Spring Boot启动类
└── iam/
    ├── common/                         # 公共组件
    │   ├── Result.java                 # 统一响应结果
    │   ├── PageResult.java             # 分页结果
    │   └── GlobalExceptionHandler.java # 全局异常处理
    ├── config/                         # 配置类
    │   └── MybatisPlusConfig.java      # MyBatis-Plus配置
    ├── controller/                     # 控制器层
    │   ├── UserController.java         # 用户管理API
    │   ├── RoleController.java         # 角色管理API
    │   ├── PermissionController.java   # 权限管理API
    │   ├── DepartmentController.java   # 部门管理API
    │   └── OrganizationController.java # 组织管理API
    ├── entity/                         # 实体类
    │   ├── BaseEntity.java            # 基础实体
    │   ├── User.java                  # 用户实体
    │   ├── Role.java                  # 角色实体
    │   ├── Permission.java            # 权限实体
    │   ├── Department.java            # 部门实体
    │   ├── Organization.java          # 组织实体
    │   ├── UserRole.java              # 用户角色关联
    │   ├── RolePermission.java        # 角色权限关联
    │   └── UserDepartment.java        # 用户部门关联
    ├── mapper/                         # MyBatis-Plus Mapper接口
    │   ├── UserMapper.java
    │   ├── RoleMapper.java
    │   ├── PermissionMapper.java
    │   ├── DepartmentMapper.java
    │   ├── OrganizationMapper.java
    │   ├── UserRoleMapper.java
    │   ├── RolePermissionMapper.java
    │   └── UserDepartmentMapper.java
    └── service/                        # 服务层
        ├── UserService.java
        ├── RoleService.java
        ├── PermissionService.java
        ├── DepartmentService.java
        ├── OrganizationService.java
        └── impl/                       # 服务实现
            ├── UserServiceImpl.java
            ├── RoleServiceImpl.java
            ├── PermissionServiceImpl.java
            ├── DepartmentServiceImpl.java
            └── OrganizationServiceImpl.java
```

## 数据库表

项目包含以下核心数据库表（完整DDL见 `src/main/resources/db/ddl.sql`）：

- `sys_user` - 用户表
- `sys_role` - 角色表
- `sys_permission` - 权限资源表
- `sys_department` - 部门表
- `sys_organization` - 组织/公司表
- `sys_user_role` - 用户角色关联表
- `sys_role_permission` - 角色权限关联表
- `sys_user_department` - 用户部门关联表
- `sys_position` - 岗位表
- `sys_user_position` - 用户岗位关联表
- `sys_user_group` - 用户组表
- `sys_group_member` - 用户组成员表
- `sys_policy` - 权限策略表
- `sys_policy_binding` - 策略绑定表
- `sys_dict` - 字典表
- `sys_dict_item` - 字典项表
- `sys_config` - 系统配置表
- `sys_login_log` - 登录日志表
- `sys_operation_log` - 操作日志表
- `sys_permission_change_log` - 权限变更日志表
- `sys_organization_admin` - 组织管理员表

## 配置说明

### 数据库配置

修改 `src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/iam_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: root
```

### 应用配置

- **端口**: 8080
- **上下文路径**: `/api`
- **时区**: Asia/Shanghai

## API接口

所有API接口前缀为 `/api`

### 用户管理 (/api/user)

- `GET /api/user/page` - 分页查询用户列表
  - 参数: `current` (当前页), `size` (每页大小), `username` (用户名), `realName` (真实姓名)
- `GET /api/user/{id}` - 根据ID查询用户
- `GET /api/user/username/{username}` - 根据用户名查询用户
- `POST /api/user` - 新增用户
- `PUT /api/user` - 更新用户
- `DELETE /api/user/{id}` - 删除用户

### 角色管理 (/api/role)

- `GET /api/role/page` - 分页查询角色列表
  - 参数: `current`, `size`, `roleCode` (角色编码), `roleName` (角色名称)
- `GET /api/role/{id}` - 根据ID查询角色
- `GET /api/role/code/{roleCode}` - 根据角色编码查询角色
- `POST /api/role` - 新增角色
- `PUT /api/role` - 更新角色
- `DELETE /api/role/{id}` - 删除角色

### 权限管理 (/api/permission)

- `GET /api/permission/tree` - 查询权限树
- `GET /api/permission/page` - 分页查询权限列表
  - 参数: `current`, `size`, `permCode` (权限编码), `permName` (权限名称)
- `GET /api/permission/{id}` - 根据ID查询权限
- `GET /api/permission/code/{permCode}` - 根据权限编码查询权限
- `POST /api/permission` - 新增权限
- `PUT /api/permission` - 更新权限
- `DELETE /api/permission/{id}` - 删除权限

### 部门管理 (/api/department)

- `GET /api/department/tree` - 查询部门树
  - 参数: `orgId` (组织ID，可选)
- `GET /api/department/page` - 分页查询部门列表
  - 参数: `current`, `size`, `deptCode` (部门编码), `deptName` (部门名称)
- `GET /api/department/{id}` - 根据ID查询部门
- `GET /api/department/code/{deptCode}` - 根据部门编码查询部门
- `POST /api/department` - 新增部门
- `PUT /api/department` - 更新部门
- `DELETE /api/department/{id}` - 删除部门

### 组织管理 (/api/organization)

- `GET /api/organization/tree` - 查询组织树
- `GET /api/organization/page` - 分页查询组织列表
  - 参数: `current`, `size`, `orgCode` (组织编码), `orgName` (组织名称)
- `GET /api/organization/{id}` - 根据ID查询组织
- `GET /api/organization/code/{orgCode}` - 根据组织编码查询组织
- `POST /api/organization` - 新增组织
- `PUT /api/organization` - 更新组织
- `DELETE /api/organization/{id}` - 删除组织

## 统一响应格式

所有API返回统一的JSON格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {},
  "success": true
}
```

## 快速开始

### 1. 创建数据库

```sql
CREATE DATABASE iam_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 执行DDL

执行 `src/main/resources/db/ddl.sql` 创建表结构

### 3. 修改配置

修改 `application.yml` 中的数据库连接信息

### 4. 编译打包

```bash
mvn clean package -DskipTests
```

### 5. 运行应用

```bash
java -jar target/iam-server-1.0-SNAPSHOT.jar
```

应用启动后访问: http://localhost:8080/api

## 开发说明

### MyBatis-Plus特性

- **自动填充**: 创建时间、更新时间、创建人、更新人自动填充
- **逻辑删除**: 使用 `isDeleted` 字段实现逻辑删除
- **乐观锁**: 使用 `version` 字段实现乐观锁
- **分页**: 内置分页插件，支持MySQL分页

### 实体类说明

- 继承 `BaseEntity` 的实体类自动包含: `createdBy`, `createdAt`, `updatedBy`, `updatedAt`, `version`, `isDeleted`, `deletedAt` 字段
- 使用Lombok注解简化getter/setter方法
- 使用MyBatis-Plus的 `@TableName` 注解指定表名
- 使用 `@JsonFormat` 注解格式化日期时间

## License

MIT License
