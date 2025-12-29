# GitHub Copilot Instructions for IAM Server

## Project Overview

This is an Identity and Access Management (IAM) Server built with Spring Boot 3.5.8, JDK 17, and MyBatis-Plus 3.5.10. The project provides a comprehensive IAM solution with user, role, permission, department, and organization management capabilities.

## Technology Stack

- **Spring Boot**: 3.5.8
- **JDK**: 17 (required)
- **MyBatis-Plus**: 3.5.10
- **MySQL**: 8.0+
- **Lombok**: For reducing boilerplate code
- **Hutool**: 5.8.31 (Java utility library)
- **Knife4j**: For API documentation
- **Redis**: For caching

## Code Style and Conventions

### General Guidelines

1. **Language**: Use Chinese for comments, JavaDocs, and log messages to maintain consistency with the existing codebase
2. **Author Tag**: Always include `@author MaxYun` in JavaDoc for new classes
3. **Date Format**: Use `@since 2025/12/29` format (YYYY/MM/DD) in JavaDoc
4. **Encoding**: Always use UTF-8 encoding

### Java Coding Standards

1. **Naming Conventions**:
   - Classes: PascalCase (e.g., `UserService`, `BaseEntity`)
   - Methods: camelCase (e.g., `getById`, `saveBatch`)
   - Variables: camelCase (e.g., `userId`, `userName`)
   - Constants: UPPER_SNAKE_CASE (e.g., `DEFAULT_PAGE_SIZE`)
   - Database tables: snake_case with `sys_` prefix (e.g., `sys_user`, `sys_role`)

2. **Lombok Usage**:
   - Use `@Data` for entity classes with getters/setters
   - Use `@AllArgsConstructor` and `@NoArgsConstructor` where appropriate
   - Use `@EqualsAndHashCode(callSuper = true)` for entities extending `BaseEntity`
   - Use `@Slf4j` for logging

3. **Entity Classes**:
   - All domain entities should extend `BaseEntity` for standard fields
   - Use `@TableName` annotation to specify database table name
   - Use `@TableId(type = IdType.AUTO)` for auto-increment primary keys
   - Use `@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")` for datetime fields
   - Use `@JsonFormat(pattern = "yyyy-MM-dd")` for date fields
   - Set `autoResultMap = true` in `@TableName` when using `JacksonTypeHandler`

4. **MyBatis-Plus Features**:
   - Leverage automatic CRUD operations from `BaseMapper`
   - Use `@TableField(fill = FieldFill.INSERT)` for creation timestamps
   - Use `@TableField(fill = FieldFill.INSERT_UPDATE)` for update timestamps
   - Use `@TableLogic` for soft delete fields (isDeleted)
   - Use `@Version` for optimistic locking
   - Use `QueryWrapper` or `LambdaQueryWrapper` for dynamic queries
   - Use `Page<T>` for pagination

5. **Service Layer**:
   - Service interfaces should extend `IService<Entity>`
   - Service implementations should extend `ServiceImpl<Mapper, Entity>`
   - Use `@Service` annotation
   - Keep business logic in service layer, not in controllers

6. **Controller Layer**:
   - Use `@RestController` annotation
   - Use `@RequestMapping` for base path (e.g., `/api/user`)
   - All endpoints should return `Result<T>` wrapper
   - Use appropriate HTTP methods: GET, POST, PUT, DELETE
   - Use `@Valid` for request body validation
   - Parameter names: use `current` and `size` for pagination

### Response Format

All API responses must use the unified `Result<T>` format:

```java
// Success
return Result.success(data);
return Result.success("操作成功", data);

// Error
return Result.error("错误信息");
return Result.error(500, "错误信息");
```

Response structure:
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {},
  "success": true
}
```

### Exception Handling

- Use `BusinessException` for business logic errors
- Global exception handling is configured in `GlobalExceptionHandler`
- Don't catch exceptions in controllers unless specific handling is needed

### Database and Persistence

1. **Table Naming**: All tables use `sys_` prefix (e.g., `sys_user`, `sys_role`)
2. **Field Naming**: Use snake_case in database, camelCase in Java
3. **Soft Delete**: Use `isDeleted` field (0 = active, 1 = deleted)
4. **Timestamps**: `createdAt`, `updatedAt`, `deletedAt` are automatically managed
5. **Optimistic Locking**: Use `version` field for concurrent updates

### API Design

1. **Base Path**: All APIs use `/api` prefix
2. **RESTful Endpoints**:
   - `GET /api/{resource}/page` - Paginated list
   - `GET /api/{resource}/{id}` - Get by ID
   - `GET /api/{resource}/code/{code}` - Get by code
   - `POST /api/{resource}` - Create
   - `PUT /api/{resource}` - Update
   - `DELETE /api/{resource}/{id}` - Delete
   - `GET /api/{resource}/tree` - Tree structure (for hierarchical data)

3. **Query Parameters**:
   - Pagination: `current` (page number), `size` (page size)
   - Filtering: Use specific field names (e.g., `username`, `realName`)

### Package Structure

```
com.xudis.iam/
├── common/         # Common utilities and response wrappers
├── config/         # Configuration classes
├── controller/     # REST controllers
├── entity/         # Domain entities
├── mapper/         # MyBatis-Plus mappers
├── service/        # Service interfaces and implementations
│   └── impl/       # Service implementations
├── dto/            # Data Transfer Objects
└── vo/             # View Objects
```

### Build and Test

1. **Build**: `mvn clean package -DskipTests`
2. **Run**: `java -jar target/iam-server-1.0-SNAPSHOT.jar`
3. **Database**: Ensure MySQL is running and `iam_db` database exists
4. **Configuration**: Update `src/main/resources/application.yml` for local setup

### Important Notes

1. **Application Entry**: Main class is `com.xudis.Main` with `@MapperScan("com.xudis.iam.mapper")`
2. **API Documentation**: Knife4j is configured, access at `/doc.html`
3. **Context Path**: `/api`
4. **Server Port**: 8080
5. **Time Zone**: Asia/Shanghai

## When Adding New Features

1. **Entity**: Create/modify entity in `entity/` package extending `BaseEntity`
2. **Mapper**: Create mapper interface extending `BaseMapper<Entity>`
3. **Service**: Create service interface extending `IService<Entity>` and implementation extending `ServiceImpl`
4. **Controller**: Create controller with proper REST endpoints returning `Result<T>`
5. **DTOs/VOs**: Create separate DTOs for input and VOs for output if needed
6. **Validation**: Use Jakarta Validation annotations (`@NotNull`, `@NotBlank`, etc.)

## Security Considerations

1. Never log sensitive information (passwords, tokens)
2. Always hash passwords before storage
3. Use parameterized queries (MyBatis-Plus handles this)
4. Validate all user inputs
5. Follow principle of least privilege for permissions

## Code Review Checklist

- [ ] Chinese comments for all public methods
- [ ] Proper JavaDoc with @author and @since
- [ ] Entity extends BaseEntity when appropriate
- [ ] Controller returns Result<T>
- [ ] Proper exception handling
- [ ] Input validation on DTOs
- [ ] Consistent naming conventions
- [ ] No hardcoded values, use constants
- [ ] Proper use of Lombok annotations
