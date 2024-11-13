package com.sparta.harmony.menu.controller;

import com.sparta.harmony.menu.dto.MenuCreateRequestDto;
import com.sparta.harmony.menu.dto.MenuCreateResponseDto;
import com.sparta.harmony.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<MenuCreateResponseDto> create(@PathVariable String storeId, @RequestBody MenuCreateRequestDto menuCreateRequestDto) {
        MenuCreateResponseDto result = menuService.createMenu(UUID.fromString(storeId), menuCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

}
