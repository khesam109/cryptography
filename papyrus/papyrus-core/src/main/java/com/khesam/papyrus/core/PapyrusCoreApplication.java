package com.khesam.papyrus.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.khesam.papyrus.core.repository")
@EntityScan(basePackages = "com.khesam.papyrus.core.repository.entity")
@SpringBootApplication
public class PapyrusCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                PapyrusCoreApplication.class, args
        );
    }
}
