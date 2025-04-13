package com.spring.fitness_application.user;

import com.spring.fitness_application.user.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findById(long id) {
       return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }
    public User create(User user) {
       // user.setPassword(hashPassword(user.getPassword()));
        return userRepository.save(user);
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public void delete(User user) {
        userRepository.delete(user);
    }
    //TODO return some token or stuff that generates separately, use ''matches'' for encoded password comparison
    public boolean verifyUser(LoginResponse response){
        Optional<User> user = findByUsername(response.username());
        boolean matches = false;
        if(user.isPresent()){
            matches = (response.password().equals(user.get().getPassword()));
        }
        return matches;
    }
}
