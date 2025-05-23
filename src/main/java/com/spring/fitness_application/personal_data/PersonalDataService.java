package com.spring.fitness_application.personal_data;


import com.spring.fitness_application.personal_data.dto.PersonalDataDTO;
import com.spring.fitness_application.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class PersonalDataService {

    private final PersonalDataRepository personalDataRepository;

    @Autowired
    public PersonalDataService(PersonalDataRepository personalDataRepository) {
        this.personalDataRepository = personalDataRepository;
    }
    //TODO BMI calc
    public BigDecimal calculateBmi(){
        return new BigDecimal(BigInteger.valueOf(0));
    }

    public void save(PersonalData personalData) {
        if(personalData == null ){
            throw new IllegalArgumentException("personalData is null");
        }
        personalDataRepository.save(personalData);
    }
    public void delete(PersonalData personalData) {
        personalDataRepository.delete(personalData);
    }
    public Optional<PersonalData> findById(Long id) {
        return personalDataRepository.findById(id);
    }
    public Optional<PersonalData> findByUser(User user) {
        return personalDataRepository.findByUser(user);
    }
    public PersonalDataDTO convertEntityToDTO(Optional<PersonalData> personalData) {
        if(personalData.isEmpty()) return null;
        PersonalDataDTO dto = new PersonalDataDTO(
                personalData.get().getHeight(),
                personalData.get().getWeight(),
                personalData.get().getAge(),
                personalData.get().getPhysicalActivity(),
                personalData.get().getGender()

        );
        return dto;
    }
    public PersonalData convertDTOToEntity(PersonalDataDTO dto, User user) {
        if(dto == null) return null;
        return new PersonalData(
                dto.height(),
                dto.weight(),
                dto.age(),
                dto.physicalActivity(),
                dto.gender(),
                user
        );
    }
}
