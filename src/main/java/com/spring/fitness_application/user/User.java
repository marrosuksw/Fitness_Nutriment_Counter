package com.spring.fitness_application.user;

import com.spring.fitness_application.product.ProductEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.spring.fitness_application.personal_data.PersonalData;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;
    @Column(name="username", nullable = false)
    private String username;
    @Column(name="password", nullable = false)
    private String password;
    @OneToOne(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private PersonalData personalData;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
