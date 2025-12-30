package com.xudis.iam.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xudis.iam.common.Result;
import com.xudis.iam.entity.OperationLog;
import com.xudis.iam.service.OperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 操作日志控制器
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@RestController
@RequestMapping("/operation-log")
@RequiredArgsConstructor
@Tag(name = "操作日志", description = "操作日志管理接口")
public class OperationLogController {

    private final OperationLogService operationLogService;

    /**
     * 分页查询操作日志
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询操作日志")
    public Result<Page<OperationLog>> page(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String module,
            @RequestParam(required = false) String operationType,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        Page<OperationLog> page = new Page<>(current, size);
        Page<OperationLog> result = operationLogService.pageLogs(page, module, operationType,
                userId, status, startTime, endTime);
        return Result.success(result);
    }

    /**
     * 查询用户操作历史
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "查询用户操作历史")
    public Result<List<OperationLog>> getUserOperations(@PathVariable Long userId) {
        List<OperationLog> logs = operationLogService.getUserOperations(userId);
        return Result.success(logs);
    }

    /**
     * 查询模块操作日志
     */
    @GetMapping("/module/{module}")
    @Operation(summary = "查询模块操作日志")
    public Result<List<OperationLog>> getModuleOperations(@PathVariable String module) {
        List<OperationLog> logs = operationLogService.getModuleOperations(module);
        return Result.success(logs);
    }

    /**
     * 根据跟踪ID查询日志链
     */
    @GetMapping("/trace/{traceId}")
    @Operation(summary = "根据跟踪ID查询日志链")
    public Result<List<OperationLog>> getLogsByTraceId(@PathVariable String traceId) {
        List<OperationLog> logs = operationLogService.getLogsByTraceId(traceId);
        return Result.success(logs);
    }

    /**
     * 操作统计分析
     */
    @GetMapping("/statistics")
    @Operation(summary = "操作统计分析")
    public Result<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = operationLogService.getStatistics();
        return Result.success(stats);
    }
}
