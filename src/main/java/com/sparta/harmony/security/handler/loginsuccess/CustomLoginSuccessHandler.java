package com.sparta.harmony.security.handler.loginsuccess;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;


@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // 성공 메시지를 JSON 형태로 작성
        String jsonResponse = "{\"status\": " + HttpServletResponse.SC_OK + ", \"message\": \"로그인 성공\"}";

        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
