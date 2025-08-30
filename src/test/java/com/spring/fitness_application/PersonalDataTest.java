package com.spring.fitness_application;

import com.spring.fitness_application.personal_data.*;
import com.spring.fitness_application.personal_data.dto.PersonalDataDTO;
import com.spring.fitness_application.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonalDataServiceTest {

    @Mock
    private PersonalDataRepository personalDataRepository;

    @InjectMocks
    private PersonalDataService personalDataService;

    private User sampleUser;
    private PersonalData sampleData;
    private PersonalDataDTO validDTO;

    @BeforeEach
    void setup() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setUsername("testUser");

        sampleData = new PersonalData();
        sampleData.setId(1L);
        sampleData.setUser(sampleUser);
        sampleData.setHeight(BigDecimal.valueOf(180));
        sampleData.setWeight(BigDecimal.valueOf(80));
        sampleData.setAge(25);
        sampleData.setGender(Gender.MALE);
        sampleData.setPhysicalActivity(PhysicalActivity.MODERATE);

        validDTO = new PersonalDataDTO(
                BigDecimal.valueOf(180),
                BigDecimal.valueOf(80),
                25,
                PhysicalActivity.MODERATE,
                Gender.MALE
        );
    }

    @Test
    void testSave_Null_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> personalDataService.save(null));
    }

    @Test
    void testSave_Valid() {
        personalDataService.save(sampleData);
        verify(personalDataRepository).save(sampleData);
    }

    @Test
    void testVerifyPersonalData_Valid() {
        assertTrue(personalDataService.verifyPersonalData(validDTO));
    }
}