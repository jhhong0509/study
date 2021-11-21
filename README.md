# 목차

### CI/CD

- [K8S](https://github.com/jhhong0509/study/blob/master/cicd/kubernetes/kubernetes.md)
- [정의](https://github.com/jhhong0509/study/blob/master/cicd/what_is.md)
- [점진적 배포 핵클 웨비나](https://github.com/jhhong0509/study/blob/master/cicd/feature_flag_webina.md)

---

### 토큰

- [JWT](https://github.com/jhhong0509/study/blob/master/token/jwt.md)
- [JWS](https://github.com/jhhong0509/study/blob/master/token/jws.md)

---

### MSA

- 공부하며 알게된 MSA 지식
  1. [기초](https://github.com/jhhong0509/study/blob/master/msa/01.msa_basic.md)
  2. [구조](https://github.com/jhhong0509/study/blob/master/msa/02.msa_structure.md)
  3. [RPC란?](https://github.com/jhhong0509/study/blob/master/msa/03.rpc.md)
  4. [MQ란?](https://github.com/jhhong0509/study/blob/master/msa/04.mq.md)

- 책
  1. Monolithic 지옥에서 벗어나라
     1. [마이크로서비스 아키텍쳐가 답이다](https://github.com/jhhong0509/study/blob/master/msa/book/01%20%EB%AA%A8%EB%86%80%EB%A6%AC%EC%8B%9D%20%EC%A7%80%EC%98%A5%EC%97%90%EC%84%9C%20%EB%B2%97%EC%96%B4%EB%82%98%EB%9D%BC/1.4%20%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%20%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90%EA%B0%80%20%EB%8B%B5%EC%9D%B4%EB%8B%A4.md)
     2. [마이크로서비스 아키텍쳐의 장단점](https://github.com/jhhong0509/study/blob/master/msa/book/01%20%EB%AA%A8%EB%86%80%EB%A6%AC%EC%8B%9D%20%EC%A7%80%EC%98%A5%EC%97%90%EC%84%9C%20%EB%B2%97%EC%96%B4%EB%82%98%EB%9D%BC/1.5%20%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%20%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90%EC%9D%98%20%EC%9E%A5%EB%8B%A8%EC%A0%90.md)
     3. [마이크로서비스 아키텍쳐 패턴 언어](https://github.com/jhhong0509/study/blob/master/msa/book/01%20%EB%AA%A8%EB%86%80%EB%A6%AC%EC%8B%9D%20%EC%A7%80%EC%98%A5%EC%97%90%EC%84%9C%20%EB%B2%97%EC%96%B4%EB%82%98%EB%9D%BC/1.6%20%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%20%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90%20%ED%8C%A8%ED%84%B4%20%EC%96%B8%EC%96%B4.md)
     4. [마이크로서비스 너머 프로세스와 조직](https://github.com/jhhong0509/study/blob/master/msa/book/01%20%EB%AA%A8%EB%86%80%EB%A6%AC%EC%8B%9D%20%EC%A7%80%EC%98%A5%EC%97%90%EC%84%9C%20%EB%B2%97%EC%96%B4%EB%82%98%EB%9D%BC/1.7%20%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%20%EB%84%88%EB%A8%B8%20%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4%EC%99%80%20%EC%A1%B0%EC%A7%81.md)
  2. 분해 전략
     1. [마이크로서비스 아키텍쳐란 무엇인가](https://github.com/jhhong0509/study/blob/master/msa/book/02%20%EB%B6%84%ED%95%B4%20%EC%A0%84%EB%9E%B5/2.1%20%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%20%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90%EB%9E%80%20%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80.md)
     2. [마이크로서비스 아키텍쳐 정의](https://github.com/jhhong0509/study/blob/master/msa/book/02%20%EB%B6%84%ED%95%B4%20%EC%A0%84%EB%9E%B5/2.2%20%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%20%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90%20%EC%A0%95%EC%9D%98.md)
  3. 프로세스 간 통신
     1. [마이크로서비스  아키텍쳐 IPC개요](https://github.com/jhhong0509/study/blob/master/msa/book/03%20%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4%20%EA%B0%84%20%ED%86%B5%EC%8B%A0/3.1%20%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4%20%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90%20IPC%20%EA%B0%9C%EC%9A%94md.md)
     2. [동기 RPI 패턴 응용 통신](https://github.com/jhhong0509/study/blob/master/msa/book/03%20%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4%20%EA%B0%84%20%ED%86%B5%EC%8B%A0/3.2%20%EB%8F%99%EA%B8%B0%20RPI%20%ED%8C%A8%ED%84%B4%20%EC%9D%91%EC%9A%A9%20%ED%86%B5%EC%8B%A0.md)
     3. [비동기 메세징 패턴 응용 통신](https://github.com/jhhong0509/study/blob/master/msa/book/03%20%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4%20%EA%B0%84%20%ED%86%B5%EC%8B%A0/3.3%20%EB%B9%84%EB%8F%99%EA%B8%B0%20%EB%A9%94%EC%84%B8%EC%A7%95%20%ED%8C%A8%ED%84%B4%20%EC%9D%91%EC%9A%A9%20%ED%86%B5%EC%8B%A0.md)
     4. [비동기 메세징으로 가용성 개선](https://github.com/jhhong0509/study/blob/master/msa/book/03%20%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4%20%EA%B0%84%20%ED%86%B5%EC%8B%A0/3.4%20%EB%B9%84%EB%8F%99%EA%B8%B0%20%EB%A9%94%EC%84%B8%EC%A7%95%EC%9C%BC%EB%A1%9C%20%EA%B0%80%EC%9A%A9%EC%84%B1%20%EA%B0%9C%EC%84%A0.md)
  4. 사가를 이용한 트랜잭션 관리
     1. [MSA에서 트랜잭션의 필요성](https://github.com/jhhong0509/study/blob/master/msa/book/04%20%EC%82%AC%EA%B0%80%EB%A5%BC%20%EC%9D%B4%EC%9A%A9%ED%95%9C%20%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%20%EA%B4%80%EB%A6%AC/4.1%20MSA%EC%97%90%EC%84%9C%20%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%EC%9D%98%20%ED%95%84%EC%9A%94%EC%84%B1.md)
     2. [Saga의 구성](https://github.com/jhhong0509/study/blob/master/msa/book/04%20%EC%82%AC%EA%B0%80%EB%A5%BC%20%EC%9D%B4%EC%9A%A9%ED%95%9C%20%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%20%EA%B4%80%EB%A6%AC/4.2%20Saga%20%EA%B5%AC%EC%84%B1.md)
     3. [비격리 문제](https://github.com/jhhong0509/study/blob/master/msa/book/04%20%EC%82%AC%EA%B0%80%EB%A5%BC%20%EC%9D%B4%EC%9A%A9%ED%95%9C%20%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%20%EA%B4%80%EB%A6%AC/4.3%20%EB%B9%84%EA%B2%A9%EB%A6%AC%20%EB%AC%B8%EC%A0%9C.md)
     4. [주문 서비스 디자인과 주문 생성 Saga 설계](https://github.com/jhhong0509/study/blob/master/msa/book/04%20%EC%82%AC%EA%B0%80%EB%A5%BC%20%EC%9D%B4%EC%9A%A9%ED%95%9C%20%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%20%EA%B4%80%EB%A6%AC/4.4%20%EC%A3%BC%EB%AC%B8%20%EC%84%9C%EB%B9%84%EC%8A%A4%20%EB%94%94%EC%9E%90%EC%9D%B8%EA%B3%BC%20%EC%A3%BC%EB%AC%B8%20%EC%83%9D%EC%84%B1%20Saga%20%EC%84%A4%EA%B3%84.md)
  5. 비지니스 로직 설계
     1. [비지니스 로직 구성 패턴](https://github.com/jhhong0509/study/blob/master/msa/book/05%20%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4%20%EB%A1%9C%EC%A7%81%20%EC%84%A4%EA%B3%84/5.1%20%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4%20%EB%A1%9C%EC%A7%81%20%EA%B5%AC%EC%84%B1%20%ED%8C%A8%ED%84%B4.md)
     2. [DDD Aggregate Pattern](https://github.com/jhhong0509/study/blob/master/msa/book/05%20%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4%20%EB%A1%9C%EC%A7%81%20%EC%84%A4%EA%B3%84/5.2%20DDD%20Aggregate%20Pattern.md)
     3. [Publish Domain Event](https://github.com/jhhong0509/study/blob/master/msa/book/05%20%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4%20%EB%A1%9C%EC%A7%81%20%EC%84%A4%EA%B3%84/5.3%20Publish%20Domain%20Event.md)
     4. [주방 서비스 비지니스 로직 예제](https://github.com/jhhong0509/study/blob/master/msa/book/05%20%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4%20%EB%A1%9C%EC%A7%81%20%EC%84%A4%EA%B3%84/5.4%20%20%EC%A3%BC%EB%B0%A9%20%EC%84%9C%EB%B9%84%EC%8A%A4%20%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4%20%EB%A1%9C%EC%A7%81%20%EC%98%88%EC%A0%9C.md)
     5. [주문 서비스 비지니스 로직 예제](https://github.com/jhhong0509/study/blob/master/msa/book/05%20%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4%20%EB%A1%9C%EC%A7%81%20%EC%84%A4%EA%B3%84/5.5%20%EC%A3%BC%EB%AC%B8%20%EC%84%9C%EB%B9%84%EC%8A%A4%20%EB%B9%84%EC%A7%80%EB%8B%88%EC%8A%A4%20%EB%A1%9C%EC%A7%81%20%EC%98%88%EC%A0%9C.md)

---

### 리팩토링

- [리팩토링이란?](https://github.com/jhhong0509/study/blob/master/refactoring/1.what_is.md)
- [언제 리팩토링 해야 하는가?](https://github.com/jhhong0509/study/blob/master/refactoring/2.when_we_should_do.md)
- [어디를 리팩토링 해야 하는가](https://github.com/jhhong0509/study/blob/master/refactoring/3.where_we_should.md)
- [어떻게 리팩토링 해야 하는가](https://github.com/jhhong0509/study/blob/master/refactoring/4.how_we_should.md)

---

### NGINX

- [NGINX란?](https://github.com/jhhong0509/study/blob/master/nginx/basic.md)
- [NGINX 사용하기](https://github.com/jhhong0509/study/blob/master/nginx/use_nginx.md)

---

### DOCKER

- [도커란?](https://github.com/jhhong0509/study/blob/master/docker/what_is_docker.md)
- [도커파일](https://github.com/jhhong0509/study/blob/master/docker/dockerfile.md)
- [컨테이너 실행](https://github.com/jhhong0509/study/blob/master/docker/docker_run.md)
- [기타 도커 명령어](https://github.com/jhhong0509/study/blob/master/docker/docker_commands_etc.md)
- [도커 데이터베이스 연결](https://github.com/jhhong0509/study/blob/master/docker/docker_database.md)
- [Docker-Compose](https://github.com/jhhong0509/study/blob/master/docker/docker_compose.md)

---

### 데이터베이스

- [이름 명명 규칙](https://github.com/jhhong0509/study/blob/master/database/column_name_rule.md)
- [용어](https://github.com/jhhong0509/study/blob/master/database/words.md)
- [Varchar size에 따른 MySQL 동작](https://github.com/jhhong0509/study/blob/master/database/varchar_size.md)
- MongoDB
  - [스키마 설계](https://github.com/jhhong0509/study/blob/master/database/mongodb/scheme_structure.md)

### SPRING 공부

---

#### 디자인 패턴

- [FACADE 패턴](https://github.com/jhhong0509/study/blob/master/stu_spring/design_pattern/facade.md)
- [Factory 패턴](https://github.com/jhhong0509/study/blob/master/stu_spring/design_pattern/factory.md)

---

#### DB 최적화

- [INDEX](https://github.com/jhhong0509/study/blob/master/stu_spring/faster_db/index.md) (심화)

---

#### FILE SERVER

- [SPRING 기본 파일 상식](https://github.com/jhhong0509/study/blob/master/stu_spring/file/information.md)
- [SPRING 파일 업로드](https://github.com/jhhong0509/study/blob/master/stu_spring/file/file_upload.md)

---

#### 오류 해결

- JWT
    1. [다른 언어에서 발급된 토큰 검증](https://github.com/jhhong0509/study/blob/master/stu_spring/fixing/token_validate.md)
    2. [필터에서 Exception처리](https://github.com/jhhong0509/study/blob/master/stu_spring/fixing/token_filter_exception.md)
- 테스트코드
    1. [다른 테스트 간섭 문제](https://github.com/jhhong0509/study/blob/master/stu_spring/fixing/testcode_gradlew_not_work.md)
- Spring Data JPA
    1. [Spring Data JPA의 flush](https://github.com/jhhong0509/study/blob/master/stu_spring/fixing/spring_data_jpa_flush.md)
- 영속성 전이
    1. [PERSIST 저장 안됨](https://github.com/jhhong0509/study/blob/master/stu_spring/fixing/cascade_persist.md)
    2. [PESIST 중복저장](https://github.com/jhhong0509/study/blob/master/stu_spring/fixing/cascade.md)
    3. [DETACHED 엔티티 저장](https://github.com/jhhong0509/study/blob/master/stu_spring/fixing/cascade_merge.md)
- MySQL
    1. [AUTO_INCREMENT 이상](https://github.com/jhhong0509/study/blob/master/stu_spring/fixing/mysql_generatedvalue.md)
    2. [도커 MySQL 접속](https://github.com/jhhong0509/study/blob/master/stu_spring/fixing/mysql_connect.md)

---

#### ETC

- [객체지향](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/stu_object_oriented.md)
- [빈 이란](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/bean/what_is.md)
- [빈 등록 어노테이션](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/bean/bean_annotation.md)
- [Bean을 주입받을 때 Interface 타입을 사용해야 하는 이유](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/bean/why_use_interface.md)
- [SLSB](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/bean/slsb.md)
- [build.gradle 관련 설명](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/spring_basic/build.gradle.md)
- [CORS와 SOP](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/security/cors_and_sop.md)
- [CSRF](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/security/csrf.md)
- [커스텀 HEADER 네이밍 규칙](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/custom_header_naming.md)
- [Exception Handler](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/coding_ways/exceptionhandler.md)
- [Json Path 사용](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/coding_ways/json_path.md) (비추)
- [QueryParameter와 PathParameter 차이](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/coding_ways/query_vs_path.md)
- [직렬화](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/serialization.md)
- [서블릿](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/spring_basic/servlet.md)
- [var](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/var.md)
- [트랜잭션](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/transaction.md)
- [불변 객체](https://github.com/jhhong0509/study/blob/master/stu_spring/etc_information/immutable_object.md)

---

### 파일

- [상식](https://github.com/jhhong0509/study/blob/master/stu_spring/file/information.md)
- [파일 업로드](https://github.com/jhhong0509/study/blob/master/stu_spring/file/file_upload.md)

---

#### 기타 라이브러리

- Lombok
    - [@Builder](https://github.com/jhhong0509/study/blob/master/stu_spring/libraries/lombok/builder.md)
- MapStruct
    - [MapStruct란?](https://github.com/jhhong0509/study/blob/master/stu_spring/libraries/mapStruct/basic.md)
    - [설정하기](https://github.com/jhhong0509/study/blob/master/stu_spring/libraries/mapStruct/setup.md)
    - [시작하기](https://github.com/jhhong0509/study/blob/master/stu_spring/libraries/mapStruct/start.md)
    - [만난 에러들](https://github.com/jhhong0509/study/blob/master/stu_spring/libraries/mapStruct/errors.md)
- LOGBACK
    - [LOGBACK](https://github.com/jhhong0509/study/blob/master/stu_spring/libraries/logback.md)
- REST TEMPLATE
    - [Rest Template](https://github.com/jhhong0509/study/blob/master/stu_spring/libraries/rest_template.md)

---

#### ORM

- [상식](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/orm_stu.md)
- [Query DSL이란?](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/what_is_query_dsl.md)
- [Query DSL 세팅하기](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/query_dsl_settings.md)
- [JPQL이란](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/what_is_jpql.md)
- [EntityManager 타입](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/type_of_entitymanager.md)
- [save는 어떻게 동작하는가?](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/spring_data_save.md)
- [Natural ID 예제](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/demo-natural-id)

---

##### 자바 ORM 표준 JPA 프로그래밍

1. [챕터1](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter01.md)
2. [챕터2](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter02.md)
3. [챕터3](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter03.md)
4. [챕터4](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter04.md)
5. [챕터5](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter05.md)
6. [챕터6](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter06.md)
7. [챕터7](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter07.md)
8. [챕터8](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter08.md)
9. [챕터9](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter09.md)
10. [챕터10](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter10.md)
11. [챕터11](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter11.md)
12. [챕터12](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter12.md)
13. [챕터13](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter13.md)
14. [챕터14](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter14.md)
15. [챕터15](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter15.md)
16. [챕터16](https://github.com/jhhong0509/study/blob/master/stu_spring/orm/jpa_basic_orm/chapter16.md)

---

#### REDIS

- [Spring Data Redis](https://github.com/jhhong0509/study/blob/master/stu_spring/redis/redis.md)

---

#### REFLECTION

- [Reflection 이란](https://github.com/jhhong0509/study/blob/master/stu_spring/reflection_and_aop/what_is_reflection.md)

---

#### AOP

- [AOP란](https://github.com/jhhong0509/study/blob/master/stu_spring/reflection_and_aop/what_is_aop.md)
- [AOP 용어](https://github.com/jhhong0509/study/blob/master/stu_spring/reflection_and_aop/aop_words.md)
- [AOP 표현식](https://github.com/jhhong0509/study/blob/master/stu_spring/reflection_and_aop/aop_expression.md)
- [AOP 사용](https://github.com/jhhong0509/study/blob/master/stu_spring/reflection_and_aop/aop_code.md)

---

#### 소켓

- [WebSocket 소개](https://github.com/jhhong0509/study/blob/master/stu_spring/socket/01.websocket.md)
- [RSocket](https://github.com/jhhong0509/study/blob/master/stu_spring/socket/03.rsocket.md)
- STOMP
  - [소개](https://github.com/jhhong0509/study/blob/master/stu_spring/socket/stomp/01.introduce.md)
  - [설정](https://github.com/jhhong0509/study/blob/master/stu_spring/socket/stomp/02.basic_config.md)
  - [사용하기](https://github.com/jhhong0509/study/blob/master/stu_spring/socket/stomp/03.using.md)

---

#### JWT

- [JWT 코드 설명](https://github.com/jhhong0509/study/blob/master/stu_spring/token/stu_token.md)

---

### 스프링 부트와 AWS로 혼자 구현하는 웹 서비스

- [기타 정보](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/information.md)
- [프로젝트 생성](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/create_project.md)
- [웹 서버란](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/web_server.md)
- [테스트 코드에 대해](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/testcode.md)
- CODE
    - [컨트롤러](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/codes/controller.md)
    - [DTO와 Entity](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/codes/DTOs_and_Entity.md)
    - [JPA](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/codes/jpa.md)
    - [서비스 레이어](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/codes/service.md)
    - [템플릿(mustache)](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/codes/template.md)
    - [OAUTH](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/codes/oauth.md)
    - [테스트코드](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/codes/test_code.md)
- AWS
    - [RDS](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/rds.md)
    - [EC2](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/ec2.md)
    - [배포](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/publish.md)
    - [배포 자동화](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/auto_publish.md)
    - [무중단 배포](https://github.com/jhhong0509/study/blob/master/stu_spring/spring_boot_sole_web/no_stop_publish.md)

---

### WebFlux

- **개인 공부**

  1. [팁](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/00.tips.md)

  2. [Reactive Stream 기초](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/01.reactive_basic.md)

  3. [Observer Pattern](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/02.observer_pattern.md)

  4. [Observer Pattern과 Reactive Programming](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/03.reactive_with_observer.md)

  5. [Non Block & Block & Async & Sync](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/04.non_block_block.md)

  6. [함수형 프로그래밍](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/05.functional_programming.md)

  7. [Stream API](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/06.stream_api.md)

  8. [동시성 프로그래밍](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/07.concurrent_programming.md)

  9. [Future Pattern](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/08.future.md)

  10. [Reactor](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/09.reactor.md)

  11. [webflux 사용](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/10.use_webflux.md)

  12. [Backpressure 심화](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/11.dipper_backpressure.md)

  13. [Webflux Jackson](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/12.jackson.md)

  14. [RSocket](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/13.rsocket.md)

      > [Reactor Pattern](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/ex.reactor_pattern.md)

- [간단한 블로그 예제코드](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/test/auth)

<br>

- [GURU Webflux 강의](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru)

  > 예제 코드 포함

  1. [Reactive Programming](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-01)
     - [Reactive Programming 소개](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-01/reactive_prgramming.md)
  2. [Webflux 주요 키워드 소개](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-02)
     - [Mono 연산자](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-02/docs/01.Mono_Operations.md)
     - [Flux 연산자](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-02/docs/02.Flux_Operations.md)
     - [Filtering 방법](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-02/docs/03.Filtering.md)
     - [Step Verifier](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-02/docs/04.StepVerifier.md)
  3. [Reactive MongoDB](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-03)
     - [Reactive MongoDB](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-03/docs/01.start_mongo.md)
     - [CommandLinerRunner](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-03/docs/02.command_line_runner.md)
     - [Service 계층](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-03/docs/03.service_layer.md)
     - [MVC 방식 Controller](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-03/docs/04.mvc_controller.md)
     - [Event Streaming](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-03/docs/05.streaming_event.md)
  4. [Webclient](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04)
     - [RestTemplate](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/01.introduction.md)
     - [함수형 프로그래밍](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/02.functional_programming.md)
     - [RestTemplate vs Webclient](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/03.resttemplate_vs_webclient.md)
     - [토이프로젝트 Overview](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/04.overview_of_toyproject.md)
     - [Webclient 시작하기](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/05.start_webclient.md)
     - [Webclient로 요청 보내기](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/06.webclient_request.md)
     - [로깅](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/07.logging.md)
     - [Path Parameter 처리](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/08.path_parameter.md)
     - [Exception Handling](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/09.exception_handling.md)
     - [Reactive 스타일로 리팩토링](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-04/docs/02.functional_programming.md)

  6. [R2DBC 사용하기](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06)
     - [섹션 소개](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06/docs/01.introduce.md)
     - [R2DBC 소개](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06/docs/02/introduce.md)
     - [DB 초기화](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06/docs/03.init_database.md)
     - [findById 리팩토링](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06/docs/04.refactoring_findById.md)
     - [getList는 리팩토링](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06/docs/05.refactoring_getlist.md)
     - [save 리팩토링](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06/docs/06.refactoring_save.md)
     - [update 리팩토링](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06/docs/07.refactoring_update.md)
     - [update 예외처리 리팩토링](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06/docs/08.refactoring_update_exception.md)
     - [delete 리팩토링](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-06/docs/09.refactoring_delete.md)
  7. [함수형 프로그래밍으로 리팩토링](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-07)
     - [함수형 엔드포인트 사용 방법](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-07/docs/02.functional_endpoint.md)
     - [함수형 엔드포인트 예제](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-07/docs/03.getBeerById.md)
     - [함수형 엔드포인트에서 Validation 사용](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-07/docs/04.body_validation.md)
  8. [데이터 스트리밍](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-08)
     - [Service](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-08/docs/01.service.md)
     - [테스트코드](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-08/docs/02.test.md)
     - [Handler](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-08/docs/03.handler.md)

  10. [Rabbit MQ](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-10)
      - [소개](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-10/docs/01.overview.md)
      - [시작하기](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-10/docs/02.setup.md)
      - [설정하기](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-10/docs/03.config.md)
      - [메세지 전송](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-10/docs/04.message_sender.md)
      - [메세지 사용](https://github.com/jhhong0509/study/blob/master/stu_spring/webflux/Reactive-Programming-Guru/sec-10/docs/05.use.md)

---

### AWS

- Secret Manager

  > [예제코드](https://github.com/jhhong0509/study/tree/master/mentoring/secretmanager)

  - [소개](https://github.com/jhhong0509/study/blob/master/mentoring/aws_secrets.md)
  - [aws-secretsmanager-jdbc](https://github.com/jhhong0509/study/blob/master/mentoring/spring_aws_secret_manager.md)

---

### 배포 자동화

0. [유의사항](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/README.md)
1. [S3 세팅](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/01.s3_setting.md)
2. [IAM 설정](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/02.IAM_setting.md)
3. [CodeCommit 설정](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/03.code_commit.md)
4. [CodeBuild 설정](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/04.code_build.md)
5. [CodeDeploy를 위한 IAM 설정](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/05.IAM_for_code_deploy.md)
6. [EC2 설정](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/06.EC2_setting.md)
7. [CodeDeploy 설정](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/07.code_deploy_setting.md)
8. [CodePipeline 설정](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/08.code_pipeline_setting.md)
9. [Docker 사용](https://github.com/jhhong0509/study/blob/master/stu_spring/auto_deployment/09.change_to_docker.md)

---

### 코틀린

- [Kotlin 기초](https://github.com/jhhong0509/study/blob/master/kotlin/basic.md)
- [Kotlin vs Java](https://github.com/jhhong0509/study/blob/master/kotlin/vs_java.md)
- [코틀린 CRUD 예제](https://github.com/jhhong0509/study/blob/master/stu_spring/kotlin_basic)

---

### 코루틴

- [기본 개념](https://github.com/jhhong0509/study/blob/master/stu_spring/coroutine.md)
