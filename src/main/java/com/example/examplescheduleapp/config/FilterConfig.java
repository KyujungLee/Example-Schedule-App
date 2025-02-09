package com.example.examplescheduleapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

    @Bean
    public String[] publicPaths() {
        return new String[] {
                "/users/login",
                "/users/signup"
        };
    }

}
