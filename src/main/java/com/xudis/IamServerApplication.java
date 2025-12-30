package com.xudis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * IAM Server Application
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@SpringBootApplication
@MapperScan("com.xudis.iam.mapper")
@EnableAsync
public class IamServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IamServerApplication.class, args);
    }
}
