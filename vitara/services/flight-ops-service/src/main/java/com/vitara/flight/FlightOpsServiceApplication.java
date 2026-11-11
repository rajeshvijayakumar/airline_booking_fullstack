package com.vitara.flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.vitara") // Replace with your top-level package path
public class FlightOpsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightOpsServiceApplication.class, args);
    }

}
