package com.sparta.harmony.store.service;

import com.sparta.harmony.review.entity.Review;
import com.sparta.harmony.review.repository.ReviewRepository;
import com.sparta.harmony.store.dto.StoreDetailResponseDto;
import com.sparta.harmony.store.dto.StoreRequestDto;
import com.sparta.harmony.store.dto.StoreResponseDto;
import com.sparta.harmony.store.dto.StoreSearchResponseDto;
import com.sparta.harmony.store.entity.Category;
import com.sparta.harmony.store.entity.Store;
import com.sparta.harmony.store.entity.StoreCategory;
import com.sparta.harmony.store.repository.CategoryRepository;
import com.sparta.harmony.store.repository.StoreRepository;
import com.sparta.harmony.user.entity.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;


    public List<StoreResponseDto> getAllStores() {
        List<Store> stores = storeRepository.findAll();

        return stores.stream()
                .map(store -> new StoreResponseDto(store.getStoreId(), store.getStoreName(), store.getPhoneNumber(),
                        store.getAddress(), store.getStoreCategories().stream()
                        .map(storeCategory -> storeCategory.getCategory().getCategoryName())
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Transactional
    public StoreResponseDto createStore(StoreRequestDto storeRequestDto) {

        Address address = new Address(storeRequestDto.getPostcode(),storeRequestDto.getAddress(), storeRequestDto.getDetailAddress());

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

    @Transactional
    public StoreResponseDto updateStore(UUID storeId, StoreRequestDto storeRequestDto) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("StoreId를 찾을 수 없습니다: " + storeId));

        store.updateStoreInfo(
                storeRequestDto.getStoreName(),
                storeRequestDto.getPhoneNumber(),
                new Address(storeRequestDto.getAddress(), storeRequestDto.getDetailAddress(), storeRequestDto.getPostcode())
        );

        store.clearCategories();
        List<StoreCategory> updatedCategories = storeRequestDto.getCategoryIds().stream()
                .map(categoryId -> {
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new IllegalArgumentException("CategoryId를 찾을 수 없습니다: " + categoryId));
                    return StoreCategory.builder()
                            .store(store)
                            .category(category)
                            .build();
                })
                .collect(Collectors.toList());
        store.addCategories(updatedCategories);

        storeRepository.save(store);

        List<String> categoryNames = updatedCategories.stream()
                .map(storeCategory -> storeCategory.getCategory().getCategoryName())
                .collect(Collectors.toList());

        return new StoreResponseDto(store.getStoreId(), store.getStoreName(), store.getPhoneNumber(), store.getAddress(), categoryNames);
    }

    @Transactional
    public void deleteStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("음식점을 찾을 수 없습니다."));

        storeRepository.delete(store);
    }

    public Object searchStores(String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return List.of("검색어를 입력해주세요");
        }

        Page<Store> storesPage = storeRepository.findByStoreNameContaining(searchKeyword, pageable);
        if (storesPage.isEmpty()) {
            return List.of("해당 검색어의 음식점이 없습니다");
        }

        List<UUID> storeIds = storesPage.getContent().stream()
                .map(Store::getStoreId)
                .collect(Collectors.toList());

        List<Object[]> ratings = reviewRepository.findAverageRatingsByStoreIds(storeIds);
        Map<UUID, Double> ratingMap = ratings.stream()
                .collect(Collectors.toMap(row -> (UUID) row[0], row -> (Double) row[1]));

        return storesPage.map(store -> new StoreSearchResponseDto(
                store.getStoreName(),
                ratingMap.getOrDefault(store.getStoreId(), 0.0)
        ));
    }


//    private double getAverageRating(UUID storeId) {
//        return reviewRepository.findAverageRatingByStoreId(storeId).orElse(0.0);
//    }

    public StoreDetailResponseDto getStoreDetail(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("음식점을 찾을 수 없습니다: " + storeId));

        List<Review> reviews = reviewRepository.findByStore(store);

        return new StoreDetailResponseDto(store, reviews);
    }
}
