package com.xudis.iam.controller;

import com.xudis.iam.annotation.LogOperation;
import com.xudis.iam.common.Result;
import com.xudis.iam.dto.BatchPermissionAssignDTO;
import com.xudis.iam.dto.RolePermissionAssignDTO;
import com.xudis.iam.entity.Permission;
import com.xudis.iam.entity.Role;
import com.xudis.iam.service.RolePermissionService;
import com.xudis.iam.vo.RolePermissionTreeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色权限关联控制器
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@RestController
@RequestMapping("/role-permission")
@Tag(name = "角色权限关联管理", description = "角色权限关联管理API")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    /**
     * 给角色分配权限
     */
    @PostMapping("/assign")
    @Operation(summary = "给角色分配权限", description = "给指定角色分配权限")
    @LogOperation(module = "角色权限管理", operationType = "ASSIGN", description = "给角色分配权限")
    public Result<Boolean> assignPermission(@Valid @RequestBody RolePermissionAssignDTO dto) {
        boolean result = rolePermissionService.assignPermission(dto);
        return result ? Result.success(result) : Result.error("分配权限失败");
    }

    /**
     * 批量分配权限
     */
    @PostMapping("/batch-assign")
    @Operation(summary = "批量分配权限", description = "给角色批量分配多个权限")
    @LogOperation(module = "角色权限管理", operationType = "ASSIGN", description = "批量分配权限")
    public Result<Boolean> batchAssignPermission(@Valid @RequestBody BatchPermissionAssignDTO dto) {
        boolean result = rolePermissionService.batchAssignPermission(dto);
        return result ? Result.success(result) : Result.error("批量分配权限失败");
    }

    /**
     * 撤销角色权限
     */
    @DeleteMapping("/revoke")
    @Operation(summary = "撤销角色权限", description = "撤销角色的指定权限")
    @LogOperation(module = "角色权限管理", operationType = "REVOKE", description = "撤销角色权限")
    public Result<Boolean> revokePermission(
            @RequestParam Long roleId,
            @RequestParam Long permissionId) {
        boolean result = rolePermissionService.revokePermission(roleId, permissionId);
        return result ? Result.success(result) : Result.error("撤销权限失败");
    }

    /**
     * 查询角色的所有权限
     */
    @GetMapping("/role/{roleId}")
    @Operation(summary = "查询角色的所有权限", description = "查询指定角色拥有的所有权限")
    @LogOperation(module = "角色权限管理", operationType = "QUERY", description = "查询角色的所有权限")
    public Result<List<Permission>> getRolePermissions(@PathVariable Long roleId) {
        List<Permission> permissions = rolePermissionService.getRolePermissions(roleId);
        return Result.success(permissions);
    }

    /**
     * 查询权限被哪些角色使用
     */
    @GetMapping("/permission/{permId}")
    @Operation(summary = "查询权限被哪些角色使用", description = "查询拥有指定权限的角色列表")
    @LogOperation(module = "角色权限管理", operationType = "QUERY", description = "查询权限被哪些角色使用")
    public Result<List<Role>> getPermissionRoles(@PathVariable Long permId) {
        List<Role> roles = rolePermissionService.getPermissionRoles(permId);
        return Result.success(roles);
    }

    /**
     * 获取角色权限树
     */
    @GetMapping("/tree/{roleId}")
    @Operation(summary = "获取角色权限树", description = "获取权限树形结构，标记已选中的权限")
    @LogOperation(module = "角色权限管理", operationType = "QUERY", description = "获取角色权限树")
    public Result<List<RolePermissionTreeVO>> getPermissionTree(@PathVariable Long roleId) {
        List<RolePermissionTreeVO> tree = rolePermissionService.getPermissionTree(roleId);
        return Result.success(tree);
    }
}
