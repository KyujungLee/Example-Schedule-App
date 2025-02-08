package com.example.examplescheduleapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class FilterConfig {

    @Bean
    public List<String> publicPaths() {
        return Arrays.asList(
                "/users/login",
                "/users/signup",
                "/schedules",
                "/schedules/**"
        );
    }

    @Bean
    public Map<String, List<String>> protectedResources() {
        Map<String, List<String>> map = new HashMap<>();

        map.put("/users", Arrays.asList("GET", "PATCH", "DELETE"));
        map.put("/users/**", Arrays.asList("GET", "PATCH", "DELETE"));

        map.put("/schedules", Arrays.asList("POST", "PATCH", "DELETE"));
        map.put("/schedules/**", Arrays.asList("POST", "PATCH", "DELETE"));

        return map;
    }
}
