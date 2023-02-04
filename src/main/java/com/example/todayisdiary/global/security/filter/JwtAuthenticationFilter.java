package com.example.todayisdiary.global.security.filter;

import com.example.todayisdiary.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request 에서 token 취함.
        String token = jwtProvider.resolveToken(request);

        if(token != null && jwtProvider.validateToken(token)){

            // 토큰이 유효하면 토큰으로부터 유저 정보를 가져옴
            Authentication authentication = jwtProvider.getAuthentication(token);
            // SpringContext 에 Authentication 객체를 가져옴
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }
}
