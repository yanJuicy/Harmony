package com.sparta.harmony.user.service;

import com.sparta.harmony.user.entity.User;
import com.sparta.harmony.user.repository.UserRepository;
import com.sparta.harmony.user.dto.UserRequestDto;
import com.sparta.harmony.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 생성
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = User.builder()
                .userName(requestDto.getUserName())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .role(requestDto.getRole())
                .address(requestDto.getAddress())
                .build();
        userRepository.save(user);
        return new UserResponseDto(user);
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
