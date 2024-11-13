package com.sparta.harmony.user.controller;

import com.sparta.harmony.user.dto.UserRequestDto;
import com.sparta.harmony.user.dto.UserResponseDto;
import com.sparta.harmony.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable UUID userId) {
        UserResponseDto responseDto = userService.getUser(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID userId,
            @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.updateUser(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId, @RequestParam String deletedBy) {
        userService.deleteUser(userId, deletedBy);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
