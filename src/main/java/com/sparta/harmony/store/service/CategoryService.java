package com.sparta.harmony.store.service;

import com.sparta.harmony.store.dto.CategoryRequestDto;
import com.sparta.harmony.store.dto.CategoryResponseDto;
import com.sparta.harmony.store.entity.Category;
import com.sparta.harmony.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> new CategoryResponseDto(
                        category.getCategoryId(),
                        category.getCategoryName()
                ))
                .collect(Collectors.toList());
    }

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {

        Category category = new Category(categoryRequestDto.getCategoryName());

        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponseDto(savedCategory.getCategoryId(), savedCategory.getCategoryName()
        );
    }

    @Transactional
    public CategoryResponseDto updateCategory(UUID categoryId, CategoryRequestDto categoryRequestDto) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다"));


        category.updateCategoryName(categoryRequestDto.getCategoryName());

        categoryRepository.save(category);

        return new CategoryResponseDto(category.getCategoryId(), category.getCategoryName());
    }

    @Transactional
    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다"));

        categoryRepository.delete(category);
    }

    public Page<String> searchCategories(String searchKeyword, Pageable pageable) {
        // 검색어가 없거나 공백일 경우
        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return new PageImpl<>(List.of("검색어를 입력해주세요"), pageable, 1);
        }

        Page<Category> categoriesPage = categoryRepository.findByCategoryNameContaining(searchKeyword, pageable);

        // 검색 결과가 없으면 메시지를 리스트로 반환
        if (categoriesPage.getTotalElements() == 0) {
            return new PageImpl<>(List.of("해당 검색어의 카테고리가 없습니다"), pageable, 0);
        }

        // 검색 결과가 있을 경우 카테고리 이름 리스트 반환
        List<String> categoryNames = categoriesPage.stream()
                .map(Category::getCategoryName)
                .collect(Collectors.toList());

        return new PageImpl<>(categoryNames, pageable, categoriesPage.getTotalElements());
    }


}
