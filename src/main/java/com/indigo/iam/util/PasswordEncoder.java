package com.indigo.iam.util;

import cn.hutool.crypto.digest.BCrypt;

/**
 * 密码加密工具类
 *
 * @author MaxYun
 * @since 2025/12/30
 */
public class PasswordEncoder {

    /**
     * 加密密码
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码（包含盐值）
     */
    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    /**
     * 验证密码
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
