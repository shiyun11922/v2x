package com.neko.seed;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com", exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.neko.seed")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}