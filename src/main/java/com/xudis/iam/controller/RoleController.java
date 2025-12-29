package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.common.Result;
import com.xudis.iam.entity.Role;
import com.xudis.iam.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleController {

    private final RoleService roleService;

    /**
     * 分页查询角色列表
     */
    @GetMapping("/page")
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
    public Result<Role> getById(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.success(role);
    }

    /**
     * 根据角色编码查询角色
     */
    @GetMapping("/code/{roleCode}")
    public Result<Role> getByRoleCode(@PathVariable String roleCode) {
        Role role = roleService.getByRoleCode(roleCode);
        return Result.success(role);
    }

    /**
     * 新增角色
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody Role role) {
        boolean result = roleService.save(role);
        return result ? Result.success(result) : Result.error("新增角色失败");
    }

    /**
     * 更新角色
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody Role role) {
        boolean result = roleService.updateById(role);
        return result ? Result.success(result) : Result.error("更新角色失败");
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = roleService.removeById(id);
        return result ? Result.success(result) : Result.error("删除角色失败");
    }
}
