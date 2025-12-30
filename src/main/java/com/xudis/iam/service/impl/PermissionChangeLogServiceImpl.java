package com.xudis.iam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudis.iam.entity.PermissionChangeLog;
import com.xudis.iam.mapper.PermissionChangeLogMapper;
import com.xudis.iam.service.PermissionChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 权限变更日志服务实现类
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Service
@RequiredArgsConstructor
public class PermissionChangeLogServiceImpl extends ServiceImpl<PermissionChangeLogMapper, PermissionChangeLog>
        implements PermissionChangeLogService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Page<PermissionChangeLog> pageLogs(Page<PermissionChangeLog> page, String changeType, String targetType,
                                              Long operatorId, String startTime, String endTime) {
        LambdaQueryWrapper<PermissionChangeLog> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(changeType)) {
            wrapper.eq(PermissionChangeLog::getChangeType, changeType);
        }
        if (StringUtils.hasText(targetType)) {
            wrapper.eq(PermissionChangeLog::getTargetType, targetType);
        }
        if (operatorId != null) {
            wrapper.eq(PermissionChangeLog::getOperatorId, operatorId);
        }
        if (StringUtils.hasText(startTime)) {
            wrapper.ge(PermissionChangeLog::getChangeTime, parseDateTime(startTime));
        }
        if (StringUtils.hasText(endTime)) {
            wrapper.le(PermissionChangeLog::getChangeTime, parseDateTime(endTime));
        }

        wrapper.orderByDesc(PermissionChangeLog::getChangeTime);
        return this.page(page, wrapper);
    }

    @Override
    public List<PermissionChangeLog> getTargetChangeHistory(String targetType, Long targetId) {
        LambdaQueryWrapper<PermissionChangeLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionChangeLog::getTargetType, targetType);
        wrapper.eq(PermissionChangeLog::getTargetId, targetId);
        wrapper.orderByDesc(PermissionChangeLog::getChangeTime);
        wrapper.last("LIMIT 100");
        return this.list(wrapper);
    }

    @Override
    public List<PermissionChangeLog> getPermissionChangeHistory(Long permissionId) {
        LambdaQueryWrapper<PermissionChangeLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionChangeLog::getPermissionId, permissionId);
        wrapper.orderByDesc(PermissionChangeLog::getChangeTime);
        wrapper.last("LIMIT 100");
        return this.list(wrapper);
    }

    @Override
    public List<PermissionChangeLog> getOperatorChangeRecords(Long operatorId) {
        LambdaQueryWrapper<PermissionChangeLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionChangeLog::getOperatorId, operatorId);
        wrapper.orderByDesc(PermissionChangeLog::getChangeTime);
        wrapper.last("LIMIT 100");
        return this.list(wrapper);
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
