# aws-secretsmanager-jdbc

## 소개

처음 build될 때 설정 파일을 읽어서 별도의 설정 없이 **바로 secrets manager에 연동되어 db connection 정보를 연동**해서 편하게 이용할 수 있게 하는 라이브러리 이다.

## 사용

### build.gradle

```groovy
implementation 'com.amazonaws.secretsmanager:aws-secretsmanager-jdbc:1.0.5'
```

위 의존성만 추가하면 끝이다.

aws sdk나 spring cloud도 추가할 필요 없다.

### application.yml

``` yaml
spring:
  datasource:
    url: jdbc-secretsmanager:mysql://pear.c6gcx2prjdrq.ap-northeast-2.rds.amazonaws.com:3306/test_mydb?serverTimezone=UTC&characterEncoding=UTF-8&allowPublicKeyRetrieval=true
    driver-class-name: com.amazonaws.secretsmanager.sql.AWSSecretsManagerMySQLDriver
    hikari:
      username: arn:aws:secretsmanager:ap-northeast-2:537331658860:secret:pear_db_secret-Abow5j
  jpa:
    database-platform: org.hibernate.dialect.MySQL5Dialect
```

> 최소한의 설정이다.