package com.spring.fitness_application.dashboard;

import com.spring.fitness_application.jwt.JwtService;
import com.spring.fitness_application.product.dto.ProductNutrimentsDTO;
import com.spring.fitness_application.user.User;
import com.spring.fitness_application.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public DashboardController(DashboardService dashboardService, JwtService jwtService, UserService userService) {
        this.dashboardService = dashboardService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/sum")
    public ResponseEntity<ProductNutrimentsDTO> getIntake(HttpServletRequest request){
        String token = jwtService.extractTokenFromCookie(request);
        if(token == null || !jwtService.validateToken(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long id = jwtService.extractId(token);
        User user = userService.findById(id);
        return new ResponseEntity<ProductNutrimentsDTO>(
                dashboardService.getAllNutrimentsFromProductList(id),
                HttpStatus.OK
        );
    }
    @GetMapping("/expenditure")
    public ResponseEntity<ProductNutrimentsDTO> getExpenditure(HttpServletRequest request){
        String token = jwtService.extractTokenFromCookie(request);
        if(token == null || !jwtService.validateToken(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long id = jwtService.extractId(token);
        User user = userService.findById(id);
        return new ResponseEntity<>(
                dashboardService.getDailyNutrimentExpenditure(user),
                HttpStatus.OK
        );
    }
}
