package com.sparta.harmony.common.handler.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "예외 발생 Api")
@Getter
@AllArgsConstructor
public class RestApiException {

    @Schema(description = "에러 메시지", example = "없는 가게 ID입니다.")
    @JsonProperty("error_message")
    private String error;

    @Schema(description = "상태 코드", example = "400")
    private int status;
}
