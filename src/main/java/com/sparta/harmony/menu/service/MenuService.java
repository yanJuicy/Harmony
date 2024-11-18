package com.sparta.harmony.menu.service;

import com.sparta.harmony.ai.service.AiService;
import com.sparta.harmony.menu.dto.MenuRequestDto;
import com.sparta.harmony.menu.dto.MenuResponseDto;
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
    private final AiService aiService;

    public MenuResponseDto createMenu(UUID storeId, MenuRequestDto menuRequestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식점"));

        String aiCreateMenuDescription = aiService.aiCreateMenuDescription(menuRequestDto.getDescription());

        Menu menu = Menu.builder()
                .name(menuRequestDto.getName())
                .description(aiCreateMenuDescription)
                .price(menuRequestDto.getPrice())
                .imageUrl(menuRequestDto.getImageUrl())
                .isAvailable(menuRequestDto.isAvailable())
                .store(store)
                .build();

        Menu savedMenu = menuRepository.save(menu);
        aiService.save(menuRequestDto.getDescription(), aiCreateMenuDescription, savedMenu);

        return MenuResponseDto.builder()
                .menuId(savedMenu.getMenuId())
                .storeId(storeId)
                .name(savedMenu.getName())
                .description(savedMenu.getDescription())
                .price(savedMenu.getPrice())
                .imageUrl(savedMenu.getImageUrl())
                .isAvailable(savedMenu.isAvailable())
                .build();
    }

    public MenuResponseDto getMenu(UUID storeId, UUID menuId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식점"));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식"));

        return MenuResponseDto.builder()
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
