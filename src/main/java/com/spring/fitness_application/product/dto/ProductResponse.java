package com.spring.fitness_application.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {
    @JsonProperty("hits")
    List<Product> hits;

}
