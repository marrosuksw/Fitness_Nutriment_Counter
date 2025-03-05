package com.spring.fitness_application.product.client;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spring.fitness_application.product.Product;
import com.spring.fitness_application.product.dto.ProductResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductClient {

    //TODO change hardcoded values
    String userQuery = "skyr";
    String queryURL = ("?q=product_name.en" + ":\""
            + userQuery + "\""
            + "&page_size=10&page=1&fields=product_name,brands,nutriments");

    private final RestClient restClient;

    public ProductClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductResponse findByName(){
        return restClient.get()
                .uri(queryURL)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
