package com.spring.fitness_application.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty("product_name")
    private String product_name;
    @JsonProperty("brands")
    private List<String> brands;
    @JsonProperty("nutriments")
    ProductNutriments productNutriments;

}
