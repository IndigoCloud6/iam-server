package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.common.Result;
import com.xudis.iam.entity.Department;
import com.xudis.iam.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询部门树
     */
    @GetMapping("/tree")
    public Result<List<Department>> tree(@RequestParam(required = false) Long orgId) {
        List<Department> list = departmentService.getDepartmentTree(orgId);
        return Result.success(list);
    }

    /**
     * 分页查询部门列表
     */
    @GetMapping("/page")
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
    public Result<Department> getById(@PathVariable Long id) {
        Department department = departmentService.getById(id);
        return Result.success(department);
    }

    /**
     * 根据部门编码查询部门
     */
    @GetMapping("/code/{deptCode}")
    public Result<Department> getByDeptCode(@PathVariable String deptCode) {
        Department department = departmentService.getByDeptCode(deptCode);
        return Result.success(department);
    }

    /**
     * 新增部门
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody Department department) {
        boolean result = departmentService.save(department);
        return result ? Result.success(result) : Result.error("新增部门失败");
    }

    /**
     * 更新部门
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody Department department) {
        boolean result = departmentService.updateById(department);
        return result ? Result.success(result) : Result.error("更新部门失败");
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = departmentService.removeById(id);
        return result ? Result.success(result) : Result.error("删除部门失败");
    }
}
