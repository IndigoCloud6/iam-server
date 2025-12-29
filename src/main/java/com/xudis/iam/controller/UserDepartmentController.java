package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.common.Result;
import com.xudis.iam.dto.UserDepartmentAssignDTO;
import com.xudis.iam.dto.UserDepartmentTransferDTO;
import com.xudis.iam.entity.Department;
import com.xudis.iam.entity.User;
import com.xudis.iam.service.UserDepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户部门关联控制器
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@RestController
@RequestMapping("/user-department")
@Tag(name = "用户部门关联管理", description = "用户部门关联管理API")
@RequiredArgsConstructor
public class UserDepartmentController {

    private final UserDepartmentService userDepartmentService;

    /**
     * 分配用户到部门
     */
    @PostMapping("/assign")
    @Operation(summary = "分配用户到部门", description = "将用户分配到指定部门")
    public Result<Boolean> assignDepartment(@Valid @RequestBody UserDepartmentAssignDTO dto) {
        boolean result = userDepartmentService.assignDepartment(dto);
        return result ? Result.success(result) : Result.error("分配部门失败");
    }

    /**
     * 设置用户主部门
     */
    @PostMapping("/set-primary")
    @Operation(summary = "设置用户主部门", description = "设置用户的主部门（同一用户只能有一个主部门）")
    public Result<Boolean> setPrimaryDepartment(
            @RequestParam Long userId,
            @RequestParam Long departmentId) {
        boolean result = userDepartmentService.setPrimaryDepartment(userId, departmentId);
        return result ? Result.success(result) : Result.error("设置主部门失败");
    }

    /**
     * 从部门移除用户
     */
    @DeleteMapping("/remove")
    @Operation(summary = "从部门移除用户", description = "将用户从指定部门移除")
    public Result<Boolean> removeDepartment(
            @RequestParam Long userId,
            @RequestParam Long departmentId) {
        boolean result = userDepartmentService.removeDepartment(userId, departmentId);
        return result ? Result.success(result) : Result.error("移除部门失败");
    }

    /**
     * 查询用户所在的所有部门
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "查询用户所在的所有部门", description = "查询用户关联的所有部门")
    public Result<List<Department>> getUserDepartments(@PathVariable Long userId) {
        List<Department> departments = userDepartmentService.getUserDepartments(userId);
        return Result.success(departments);
    }

    /**
     * 查询部门下的所有用户（分页）
     */
    @GetMapping("/department/{deptId}")
    @Operation(summary = "查询部门下的所有用户", description = "分页查询部门下的所有用户")
    public Result<Page<User>> getDepartmentUsers(
            @PathVariable Long deptId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<User> page = new Page<>(current, size);
        Page<User> result = userDepartmentService.getDepartmentUsers(page, deptId);
        return Result.success(result);
    }

    /**
     * 用户部门调动
     */
    @PutMapping("/transfer")
    @Operation(summary = "用户部门调动", description = "将用户从一个部门调动到另一个部门")
    public Result<Boolean> transferDepartment(@Valid @RequestBody UserDepartmentTransferDTO dto) {
        boolean result = userDepartmentService.transferDepartment(dto);
        return result ? Result.success(result) : Result.error("部门调动失败");
    }
}
