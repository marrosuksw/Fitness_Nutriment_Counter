package com.spring.fitness_application.product;

import com.spring.fitness_application.product.dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Override
    Optional<ProductEntity> findById(Long id);
    List<ProductEntity> findByUserId(Long id);
}
