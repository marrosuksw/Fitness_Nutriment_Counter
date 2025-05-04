package com.spring.fitness_application.product;


import com.spring.fitness_application.product.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    //This service connects to the list of products already added by the user
    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> findAllProducts() {
        return productRepository.findAll();
    }
    public ProductEntity saveProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }
    public void deleteProduct(ProductEntity productEntity) {
        productRepository.delete(productEntity);
    }
    public Optional<ProductEntity> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
