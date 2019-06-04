package com.neo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by xzm on 2019/6/4.
 */
@Configuration
@ComponentScan(basePackages = "com.neo")
@EnableAutoConfiguration()
public class ESApplication {
    public static void main(String args[]) {
        SpringApplication.run(ESApplication.class);
    }
}
