package com.example.examplescheduleapp.filter;

import com.example.examplescheduleapp.config.Const;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RequiredArgsConstructor
public class SecurityFilter implements Filter {

    private final String[] publicPaths;


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

        // 예외 발생시 명시적으로 가로채어, 톰캣이 오류를 감지하고 500 에러를 클라이언트에 응답하는 것을 방지
        try {
            HttpSession session = httpRequest.getSession(false);

            // 세션이 없으면 바로 예외 발생 (NPE 방지)
            if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
            }

            // 정상적으로 로그인된 사용자만 다음 필터 실행
            filterChain.doFilter(request, response);

        // 예외 발생 시 예외를 가로채어 FilterErrorController 로 강제 포워딩
        // 이후 ControllerAdvice(GlobalExceptionHandler) 가 가로채어 형식에 맞춘 예외 메시지 응답 가능
        } catch (ResponseStatusException e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error/auth").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("/error/internal").forward(request, response);
        }
    }

    private boolean isPublicPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(publicPaths, requestURI);
    }

}
