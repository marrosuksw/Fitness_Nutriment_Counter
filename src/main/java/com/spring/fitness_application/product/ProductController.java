package com.spring.fitness_application.product;

import com.spring.fitness_application.product.client.ProductClient;
import com.spring.fitness_application.product.dto.Product;
import com.spring.fitness_application.product.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductClient productClient;
    private final ProductService productService;


    @Autowired
    public ProductController(ProductClient productClient, ProductService productService) {
        this.productClient = productClient;
        this.productService = productService;
    }
    //Search results
    @GetMapping("/search")
    public ResponseEntity<ProductResponse> findByName(@RequestParam String name) {
        System.out.println(productClient.findByName(name));
        return ResponseEntity.ok(productClient.findByName(name));
    }
    //List of products added to the database by the user
    @GetMapping("/list")
    public ResponseEntity<List<ProductEntity>> findAll(){
        return ResponseEntity.ok(productService.findAllProducts());
    }
    //Saving a product given by the user; fetched from frontend
    @PostMapping("/add")
    public ResponseEntity<ProductEntity> saveProduct
            (@RequestBody Product product){
        try {
            if (product != null) {
                ProductEntity productEntity;
                if (product.getProductNutriments() != null) {
                    productEntity = new ProductEntity(
                            product.getProduct_name(),
                            product.getBrands() == null ? "Unknown" : product.brandListToString(),
                            product.getProductNutriments().getCalories() == null ? BigDecimal.ZERO : product.getProductNutriments().getCalories(),
                            product.getProductNutriments().getProtein() == null ? BigDecimal.ZERO : product.getProductNutriments().getProtein(),
                            product.getProductNutriments().getCarbohydrates() == null ? BigDecimal.ZERO : product.getProductNutriments().getCarbohydrates(),
                            product.getProductNutriments().getFat() == null ? BigDecimal.ZERO : product.getProductNutriments().getFat(),
                            product.getProductNutriments().getSugar() == null ? BigDecimal.ZERO : product.getProductNutriments().getSugar(),
                            product.getProductNutriments().getSaturatedFat() == null ? BigDecimal.ZERO : product.getProductNutriments().getSaturatedFat()
                    );
                }
                else{
                    productEntity = new ProductEntity(
                            product.getProduct_name(),
                            product.getBrands() == null ? "Unknown" : product.brandListToString()
                    );
                }
                productService.saveProduct(productEntity);
                System.out.println(productEntity.toString());
                return new ResponseEntity<>(HttpStatus.CREATED);

            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    //
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductEntity> deleteProduct(@PathVariable Long id) {
        try{
            Optional<ProductEntity> foundProduct = productService.getProductById(id);
            foundProduct.ifPresent(productService::deleteProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
