package com.example.examplescheduleapp.filter;

import com.example.examplescheduleapp.config.Const;
import com.example.examplescheduleapp.exception.UnauthorizedException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SecurityFilter implements Filter {

    private final List<String> publicPaths;
    private final Map<String, List<String>> protectedResources;
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public SecurityFilter(List<String> publicPaths, Map<String, List<String>> protectedResources) {
        this.publicPaths = publicPaths;
        this.protectedResources = protectedResources;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        if (isPublicPath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        if (requiresAuth(requestURI, method, session)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String requestURI) {
        return publicPaths.stream().anyMatch(path -> pathMatcher.match(path, requestURI));
    }

    private boolean requiresAuth(String requestURI, String method, HttpSession session) {
        return protectedResources.entrySet().stream()
                .anyMatch(entry -> pathMatcher.match(entry.getKey(), requestURI) &&
                        entry.getValue().contains(method.toUpperCase()) &&
                        !isUserAuthorized(session, requestURI));
    }

    private boolean isUserAuthorized(HttpSession session, String requestURI) {
        String loggedInUser = (String) session.getAttribute(Const.LOGIN_USER);
        return loggedInUser != null;
    }
}
