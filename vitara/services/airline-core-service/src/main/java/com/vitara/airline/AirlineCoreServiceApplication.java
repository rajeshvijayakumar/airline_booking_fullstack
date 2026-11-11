package com.vitara.airline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.vitara")
@EntityScan(basePackages = "com.vitara")
@EnableJpaRepositories(basePackages = "com.vitara")
@EnableJpaAuditing
public class AirlineCoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirlineCoreServiceApplication.class, args);
    }

}
