package com.example.examplescheduleapp.filter;

import com.example.examplescheduleapp.config.Const;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class SecurityFilter implements Filter {

    private final String[] publicPaths;

    public SecurityFilter(String[] publicPaths) {
        this.publicPaths = publicPaths;
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

        try {
            HttpSession session = httpRequest.getSession(false);

            // 세션이 없으면 바로 예외 발생 (NPE 방지)
            if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
            }

            // 정상적으로 로그인된 사용자만 다음 필터 실행
            filterChain.doFilter(request, response);

        } catch (ResponseStatusException e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error/unauthorized").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error/internal").forward(request, response);
        }

    }

    private boolean isPublicPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(publicPaths, requestURI);
    }

}
