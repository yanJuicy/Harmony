# 🍱:Harmony:🍱
# 프로젝트 소개
- 음식점들의 배달 및 포장 주문 관리, 결제, 그리고 주문 내역 관리 기능을 제공하는 플랫폼 입니다.

   ° Spring Boot를 사용한 안정적인 웹 구현, PostgreSQL로 데이터 관리합니다. <br>
    ° 유지보수성을 높이기 위해 JPA를 선택해 Spring Data JPA와의 통합을 통한 효율적인 데이터 처리를 하고자 했습니다.<br>
      ° JPA의 ORM 기능을 활용하여 객체와 데이터베이스 간의 매핑을 최적화했습니다. <br>
        ° API 명세서 : https://caring-pin-8ac.notion.site/API-136dcca716cf808aa2fdee25878f0747

  <br>
  
## 팀원 구성
<div align="center">

| 윤홍찬 | 이한주 | 조창현 | 장윤지 |
| :------: |  :------: | :------: | :------: |
|[<img width="98" src="https://github.com/user-attachments/assets/cef5bc12-a48b-43be-ad5c-43396028e6c9"> <br> @Chani0324](https://github.com/Chani0324)|[<image width="98" src="https://github.com/user-attachments/assets/d8730fb7-92f5-42d4-b8bd-435e87129198"><br> @yanJuicy](https://github.com/yanJuicy)|[<img width="99" src="https://github.com/user-attachments/assets/43f4d21f-7891-46a4-bd25-745120fb6c26"> <br>@ch0Changhyun](https://github.com/ch0Changhyun)|[<img width="98" src="https://github.com/user-attachments/assets/dd0e73fb-f5ef-4c47-9cda-2af955d54f5b"> <br>@elliaaa](https://github.com/elliaaa)|
</div>

</div>


## 1. 개발 환경
- 프로젝트 개발 환경 : IntelliJ
- 사용 기술 : Java, Spring Boot
- DB 및 서버 관리 : AWS
- 배포 : Docker, Git Action
- 수행 환경 : Spring Security, Spring Data JPA …
- 버전 관리 : Git
- 협업 툴 : Slack, Notion
  
## 2. 개발 기간 및 작업 관리
- 24-11-07 ~ 24-11-18(총 12일)
  
## 3. 아키텍쳐
<img width="903" alt="image" src="https://github.com/user-attachments/assets/15b6d910-043c-4e42-b41e-f8ede32422e4">


### 배포 작업
<img width="982" alt="image" src="https://github.com/user-attachments/assets/ccf5354c-9b31-4ca6-8650-7d8a8c8df0fd">


## 4. ERD 
![image](https://github.com/user-attachments/assets/4336fe28-0665-4488-a408-5f1ee4028a93)

## 5. 역할 분담
- 윤홍찬 : 주문, 결제
- 이한주 : 메뉴, AI 요청
- 조창현 : 리뷰, 유저
- 장윤지 : 카테고리, 음식점

## 어려웠던 점

### 중첩 json에서 원하는 데이터 추출

Dto 클래스를 만들어서 한 번에 중첩 json을 매핑하고 싶었으나 하지 못해서 String 클래스로 매핑해서 해결

```json
{
    "candidates": [
        {
            "content": {
                "parts": [
                    {
                        "text": "엄마표 매콤한 김치가 듬뿍!  집김치의 시원한 매운맛이 일품인 김치만두입니다.  한 입 베어물면 중독되는 맛!\n"
                    }
                ],
                "role": "model"
            },
            "finishReason": "STOP",
            "avgLogprobs": -0.22993026177088419
        }
    ],
    "usageMetadata": {
        "promptTokenCount": 42,
        "candidatesTokenCount": 48,
        "totalTokenCount": 90
    },
    "modelVersion": "gemini-1.5-flash-002"
}
```

```java
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
                "                \"text\": " + "\"" + requestText + "\"" +
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

            if (response.contains("Please provide me")) {
                throw new IllegalArgumentException("AI에게 메뉴를 설명해달라고 작성해주세요.");
            }

            return response;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("AI에게 메뉴를 설명해달라고 작성해주세요.");
        }
    }
```



