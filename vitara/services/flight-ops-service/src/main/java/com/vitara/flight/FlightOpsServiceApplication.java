package com.vitara.flight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.vitara.flight") // Replace with your top-level package path
@EnableJpaAuditing
@EntityScan("com.vitara.flight")
public class FlightOpsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightOpsServiceApplication.class, args);
    }

}
