package com.spring.fitness_application.user.dto;

import com.spring.fitness_application.personal_data.dto.PersonalDataDTO;

public record RegisterRequest(
        String username,
        String password,
        PersonalDataDTO personalDataDTO) {
}
