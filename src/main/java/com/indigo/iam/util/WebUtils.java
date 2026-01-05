package com.indigo.iam.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Web 工具类
 *
 * @author MaxYun
 * @since 2025/12/30
 */
public class WebUtils {

    private static final String UNKNOWN = "unknown";
    private static final String IP_SEPARATOR = ",";
    private static final int IP_LENGTH = 15;

    /**
     * 获取当前请求对象
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 获取客户端 IP 地址
     */
    public static String getClientIp() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return UNKNOWN;
        }

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 处理多个 IP 的情况（X-Forwarded-For 可能包含多个 IP）
        if (ip != null && ip.contains(IP_SEPARATOR)) {
            ip = ip.split(IP_SEPARATOR)[0].trim();
        }

        // 对于 IPv6 本地地址的特殊处理
        if (ip != null && ip.length() > IP_LENGTH) {
            int index = ip.indexOf(",");
            if (index > 0) {
                ip = ip.substring(0, index);
            }
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取 User-Agent
     */
    public static String getUserAgent() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return "";
        }
        return request.getHeader("User-Agent");
    }

    /**
     * 获取请求 URI
     */
    public static String getRequestUri() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return "";
        }
        return request.getRequestURI();
    }

    /**
     * 获取请求方法
     */
    public static String getRequestMethod() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return "";
        }
        return request.getMethod();
    }

    /**
     * 获取请求参数
     */
    public static String getRequestParams() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return "";
        }

        // 获取查询参数
        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            return queryString;
        }

        return "";
    }
}
