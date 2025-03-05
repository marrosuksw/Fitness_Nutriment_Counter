package com.spring.fitness_application.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductNutriments{
        @JsonProperty("energy-kcal_100g") BigDecimal calories;
        @JsonProperty("proteins_100g") BigDecimal protein;
        @JsonProperty("carbohydrates_100g") BigDecimal carbohydrates;
        @JsonProperty("fat_100g") BigDecimal fat;
        @JsonProperty("sugars_100g") BigDecimal sugar;
        @JsonProperty("saturated-fat_100g") BigDecimal saturatedFat;
}
