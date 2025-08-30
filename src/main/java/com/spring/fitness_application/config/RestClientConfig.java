package com.spring.fitness_application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;



@Configuration
public class RestClientConfig {


    @Value("${client.name}")
    String applicationName;
    @Value("${client.version}")
    String applicationVersion;
    @Value("${client.email}")
    String email;

    String userAgent = applicationName + "/" + applicationVersion + "(" + email + ")";
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://search.openfoodfacts.org/search")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("User-Agent", userAgent)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Accept-Encoding", "gzip, deflate, br")
                .build();
    }

}
