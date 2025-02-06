package com.example.examplescheduleapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 10)
    private String name;

    @Setter
    @Column(length = 100, nullable = false, unique = true)
    private String email;


    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void updateNameAndEmail(String name, String email){
        this.name = name;
        this.email = email;
    }
}
