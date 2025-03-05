package com.spring.fitness_application.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(long id) {
       return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }
    public User create(User user) {
        return userRepository.save(user);
    }
    public void delete(User user) {
        userRepository.delete(user);
    }

}
