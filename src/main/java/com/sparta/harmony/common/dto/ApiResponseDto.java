package com.sparta.harmony.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Api 응답 Dto")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {

    @Schema(description = "상태 코드", example = "200")
    private int status;

    @Schema(description = "응답 메시지", example = "주문이 성공적으로 접수되었습니다.")
    private String message;

    @Schema(description = "응답 Data")
    private T data;
}
