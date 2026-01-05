package com.indigo.iam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.indigo.iam.common.BusinessException;
import com.indigo.iam.dto.CreateConfigRequest;
import com.indigo.iam.dto.UpdateConfigRequest;
import com.indigo.iam.entity.Config;
import com.indigo.iam.mapper.ConfigMapper;
import com.indigo.iam.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统配置服务实现类
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public Config getByConfigKey(String configKey) {
        LambdaQueryWrapper<Config> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Config::getConfigKey, configKey);
        return this.getOne(wrapper);
    }

    @Override
    @Cacheable(value = "config", key = "#configKey")
    public String getConfigValue(String configKey) {
        Config config = getByConfigKey(configKey);
        if (config == null) {
            return null;
        }
        // 如果是敏感配置，返回脱敏值
        if (config.getIsSensitive() != null && config.getIsSensitive() == 1) {
            return "******";
        }
        return config.getConfigValue();
    }

    @Override
    @Cacheable(value = "config", key = "#configKey")
    public String getConfigValue(String configKey, String defaultValue) {
        String value = getConfigValue(configKey);
        return StringUtils.hasText(value) ? value : defaultValue;
    }

    @Override
    public List<Config> getConfigsByScope(String scope, Long scopeId) {
        LambdaQueryWrapper<Config> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Config::getScope, scope);
        if (scopeId != null) {
            wrapper.eq(Config::getScopeId, scopeId);
        }
        return this.list(wrapper);
    }

    @Override
    public Page<Config> pageConfigs(Page<Config> page, String configKey, String scope) {
        LambdaQueryWrapper<Config> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(configKey)) {
            wrapper.like(Config::getConfigKey, configKey);
        }
        if (StringUtils.hasText(scope)) {
            wrapper.eq(Config::getScope, scope);
        }
        wrapper.orderByDesc(Config::getCreatedAt);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Config createConfig(CreateConfigRequest request) {
        // 检查配置键是否已存在
        Config existingConfig = getByConfigKey(request.getConfigKey());
        if (existingConfig != null) {
            throw new BusinessException("配置键已存在: " + request.getConfigKey());
        }

        // 创建配置实体
        Config config = new Config();
        BeanUtil.copyProperties(request, config);

        // 设置默认值
        if (!StringUtils.hasText(config.getConfigType())) {
            config.setConfigType("STRING");
        }
        if (!StringUtils.hasText(config.getScope())) {
            config.setScope("GLOBAL");
        }
        if (config.getVersion() == null) {
            config.setVersion(1);
        }
        if (config.getIsSensitive() == null) {
            config.setIsSensitive(0);
        }
        if (config.getIsSystem() == null) {
            config.setIsSystem(0);
        }

        // 如果是敏感配置，加密存储
        if (config.getIsSensitive() == 1) {
            config.setConfigValue(encryptValue(config.getConfigValue()));
        }

        // 保存配置
        boolean saved = this.save(config);
        if (!saved) {
            throw new BusinessException("创建配置失败");
        }

        log.info("创建配置成功: configKey={}, type={}, scope={}",
                config.getConfigKey(), config.getConfigType(), config.getScope());

        return config;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "config", key = "#request.configKey")
    public boolean updateConfig(UpdateConfigRequest request) {
        // 检查配置是否存在
        Config existingConfig = this.getById(request.getId());
        if (existingConfig == null) {
            throw new BusinessException("配置不存在: " + request.getId());
        }

        // 系统配置保护
        if (existingConfig.getIsSystem() != null && existingConfig.getIsSystem() == 1) {
            // 系统配置只能修改配置值
            if (!StringUtils.hasText(request.getConfigValue())) {
                throw new BusinessException("系统配置只能修改配置值");
            }
        }

        // 如果修改了配置键，检查新键是否已被使用
        if (StringUtils.hasText(request.getConfigKey()) &&
            !request.getConfigKey().equals(existingConfig.getConfigKey())) {
            Config configWithSameKey = getByConfigKey(request.getConfigKey());
            if (configWithSameKey != null) {
                throw new BusinessException("配置键已被使用: " + request.getConfigKey());
            }
        }

        // 保存旧值用于版本管理
        String oldValue = existingConfig.getConfigValue();

        // 复制属性
        Config config = new Config();
        BeanUtil.copyProperties(request, config);

        // 版本号加1
        config.setVersion(existingConfig.getVersion() + 1);
        config.setPreviousValue(oldValue);

        // 如果是敏感配置，加密新值
        if (existingConfig.getIsSensitive() != null && existingConfig.getIsSensitive() == 1
            && StringUtils.hasText(request.getConfigValue())) {
            config.setConfigValue(encryptValue(request.getConfigValue()));
        }

        // 更新配置
        boolean updated = this.updateById(config);

        if (updated) {
            log.info("更新配置成功: configKey={}, version={}", config.getConfigKey(), config.getVersion());
        }

        return updated;
    }

    @Override
    @CacheEvict(value = "config", key = "#configKey")
    public void refreshConfigCache(String configKey) {
        log.info("刷新配置缓存: configKey={}", configKey);
    }

    /**
     * 加密敏感配置值
     *
     * @param value 原始值
     * @return 加密后的值
     */
    private String encryptValue(String value) {
        try {
            return SecureUtil.md5(value); // 实际项目中应使用更强的加密方式
        } catch (Exception e) {
            log.error("加密配置值失败", e);
            return value;
        }
    }
}
