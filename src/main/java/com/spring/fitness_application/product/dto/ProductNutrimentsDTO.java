package com.spring.fitness_application.product.dto;


import java.math.BigDecimal;

public record ProductNutrimentsDTO(
        BigDecimal calories,
        BigDecimal protein,
        BigDecimal carbohydrates,
        BigDecimal fat,
        BigDecimal sugar,
        BigDecimal saturatedFat
) {
}
