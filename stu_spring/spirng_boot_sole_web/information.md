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
