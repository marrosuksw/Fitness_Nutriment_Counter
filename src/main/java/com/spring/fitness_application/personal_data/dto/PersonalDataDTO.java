package com.spring.fitness_application.personal_data.dto;

import com.spring.fitness_application.personal_data.Gender;
import com.spring.fitness_application.personal_data.PhysicalActivity;

import java.math.BigDecimal;

public record PersonalDataDTO(
        BigDecimal height,
        BigDecimal weight,
        Integer age,
        PhysicalActivity physicalActivity,
        Gender gender
) {

}
