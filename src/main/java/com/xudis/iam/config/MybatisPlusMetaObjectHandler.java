package com.xudis.iam.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis Plus 元数据自动填充处理器
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("开始插入填充...");

        LocalDateTime now = LocalDateTime.now();

        // 填充创建时间
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "createdBy", Long.class, getCurrentUserId());

        // 填充更新时间
        this.strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updatedBy", Long.class, getCurrentUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("开始更新填充...");

        // 填充更新时间
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updatedBy", Long.class, getCurrentUserId());
    }

    /**
     * 获取当前用户ID
     * TODO: 从 Security 上下文或 ThreadLocal 中获取当前登录用户ID
     */
    private Long getCurrentUserId() {
        // 暂时返回系统用户 ID，后续集成认证系统后从上下文中获取
        return 1L;
    }
}
