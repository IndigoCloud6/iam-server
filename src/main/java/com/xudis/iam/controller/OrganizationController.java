package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.annotation.LogOperation;
import com.xudis.iam.common.Result;
import com.xudis.iam.dto.CreateOrganizationRequest;
import com.xudis.iam.dto.UpdateOrganizationRequest;
import com.xudis.iam.entity.Organization;
import com.xudis.iam.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织控制器
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
@Tag(name = "组织管理", description = "组织相关接口")
public class OrganizationController {

    private final OrganizationService organizationService;

    /**
     * 查询组织树
     */
    @GetMapping("/tree")
    @Operation(summary = "查询组织树")
    @LogOperation(module = "组织管理", operationType = "QUERY", description = "查询组织树")
    public Result<List<Organization>> tree() {
        List<Organization> list = organizationService.getOrganizationTree();
        return Result.success(list);
    }

    /**
     * 分页查询组织列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询组织列表")
    @LogOperation(module = "组织管理", operationType = "QUERY", description = "分页查询组织列表")
    public Result<Page<Organization>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String orgCode,
            @RequestParam(required = false) String orgName) {
        Page<Organization> page = new Page<>(current, size);
        Page<Organization> result = organizationService.pageOrganizations(page, orgCode, orgName);
        return Result.success(result);
    }

    /**
     * 根据ID查询组织
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询组织")
    public Result<Organization> getById(@PathVariable Long id) {
        Organization organization = organizationService.getById(id);
        return Result.success(organization);
    }

    /**
     * 根据组织编码查询组织
     */
    @GetMapping("/code/{orgCode}")
    @Operation(summary = "根据组织编码查询组织")
    public Result<Organization> getByOrgCode(@PathVariable String orgCode) {
        Organization organization = organizationService.getByOrgCode(orgCode);
        return Result.success(organization);
    }

    /**
     * 新增组织
     */
    @PostMapping
    @Operation(summary = "新增组织")
    @LogOperation(module = "组织管理", operationType = "CREATE", description = "新增组织")
    public Result<Organization> save(@Valid @RequestBody CreateOrganizationRequest request) {
        Organization organization = organizationService.createOrganization(request);
        return Result.success(organization);
    }

    /**
     * 更新组织
     */
    @PutMapping
    @Operation(summary = "更新组织")
    @LogOperation(module = "组织管理", operationType = "UPDATE", description = "更新组织")
    public Result<Boolean> update(@Valid @RequestBody UpdateOrganizationRequest request) {
        boolean result = organizationService.updateOrganization(request);
        return result ? Result.success(result) : Result.error("更新组织失败");
    }

    /**
     * 删除组织
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除组织")
    @LogOperation(module = "组织管理", operationType = "DELETE", description = "删除组织")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = organizationService.removeById(id);
        return result ? Result.success(result) : Result.error("删除组织失败");
    }
}
