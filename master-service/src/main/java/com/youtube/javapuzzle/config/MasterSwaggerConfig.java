package com.youtube.javapuzzle.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MasterSwaggerConfig {
    @Bean
    public GroupedOpenApi customOpenAPI() {
        return GroupedOpenApi.builder()
                .group("debezium-master-postgresql-api") // Specify a group name
                .packagesToScan("com.youtube.javapuzzle.controller") // Update with your package
                .build();
    }


}
