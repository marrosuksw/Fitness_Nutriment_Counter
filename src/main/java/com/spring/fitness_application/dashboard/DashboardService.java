package com.spring.fitness_application.dashboard;


import com.spring.fitness_application.personal_data.*;
import com.spring.fitness_application.product.ProductEntity;
import com.spring.fitness_application.product.ProductService;
import com.spring.fitness_application.product.dto.ProductNutrimentsDTO;
import com.spring.fitness_application.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final ProductService productService;
    private final PersonalDataService personalDataService;
    private final NutrimentInterpreter nutrimentInterpreter = new NutrimentInterpreter();

    @Autowired
    public DashboardService(ProductService productService, PersonalDataService personalDataService) {
        this.productService = productService;
        this.personalDataService = personalDataService;
    }
    public List<ProductEntity> getProductsFromToday(Long id){
        return productService.findAllCreatedToday(id);
    }
    public ProductNutrimentsDTO getDailyNutrimentExpenditure(User user){
        PersonalData personalData = personalDataService.findByUser(user).orElse(null);
        double calorieIntake = nutrimentInterpreter.calculateCalorieIntake(personalData);
        double proteinIntake = nutrimentInterpreter.interpretProteinIntake(personalData);
        double carbohydratesIntake = nutrimentInterpreter.interpretCarbohydrateIntake(personalData);
        double fatIntake = nutrimentInterpreter.interpretFatIntake(personalData);
        return new ProductNutrimentsDTO(
                BigDecimal.valueOf(calorieIntake),
                BigDecimal.valueOf(proteinIntake),
                BigDecimal.valueOf(carbohydratesIntake),
                BigDecimal.valueOf(fatIntake),
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(fatIntake/10)
        );
    }
    public ProductNutrimentsDTO getAllNutrimentsFromProductList(Long id) {
        List<ProductEntity> productList = getProductsFromToday(id);
        var proteinSum = productList
                .stream()
                .map(ProductEntity::getProtein)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var carbohydrateSum = productList
                .stream()
                .map(ProductEntity::getCarbohydrates)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var calorieSum = productList
                .stream()
                .map(ProductEntity::getCalories)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var fatSum = productList
                .stream()
                .map(ProductEntity::getFat)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var sugarSum = productList
                .stream()
                .map(ProductEntity::getSugar)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var saturatedFatSum = productList
                .stream()
                .map(ProductEntity::getSaturatedFat)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ProductNutrimentsDTO(
                calorieSum,
                proteinSum,
                carbohydrateSum,
                fatSum,
                sugarSum,
                saturatedFatSum);
    }


}
