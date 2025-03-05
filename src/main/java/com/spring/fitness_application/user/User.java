package com.spring.fitness_application.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @GeneratedValue
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private long id;
    @Column(name="username", nullable = false)
    private String username;
    @Column(name="password", nullable = false)
    private String password;

    public User() {}

}
