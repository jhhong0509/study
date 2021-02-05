# 스프링 부트에 관한 정보들

### 직렬화

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

### JPA 관계 매핑

- 두 엔티티 사이의 관계를 나타낸다.
- 관계 매핑 종류
  - 1:1
    - @OneToOne 으로 사용할 수 있다.
  - 1:N
    - @OneToMany로 사용할 수 있다.
    - 게시글 하나에 여러개의 댓글이 있는 것 처럼 1개 -> 여러개 로 이어질 때 사용한다.
  - N:1
    - @ManyToOne로 사용할 수 있다
    - 보통 연결의 주체가 된다.
    - 여러개의 댓글들은 하나의 게시글에서 만나는 것과 같다.
  - N:N
    - @ManyToMany로 사용할 수 있다.
- 관계 매핑의 주인
  - id가 있는 쪽이 보통 주인이 된다.
    - 자식 테이블
  - 관계 매핑의 주인만 외래키를 등록, 수정, 삭제 등을 할 수 있다.
  - 관계 매핑의 자식은 읽기만 가능하다.
- 순환참조의 문제
  - 양방향 매핑일 때, 하나의 엔티티가 다른 엔티티를 참조한다.
  - 다른 엔티티가 다시 그 엔티티를 참조한다.
  - 이렇게 하면 무한히 반복되기 때문에 조심해야 한다.

### Cascade

- spring boot에서의 cascade는 영속성의 전이를 의미한다.
- 영속성의 전이
  - 만약 부모 객체가 영속화 되었다면 자식 객체도 영속화 한다.
  - 부모 객체가 저장된다면 자식 객체 또한 저장된다.
- Cascade의 종류

| Persist                                         | Merge                                                        | Refresh                                 | Remove                                      | Detach                                                       | All                        |
| ----------------------------------------------- | ------------------------------------------------------------ | --------------------------------------- | ------------------------------------------- | ------------------------------------------------------------ | -------------------------- |
| 부모가 영속화 될 때, 자식들도 함께 영속화 된다. | 부모가 Merge 메소드를 실행할 때, 자식 또한 수행한다.(같이 저장됨) | 부모가 새로고침 될 때, 자식도 수행한다. | 삭제할 때, 부모가 삭제되면 자식도 삭제된다. | 부모 엔티티가 Detach()를 수행하면, 자식 또한 수행한다.(저장되지 않음) | 다른 모든 속성을 적용한다. |

### Spring boot 관계매핑 구현

- @OneToMany(orphanRemoval = true)

  - 만약 부모 객체에서 외래키가 바뀌거나 지워진다면, 자식이 알아서 삭제된다.
  - 즉 부모가 가진 자식 Id가 2고 자식의 id가 2라면 문제 없지만, 만약 부모에서 자식id를 3으로 바꾸면 id가 2인 자식이 사라진다.

- 순환 참조 문제 해결

  - 양방향 매핑에서 순환참조를 해결하는 방법

  - 부모 엔티티(ID를 가진 쪽)에 @JsonManagedReference를 붙여준다.

  - 자식 엔티티에는 @JsonBackReference를 붙여준다.

  - 해결된 코드

  - ``` java
    @OneToMany(mappedBy = "timeline")
    @JsonBackReference
    private List<Comment> commentList;
    ```

  - ``` java
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "timelineId")
    private Timeline timeline;
    ```

- Join 시키기
  - @JoinColumn을 통해 다른 컬럼을 Join할 수 있다.
  - @JoinColumn(name="부모엔티티의 고유키")처럼 사용할 수 있다.
    - 부모 엔티티의 이름이 entity이고, 고유키 이름이 email이라면, entityEmail처럼 사용하면 된다.
  - 자식 테이블(관계의 주인)에서 사용해야 한다.
  - @JoinColumn을 받는 쪽의 엔티티에는 @OneToMany(mappedBy="이름")을 사용해야 한다.
    - JPA가 양방향 매핑이라는 사실을 알 수 있도록
    - 반대쪽 매핑의 필드 값을 주면 된다.
    - 즉, 관계의 자식 쪽에서 @JoinColumn을 사용하는 필드의 이름을 써 준다.

### JsonPath

- JSON 형식의 반환값을 증명하기 위한 정형화된 방법이다.
- 테스트코드에서 .andExpect(jsonPath("$.경로"),is("원하는 값"))처럼 사용할 수 있다.
  - 여기서의 is는 alt+enter에서 보이지 않는다(이유는 모르겠다..)그래서 직접 써줘야 한다.
    - import static org.hamcrest.core.Is.is;
- JsonPath의 경로

| XPath | JsonPath          | 설명                                       |
| ----- | ----------------- | ------------------------------------------ |
| /     | $                 | 루트                                       |
| .     | @                 | 현재 요소                                  |
| /     | . 또는 []         | 하위 요소                                  |
| ..    | X                 | 부모 요소(JsonPath는 지원 X)               |
| //    | ..                | 모든 자식을 일치                           |
| *     | *                 | 와일드 카드 하위요소(?)                    |
| @     | X                 | 속성 일치(JsonPath는 지원 X)               |
| []    | []                | 요소의 인덱스(XPath는 1부터, Json은 0부터) |
| \|    | [,]               | ?                                          |
| []    | ?()               | 필터                                       |
| X     | [start:end :step] | 데이터 슬라이스(XPath는 지원X)             |
| ()    | X                 | 그룹화(JsonPath는 지원 X)                  |

