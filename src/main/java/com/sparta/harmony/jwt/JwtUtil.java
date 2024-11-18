package com.sparta.harmony.jwt;

import com.sparta.harmony.user.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil") // 로깅 기능을 위한 어노테이션
@Component // Spring Bean으로 등록
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization"; // HTTP Header에서 토큰을 가져오기 위한 KEY 값
    public static final String AUTHORIZATION_KEY = "auth"; // 사용자 권한을 표현하기 위한 KEY 값
    public static final String BEARER_PREFIX = "Bearer "; // 토큰 앞에 붙는 접두사
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 토큰 유효 시간: 60분

    @Value("${jwt.secret.key}") // application.properties 파일에서 비밀 키 설정
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 서명 알고리즘

    // Secret Key를 디코딩하여 key 변수에 저장
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 토큰 생성
    public String createToken(String email, Role role) {
        Date date = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(email) // 사용자 식별자 값으로 email 사용
                .claim(AUTHORIZATION_KEY, role) // 사용자 권한 추가
                .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간 설정
                .setIssuedAt(date) // 발급 시간 설정
                .signWith(key, signatureAlgorithm) // 서명 알고리즘과 키 설정
                .compact(); // JWT 문자열 생성
    }

    // HTTP 헤더에서 JWT 토큰 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length()); // "Bearer " 접두사 제거 후 반환
        }
        return null;
    }

    // JWT 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    // JWT 토큰에서 사용자 정보(Claims) 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}