package com.springboot.todojpa.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "com.springboot.todojpa.*")
@EnableJpaRepositories("com.springboot.todojpa.*")
@EntityScan("com.springboot.todojpa.domain")
@SpringBootApplication
public class TodoJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoJpaApplication.class, args);
    }
}
