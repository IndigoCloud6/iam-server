package com.indigo.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.indigo.iam.annotation.LogOperation;
import com.indigo.iam.common.Result;
import com.indigo.iam.dto.CreateRoleRequest;
import com.indigo.iam.dto.UpdateRoleRequest;
import com.indigo.iam.entity.Role;
import com.indigo.iam.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 角色控制器
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@Tag(name = "角色管理", description = "角色相关接口")
public class RoleController {

    private final RoleService roleService;

    /**
     * 分页查询角色列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询角色列表")
    @LogOperation(module = "角色管理", operationType = "QUERY", description = "分页查询角色列表")
    public Result<Page<Role>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String roleCode,
            @RequestParam(required = false) String roleName) {
        Page<Role> page = new Page<>(current, size);
        Page<Role> result = roleService.pageRoles(page, roleCode, roleName);
        return Result.success(result);
    }

    /**
     * 根据ID查询角色
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询角色")
    public Result<Role> getById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.success(role);
    }

    /**
     * 根据角色编码查询角色
     */
    @GetMapping("/code/{roleCode}")
    @Operation(summary = "根据角色编码查询角色")
    public Result<Role> getByRoleCode(@PathVariable String roleCode) {
        Role role = roleService.getByRoleCode(roleCode);
        return Result.success(role);
    }

    /**
     * 新增角色
     */
    @PostMapping
    @Operation(summary = "新增角色")
    @LogOperation(module = "角色管理", operationType = "CREATE", description = "新增角色")
    public Result<Role> save(@Valid @RequestBody CreateRoleRequest request) {
        Role role = roleService.createRole(request);
        return Result.success(role);
    }

    /**
     * 更新角色
     */
    @PutMapping
    @Operation(summary = "更新角色")
    @LogOperation(module = "角色管理", operationType = "UPDATE", description = "更新角色")
    public Result<Boolean> update(@Valid @RequestBody UpdateRoleRequest request) {
        boolean result = roleService.updateRole(request);
        return result ? Result.success(result) : Result.error("更新角色失败");
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    @LogOperation(module = "角色管理", operationType = "DELETE", description = "删除角色")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = roleService.removeById(id);
        return result ? Result.success(result) : Result.error("删除角色失败");
    }
}
