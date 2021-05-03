# 직렬화

- 직렬화란?
  - 객체를 전송 가능한 형태로 만드는 것
  - 예) JSON, XML
- 역직렬화란?
  -  직렬화된 데이터들을 자바의 객체로 변환시켜 주는 것

- Jackson

  - Spring 3.0부터, Jackson 관련 API를 제공하기 때문에 다른 라이브러리보다 발전되었다.

  - 귀찮게 따로 바꾸는 코드를 짜지 않아도, 리턴 방식이 @RequestBody라면, 알아서 리턴하는 객체를 뜯어서 JSON으로 만든다.

### LocalDateTime 타입 직렬화 오류

- 기본적으로 Jackson을 통해 LocalDateTime 타입을 직렬화 할 수 없다.
- 그래서 그냥 String 타입으로 처리하는 경우를 자주 볼 수 있다.

### LocalDateTime을 직렬화 하기

#### ModelAttribute

- ModelAttribute란
  - URL 파라미터로 넘어오는 값을 받을 수 있는 방법
  - @RequestParam과 비슷하다.
    - 하지만 @RequestParam은 하나씩 받는다.
    - ModelAttribute는 DTO 객체를 다 받는다.

```java
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
```

- 위 방법으로는 해결할 수  없다.
- Model에는 있어도 없을때와 같은 오류가 발생한다.
- 하지만 @RequestBody에 있을때엔 오류를 해결하는 방법이 될 수 있다.
- pattern은 예를들어, "2021-01-24T04:24:10" 과 같은 시간이다.
  - 중간에 T가 들어가는 이유는 띄어쓰기를 하면 통신중 오류가 발생할 수 있기 때문이다.

```java
@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
```

- 이 방법을 사용하면 해결할 수 있다.
  - @RequestParam, 모델(테이블과 연결된 클래스), @RequestBody등 모두 해결할 수 있다.
- @DateTimeFormat은 LocalDateTime과 같은 타입을 직렬화 시켜주는 타입이다.
- 만약 Controller에서 직렬화가 필요하다면, 그때도 이 어노테이션을 붙여주면 해결된다.
  - 컨트롤러에서 @RequestParam 등을 통해 LocalDateTime을 받을 때

### LocalDate 테스트코드

- 기존 코드

``` java
new ObjectMapper().writeValueAsString(request)
```

- 바뀐 코드

``` java
new ObjectMapper().registerModule(new JavaTimeModule())
```

- 모듈을 JavaTimeModule로 해준다.