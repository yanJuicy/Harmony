package com.sparta.harmony.user.service;

import com.sparta.harmony.jwt.JwtUtil;
import com.sparta.harmony.user.dto.LoginRequestDto;
import com.sparta.harmony.user.entity.User;
import com.sparta.harmony.user.repository.UserRepository;
import com.sparta.harmony.user.dto.UserRequestDto;
import com.sparta.harmony.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 유저 생성
    @Transactional
    public UserResponseDto createUser(@Valid UserRequestDto requestDto) {
        String encryptedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = User.builder()
                .userName(requestDto.getUserName())
                .email(requestDto.getEmail())
                .password(encryptedPassword)
                .role(requestDto.getRole())
                .address(requestDto.getAddress())
                .build();
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    // 로그인 - 토큰발급
    @Transactional(readOnly = true)
    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 비밀번호 확인
        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            // 비밀번호가 맞으면 JWT 토큰을 생성하여 반환
            return jwtUtil.createToken(user.getEmail(), user.getRole());
        } else {
            return null; // 로그인 실패
        }
    }

    // 유저 조회
    @Transactional(readOnly = true)
    public UserResponseDto getUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        return new UserResponseDto(user);
    }

    // 유저 수정
    @Transactional
    public UserResponseDto updateUser(UUID userId, UserRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        user.updateUserInfo(requestDto.getUserName(), requestDto.getPassword(), requestDto.getRole(), requestDto.getAddress());
        return new UserResponseDto(user);
    }

    // 유저 삭제 (소프트 삭제)
    @Transactional
    public void deleteUser(UUID userId, String deletedBy) {
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // 유저의 리뷰도 함께 soft delete 처리
        user.getReviews().forEach(review -> review.softDelete(deletedBy));

        // 유저 soft delete 처리
        user.softDelete(deletedBy);

        userRepository.save(user); // 변경 사항 저장
    }
}
