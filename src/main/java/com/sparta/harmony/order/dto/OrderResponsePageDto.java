package com.sparta.harmony.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "주문 성공 페이지 응답 Dto")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderResponsePageDto {

    @Schema(description = "현재 페이지 번호", example = "1")
    private int page;

    @Schema(description = "총 페이지 수", example = "20")
    @JsonProperty("total_page")
    private int totalPage;

    @Schema(description = "총 요소 수", example = "200")
    @JsonProperty("total_elements")
    private long totalElements;

    @Schema(description = "페이지당 요소 수", example = "10")
    private int size;

    @Schema(description = "주문 리스트")
    @JsonProperty("order_list")
    private List<OrderResponseDto> orderList;
}
