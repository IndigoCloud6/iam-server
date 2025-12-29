package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.common.Result;
import com.xudis.iam.entity.Organization;
import com.xudis.iam.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrganizationController {

    private final OrganizationService organizationService;

    /**
     * 查询组织树
     */
    @GetMapping("/tree")
    public Result<List<Organization>> tree() {
        List<Organization> list = organizationService.getOrganizationTree();
        return Result.success(list);
    }

    /**
     * 分页查询组织列表
     */
    @GetMapping("/page")
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
    public Result<Organization> getById(@PathVariable Long id) {
        Organization organization = organizationService.getById(id);
        return Result.success(organization);
    }

    /**
     * 根据组织编码查询组织
     */
    @GetMapping("/code/{orgCode}")
    public Result<Organization> getByOrgCode(@PathVariable String orgCode) {
        Organization organization = organizationService.getByOrgCode(orgCode);
        return Result.success(organization);
    }

    /**
     * 新增组织
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody Organization organization) {
        boolean result = organizationService.save(organization);
        return result ? Result.success(result) : Result.error("新增组织失败");
    }

    /**
     * 更新组织
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody Organization organization) {
        boolean result = organizationService.updateById(organization);
        return result ? Result.success(result) : Result.error("更新组织失败");
    }

    /**
     * 删除组织
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = organizationService.removeById(id);
        return result ? Result.success(result) : Result.error("删除组织失败");
    }
}
