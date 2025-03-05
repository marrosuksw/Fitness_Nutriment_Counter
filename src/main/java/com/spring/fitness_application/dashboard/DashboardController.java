package com.spring.fitness_application.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {


    @GetMapping("/dashboard")
    public String dashboardTest(){
        return ("This will be the main dashboard");
    }

}
