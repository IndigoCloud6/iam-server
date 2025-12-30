package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.common.Result;
import com.xudis.iam.entity.PermissionChangeLog;
import com.xudis.iam.service.PermissionChangeLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限变更日志控制器
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@RestController
@RequestMapping("/perm-change-log")
@RequiredArgsConstructor
@Tag(name = "权限变更日志", description = "权限变更日志管理接口")
public class PermissionChangeLogController {

    private final PermissionChangeLogService permissionChangeLogService;

    /**
     * 分页查询权限变更日志
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询权限变更日志")
    public Result<Page<PermissionChangeLog>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String changeType,
            @RequestParam(required = false) String targetType,
            @RequestParam(required = false) Long operatorId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        Page<PermissionChangeLog> page = new Page<>(current, size);
        Page<PermissionChangeLog> result = permissionChangeLogService.pageLogs(page, changeType,
                targetType, operatorId, startTime, endTime);
        return Result.success(result);
    }

    /**
     * 查询目标的权限变更历史
     */
    @GetMapping("/target/{targetType}/{targetId}")
    @Operation(summary = "查询目标的权限变更历史")
    public Result<List<PermissionChangeLog>> getTargetChangeHistory(
            @PathVariable String targetType,
            @PathVariable Long targetId) {
        List<PermissionChangeLog> logs = permissionChangeLogService.getTargetChangeHistory(targetType, targetId);
        return Result.success(logs);
    }

    /**
     * 查询权限的变更历史
     */
    @GetMapping("/permission/{permId}")
    @Operation(summary = "查询权限的变更历史")
    public Result<List<PermissionChangeLog>> getPermissionChangeHistory(@PathVariable Long permId) {
        List<PermissionChangeLog> logs = permissionChangeLogService.getPermissionChangeHistory(permId);
        return Result.success(logs);
    }

    /**
     * 查询操作人的变更记录
     */
    @GetMapping("/operator/{operatorId}")
    @Operation(summary = "查询操作人的变更记录")
    public Result<List<PermissionChangeLog>> getOperatorChangeRecords(@PathVariable Long operatorId) {
        List<PermissionChangeLog> logs = permissionChangeLogService.getOperatorChangeRecords(operatorId);
        return Result.success(logs);
    }
}
