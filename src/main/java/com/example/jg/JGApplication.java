package com.example.jg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class JGApplication {

    public static void main(String[] args) {
        SpringApplication.run(JGApplication.class, args);
        log.info("项目成功启动");
    }

}
