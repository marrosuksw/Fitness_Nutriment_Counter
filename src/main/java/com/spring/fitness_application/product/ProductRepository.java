package com.spring.fitness_application.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Override
    Optional<ProductEntity> findById(Long id);
    List<ProductEntity> findByUserId(Long id);
    List<ProductEntity> findByUserIdAndCreated(Long id, LocalDate created);
}
