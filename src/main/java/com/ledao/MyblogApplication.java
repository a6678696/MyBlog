package com.ledao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author LeDao
 */
@SpringBootApplication
@MapperScan("com.ledao.mapper")
public class MyblogApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MyblogApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MyblogApplication.class, args);
    }

}
