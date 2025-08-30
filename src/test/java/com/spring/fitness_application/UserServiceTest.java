package com.spring.fitness_application;

import com.spring.fitness_application.personal_data.Gender;
import com.spring.fitness_application.personal_data.PhysicalActivity;
import com.spring.fitness_application.personal_data.dto.PersonalDataDTO;
import com.spring.fitness_application.user.User;
import com.spring.fitness_application.user.UserRepository;
import com.spring.fitness_application.user.UserService;
import com.spring.fitness_application.user.dto.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private UserService userService;

    private User sampleUser;
    private PersonalDataDTO samplePersonalDataDTO;

    @BeforeEach
    void setup() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setUsername("testUser");
        sampleUser.setPassword("plainPassword");
        samplePersonalDataDTO = new PersonalDataDTO(BigDecimal.valueOf(170.0), BigDecimal.valueOf(75.0), 22, PhysicalActivity.HIGH, Gender.MALE);
    }

    @Test
    void testFindById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        User result = userService.findById(1L);

        assertEquals("testUser", result.getUsername());
        verify(userRepository).findById(1L);
    }

    @Test
    void testFindById_UserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.findById(99L));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testValidateCredentials_Valid() {
        RegisterRequest req = new RegisterRequest("testUser", "plainPassword", samplePersonalDataDTO);
        assertTrue(userService.validateCredentials(req));
    }

    @Test
    void testValidateCredentials_Invalid() {
        RegisterRequest req = new RegisterRequest("", "short", samplePersonalDataDTO);
        assertFalse(userService.validateCredentials(req));
    }
}
