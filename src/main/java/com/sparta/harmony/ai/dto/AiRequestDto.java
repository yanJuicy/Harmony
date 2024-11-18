package com.sparta.harmony.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class AiRequestDto {

    @JsonProperty("contents")
    private Contents contents;

    public AiRequestDto(Contents contents) {
        this.contents = contents;
    }

    @Getter
    public static class Contents {
        @JsonProperty("parts")
        private List<Part> parts;

        public Contents(List<Part> parts) {
            this.parts = parts;
        }
    }

    @Getter
    public static class Part {
        @JsonProperty("text")
        private String text;

        public Part(String text) {
            this.text = text;
        }
    }

}
