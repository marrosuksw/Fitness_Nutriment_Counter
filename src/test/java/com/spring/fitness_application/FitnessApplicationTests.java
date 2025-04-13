package com.spring.fitness_application;

import com.spring.fitness_application.user.UserService;
import com.spring.fitness_application.user.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
class FitnessApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void userLoginResponseTest(){
		LoginResponse loginResponse = new LoginResponse("admin","admin");
		assertEquals("admin", loginResponse.username());
	}




}
