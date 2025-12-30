package com.xudis.iam.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xudis.iam.annotation.LogOperation;
import com.xudis.iam.entity.OperationLog;
import com.xudis.iam.service.OperationLogService;
import com.xudis.iam.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 操作日志切面
 *
 * @author MaxYun
 * @since 2025/12/30
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final OperationLogService operationLogService;
    private final ObjectMapper objectMapper;

    /**
     * 定义切点：拦截所有带 @LogOperation 注解的方法
     */
    @Pointcut("@annotation(com.xudis.iam.annotation.LogOperation)")
    public void operationLogPointcut() {
    }

    /**
     * 环绕通知：记录操作日志
     */
    @Around("operationLogPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        OperationLog operationLog = new OperationLog();

        try {
            // 获取注解信息
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            LogOperation annotation = method.getAnnotation(LogOperation.class);

            // 获取请求信息
            HttpServletRequest request = WebUtils.getCurrentRequest();
            String requestMethod = WebUtils.getRequestMethod();
            String requestUrl = WebUtils.getRequestUri();
            String clientIp = WebUtils.getClientIp();
            String userAgent = WebUtils.getUserAgent();

            // 设置基本信息
            operationLog.setModule(annotation.module());
            operationLog.setOperationType(annotation.operationType());
            operationLog.setDescription(annotation.description());
            operationLog.setRequestMethod(requestMethod);
            operationLog.setRequestUrl(requestUrl);
            operationLog.setOperatorIp(clientIp);
            operationLog.setUserAgent(userAgent);
            operationLog.setTraceId(UUID.randomUUID().toString());

            // TODO: 从认证上下文中获取当前用户信息
            operationLog.setOperatorId(1L);
            operationLog.setOperatorName("系统管理员");

            // 记录请求参数
            if (annotation.saveParams()) {
                String params = getRequestParams(joinPoint, request);
                operationLog.setRequestParams(params);
            }

            // 执行目标方法
            Object result = joinPoint.proceed();

            // 记录响应结果
            long duration = System.currentTimeMillis() - startTime;
            operationLog.setDuration(duration);
            operationLog.setStatus(1); // 成功

            if (annotation.saveResponse()) {
                String responseData = objectMapper.writeValueAsString(result);
                operationLog.setResponseData(responseData);
            }

            // 异步保存日志
            saveOperationLog(operationLog);

            return result;

        } catch (Exception e) {
            // 记录失败日志
            long duration = System.currentTimeMillis() - startTime;
            operationLog.setDuration(duration);
            operationLog.setStatus(0); // 失败
            operationLog.setErrorMsg(e.getMessage());

            // 异步保存日志
            saveOperationLog(operationLog);

            throw e;
        }
    }

    /**
     * 获取请求参数
     */
    private String getRequestParams(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
        try {
            // 优先从 request 中获取参数
            if (request != null && !isMultipartContent(request)) {
                String params = WebUtils.getRequestParams();
                if (params != null && !params.isEmpty()) {
                    return params;
                }
            }

            // 从方法参数中获取
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) {
                return "";
            }

            // 过滤掉不能序列化的参数（如 MultipartFile）
            StringBuilder params = new StringBuilder();
            for (Object arg : args) {
                if (arg == null) {
                    continue;
                }
                // 跳过文件类型和 HttpServletRequest/Response
                if (arg instanceof MultipartFile ||
                    arg instanceof HttpServletRequest ||
                    arg instanceof jakarta.servlet.http.HttpServletResponse) {
                    continue;
                }
                String paramStr = objectMapper.writeValueAsString(arg);
                params.append(paramStr).append("; ");
            }

            return params.toString();

        } catch (Exception e) {
            log.warn("获取请求参数失败", e);
            return "参数解析失败";
        }
    }

    /**
     * 判断是否是文件上传请求
     */
    private boolean isMultipartContent(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }

    /**
     * 异步保存操作日志
     */
    @Async
    public void saveOperationLog(OperationLog operationLog) {
        try {
            operationLog.setOperationTime(java.time.LocalDateTime.now());
            operationLogService.save(operationLog);
            log.debug("操作日志保存成功: {}", operationLog.getTraceId());
        } catch (Exception e) {
            log.error("操作日志保存失败", e);
        }
    }
}
