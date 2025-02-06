package com.example.examplescheduleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ExampleScheduleAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleScheduleAppApplication.class, args);
    }

}
