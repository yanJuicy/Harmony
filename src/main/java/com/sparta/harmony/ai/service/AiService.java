package com.sparta.harmony.ai.service;

import com.sparta.harmony.ai.entity.AiReqRes;
import com.sparta.harmony.ai.repository.AiReqResRepository;
import com.sparta.harmony.menu.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AiService {

    private final RestTemplate restTemplate;
    private final AiReqResRepository aiReqResRepository;

    @Value("${gemini.key}")
    private String gemeniKey;

    public String aiCreateMenuDescription(String requestText) {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://generativelanguage.googleapis.com")
                .path("/v1beta/models/gemini-1.5-flash-latest:generateContent")
                .queryParam("key", gemeniKey)
                .encode()
                .build()
                .toUri();

        String request = "{\n" +
                "    \"contents\": {\n" +
                "        \"parts\": [\n" +
                "            {\n" +
                "                \"text\": " + requestText +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        try {
            // String(request) -> json
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(request);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, jsonObj, String.class);

            // json -> String(response)
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody());
            JSONArray candidates = (JSONArray) jsonObject.get("candidates");
            JSONObject content = (JSONObject) ((JSONObject) candidates.get(0)).get("content");
            JSONArray parts = (JSONArray) content.get("parts");
            String response = (String) ((JSONObject) parts.get(0)).get("text");

            return response;
        } catch (ParseException e) {
            throw new RuntimeException("AI 요청이 잘못 됐습니다.");
        }
    }

    public UUID save(String request, String response, Menu menu) {
        AiReqRes savedAiReqRes = aiReqResRepository.save(AiReqRes.builder()
                .question(request)
                .answer(response)
                .menu(menu)
                .build());

        return savedAiReqRes.getAiReqResId();
    }

}
