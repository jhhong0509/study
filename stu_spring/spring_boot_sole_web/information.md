# 콘솔에 쿼리 찍기
``` yml
spring:
  jpa:
    show_sql: true
```
- 위 코드를 yml에 추가한다면 약간 이상하지만 쿼리가 찍힌다.
    - 이상한 이유는 H2(테스트용)의 sql 문법을 이용하기 때문이다.
    - H2는 mysql의 쿼리를 지원하기 때문에, 그냥 mysql 문법을 사용해도 오류가 나지 않는다.
``` yml
spring:
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL5InnoDBDialect
```
# 스프링 웹 어플리케이션 아키텍쳐
<img src = "https://user-images.githubusercontent.com/48408417/99790334-4c7fc580-2b67-11eb-84c9-8a26c9d93373.png">

### 웹 레이어(Web Layer)
- 컨트롤러, 템플릿, 필터 등 외부 요청을 처리/응답하는 레이어
- DTO를 통한 데이터 교환
### 서비스 레이어(Service Layer)
- 컨트롤러와 DAO(데이터 접근 객체?) 사이에서 사용되는 영역
- 하나의 작업을 하나의 트랜잭션으로 묶어주는 역할
- 처리는 도메인 모델에서
- 다른 서비스 참조 가능
### 레포지토리 레이어(Repository Layer)
- DB접근 역할
- DAO와 같음
### DTOs
- 계층간 데이터 교환을 위한 객체
### Domain Model
- @Entity가 사용된 영역
- 무조건적으로 DB의 테이블과 관련 있어야 하는것은 아님

# spring boot profile

### properties

``` properties
spring.profile.include=oauth
```

- 스프링 부트에서는 application-이름.properties와 같은 형식으로 profile을 만들 수 있다.
- 위와 같은 형태는 oauth라는 이름의 profile을 가져온다.

### yml

``` yaml
spring:
  profile:
    active: test

---

spring:
  profile: dev

server:
  port: 8080

---

spring:
  profile: test

server:
  port: 8181
```



- yml 형식의 파일에서는, 훨씬 더 간편하게 profile 설정을 해줄 수 있다.
- 위와 같이, 하나의 yml 파일 내에서 여러개의 프로필들을 만들어줄 수 있다.
- --- 이라는 구분자를 통해, 다른 파일을 관리하듯이 사용할 수 있다.
  - 위 구조에서, dev라는 프로필과, test 라는 프로필을 만들었다.
  - 또한, active를 통해 test 라는 프로필을 활성화 했다는것을 쉽게 알 수 있다.
- profile: 이름 과 같은 형식을 통해 프로필의 이름을 설정해줄 수 있다.
