package com.spring.fitness_application.personal_data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.spring.fitness_application.user.User;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="personal_data")
public class PersonalData {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;
    @Column(name="weight", nullable = false)
    private BigDecimal weight;
    @Column(name="height", nullable = false)
    private BigDecimal height;
    @Column(name="age", nullable = false)
    private Integer age;
    @Enumerated(EnumType.STRING)
    @Column(name="physical_activity", nullable = false)
    private PhysicalActivity physicalActivity;
    @Column(name="gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne
    @JoinColumn(name = "user_id_fk", nullable = false)
    private User user;

    public PersonalData(BigDecimal height, BigDecimal weight, Integer age, PhysicalActivity physicalActivity, Gender gender, User user) {
        this.weight = height;
        this.height = weight;
        this.age = age;
        this.physicalActivity = physicalActivity;
        this.gender = gender;
        this.user = user;
    }
}
