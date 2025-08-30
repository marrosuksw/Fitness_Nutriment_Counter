package com.spring.fitness_application.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
//change to Record
public class ProductDTO {
    private Long id;
    private String name;
    private String brands;
    private BigDecimal calories;
    private BigDecimal protein;
    private BigDecimal carbohydrates;
    private BigDecimal fat;
    private BigDecimal sugar;
    private BigDecimal saturatedFat;
    private LocalDate createdAt;

}
