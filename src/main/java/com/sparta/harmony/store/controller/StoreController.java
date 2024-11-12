package com.sparta.harmony.store.controller;

import com.sparta.harmony.store.dto.StoreRequestDto;
import com.sparta.harmony.store.dto.StoreResponseDto;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping
    public StoreResponseDto createStore(@RequestBody StoreRequestDto storeRequestDto) {
        return storeService.createStore(storeRequestDto);
    }

    @PutMapping("/{storeId}")
    public StoreResponseDto updateStore(@PathVariable UUID storeId, @RequestBody StoreRequestDto requestDto) {
        return storeService.updateStore(storeId, requestDto);
    }

    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
    }
}
