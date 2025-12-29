# API测试示例

本文档提供了IAM Server REST API的测试示例

## 前置条件

1. 应用已启动: `http://localhost:8080`
2. 数据库已创建并执行了DDL脚本

## 使用curl测试

### 1. 用户管理API

#### 查询用户列表（分页）
```bash
curl -X GET "http://localhost:8080/api/user/page?current=1&size=10"
```

#### 根据ID查询用户
```bash
curl -X GET "http://localhost:8080/api/user/1"
```

#### 根据用户名查询用户
```bash
curl -X GET "http://localhost:8080/api/user/username/admin"
```

#### 新增用户
```bash
curl -X POST "http://localhost:8080/api/user" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "passwordHash": "hashed_password",
    "passwordSalt": "salt123",
    "realName": "测试用户",
    "email": "test@example.com",
    "mobile": "13800138000",
    "accountStatus": 1
  }'
```

#### 更新用户
```bash
curl -X PUT "http://localhost:8080/api/user" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "username": "testuser",
    "realName": "测试用户更新",
    "email": "updated@example.com"
  }'
```

#### 删除用户
```bash
curl -X DELETE "http://localhost:8080/api/user/1"
```

### 2. 角色管理API

#### 查询角色列表（分页）
```bash
curl -X GET "http://localhost:8080/api/role/page?current=1&size=10"
```

#### 根据ID查询角色
```bash
curl -X GET "http://localhost:8080/api/role/1"
```

#### 根据角色编码查询角色
```bash
curl -X GET "http://localhost:8080/api/role/code/admin"
```

#### 新增角色
```bash
curl -X POST "http://localhost:8080/api/role" \
  -H "Content-Type: application/json" \
  -d '{
    "roleCode": "test_role",
    "roleName": "测试角色",
    "roleType": 1,
    "roleLevel": 99,
    "dataScope": 1,
    "status": 1,
    "isPermanent": 1,
    "isSystem": 0,
    "isDefault": 0,
    "requireMfa": 0,
    "description": "这是一个测试角色"
  }'
```

#### 更新角色
```bash
curl -X PUT "http://localhost:8080/api/role" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "roleCode": "test_role",
    "roleName": "测试角色更新",
    "description": "角色描述已更新"
  }'
```

#### 删除角色
```bash
curl -X DELETE "http://localhost:8080/api/role/1"
```

### 3. 权限管理API

#### 查询权限树
```bash
curl -X GET "http://localhost:8080/api/permission/tree"
```

#### 查询权限列表（分页）
```bash
curl -X GET "http://localhost:8080/api/permission/page?current=1&size=10"
```

#### 根据ID查询权限
```bash
curl -X GET "http://localhost:8080/api/permission/1"
```

#### 根据权限编码查询权限
```bash
curl -X GET "http://localhost:8080/api/permission/code/user:create"
```

#### 新增权限
```bash
curl -X POST "http://localhost:8080/api/permission" \
  -H "Content-Type: application/json" \
  -d '{
    "parentId": 0,
    "permType": 3,
    "permCode": "user:create",
    "permName": "创建用户",
    "permLevel": 1,
    "apiPath": "/api/user",
    "apiMethod": "POST",
    "status": 1,
    "orderNum": 1,
    "remark": "创建用户权限"
  }'
```

#### 更新权限
```bash
curl -X PUT "http://localhost:8080/api/permission" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "permCode": "user:create",
    "permName": "创建用户（已更新）",
    "remark": "权限描述已更新"
  }'
```

#### 删除权限
```bash
curl -X DELETE "http://localhost:8080/api/permission/1"
```

### 4. 部门管理API

#### 查询部门树
```bash
curl -X GET "http://localhost:8080/api/department/tree"
```

#### 查询指定组织的部门树
```bash
curl -X GET "http://localhost:8080/api/department/tree?orgId=1"
```

#### 查询部门列表（分页）
```bash
curl -X GET "http://localhost:8080/api/department/page?current=1&size=10"
```

#### 根据ID查询部门
```bash
curl -X GET "http://localhost:8080/api/department/1"
```

#### 根据部门编码查询部门
```bash
curl -X GET "http://localhost:8080/api/department/code/tech_dept"
```

#### 新增部门
```bash
curl -X POST "http://localhost:8080/api/department" \
  -H "Content-Type: application/json" \
  -d '{
    "orgId": 1,
    "deptCode": "tech_dept",
    "deptName": "技术部",
    "parentId": 0,
    "deptLevel": 1,
    "deptType": 2,
    "status": 1,
    "orderNum": 1,
    "description": "技术研发部门"
  }'
```

#### 更新部门
```bash
curl -X PUT "http://localhost:8080/api/department" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "deptCode": "tech_dept",
    "deptName": "技术研发部",
    "description": "技术研发部门（已更新）"
  }'
```

#### 删除部门
```bash
curl -X DELETE "http://localhost:8080/api/department/1"
```

### 5. 组织管理API

#### 查询组织树
```bash
curl -X GET "http://localhost:8080/api/organization/tree"
```

#### 查询组织列表（分页）
```bash
curl -X GET "http://localhost:8080/api/organization/page?current=1&size=10"
```

#### 根据ID查询组织
```bash
curl -X GET "http://localhost:8080/api/organization/1"
```

#### 根据组织编码查询组织
```bash
curl -X GET "http://localhost:8080/api/organization/code/HQ"
```

#### 新增组织
```bash
curl -X POST "http://localhost:8080/api/organization" \
  -H "Content-Type: application/json" \
  -d '{
    "orgCode": "HQ",
    "orgName": "总公司",
    "orgShortName": "总公司",
    "orgType": 1,
    "orgLevel": 1,
    "parentId": 0,
    "orgStatus": 1,
    "isIndependent": 1,
    "isVirtual": 0,
    "contactPerson": "张三",
    "contactPhone": "010-12345678",
    "contactEmail": "contact@company.com",
    "address": "北京市朝阳区",
    "timezone": "Asia/Shanghai",
    "languageCode": "zh-CN"
  }'
```

#### 更新组织
```bash
curl -X PUT "http://localhost:8080/api/organization" \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "orgCode": "HQ",
    "orgName": "总公司（集团）",
    "description": "组织信息已更新"
  }'
```

#### 删除组织
```bash
curl -X DELETE "http://localhost:8080/api/organization/1"
```

## 使用Postman测试

1. 导入以下环境变量：
   - `BASE_URL`: `http://localhost:8080/api`

2. 创建请求集合，每个API对应一个请求

3. 对于POST和PUT请求，设置Header：
   ```
   Content-Type: application/json
   ```

4. 在Body中选择raw和JSON格式，粘贴对应的JSON数据

## 响应格式

所有API返回统一的JSON格式：

### 成功响应
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    // 返回的数据对象
  },
  "success": true
}
```

### 分页响应
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "current": 1,
    "size": 10,
    "total": 100,
    "pages": 10,
    "records": [
      // 数据列表
    ]
  },
  "success": true
}
```

### 错误响应
```json
{
  "code": 500,
  "message": "错误信息",
  "data": null,
  "success": false
}
```

## 注意事项

1. 所有时间字段格式为: `yyyy-MM-dd HH:mm:ss`
2. 所有日期字段格式为: `yyyy-MM-dd`
3. ID字段类型为Long (64位整数)
4. 状态字段通常: 1-启用/正常, 2-停用/禁用
5. 逻辑删除字段: isDeleted (0-未删除, 1-已删除)
6. 乐观锁字段: version (整数，每次更新自动+1)
