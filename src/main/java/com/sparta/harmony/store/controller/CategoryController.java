package com.sparta.harmony.store.controller;

import com.sparta.harmony.common.dto.ApiPageResponseDto;
import com.sparta.harmony.common.dto.ApiResponseDto;
import com.sparta.harmony.common.handler.success.SuccessResponseHandler;
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
    private final SuccessResponseHandler successResponseHandler;


    /**
     * 전체 카테고리 조회 메소드
     * @return 모든 카테고리 정보를 담고 있는 CategoryResponseDto 객체 리스트
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return successResponseHandler.handleSuccess(HttpStatus.OK, "카테고리 목록 조회 성공", categories);
    }

    /**
     * 새로운 카테고리 생성 메소드
     * @param requestDto 카테고리 정보를 담고 있는 CategoryRequestDto 객체
     * @return 생성된 카테고리 정보를 담고 있는 CategoryResponseDto 객체
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<CategoryResponseDto>> createCategory(@RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(requestDto);
        return successResponseHandler.handleSuccess(HttpStatus.CREATED, "카테고리 생성 성공", categoryResponseDto);
    }


    /**
     * 카테고리 수정 메소드
     * @param categoryId 수정할 카테고리의 UUID
     * @param requestDto 카테고리 수정 정보를 담고 있는 CategoryRequestDto 객체
     * @return 수정된 카테고리 정보를 담고 있는 CategoryResponseDto 객체
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDto<CategoryResponseDto>> updateCategory(@PathVariable UUID categoryId, @RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId, requestDto);
        return successResponseHandler.handleSuccess(HttpStatus.OK, "카테고리 수정 성공", categoryResponseDto);
    }

    /**
     * 카테고리 삭제 메소드
     * @param categoryId 삭제할 카테고리의 UUID
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return successResponseHandler.handleSuccess(HttpStatus.NO_CONTENT, "카테고리 삭제 성공", null);
    }

    /**
     * 카테고리 이름 검색 메서드
     * @param searchKeyword 검색어
     * @param pageable 페이지 번호와 크기를 위한 Pageable 객체
     * @return 검색된 카테고리 이름 리스트(페이징 처리됨)
     */
    @GetMapping("/search")
    public ResponseEntity<ApiPageResponseDto<String>> searchCategory(@RequestParam(required = false) String searchKeyword, Pageable pageable) {
        Page<String> searchResults = categoryService.searchCategories(searchKeyword, pageable);
        return successResponseHandler.handlePageSuccess(HttpStatus.OK, "카테고리 검색 결과", searchResults);
    }
}
