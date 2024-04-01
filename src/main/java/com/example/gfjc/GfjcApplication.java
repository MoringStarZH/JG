package com.example.gfjc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GfjcApplication {

    public static void main(String[] args) {
        SpringApplication.run(GfjcApplication.class, args);
        log.info("项目成功启动");
    }

}
