package com.sparta.harmony.store.controller;

import com.sparta.harmony.store.dto.StoreDetailResponseDto;
import com.sparta.harmony.store.dto.StoreRequestDto;
import com.sparta.harmony.store.dto.StoreResponseDto;
import com.sparta.harmony.store.dto.StoreSearchResponseDto;
import com.sparta.harmony.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * 모든 음식점 정보 조회
     * @return 모든 음식점 정보를 담고 있는 StoreResponseDto 객체 리스트
     */
    @GetMapping
    public List<StoreResponseDto> getAllStores() {
        return storeService.getAllStores();
    }

    /**
     * 음식점 생성 메소드
     * @param storeRequestDto 음식점 정보를 담고 있는 StoreRequestDto 객체
     * @return 생성된 음식점 정보를 담고 있는 StoreResponseDto 객체
     */
    @PostMapping
    public StoreResponseDto createStore(@RequestBody StoreRequestDto storeRequestDto) {
        return storeService.createStore(storeRequestDto);
    }

    /**
     * 음식점 정보 수정 메소드
     * @param storeId 수정할 음식점의 UUID
     * @param storeRequestDto 음식점 수정 정보를 담고 있는 StoreRequestDto 객체
     * @return 수정된 음식점 정보를 담고 있는 StoreResponseDto 객체
     */
    @PutMapping("/{storeId}")
    public StoreResponseDto updateStore(@PathVariable UUID storeId, @RequestBody StoreRequestDto storeRequestDto) {
        return storeService.updateStore(storeId, storeRequestDto);
    }

    /**
     * 음식점 삭제 메소드
     * @param storeId 삭제할 음식점의 UUID
     */
    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
    }

    /**
     * 음식점 이름과 리뷰의 평균 rating을 검색, 페이징 처리하여 반환
     *
     * @param searchKeyword 음식점 이름 검색 키워드
     * @param pageable 페이징을 위한 Pageable 객체
     * @return 음식점 이름과 평균 rating을 포함한 페이징 처리된 음식점 목록
     */
    @GetMapping("/search")
    public Object searchStores(@RequestParam(required = false) String searchKeyword, Pageable pageable) {
        Object response = storeService.searchStores(searchKeyword, pageable);

        if (response instanceof List) {
            List<String> messages = (List<String>) response;
            return messages;
        }
        return response;
    }

    /**
     * 특정 음식점의 상세 정보를 반환 (음식점 검색 후 음식점 상세 정보 조회시)
     *
     * @param storeId 음식점 ID
     * @return 해당 음식점의 상세 정보
     */
    @GetMapping("/{storeId}")
    public StoreDetailResponseDto getStoreDetail(@PathVariable UUID storeId) {
        return storeService.getStoreDetail(storeId);
    }
}
