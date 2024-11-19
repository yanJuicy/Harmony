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

### - 데이터 무결성 유지
- **문제**:
  - 리뷰 생성 시 주문 및 음식점 정보가 유효하지 않거나 삭제된 데이터가 노출되는 문제 발생.
- **해결 방안**:
  - 소프트 삭제 상태를 필터링하는 로직을 추가. <br>
### - 리뷰 평점을 계산해 음식점 검색 시 평점 노출 JPA N+1 문제
👉 https://blog.naver.com/wooteacher74/223665444762

### - 중첩 json에서 원하는 데이터 추출

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

### 명세서 분석과 작성
|요구사항 명세서|테이블 명세서|ERD 명세서|API 명세서|인프라 명세서|
|--|--|--|--|--|
|고객이 어떤 기능을 원하는지|각 도메인에 어떤 테이블을 구성할지|테이블의 연관관계를 어떻게 구성해나갈지|API 방식은 어떻게 할건지|어떤 기술을 사용해서 개발을 진행하고 구성할지|
|내가 만든 기능이 정말 고객이 원하는건지|테이블 내에 어떤 필드값들이 들어갈지|연관관계를 어떻게 구성해서 무결성을 확보할지|통일된 req, res를 작성해서 구체적 개발이 가능하게|전체 프로젝트의 흐름을 한눈에 파악|

### logging과 swagger

팀 프로젝트는 혼자 하는 것이 아니라 전체가 협업해서 하는 것이기 때문에 의사소통이 명확히 전달되는게 중요하다고 생각합니다.
이에 생각해본 것이 logging과 swagger를 적용해서 req, res에 대한 파악을 좀 더 쉽게 해보고 싶어서 이번 프로젝트에 적용해보았습니다.

> logging

```java
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReqResLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ReqResLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request 추적을 위한 id 생성
        MDC.put("traceId", UUID.randomUUID().toString());

        // 래퍼로 요청, 응답 감싸기.
        final ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        final ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        // request 정보 로깅.
        logger.info("Request Method: {}", cachingRequestWrapper.getMethod());
        logger.info("Request URL: {}", cachingRequestWrapper.getRequestURL());

        // request 헤더 로깅.
        StringBuilder headers = new StringBuilder();
        cachingRequestWrapper.getHeaderNames().asIterator().forEachRemaining(headerName ->
                headers.append(headerName).append(": ").append(cachingRequestWrapper.getHeader(headerName)).append("\n"));
        logger.info("Request Headers:\n{}", headers);

        // 다음 필터 또는 서블릿 호출.
        filterChain.doFilter(cachingRequestWrapper, contentCachingResponseWrapper);

        // requestbody에 password가 들어있을시 로깅x
        String requestBody = new String(cachingRequestWrapper.getContentAsByteArray(), cachingRequestWrapper.getCharacterEncoding());
        if (requestBody.contains("password")) {
            logger.info("개인정보의 중요 부분이 포함되어 Request Body 로깅이 제한됩니다.");
        } else {
            logger.info("Request Body: \n{}", requestBody);
        }

        // response 후 로깅 및 보기 좋게 다듬기.
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // 줄바꿈 및 들여쓰기 활성화

        String responseBody = new String(contentCachingResponseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);

        logger.info("Response Status: {}", contentCachingResponseWrapper.getStatus());
        logger.info("Response Header - Authorization: {}", contentCachingResponseWrapper.getHeader("Authorization"));

        if (responseBody.isEmpty()) {
            logger.info("Response body is empty");
            MDC.clear();

            return;
        } else if (responseBody.contains("<html") || responseBody.contains("swagger-ui/oauth2-redirect.html") || responseBody.contains("localhost:8080")) {
            logger.info("html response");
            contentCachingResponseWrapper.copyBodyToResponse();
            MDC.clear();

            return;
        }

        Object json = objectMapper.readValue(responseBody, Object.class); // JSON 문자열을 객체로 변환
        String prettyResponseBody = objectMapper.writeValueAsString(json); // 다시 포맷된 JSON 문자열로 변환

        logger.info("Response Content: \n{}", prettyResponseBody);

        contentCachingResponseWrapper.copyBodyToResponse();

        MDC.clear();
    }

    // swagger의 경우, 이후 필터(security)를 진행시키지 않도록 설정..
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/swagger-ui/",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
```

![image](https://github.com/user-attachments/assets/16bcc7bd-d60d-4de3-8229-9216695ece75)


AOP 방식을 택해서 좀더 controller단과 가까운 곳에서 메소드의 흐름을 보도록 할것인지 아니면 filter쪽에 적용을 해서 request를 받아오는 가장 가까운 곳에서 req를 볼 것인지에 대해 적용 시점이 일단 어느정도 개발이 진행된 상태라 filter쪽에 logging을 넣어 req와 res를 파악하기 쉽게했습니다.

전체적인 흐름으로 보면 각 request마다 traceId를 uuid로 생성하여 요청을 추적할 수 있게 하였고 요청 url이 어디인지, header는 뭐가 있는지, body에는 뭐가 있는지 등등을 확인할 수 있습니다.
response는 header에 jwt가 있는지, body에는 어떤 것이 있는지 확인할 수 있습니다.

프로젝트에는 security가 적용되어 있는데 security filter의 경우 우선순위가 최상위 (-100)로 설정되어 가장 빠르게 되어 있습니다. 
그렇기 때문에 security config에서 가장 빠른 filter앞에 오도록 설정해주고, @order를 이용해 순서를 최상위로 만들어서 
client의 request에 가장 먼저 filter를 거쳐 어떤 요청이 오는지 볼 수 있습니다. 

추가로 여러 설정들이 추가되었는데 Config.java나 resource에 .xml 파일 작성 등.. 시간을 참 많이 잡아먹고 에러도 참 많이 발생했던 것 같습니다.


> swagger

이 프로젝트를 처음 보는 개발자 입장에서 어떻게 하면 좀 더 쉽고 명확하게 이해시킬 수 있을까 고민 끝에 swagger와 rest docs를 알게 되었고
이중 swagger를 선택한 이유는 rest docs는 구현까지 시간이 부족할 것이라고 판단했기 때문입니다.
![image](https://github.com/user-attachments/assets/dbfaf2ac-9093-4d2d-bdb6-1c962009ec41)

![image](https://github.com/user-attachments/assets/9c61a2d3-dc31-4788-a9fd-066a54ec28ba)

직접 swagger 사용해보고 싶으시면 👉 [click here](http://ec2-3-35-214-44.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html) 
*Order controller, Payment controller만 custom 되어있습니다만, 현재는 UUID가 맞지 않는 것이 많이 있어 수정중에 있습니다.*

<br><br>


## 프로젝트 회고
- 윤홍찬 : 고객의 요구사항에 맞는 로직을 어떻게 구성할지에 대한 고민을 참 많이 했던 것 같습니다. 간단한 예로 단순히 주문을 생성하는 서비스 로직을 구현함에 있어서 실제로 어떤 조건들이 있을까..이전에는 몰랐지만 사실 요구사항 명세서의 분석, 테이블/ERD/API/인프라 등등 명세서 작성이 정말 중요하다고 느꼈습니다.모든 프로젝트의 기초가 되는 만큼 아마 앞으로 이 부분에서 정말 많은 시간을 투자하게 될 것 같습니다. 비즈니스 로직에 맞는 구현에 있어서도 고민이 많았습니다.

```java
@Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user) {

        User userInfo = userRepository.findByEmail(user.getEmail()).orElseThrow(()
                -> new IllegalArgumentException("유저 정보를 확인해 주세요"));

        Address address;

        if ((orderRequestDto.getAddress().isEmpty())
                && (orderRequestDto.getDetailAddress().isEmpty())) {
            // 주소지가 따로 입력되지 않은 경우
            if (orderRequestDto.getOrderType().equals(OrderTypeEnum.TAKEOUT)) {
                UUID storeId = orderRequestDto.getStoreId();
                Address storeAddress = storeRepository.findById(storeId).orElseThrow(
                        () -> new IllegalArgumentException("가게 ID를 확인해주세요")).getAddress();

                address = buildAddressUseAddress(storeAddress);
            } else {
                Address basicUserAddress = userInfo.getAddress();

                address = buildAddressUseAddress(basicUserAddress);
            }
```
단순히 주문 생성이지만 주소지가 들어오는 경우와 주소지가 안들어온 경우가 있을텐데 주소지가 안들어온 경우 중에서 포장인 경우엔 가게의 주소를, 배달인 경우 user의 기본 주소를.. 모든 API에 대해서 과연 단순한 CRUD가 아니라 어떤 조건이 들어오게 되어서 결과가 바뀔지에 대한 생각을 많이 했던 것 같습니다. 또한 logging과 swagger를 제외해버리고 비즈니스 로직을 좀 더 상세히 구현한다던가, 미쳐 구현하지 못했던 기능을 해본다던가.. 하는 생각을 했었지만 제가 구현한 것을 남들이 더 잘 다룰 수 있게, 어떤 기능들이 있는지 보여주는 것이 더 큰 가치가 있다고 판단하고 이 기능들을 구현해보았습니다. 

이번에 프로젝트를 할 때 test code와 debugging을 많이 활용하지 못한 부분에 대한 아쉬움도 생각납니다. 서비스 로직을 만들고 app 실행 후 postman으로 검증하는 부분이 많았기 때문에 이전에 unit test 등을 많이 하였으면 조기에 문제를 발견 했을텐데..하는 생각을 많이 했었습니다. debugging도 마찬가지로 console에 하나하나 출력해보는 경우가 많았는데 다음부터는 debugging을 이용해서 bug를 찾고 코드를 수정할 수 있도록 해야겠습니다.

- 이한주 : 
- 조창현 : 또한, 단순히 기술을 구현하는 것을 넘어 음식 주문 관리 플랫폼의 도메인 로직을 깊이 이해하고 이를 코드로 풀어내는 과정이 중요하다고 생각했습니다.
- 장윤지 : 음식점 생성 api를 호출할 때 postcode의 데이터 값을 varchar(6)으로 설정하고 6보다 작은 길이의 값을 넣어 테스트하였는데 계속 6보다 긴 길이의 값이 들어갔다는 오류가 발생했습니다. 살펴보니, Address 클래스의 생성자 매개변수 순서와 createStore 메서드에서 생성자를 호출하는 인수의 순서가 일치하지 않아서 계속 오류가 발생하던 것이었습니다. 순서가 일치하지 않기때문에 postcode 값에는 address가, address값에는 postcode가 들어가 있어 발생하는 오류 임을 인지한 후 순서를 바꿔주니 정상 작동하는 것을 확인했습니다. 
![image](https://github.com/user-attachments/assets/08cb0d18-e661-415f-8abb-b444ccb00057)

