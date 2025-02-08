package com.example.examplescheduleapp.config;

import com.example.examplescheduleapp.filter.LoggingFilter;
import com.example.examplescheduleapp.filter.SecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<String> publicPaths;
    private final Map<String, List<String>> protectedResources;

    public WebConfig(List<String> publicPaths, Map<String, List<String>> protectedResources) {
        this.publicPaths = publicPaths;
        this.protectedResources = protectedResources;
    }

    @Bean
    public FilterRegistrationBean<SecurityFilter> securityFilter(){
        FilterRegistrationBean<SecurityFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new SecurityFilter(publicPaths, protectedResources));
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter(){
        FilterRegistrationBean<LoggingFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoggingFilter());
        filterFilterRegistrationBean.setOrder(2);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
    }
}
