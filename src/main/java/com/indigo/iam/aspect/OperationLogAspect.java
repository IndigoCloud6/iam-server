package com.indigo.iam.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indigo.iam.annotation.LogOperation;
import com.indigo.iam.entity.OperationLog;
import com.indigo.iam.service.OperationLogService;
import com.indigo.iam.util.WebUtils;
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
    @Pointcut("@annotation(com.indigo.iam.annotation.LogOperation)")
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
            String requestUri = WebUtils.getRequestUri();
            String clientIp = WebUtils.getClientIp();
            String userAgent = WebUtils.getUserAgent();

            // 设置基本信息
            operationLog.setTraceId(UUID.randomUUID().toString());
            operationLog.setOperationType(annotation.operationType());
            operationLog.setOperationModule(annotation.module());
            operationLog.setOperationDesc(annotation.description());
            operationLog.setRequestMethod(requestMethod);
            operationLog.setRequestUri(requestUri);
            operationLog.setClientIp(clientIp);
            operationLog.setUserAgent(userAgent);

            // TODO: 从认证上下文中获取当前用户信息
            operationLog.setUserId(1L);
            operationLog.setUsername("admin");
            operationLog.setRealName("系统管理员");

            // 记录请求参数
            if (annotation.saveParams()) {
                String requestParams = getRequestParams(joinPoint, request);
                operationLog.setRequestParams(requestParams);

                String requestBody = getRequestBody(joinPoint, request);
                operationLog.setRequestBody(requestBody);
            }

            // 执行目标方法
            Object result = joinPoint.proceed();

            // 记录响应结果
            long duration = System.currentTimeMillis() - startTime;
            operationLog.setExecuteTime(duration);
            operationLog.setResponseStatus(200); // 成功

            if (annotation.saveResponse()) {
                String responseBody = objectMapper.writeValueAsString(result);
                operationLog.setResponseBody(responseBody);
            }

            // 获取内存使用情况
            Runtime runtime = Runtime.getRuntime();
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            operationLog.setMemoryUsed(usedMemory);

            // 异步保存日志
            saveOperationLog(operationLog);

            return result;

        } catch (Exception e) {
            // 记录失败日志
            long duration = System.currentTimeMillis() - startTime;
            operationLog.setExecuteTime(duration);
            operationLog.setResponseStatus(500); // 失败
            operationLog.setErrorMessage(e.getMessage());

            // 异步保存日志
            saveOperationLog(operationLog);

            throw e;
        }
    }

    /**
     * 获取请求参数（URL参数）
     */
    private String getRequestParams(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
        try {
            if (request != null) {
                String params = WebUtils.getRequestParams();
                if (params != null && !params.isEmpty()) {
                    return params;
                }
            }
            return "";
        } catch (Exception e) {
            log.warn("获取请求参数失败", e);
            return "";
        }
    }

    /**
     * 获取请求体
     */
    private String getRequestBody(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
        try {
            // 从方法参数中获取
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) {
                return "";
            }

            // 过滤掉不能序列化的参数（如 MultipartFile）
            StringBuilder requestBody = new StringBuilder();
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
                requestBody.append(paramStr).append("; ");
            }

            return requestBody.toString();

        } catch (Exception e) {
            log.warn("获取请求体失败", e);
            return "请求体解析失败";
        }
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
