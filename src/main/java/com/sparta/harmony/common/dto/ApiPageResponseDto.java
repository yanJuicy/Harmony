package com.sparta.harmony.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Api Page 응답 Dto")
@Getter
@Builder
@NoArgsConstructor
public class ApiPageResponseDto<T> {

    @Schema(description = "상태 코드", example = "200")
    private int status;

    @Schema(description = "응답 메시지", example = "주문이 성공적으로 접수되었습니다.")
    private String message;

    @Schema(description = "응답 page", example = "1")
    private int page;

    @Schema(description = "페이지의 사이즈", example = "10")
    private int size;

    @Schema(description = "data의 총 개수", example = "200")
    @JsonProperty("total_elements")
    private long totalElements;

    @Schema(description = "총 페이지", example = "20")
    @JsonProperty("total_page")
    private int totalPage;

    @Schema(description = "응답 Data")
    @JsonProperty("data")
    private List<T> data;

    public ApiPageResponseDto(int status, String message, int page, int size, long totalElements, int totalPage, List<T> data) {
        this.status = status;
        this.message = message;
        this.page = page + 1;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPage = totalPage;
        this.data = data;
    }
}
