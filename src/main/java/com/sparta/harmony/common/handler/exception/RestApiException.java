package com.sparta.harmony.common.handler.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiException {

    @JsonProperty("error_message")
    private String error;

    private int status;
}
