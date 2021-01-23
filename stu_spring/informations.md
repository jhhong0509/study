# 스프링 부트에 관한 정보들

### LocalDateTime 타입 직렬화 하기

- [직렬화란?](#직렬화와-역직렬화)

- LocalDateTime 타입 직렬화 하기
  - 기본적으로 Jackson을 통해 LocalDateTime 타입을 직렬화 할 수 없다.
  - 그래서 그냥 String 타입으로 처리하는 경우를 자주 볼 수 있다.
  - 해겨