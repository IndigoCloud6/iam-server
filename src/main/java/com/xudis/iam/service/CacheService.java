package com.xudis.iam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存，带过期时间
     *
     * @param key     缓存key
     * @param value   缓存值
     * @param timeout 过期时间（秒）
     */
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存
     *
     * @param key 缓存key
     * @return 缓存值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取缓存（泛型）
     *
     * @param key   缓存key
     * @param clazz 返回值类型
     * @param <T>   泛型类型
     * @return 缓存值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? (T) value : null;
    }

    /**
     * 删除缓存
     *
     * @param key 缓存key
     * @return 是否删除成功
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     *
     * @param keys 缓存key集合
     * @return 删除数量
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 根据模式删除缓存
     *
     * @param pattern 模式，如 "user:*"
     * @return 删除数量
     */
    public Long deleteByPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            return redisTemplate.delete(keys);
        }
        return 0L;
    }

    /**
     * 判断key是否存在
     *
     * @param key 缓存key
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     *
     * @param key     缓存key
     * @param timeout 过期时间（秒）
     * @return 是否设置成功
     */
    public Boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取过期时间
     *
     * @param key 缓存key
     * @return 过期时间（秒），-1表示永久有效，-2表示key不存在
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 清除用户权限相关缓存
     *
     * @param userId 用户ID
     */
    public void clearUserPermissionCache(Long userId) {
        deleteByPattern("user:*:" + userId);
    }

    /**
     * 清除角色权限相关缓存
     *
     * @param roleId 角色ID
     */
    public void clearRolePermissionCache(Long roleId) {
        delete("role:permissions:" + roleId);
        // 同时需要清除所有拥有该角色的用户的权限缓存
        // 这里可以通过查询用户角色关系来清除，或者使用更粗粒度的清除策略
    }

    /**
     * 清除所有权限相关缓存
     */
    public void clearAllPermissionCache() {
        deleteByPattern("user:permissions:*");
        deleteByPattern("user:menus:*");
        deleteByPattern("user:buttons:*");
        deleteByPattern("user:apis:*");
        deleteByPattern("role:permissions:*");
    }

    /**
     * 清除部门树缓存
     *
     * @param orgId 组织ID（可选）
     */
    public void clearDepartmentTreeCache(Long orgId) {
        if (orgId != null) {
            delete("department:tree:" + orgId);
        } else {
            deleteByPattern("department:tree:*");
        }
    }

    /**
     * 清除权限树缓存
     */
    public void clearPermissionTreeCache() {
        delete("permission:tree");
    }

    /**
     * 清除字典缓存
     *
     * @param dictId 字典ID
     */
    public void clearDictCache(Long dictId) {
        deleteByPattern("dict:*:" + dictId);
    }
}
