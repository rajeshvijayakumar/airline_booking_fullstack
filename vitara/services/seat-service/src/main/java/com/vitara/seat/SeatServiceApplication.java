package com.vitara.seat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.vitara") // Replace with your top-level package path
public class SeatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeatServiceApplication.class, args);
    }

}
