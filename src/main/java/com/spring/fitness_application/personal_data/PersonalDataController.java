package com.spring.fitness_application.personal_data;


import com.spring.fitness_application.jwt.JwtService;
import com.spring.fitness_application.personal_data.dto.PersonalDataDTO;
import com.spring.fitness_application.user.User;
import com.spring.fitness_application.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class PersonalDataController {

    private final PersonalDataService personalDataService;
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public PersonalDataController(PersonalDataService personalDataService, JwtService jwtService, UserService userService) {
        this.personalDataService = personalDataService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/data")
    public ResponseEntity<PersonalDataDTO> getPersonalData(HttpServletRequest request) {
        //Server validates the token and extracts sender ID
        //Server fetches users personal data with username
        //Server sends the data via get: PersonalData object
        String token = jwtService.extractTokenFromCookie(request);
        if(token == null || !jwtService.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long id = jwtService.extractId(token);
        User user = userService.findById(id);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Optional<PersonalData> personalData = personalDataService.findByUser(user);
        if(personalData.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        PersonalDataDTO personalDataDTO = personalDataService.convertEntityToDTO(personalData);

        return new ResponseEntity<>(personalDataDTO, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updatePersonalData(@RequestBody PersonalDataDTO personalDataDto, HttpServletRequest request) {

        String token = jwtService.extractTokenFromCookie(request);
        if(token == null || !jwtService.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long id = jwtService.extractId(token);
        User user = userService.findById(id);
        if(id == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if(personalDataDto == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        boolean updateVerification = personalDataService.update(personalDataDto, user);
        if(!updateVerification)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
