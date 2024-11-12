package com.sparta.harmony.store.service;

import com.sparta.harmony.store.dto.StoreRequestDto;
import com.sparta.harmony.store.dto.StoreResponseDto;
import com.sparta.harmony.store.entity.Category;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.store.entity.StoreCategory;
import com.sparta.harmony.store.repository.CategoryRepository;
import com.sparta.harmony.store.repository.StoreRepository;
import com.sparta.harmony.user.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto) {

        Address address = new Address(storeRequestDto.getAddress(), storeRequestDto.getDetailAddress(), storeRequestDto.getPostcode());

        Store store = Store.builder()
                .storeName(storeRequestDto.getStoreName())
                .phoneNumber(storeRequestDto.getPhoneNumber())
                .address(address)
                .storeCategories(new ArrayList<>())
                .build();

        // StoreCategory 리스트 생성 후 Store에 추가
        List<StoreCategory> storeCategories = storeRequestDto.getCategoryIds().stream()
                .map(categoryId -> {
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new IllegalArgumentException("카테고리 아이디를 찾을 수 없습니다.: " + categoryId));
                    return StoreCategory.builder()
                            .store(store)
                            .category(category)
                            .build();
                })
                .collect(Collectors.toList());

        store.addCategories(storeCategories);

        storeRepository.save(store);

        List<String> categoryNames = storeCategories.stream()
                .map(storeCategory -> storeCategory.getCategory().getCategoryName())
                .collect(Collectors.toList());

        return new StoreResponseDto(store.getStoreId(), store.getStoreName(), store.getPhoneNumber(), address, categoryNames);
    }
}
