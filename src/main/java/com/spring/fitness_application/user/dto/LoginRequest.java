package com.spring.fitness_application.user.dto;


public record LoginRequest(
        String username,
        String password
) {}
