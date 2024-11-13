package com.sparta.harmony.store.controller;

import com.sparta.harmony.store.dto.CategoryRequestDto;
import com.sparta.harmony.store.dto.CategoryResponseDto;
import com.sparta.harmony.store.entity.Category;
import com.sparta.harmony.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 전체 카테고리 조회 메소드
     * @return 모든 카테고리 정보를 담고 있는 CategoryResponseDto 객체 리스트
     */
    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * 새로운 카테고리 생성 메소드
     * @param requestDto 카테고리 정보를 담고 있는 CategoryRequestDto 객체
     * @return 생성된 카테고리 정보를 담고 있는 CategoryResponseDto 객체
     */
    @PostMapping
    public CategoryResponseDto createProduct(@RequestBody CategoryRequestDto requestDto) {
        return categoryService.createCategory(requestDto);
    }

    /**
     * 카테고리 수정 메소드
     * @param categoryId 수정할 카테고리의 UUID
     * @param requestDto 카테고리 수정 정보를 담고 있는 CategoryRequestDto 객체
     * @return 수정된 카테고리 정보를 담고 있는 CategoryResponseDto 객체
     */
    @PutMapping("/{categoryId}")
    public CategoryResponseDto updateCategory(@PathVariable UUID categoryId, @RequestBody CategoryRequestDto requestDto) {
        return categoryService.updateCategory(categoryId, requestDto);
    }

    /**
     * 카테고리 삭제 메소드
     * @param categoryId 삭제할 카테고리의 UUID
     */
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    /**
     * 카테고리 이름 검색 메서드
     * @param searchKeyword 검색어
     * @param pageable 페이지 번호와 크기를 위한 Pageable 객체
     * @return 검색된 카테고리 이름 리스트(페이징 처리됨)
     */
    @GetMapping("/search")
    public Page<String> searchCategory(@RequestParam(required = false) String searchKeyword, Pageable pageable) {
        return categoryService.searchCategories(searchKeyword,pageable);
    }
}
