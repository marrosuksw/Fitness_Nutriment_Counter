package com.spring.fitness_application.personal_data;

import com.spring.fitness_application.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalDataRepository
        extends JpaRepository<PersonalData, Long> {
    Optional<PersonalData> findById(Long id);
    Optional<PersonalData> findByUser(User user);
}
