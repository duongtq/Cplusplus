package com.springboot.todojdbc.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.springboot.todojdbc.*")
public class TodoJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoJdbcApplication.class, args);
    }

}
