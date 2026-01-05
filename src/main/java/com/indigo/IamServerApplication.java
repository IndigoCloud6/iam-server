package com.indigo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * IAM Server Application
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@SpringBootApplication
@MapperScan("com.indigo.iam.mapper")
@EnableAsync
@EnableCaching
@EnableDiscoveryClient
public class IamServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IamServerApplication.class, args);
    }
}
