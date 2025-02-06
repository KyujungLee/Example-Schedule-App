package com.example.examplescheduleapp.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = true)
    private String contents;


    public Schedule() {}

    public Schedule(String name, String email, String title, String contents) {
        user.setName(name);
        user.setEmail(email);
        this.title = title;
        this.contents = contents;
    }


    public void updateNameAndEmailAndTitleAndContents(String name, String email, String title, String contents) {
        user.setName(name);
        user.setEmail(email);
        this.title = title;
        this.contents = contents;
    }
}
