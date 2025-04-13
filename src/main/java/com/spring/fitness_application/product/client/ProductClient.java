package com.spring.fitness_application.product.client;

import com.spring.fitness_application.product.dto.ProductResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductResponse findByName(String productNameQuery) {
        String queryURL = ("?q=product_name.en" + ":\""
                + productNameQuery + "\""
                + "&page_size=10&page=1&fields=product_name,brands,nutriments");
        return restClient.get()
                .uri(queryURL)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
