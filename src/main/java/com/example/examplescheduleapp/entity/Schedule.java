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

    public Schedule(String name, String title, String contents) {
        user.setName(name);
        this.title = title;
        this.contents = contents;
    }


    public void updateNameAndTitleAndContents(String name, String title, String contents) {
        user.setName(name);
        this.title = title;
        this.contents = contents;
    }
}
