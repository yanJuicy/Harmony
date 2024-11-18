package com.sparta.harmony.menu.controller;

import com.sparta.harmony.common.dto.ApiResponseDto;
import com.sparta.harmony.common.handler.success.SuccessResponseHandler;
import com.sparta.harmony.menu.dto.MenuRequestDto;
import com.sparta.harmony.menu.dto.MenuResponseDto;
import com.sparta.harmony.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<ApiResponseDto<MenuResponseDto>> create(@PathVariable String storeId, @RequestBody MenuRequestDto menuRequestDto) {
        MenuResponseDto result = menuService.createMenu(UUID.fromString(storeId), menuRequestDto);

        return new SuccessResponseHandler().handleSuccess(HttpStatus.CREATED, "메뉴 생성 성공", result);
    }

    @GetMapping("/{storeId}/menus/{menuId}")
    public ResponseEntity<ApiResponseDto<MenuResponseDto>> get(@PathVariable String storeId, @PathVariable String menuId) {
        MenuResponseDto result = menuService.getMenu(UUID.fromString(storeId), UUID.fromString(menuId));

        return new SuccessResponseHandler().handleSuccess(HttpStatus.CREATED, "메뉴 조회 성공", result);
    }


}
