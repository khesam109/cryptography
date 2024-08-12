package com.khesam.papyrus.gateway;

import com.khesam.papyrus.gateway.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class})
public class PapyrusGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                PapyrusGatewayApplication.class, args
        );
    }
}
