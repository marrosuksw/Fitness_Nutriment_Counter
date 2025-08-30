package com.spring.fitness_application.product;


import com.spring.fitness_application.product.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<ProductEntity> findAllProducts(Long userId) {
        return productRepository.findByUserId(userId);
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
    public List<ProductEntity> findAllCreatedToday(Long userId) {
        LocalDate today = LocalDate.now();
        return productRepository.findByUserIdAndCreated(userId, today);
    }
    public List<ProductDTO> fetchAllFromList(Long id) {
        List<ProductEntity> productList = findAllProducts(id);
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (ProductEntity productEntity : productList) {
            productDTOList.add(new ProductDTO(
                    productEntity.getId(),
                    productEntity.getName(),
                    productEntity.getBrands(),
                    productEntity.getCalories(),
                    productEntity.getProtein(),
                    productEntity.getCarbohydrates(),
                    productEntity.getFat(),
                    productEntity.getSugar(),
                    productEntity.getSaturatedFat(),
                    productEntity.getCreated()
            ));
        }
        return productDTOList;
    }
}
