package com.spring.fitness_application.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    @JsonProperty("product_name")
    private String product_name;
    @JsonProperty("brands")
    private List<String> brands;
    @JsonProperty("nutriments")
    ProductNutriments productNutriments;

    public String brandListToString(){
        return brands.toString();
    }
}
