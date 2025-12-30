package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.entity.OperationLog;

import java.util.List;
import java.util.Map;

/**
 * 操作日志服务接口
 *
 * @author MaxYun
 * @since 2025/12/30
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 分页查询操作日志
     *
     * @param page         分页对象
     * @param module       模块
     * @param operationType 操作类型
     * @param userId       用户ID
     * @param status       状态
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @return 分页结果
     */
    Page<OperationLog> pageLogs(Page<OperationLog> page, String module, String operationType,
                                 Long userId, Integer status, String startTime, String endTime);

    /**
     * 查询用户操作历史
     *
     * @param userId 用户ID
     * @return 操作日志列表
     */
    List<OperationLog> getUserOperations(Long userId);

    /**
     * 查询模块操作日志
     *
     * @param module 模块名称
     * @return 操作日志列表
     */
    List<OperationLog> getModuleOperations(String module);

    /**
     * 根据跟踪ID查询日志链
     *
     * @param traceId 跟踪ID
     * @return 操作日志列表
     */
    List<OperationLog> getLogsByTraceId(String traceId);

    /**
     * 操作统计分析
     *
     * @return 统计数据
     */
    Map<String, Object> getStatistics();
}
