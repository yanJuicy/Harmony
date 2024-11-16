package com.sparta.harmony.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.harmony.jwt.JwtUtil;
import com.sparta.harmony.security.handler.loginfail.CustomLoginFailureHandler;
import com.sparta.harmony.security.handler.loginsuccess.CustomLoginSuccessHandler;
import com.sparta.harmony.user.dto.LoginRequestDto;
import com.sparta.harmony.user.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final CustomLoginFailureHandler customLoginFailureHandler;
    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                   CustomLoginFailureHandler customLoginFailureHandler,
                                   CustomLoginSuccessHandler customLoginSuccessHandler
    ) {
        this.jwtUtil = jwtUtil;
        this.customLoginFailureHandler = customLoginFailureHandler;
        this.customLoginSuccessHandler = customLoginSuccessHandler;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getEmail();
        Role role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(email, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        customLoginSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        customLoginFailureHandler.onAuthenticationFailure(request, response, failed);
    }

}