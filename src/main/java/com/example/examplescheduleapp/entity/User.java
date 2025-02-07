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
    private String username;

    @Setter
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Setter
    @Column(length = 20, nullable = false)
    private String password;

    @Setter
    @Column(length = 10, nullable = false, unique = true)
    private String nickname;


    public User() {}

    public User(String username, String nickname, String email, String password) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String name){
        this.username = name;
    }
}
