package com.gifprojects.financity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FinancityApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancityApplication.class, args);
    }

}