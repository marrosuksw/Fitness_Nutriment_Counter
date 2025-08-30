package com.spring.fitness_application.user;

import com.spring.fitness_application.user.dto.LoginRequest;
import com.spring.fitness_application.user.dto.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
       return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }
    public boolean validateCredentials(RegisterRequest registerRequest) {
        return !registerRequest.username().isEmpty() && !registerRequest.password().isEmpty()
                && registerRequest.password().length() > 8 && registerRequest.username().length() > 3
                && registerRequest.username().length() < 16 && registerRequest.password().length() < 16;
    }
    public void create(User user) {
        if(findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> verifyUser(LoginRequest response){
        Optional<User> user = userRepository.findByUsername(response.username());
        String hashedPassword = user.get().getPassword();
        String responsePassword = response.password();
        if(user.isPresent() && bCryptPasswordEncoder.matches(responsePassword, hashedPassword)) {
            return user;
        }
        return Optional.empty();
    }
    public void logout(HttpServletResponse response) {
        Cookie accessCookie = new Cookie("accessToken", null);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);

        Cookie refreshCookie = new Cookie("refreshToken", null);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }
}
