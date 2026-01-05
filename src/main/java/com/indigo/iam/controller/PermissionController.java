package com.indigo.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.indigo.iam.annotation.LogOperation;
import com.indigo.iam.common.Result;
import com.indigo.iam.dto.CreatePermissionRequest;
import com.indigo.iam.dto.UpdatePermissionRequest;
import com.indigo.iam.entity.Permission;
import com.indigo.iam.service.PermissionService;
import com.indigo.iam.vo.PermissionTreeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限控制器
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@Tag(name = "权限管理", description = "权限相关接口")
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 查询权限树
     */
    @GetMapping("/tree")
    @Operation(summary = "查询权限树")
    @LogOperation(module = "权限管理", operationType = "QUERY", description = "查询权限树")
    public Result<List<PermissionTreeVO>> tree() {
        List<PermissionTreeVO> tree = permissionService.getPermissionTreeVO();
        return Result.success(tree);
    }

    /**
     * 分页查询权限列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询权限列表")
    @LogOperation(module = "权限管理", operationType = "QUERY", description = "分页查询权限列表")
    public Result<Page<Permission>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String permCode,
            @RequestParam(required = false) String permName) {
        Page<Permission> page = new Page<>(current, size);
        Page<Permission> result = permissionService.pagePermissions(page, permCode, permName);
        return Result.success(result);
    }

    /**
     * 根据ID查询权限
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询权限")
    public Result<Permission> getById(@PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        return Result.success(permission);
    }

    /**
     * 根据权限编码查询权限
     */
    @GetMapping("/code/{permCode}")
    @Operation(summary = "根据权限编码查询权限")
    public Result<Permission> getByPermCode(@PathVariable String permCode) {
        Permission permission = permissionService.getByPermCode(permCode);
        return Result.success(permission);
    }

    /**
     * 新增权限
     */
    @PostMapping
    @Operation(summary = "新增权限")
    @LogOperation(module = "权限管理", operationType = "CREATE", description = "新增权限")
    public Result<Permission> save(@Valid @RequestBody CreatePermissionRequest request) {
        Permission permission = permissionService.createPermission(request);
        return Result.success(permission);
    }

    /**
     * 更新权限
     */
    @PutMapping
    @Operation(summary = "更新权限")
    @LogOperation(module = "权限管理", operationType = "UPDATE", description = "更新权限")
    public Result<Boolean> update(@Valid @RequestBody UpdatePermissionRequest request) {
        boolean result = permissionService.updatePermission(request);
        return result ? Result.success(result) : Result.error("更新权限失败");
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除权限")
    @LogOperation(module = "权限管理", operationType = "DELETE", description = "删除权限")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = permissionService.removeById(id);
        return result ? Result.success(result) : Result.error("删除权限失败");
    }
}
