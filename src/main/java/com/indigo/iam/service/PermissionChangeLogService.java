package com.indigo.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.indigo.iam.entity.PermissionChangeLog;

import java.util.List;

/**
 * 权限变更日志服务接口
 *
 * @author MaxYun
 * @since 2025/12/30
 */
public interface PermissionChangeLogService extends IService<PermissionChangeLog> {

    /**
     * 分页查询权限变更日志
     *
     * @param page        分页对象
     * @param changeType  变更类型
     * @param targetType  目标类型
     * @param operatorId  操作人ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 分页结果
     */
    Page<PermissionChangeLog> pageLogs(Page<PermissionChangeLog> page, String changeType, String targetType,
                                        Long operatorId, String startTime, String endTime);

    /**
     * 查询目标的权限变更历史
     *
     * @param targetType 目标类型
     * @param targetId   目标ID
     * @return 权限变更日志列表
     */
    List<PermissionChangeLog> getTargetChangeHistory(String targetType, Long targetId);

    /**
     * 查询权限的变更历史
     *
     * @param permissionId 权限ID
     * @return 权限变更日志列表
     */
    List<PermissionChangeLog> getPermissionChangeHistory(Long permissionId);

    /**
     * 查询操作人的变更记录
     *
     * @param operatorId 操作人ID
     * @return 权限变更日志列表
     */
    List<PermissionChangeLog> getOperatorChangeRecords(Long operatorId);
}
