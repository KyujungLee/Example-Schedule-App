package com.example.examplescheduleapp.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

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

    public Schedule(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }


    public void updateTitleAndContents(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
