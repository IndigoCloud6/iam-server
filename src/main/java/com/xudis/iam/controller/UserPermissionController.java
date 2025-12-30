package com.xudis.iam.controller;

import com.xudis.iam.annotation.LogOperation;
import com.xudis.iam.common.Result;
import com.xudis.iam.dto.BatchUserPermissionAssignDTO;
import com.xudis.iam.dto.UserPermissionAssignDTO;
import com.xudis.iam.entity.Permission;
import com.xudis.iam.service.UserPermissionService;
import com.xudis.iam.vo.UserAllPermissionsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户权限关联控制器
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@RestController
@RequestMapping("/user-permission")
@RequiredArgsConstructor
@Tag(name = "用户权限关联管理", description = "用户权限直接关联管理API")
public class UserPermissionController {

    private final UserPermissionService userPermissionService;

    /**
     * 给用户分配权限
     */
    @PostMapping("/assign")
    @Operation(summary = "给用户分配权限", description = "给指定用户直接分配权限")
    @LogOperation(module = "用户权限管理", operationType = "ASSIGN", description = "给用户分配权限")
    public Result<Boolean> assignPermission(@Valid @RequestBody UserPermissionAssignDTO dto) {
        boolean result = userPermissionService.assignPermission(dto);
        return result ? Result.success(result) : Result.error("分配权限失败");
    }

    /**
     * 批量给用户分配权限
     */
    @PostMapping("/batch-assign")
    @Operation(summary = "批量分配权限", description = "给用户批量分配多个权限")
    @LogOperation(module = "用户权限管理", operationType = "ASSIGN", description = "批量分配权限")
    public Result<Boolean> batchAssignPermission(@Valid @RequestBody BatchUserPermissionAssignDTO dto) {
        boolean result = userPermissionService.batchAssignPermission(dto);
        return result ? Result.success(result) : Result.error("批量分配权限失败");
    }

    /**
     * 撤销用户权限
     */
    @DeleteMapping("/revoke")
    @Operation(summary = "撤销用户权限", description = "撤销用户的指定权限")
    @LogOperation(module = "用户权限管理", operationType = "REVOKE", description = "撤销用户权限")
    public Result<Boolean> revokePermission(
            @RequestParam Long userId,
            @RequestParam Long permissionId) {
        boolean result = userPermissionService.revokePermission(userId, permissionId);
        return result ? Result.success(result) : Result.error("撤销权限失败");
    }

    /**
     * 查询用户的直接权限列表
     */
    @GetMapping("/direct/{userId}")
    @Operation(summary = "查询用户的直接权限", description = "查询用户直接拥有的权限列表")
    @LogOperation(module = "用户权限管理", operationType = "QUERY", description = "查询用户的直接权限")
    public Result<List<Permission>> getUserDirectPermissions(@PathVariable Long userId) {
        List<Permission> permissions = userPermissionService.getUserDirectPermissions(userId);
        return Result.success(permissions);
    }

    /**
     * 查询用户的所有权限（直接权限 + 角色权限）
     */
    @GetMapping("/all/{userId}")
    @Operation(summary = "查询用户的所有权限", description = "查询用户的所有权限，包括直接权限和通过角色获得的权限")
    @LogOperation(module = "用户权限管理", operationType = "QUERY", description = "查询用户的所有权限")
    public Result<UserAllPermissionsVO> getUserAllPermissions(@PathVariable Long userId) {
        UserAllPermissionsVO allPermissions = userPermissionService.getUserAllPermissions(userId);
        return Result.success(allPermissions);
    }

    /**
     * 检查用户是否拥有指定权限
     */
    @GetMapping("/has-permission")
    @Operation(summary = "检查用户权限", description = "检查用户是否拥有指定的权限")
    public Result<Boolean> hasPermission(
            @RequestParam Long userId,
            @RequestParam String permCode) {
        boolean hasPermission = userPermissionService.hasPermission(userId, permCode);
        return Result.success(hasPermission);
    }

    /**
     * 查询拥有指定权限的用户列表
     */
    @GetMapping("/permission/{permissionId}/users")
    @Operation(summary = "查询拥有指定权限的用户", description = "查询拥有指定权限的用户ID列表")
    @LogOperation(module = "用户权限管理", operationType = "QUERY", description = "查询拥有指定权限的用户")
    public Result<List<Long>> getUserIdsByPermission(@PathVariable Long permissionId) {
        List<Long> userIds = userPermissionService.getUserIdsByPermission(permissionId);
        return Result.success(userIds);
    }
}
