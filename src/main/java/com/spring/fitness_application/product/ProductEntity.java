package com.spring.fitness_application.product;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="products")
@Getter @Setter
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="brands", nullable = false)
    private String brands;
    @Column(name="calories", nullable = false)
    private BigDecimal calories;
    @Column(name="protein", nullable = false)
    private BigDecimal protein;
    @Column(name="carbohydrates", nullable = false)
    private BigDecimal carbohydrates;
    @Column(name="fat", nullable = false)
    private BigDecimal fat;
    @Column(name="sugar", nullable = false)
    private BigDecimal sugar;
    @Column(name="saturated_fat", nullable = false)
    private BigDecimal saturatedFat;

    public ProductEntity(String name, String brands, BigDecimal calories,
                         BigDecimal protein, BigDecimal carbohydrates, BigDecimal fat,
                         BigDecimal sugar, BigDecimal saturatedFat){
        this.name = name;
        this.brands = brands;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.sugar = sugar;
        this.saturatedFat = saturatedFat;
    }
    public ProductEntity(String name, String brands){
        this.name = name;
        this.brands = brands;
        this.calories = BigDecimal.ZERO;
        this.protein = BigDecimal.ZERO;
        this.carbohydrates = BigDecimal.ZERO;
        this.fat = BigDecimal.ZERO;
        this.sugar = BigDecimal.ZERO;
        this.saturatedFat = BigDecimal.ZERO;
    }

}
