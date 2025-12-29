package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.common.Result;
import com.xudis.iam.dto.BatchRoleAssignDTO;
import com.xudis.iam.dto.UpdateRoleValidityDTO;
import com.xudis.iam.dto.UserRoleAssignDTO;
import com.xudis.iam.entity.Role;
import com.xudis.iam.entity.User;
import com.xudis.iam.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户角色关联控制器
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@RestController
@RequestMapping("/user-role")
@Tag(name = "用户角色关联管理", description = "用户角色关联管理API")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 给用户分配角色
     */
    @PostMapping("/assign")
    @Operation(summary = "给用户分配角色", description = "给指定用户分配角色")
    public Result<Boolean> assignRole(@Valid @RequestBody UserRoleAssignDTO dto) {
        boolean result = userRoleService.assignRole(dto);
        return result ? Result.success(result) : Result.error("分配角色失败");
    }

    /**
     * 批量分配角色给多个用户
     */
    @PostMapping("/batch-assign")
    @Operation(summary = "批量分配角色", description = "给多个用户批量分配相同角色")
    public Result<Boolean> batchAssignRole(@Valid @RequestBody BatchRoleAssignDTO dto) {
        boolean result = userRoleService.batchAssignRole(dto);
        return result ? Result.success(result) : Result.error("批量分配角色失败");
    }

    /**
     * 撤销用户角色
     */
    @DeleteMapping("/revoke")
    @Operation(summary = "撤销用户角色", description = "撤销用户的指定角色")
    public Result<Boolean> revokeRole(
            @RequestParam Long userId,
            @RequestParam Long roleId,
            @RequestParam(required = false) Long departmentId) {
        boolean result = userRoleService.revokeRole(userId, roleId, departmentId);
        return result ? Result.success(result) : Result.error("撤销角色失败");
    }

    /**
     * 查询用户的所有角色
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "查询用户的所有角色", description = "查询指定用户拥有的所有角色")
    public Result<List<Role>> getUserRoles(@PathVariable Long userId) {
        List<Role> roles = userRoleService.getUserRoles(userId);
        return Result.success(roles);
    }

    /**
     * 查询角色下的所有用户（分页）
     */
    @GetMapping("/role/{roleId}")
    @Operation(summary = "查询角色下的所有用户", description = "分页查询拥有指定角色的用户列表")
    public Result<Page<User>> getRoleUsers(
            @PathVariable Long roleId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<User> page = new Page<>(current, size);
        Page<User> result = userRoleService.getRoleUsers(page, roleId);
        return Result.success(result);
    }

    /**
     * 查询用户的生效角色
     */
    @GetMapping("/effective")
    @Operation(summary = "查询用户的生效角色", description = "查询用户当前生效的角色（考虑时间范围和状态）")
    public Result<List<Role>> getEffectiveRoles(@RequestParam Long userId) {
        List<Role> roles = userRoleService.getEffectiveRoles(userId);
        return Result.success(roles);
    }

    /**
     * 更新角色有效期
     */
    @PutMapping("/update-validity")
    @Operation(summary = "更新角色有效期", description = "更新用户角色的生效时间和失效时间")
    public Result<Boolean> updateValidity(@Valid @RequestBody UpdateRoleValidityDTO dto) {
        boolean result = userRoleService.updateValidity(dto);
        return result ? Result.success(result) : Result.error("更新有效期失败");
    }
}
