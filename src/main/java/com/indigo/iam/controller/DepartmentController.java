package com.indigo.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.indigo.iam.annotation.LogOperation;
import com.indigo.iam.common.Result;
import com.indigo.iam.dto.CreateDepartmentRequest;
import com.indigo.iam.dto.UpdateDepartmentRequest;
import com.indigo.iam.entity.Department;
import com.indigo.iam.service.DepartmentService;
import com.indigo.iam.vo.DepartmentTreeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@Tag(name = "部门管理", description = "部门相关接口")
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * 查询部门树
     */
    @GetMapping("/tree")
    @Operation(summary = "查询部门树")
    @LogOperation(module = "部门管理", operationType = "QUERY", description = "查询部门树")
    public Result<List<DepartmentTreeVO>> tree(@RequestParam(required = false) Long orgId) {
        List<DepartmentTreeVO> tree = departmentService.getDepartmentTreeVO(orgId);
        return Result.success(tree);
    }

    /**
     * 分页查询部门列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询部门列表")
    @LogOperation(module = "部门管理", operationType = "QUERY", description = "分页查询部门列表")
    public Result<Page<Department>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String deptCode,
            @RequestParam(required = false) String deptName) {
        Page<Department> page = new Page<>(current, size);
        Page<Department> result = departmentService.pageDepartments(page, deptCode, deptName);
        return Result.success(result);
    }

    /**
     * 根据ID查询部门
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询部门")
    public Result<Department> getById(@PathVariable Long id) {
        Department department = departmentService.getById(id);
        return Result.success(department);
    }

    /**
     * 根据部门编码查询部门
     */
    @GetMapping("/code/{deptCode}")
    @Operation(summary = "根据部门编码查询部门")
    public Result<Department> getByDeptCode(@PathVariable String deptCode) {
        Department department = departmentService.getByDeptCode(deptCode);
        return Result.success(department);
    }

    /**
     * 新增部门
     */
    @PostMapping
    @Operation(summary = "新增部门")
    @LogOperation(module = "部门管理", operationType = "CREATE", description = "新增部门")
    public Result<Department> save(@Valid @RequestBody CreateDepartmentRequest request) {
        Department department = departmentService.createDepartment(request);
        return Result.success(department);
    }

    /**
     * 更新部门
     */
    @PutMapping
    @Operation(summary = "更新部门")
    @LogOperation(module = "部门管理", operationType = "UPDATE", description = "更新部门")
    public Result<Boolean> update(@Valid @RequestBody UpdateDepartmentRequest request) {
        boolean result = departmentService.updateDepartment(request);
        return result ? Result.success(result) : Result.error("更新部门失败");
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除部门")
    @LogOperation(module = "部门管理", operationType = "DELETE", description = "删除部门")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = departmentService.removeById(id);
        return result ? Result.success(result) : Result.error("删除部门失败");
    }
}
