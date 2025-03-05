package com.spring.fitness_application.product;

import com.spring.fitness_application.product.client.ProductClient;
import com.spring.fitness_application.product.dto.ProductResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductClient productClient;

    public ProductController(ProductClient productClient) {
        this.productClient = productClient;
    }
    @GetMapping("/search")
    public ProductResponse findByName() {
        System.out.println(productClient.findByName());
        return productClient.findByName();
    }
}
