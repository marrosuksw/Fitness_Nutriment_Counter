package com.spring.fitness_application.user.dto;


import com.spring.fitness_application.user.PhysicalActivity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UserDataResponse {

    private BigDecimal weight;
    private BigDecimal height;
    private Integer age;
    private PhysicalActivity physicalActivity;
    private String gender;

    public UserDataResponse(BigDecimal weight, BigDecimal height, Integer age, PhysicalActivity physicalActivity, String gender) {
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.physicalActivity = physicalActivity;
        this.gender = gender;
    }
}
