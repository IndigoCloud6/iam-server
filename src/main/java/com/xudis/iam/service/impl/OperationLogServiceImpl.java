package com.xudis.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.entity.OperationLog;
import com.xudis.iam.mapper.OperationLogMapper;
import com.xudis.iam.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志服务实现类
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Page<OperationLog> pageLogs(Page<OperationLog> page, String module, String operationType,
                                       Long userId, Integer status, String startTime, String endTime) {
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(module)) {
            wrapper.eq(OperationLog::getOperationModule, module);
        }
        if (StringUtils.hasText(operationType)) {
            wrapper.eq(OperationLog::getOperationType, operationType);
        }
        if (userId != null) {
            wrapper.eq(OperationLog::getUserId, userId);
        }
        if (status != null) {
            wrapper.eq(OperationLog::getResponseStatus, status);
        }
        if (StringUtils.hasText(startTime)) {
            wrapper.ge(OperationLog::getOperationTime, parseDateTime(startTime));
        }
        if (StringUtils.hasText(endTime)) {
            wrapper.le(OperationLog::getOperationTime, parseDateTime(endTime));
        }

        wrapper.orderByDesc(OperationLog::getOperationTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<OperationLog> getUserOperations(Long userId) {
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OperationLog::getUserId, userId);
        wrapper.orderByDesc(OperationLog::getOperationTime);
        wrapper.last("LIMIT 100");
        return this.list(wrapper);
    }

    @Override
    public List<OperationLog> getModuleOperations(String module) {
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OperationLog::getOperationModule, module);
        wrapper.orderByDesc(OperationLog::getOperationTime);
        wrapper.last("LIMIT 100");
        return this.list(wrapper);
    }

    @Override
    public List<OperationLog> getLogsByTraceId(String traceId) {
        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OperationLog::getTraceId, traceId);
        wrapper.orderByAsc(OperationLog::getOperationTime);
        return this.list(wrapper);
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // 总操作数
        long totalCount = this.count();
        stats.put("totalCount", totalCount);

        // 今日操作数
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = LocalDate.now().plusDays(1).atStartOfDay();
        LambdaQueryWrapper<OperationLog> todayWrapper = new LambdaQueryWrapper<>();
        todayWrapper.ge(OperationLog::getOperationTime, todayStart);
        todayWrapper.lt(OperationLog::getOperationTime, todayEnd);
        long todayCount = this.count(todayWrapper);
        stats.put("todayCount", todayCount);

        // 成功操作数
        LambdaQueryWrapper<OperationLog> successWrapper = new LambdaQueryWrapper<>();
        successWrapper.eq(OperationLog::getResponseStatus, 200);
        long successCount = this.count(successWrapper);
        stats.put("successCount", successCount);

        // 失败操作数
        LambdaQueryWrapper<OperationLog> failWrapper = new LambdaQueryWrapper<>();
        failWrapper.ne(OperationLog::getResponseStatus, 200);
        long failCount = this.count(failWrapper);
        stats.put("failCount", failCount);

        // 平均执行时长
        LambdaQueryWrapper<OperationLog> avgWrapper = new LambdaQueryWrapper<>();
        avgWrapper.isNotNull(OperationLog::getExecuteTime);
        List<OperationLog> logsWithDuration = this.list(avgWrapper);
        if (!logsWithDuration.isEmpty()) {
            double avgDuration = logsWithDuration.stream()
                    .mapToLong(OperationLog::getExecuteTime)
                    .average()
                    .orElse(0.0);
            stats.put("avgDuration", avgDuration);
        } else {
            stats.put("avgDuration", 0.0);
        }

        return stats;
    }

    /**
     * 解析日期时间字符串
     */
    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }
}
