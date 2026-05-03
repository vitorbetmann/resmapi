package com.vitorbetmann.resmapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ResmapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResmapiApplication.class, args);
    }

}
