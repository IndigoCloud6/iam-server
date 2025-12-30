package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.annotation.LogOperation;
import com.xudis.iam.common.Result;
import com.xudis.iam.dto.CreateConfigRequest;
import com.xudis.iam.dto.UpdateConfigRequest;
import com.xudis.iam.entity.Config;
import com.xudis.iam.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置控制器
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
@Tag(name = "系统配置管理", description = "系统配置相关接口")
public class ConfigController {

    private final ConfigService configService;

    /**
     * 分页查询配置列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询配置列表")
    @LogOperation(module = "配置管理", operationType = "QUERY", description = "分页查询配置列表")
    public Result<Page<Config>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String configKey,
            @RequestParam(required = false) String scope) {
        Page<Config> page = new Page<>(current, size);
        Page<Config> result = configService.pageConfigs(page, configKey, scope);
        return Result.success(result);
    }

    /**
     * 根据配置键查询配置
     */
    @GetMapping("/key/{configKey}")
    @Operation(summary = "根据配置键查询配置")
    @LogOperation(module = "配置管理", operationType = "QUERY", description = "根据配置键查询配置")
    public Result<Config> getByConfigKey(@PathVariable String configKey) {
        Config config = configService.getByConfigKey(configKey);
        return Result.success(config);
    }

    /**
     * 根据配置键获取配置值
     */
    @GetMapping("/value/{configKey}")
    @Operation(summary = "根据配置键获取配置值", description = "敏感配置将返回脱敏值")
    public Result<String> getConfigValue(@PathVariable String configKey) {
        String value = configService.getConfigValue(configKey);
        return Result.success(value);
    }

    /**
     * 根据作用域查询配置列表
     */
    @GetMapping("/scope/{scope}")
    @Operation(summary = "根据作用域查询配置列表")
    @LogOperation(module = "配置管理", operationType = "QUERY", description = "根据作用域查询配置列表")
    public Result<List<Config>> getConfigsByScope(
            @PathVariable String scope,
            @RequestParam(required = false) Long scopeId) {
        List<Config> configs = configService.getConfigsByScope(scope, scopeId);
        return Result.success(configs);
    }

    /**
     * 新增配置
     */
    @PostMapping
    @Operation(summary = "新增配置")
    @LogOperation(module = "配置管理", operationType = "CREATE", description = "新增配置")
    public Result<Config> save(@Valid @RequestBody CreateConfigRequest request) {
        Config config = configService.createConfig(request);
        return Result.success(config);
    }

    /**
     * 更新配置
     */
    @PutMapping
    @Operation(summary = "更新配置")
    @LogOperation(module = "配置管理", operationType = "UPDATE", description = "更新配置")
    @CacheEvict(value = "config", allEntries = true)
    public Result<Boolean> update(@Valid @RequestBody UpdateConfigRequest request) {
        boolean result = configService.updateConfig(request);
        return result ? Result.success(result) : Result.error("更新配置失败");
    }

    /**
     * 删除配置
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除配置")
    @LogOperation(module = "配置管理", operationType = "DELETE", description = "删除配置")
    public Result<Boolean> delete(@PathVariable Long id) {
        Config config = configService.getById(id);
        if (config == null) {
            return Result.error("配置不存在");
        }
        // 系统配置不允许删除
        if (config.getIsSystem() != null && config.getIsSystem() == 1) {
            return Result.error("系统配置不允许删除");
        }
        boolean result = configService.removeById(id);
        return result ? Result.success(result) : Result.error("删除配置失败");
    }

    /**
     * 刷新配置缓存
     */
    @PostMapping("/refresh/{configKey}")
    @Operation(summary = "刷新配置缓存")
    @LogOperation(module = "配置管理", operationType = "UPDATE", description = "刷新配置缓存")
    public Result<Void> refreshCache(@PathVariable String configKey) {
        configService.refreshConfigCache(configKey);
        return Result.success();
    }
}
