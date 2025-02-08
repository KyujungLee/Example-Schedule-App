package com.example.examplescheduleapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("[REQUEST] " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
        httpRequest.getSession(false);

        filterChain.doFilter(request, response);
    }
}
