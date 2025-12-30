package com.xudis.iam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xudis.iam.dto.CreateConfigRequest;
import com.xudis.iam.dto.UpdateConfigRequest;
import com.xudis.iam.entity.Config;

/**
 * 系统配置服务接口
 *
 * @author MaxYun
 * @since 2025/12/30
 */
public interface ConfigService extends IService<Config> {

    /**
     * 根据配置键查询配置
     *
     * @param configKey 配置键
     * @return 配置信息
     */
    Config getByConfigKey(String configKey);

    /**
     * 根据配置键获取配置值（String类型）
     *
     * @param configKey 配置键
     * @return 配置值
     */
    String getConfigValue(String configKey);

    /**
     * 根据配置键获取配置值（指定默认值）
     *
     * @param configKey    配置键
     * @param defaultValue 默认值
     * @return 配置值
     */
    String getConfigValue(String configKey, String defaultValue);

    /**
     * 根据作用域查询配置列表
     *
     * @param scope   作用域
     * @param scopeId 作用域ID
     * @return 配置列表
     */
    List<Config> getConfigsByScope(String scope, Long scopeId);

    /**
     * 分页查询配置
     *
     * @param page      分页对象
     * @param configKey 配置键
     * @param scope     作用域
     * @return 分页结果
     */
    Page<Config> pageConfigs(Page<Config> page, String configKey, String scope);

    /**
     * 创建配置
     *
     * @param request 创建配置请求
     * @return 创建的配置信息
     */
    Config createConfig(CreateConfigRequest request);

    /**
     * 更新配置
     *
     * @param request 更新配置请求
     * @return 是否更新成功
     */
    boolean updateConfig(UpdateConfigRequest request);

    /**
     * 刷新配置缓存
     *
     * @param configKey 配置键
     */
    void refreshConfigCache(String configKey);
}
