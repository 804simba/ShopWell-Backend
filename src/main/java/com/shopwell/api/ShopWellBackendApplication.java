package com.shopwell.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShopWellBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopWellBackendApplication.class, args);
    }

}
