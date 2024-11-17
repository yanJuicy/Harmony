package com.sparta.harmony.menu.service;

import com.sparta.harmony.menu.dto.MenuCreateRequestDto;
import com.sparta.harmony.menu.dto.MenuCreateResponseDto;
import com.sparta.harmony.menu.dto.MenuGetResponseDto;
import com.sparta.harmony.menu.entity.Menu;
import com.sparta.harmony.menu.repository.MenuRepository;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuCreateResponseDto createMenu(UUID storeId, MenuCreateRequestDto menuCreateRequestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식점"));

        Menu menu = Menu.builder()
                .name(menuCreateRequestDto.getName())
                .description(menuCreateRequestDto.getDescription())
                .price(menuCreateRequestDto.getPrice())
                .imageUrl(menuCreateRequestDto.getImageUrl())
                .isAvailable(menuCreateRequestDto.isAvailable())
                .store(store)
                .build();

        Menu savedMenu = menuRepository.save(menu);

        return MenuCreateResponseDto.builder()
                .menuId(savedMenu.getMenuId())
                .storeId(storeId)
                .name(savedMenu.getName())
                .description(savedMenu.getDescription())
                .price(savedMenu.getPrice())
                .imageUrl(savedMenu.getImageUrl())
                .isAvailable(savedMenu.isAvailable())
                .build();
    }

    public MenuGetResponseDto getMenu(UUID storeId, UUID menuId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식점"));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식"));

        return MenuGetResponseDto.builder()
                .menuId(menuId)
                .storeId(storeId)
                .name(menu.getName())
                .description(menu.getDescription())
                .imageUrl(menu.getImageUrl())
                .price(menu.getPrice())
                .isAvailable(menu.isAvailable())
                .build();
    }
}
