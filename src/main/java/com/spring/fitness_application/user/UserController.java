package com.spring.fitness_application.user;

import com.spring.fitness_application.jwt.JwtService;
import com.spring.fitness_application.personal_data.PersonalData;
import com.spring.fitness_application.personal_data.PersonalDataService;
import com.spring.fitness_application.user.dto.LoginRequest;
import com.spring.fitness_application.user.dto.LoginResponse;
import com.spring.fitness_application.user.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final PersonalDataService personalDataService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService, PersonalDataService personalDataService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.personalDataService = personalDataService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            if(!userService.validateCredentials(registerRequest)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            User user = new User(registerRequest.username(), registerRequest.password());
            userService.create(user);
            personalDataService.save(new PersonalData(
                    registerRequest.personalDataDTO().height(),
                    registerRequest.personalDataDTO().weight(),
                    registerRequest.personalDataDTO().age(),
                    registerRequest.personalDataDTO().physicalActivity(),
                    registerRequest.personalDataDTO().gender(),
                    user
            ));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Error in register, catch section entered.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> verifyUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        Optional<User> user = userService.verifyUser(loginRequest);
        if(user != null && user.isPresent()) {
            String accessToken = jwtService.generateAccessToken(user.get().getId());
            String refreshToken = jwtService.generateRefreshToken(user.get().getId());
            jwtService.setTokenCookies(response, accessToken, refreshToken);
            return new ResponseEntity<>(new LoginResponse("User authorized"), HttpStatus.OK);
        }
        System.out.println("Authorization rejected");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        userService.logout(response);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/me")
    public ResponseEntity<?> verifyCurrentlyLoggedIn(@CookieValue(name = "accessToken", required = false) String accessToken) {
        if(accessToken != null && !accessToken.isEmpty()) {
            System.out.println(accessToken);
            if(jwtService.validateToken(accessToken)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
