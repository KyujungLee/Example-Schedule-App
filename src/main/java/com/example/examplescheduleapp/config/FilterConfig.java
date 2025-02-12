package com.example.examplescheduleapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

    // 회원가입과 로그인, 일정과 댓글 보기는(/guest) 보안필터를 통과하도록 설정
    @Bean
    public String[] publicPaths() {
        return new String[] {
                "/users/login",
                "/users/signup",
                "/schedules/guest",
                "/schedules/*/guest",
                "/reply/*/guest"
        };
    }

}
