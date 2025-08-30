package com.spring.fitness_application.personal_data;


import com.spring.fitness_application.personal_data.dto.PersonalDataDTO;
import com.spring.fitness_application.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    public void save(PersonalData personalData) {
        if(personalData == null ){
            throw new IllegalArgumentException("personalData is null");
        }
        personalDataRepository.save(personalData);
    }
    public boolean verifyPersonalData(PersonalDataDTO personalDataDTO) {
        return !(personalDataDTO == null || personalDataDTO.height() == null
                || personalDataDTO.height().intValue() < 0
                || personalDataDTO.height().intValue() > 250 || personalDataDTO.weight() == null
                || personalDataDTO.weight().intValue() < 0
                || personalDataDTO.weight().intValue() > 250 || personalDataDTO.age() == null
                || personalDataDTO.age() < 15
                || personalDataDTO.age() > 100 || personalDataDTO.gender().toString().isEmpty()
                || personalDataDTO.physicalActivity().toString().isEmpty());
    }
    public boolean update(PersonalDataDTO personalDataDto, User user) {
        if(personalDataDto == null ){
            throw new IllegalArgumentException("personalData is null");
        }
        if(!verifyPersonalData(personalDataDto)) return false;
        Optional<PersonalData> existing = personalDataRepository.findByUser(user);
        PersonalData data;
        if(existing.isPresent()){
            data = existing.get();
            data.setHeight(personalDataDto.height());
            data.setWeight(personalDataDto.weight());
            data.setAge(personalDataDto.age());
            data.setGender(personalDataDto.gender());
            data.setPhysicalActivity(personalDataDto.physicalActivity());
        }
        else {
            throw new IllegalArgumentException("personalData not found");
        }
        personalDataRepository.save(data);
        return true;
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
}
