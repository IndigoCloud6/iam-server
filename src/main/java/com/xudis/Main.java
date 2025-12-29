package com.xudis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * IAM Server Application
 *
 * @author MaxYun
 * @since 2025/12/29
 */
@SpringBootApplication
@MapperScan("com.xudis.iam.mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}