package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.annotation.OperationLog;
import com.xudis.iam.common.Result;
import com.xudis.iam.dto.CreateUserRequest;
import com.xudis.iam.dto.UpdateUserRequest;
import com.xudis.iam.entity.User;
import com.xudis.iam.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {

    private final UserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户列表")
    @OperationLog(module = "用户管理", operationType = "QUERY", description = "分页查询用户列表")
    public Result<Page<User>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String realName) {
        Page<User> page = new Page<>(current, size);
        Page<User> result = userService.pageUsers(page, username, realName);
        return Result.success(result);
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户")
    public Result<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/username/{username}")
    @Operation(summary = "根据用户名查询用户")
    public Result<User> getByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return Result.success(user);
    }

    /**
     * 新增用户
     */
    @PostMapping
    @Operation(summary = "新增用户")
    @OperationLog(module = "用户管理", operationType = "CREATE", description = "新增用户", saveResponse = true)
    public Result<User> save(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return Result.success(user);
    }

    /**
     * 更新用户
     */
    @PutMapping
    @Operation(summary = "更新用户")
    @OperationLog(module = "用户管理", operationType = "UPDATE", description = "更新用户信息")
    public Result<Boolean> update(@Valid @RequestBody UpdateUserRequest request) {
        boolean result = userService.updateUser(request);
        return result ? Result.success(result) : Result.error("更新用户失败");
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    @OperationLog(module = "用户管理", operationType = "DELETE", description = "删除用户")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = userService.removeById(id);
        return result ? Result.success(result) : Result.error("删除用户失败");
    }
}
