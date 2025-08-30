package com.spring.fitness_application.product.dto.external_api;

import com.fasterxml.jackson.annotation.JsonProperty;

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
        StringBuilder brandList = new StringBuilder();
        for(String brand: brands){
            brandList.append(brand).append(",");
        }
        return brandList.toString();
    }
}
