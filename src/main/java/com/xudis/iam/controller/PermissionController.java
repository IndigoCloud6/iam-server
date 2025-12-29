package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.common.Result;
import com.xudis.iam.entity.Permission;
import com.xudis.iam.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * 查询权限树
     */
    @GetMapping("/tree")
    public Result<List<Permission>> tree() {
        List<Permission> list = permissionService.getPermissionTree();
        return Result.success(list);
    }

    /**
     * 分页查询权限列表
     */
    @GetMapping("/page")
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
    public Result<Permission> getById(@PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        return Result.success(permission);
    }

    /**
     * 根据权限编码查询权限
     */
    @GetMapping("/code/{permCode}")
    public Result<Permission> getByPermCode(@PathVariable String permCode) {
        Permission permission = permissionService.getByPermCode(permCode);
        return Result.success(permission);
    }

    /**
     * 新增权限
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody Permission permission) {
        boolean result = permissionService.save(permission);
        return result ? Result.success(result) : Result.error("新增权限失败");
    }

    /**
     * 更新权限
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody Permission permission) {
        boolean result = permissionService.updateById(permission);
        return result ? Result.success(result) : Result.error("更新权限失败");
    }

    /**
     * 删除权限
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = permissionService.removeById(id);
        return result ? Result.success(result) : Result.error("删除权限失败");
    }
}
