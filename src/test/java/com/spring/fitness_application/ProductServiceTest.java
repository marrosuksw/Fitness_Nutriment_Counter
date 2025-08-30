package com.spring.fitness_application;

import com.spring.fitness_application.product.ProductEntity;
import com.spring.fitness_application.product.ProductRepository;
import com.spring.fitness_application.product.ProductService;
import com.spring.fitness_application.product.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductEntity sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProduct = new ProductEntity();
        sampleProduct.setId(1L);
        sampleProduct.setName("Protein Bar");
        sampleProduct.setBrands("BrandX");
        sampleProduct.setCalories(BigDecimal.valueOf(200));
        sampleProduct.setProtein(BigDecimal.valueOf(15.0));
        sampleProduct.setCarbohydrates(BigDecimal.valueOf(20.0));
        sampleProduct.setFat(BigDecimal.valueOf(5.0));
        sampleProduct.setSugar(BigDecimal.valueOf(10.0));
        sampleProduct.setSaturatedFat(BigDecimal.valueOf(2.0));
        sampleProduct.setCreated(LocalDate.now());
    }

    @Test
    void testFindAllProducts() {
        when(productRepository.findByUserId(123L))
                .thenReturn(Arrays.asList(sampleProduct));

        List<ProductEntity> result = productService.findAllProducts(123L);

        assertEquals(1, result.size());
        assertEquals("Protein Bar", result.get(0).getName());
        verify(productRepository, times(1)).findByUserId(123L);
    }

    @Test
    void testSaveProduct() {
        when(productRepository.save(sampleProduct)).thenReturn(sampleProduct);

        ProductEntity result = productService.saveProduct(sampleProduct);

        assertEquals("Protein Bar", result.getName());
        verify(productRepository, times(1)).save(sampleProduct);
    }

    @Test
    void testDeleteProduct() {
        productService.deleteProduct(sampleProduct);

        verify(productRepository, times(1)).delete(sampleProduct);
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        Optional<ProductEntity> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Protein Bar", result.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllCreatedToday() {
        LocalDate today = LocalDate.now();
        when(productRepository.findByUserIdAndCreated(123L, today))
                .thenReturn(Arrays.asList(sampleProduct));

        List<ProductEntity> result = productService.findAllCreatedToday(123L);

        assertEquals(1, result.size());
        assertEquals("Protein Bar", result.get(0).getName());
        verify(productRepository, times(1)).findByUserIdAndCreated(123L, today);
    }
}
