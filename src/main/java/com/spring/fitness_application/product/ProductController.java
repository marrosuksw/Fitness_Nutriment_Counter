package com.spring.fitness_application.product;

import com.spring.fitness_application.jwt.JwtService;
import com.spring.fitness_application.product.client.ProductClient;
import com.spring.fitness_application.product.dto.external_api.Product;
import com.spring.fitness_application.product.dto.ProductDTO;
import com.spring.fitness_application.product.dto.external_api.ProductResponse;
import com.spring.fitness_application.user.User;
import com.spring.fitness_application.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductClient productClient;
    private final ProductService productService;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public ProductController(ProductClient productClient, ProductService productService, UserService userService, JwtService jwtService) {
        this.productClient = productClient;
        this.productService = productService;
        this.userService = userService;
        this.jwtService = jwtService;
    }
    //Search results endpoint
    @GetMapping("/search")
    public ResponseEntity<ProductResponse> findByName(@RequestParam String name, HttpServletRequest request) {
        String token = jwtService.extractTokenFromCookie(request);
        if(!jwtService.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(productClient.findByName(name));
    }
    // Endpoint with a list of products added to the database by the currently logged in user
    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> findAllByUserId(HttpServletRequest request) {
        String token = jwtService.extractTokenFromCookie(request);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long id = jwtService.extractId(token);
        return ResponseEntity.ok(productService.fetchAllFromList(id));
    }
    //Saving a product given by the user; fetched from frontend
    @PostMapping("/add")
    public ResponseEntity<ProductEntity> saveProduct
            (@RequestBody Product product, HttpServletRequest request) {
        try {
            String token = jwtService.extractTokenFromCookie(request);
            if(!jwtService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if (product != null) {
                Long id = jwtService.extractId(token);
                User userFromToken = userService.findById(id);
                ProductEntity productEntity;
                LocalDate now = LocalDate.now();
                if (product.getProductNutriments() != null) {
                    productEntity = new ProductEntity(
                            product.getProduct_name(),
                            product.getBrands() == null ? "Unknown" : product.brandListToString(),
                            product.getProductNutriments()
                                    .getCalories() == null ? BigDecimal.ZERO : product.getProductNutriments().getCalories(),
                            product.getProductNutriments()
                                    .getProtein() == null ? BigDecimal.ZERO : product.getProductNutriments().getProtein(),
                            product.getProductNutriments()
                                    .getCarbohydrates() == null ? BigDecimal.ZERO : product.getProductNutriments().getCarbohydrates(),
                            product.getProductNutriments()
                                    .getFat() == null ? BigDecimal.ZERO : product.getProductNutriments().getFat(),
                            product.getProductNutriments()
                                    .getSugar() == null ? BigDecimal.ZERO : product.getProductNutriments().getSugar(),
                            product.getProductNutriments()
                                    .getSaturatedFat() == null ? BigDecimal.ZERO : product.getProductNutriments().getSaturatedFat(),
                            userFromToken,
                            now

                    );
                }
                else{
                    productEntity = new ProductEntity(
                            product.getProduct_name(),
                            product.getBrands() == null ? "Unknown" : product.brandListToString(),
                            userFromToken,
                            now
                    );
                }
                productService.saveProduct(productEntity);
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
    public ResponseEntity<ProductEntity> deleteProduct(@PathVariable Long id, HttpServletRequest request) {
        try{
            String token = jwtService.extractTokenFromCookie(request);
            if(!jwtService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
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
