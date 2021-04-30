# 챕터10 객체지향 쿼리 언어

### 객체지향 쿼리

하나 하나 EntityManager의 메소드를 호출해서 사용할 수 있다.

하지만 이걸로 애플리케이션을 개발하기엔 기능이 너무 빈약하다.



결국 SQL로 필요한 내용을 걸러서 조회해야 하는데, ORM을 사용하면 엔티티 객체를 대상으로 개발하기 때문에 검색할 때 언어가 필요하다.

그래서 나온게 JPQL이다.



### JPQL 특징

- 테이블이 아닌 객체를 대상으로 검색한다.
- SQL을 추상화 했기 때문에 특정 DB에 의존하지 않는다.
- SQL보다 간결하다.

### Criteria

JPQL의 생성을 돕는 빌더 클래스다.

~~어차피 뒤에서 나오는 Querydsl만 쓴다. 알아만 두자~~

- 컴파일 시점에서 오류를 발견할 수 있다.
- IDE가 코드 자동완성을 지원한다.
- 동적 쿼리를 작성하기 편하다.

### Native Query

JPQL을 사용하더라도, 특정 DB의 함수를 사용하고 싶을 수 있다.

그래서 JPA는 정말 생 쿼리문을 작성하도록 할 수 있다.

### QueryDsl

QueryDsl도 Criteria처럼 JPQL 빌더 역할을 한다.

QueryDsl은 코드 기반이면서, 단순해서 사용하기 쉬운데다, JPQL과 코드가 비슷해서 한눈에 들어온다.

> QueryDsl은 오픈소스 프로젝트인데, Spring Data Project의 지원을 받고있기 때문에 많은 사람들이 이용하며, 발전 가능성도 충분히 있다.

### JPQL 사용 방법

JPQL은 객체 지향 쿼리 언어이기 때문에 엔티티 객체를 대상으로 쿼리한다.

#### 기본 문법

JPQL도 SQL과 비슷하게 SELECT, UPDATE, DELEETE를 사용할 수 있다.

참고로 INSERT는 PERSIST 메소드로 충분하기 때문에 없다.



#### SELECT

``` sql
SELECT m FROM Member AS m WHERE m.username = 'hello'
```

- 엔티티와 속성은 대소문자를 구분한다. 예를 들어 Member, username은 대소문자를 구분한다.

  > 반대로 다른 JPQL 키워드는 대소문자를 구분하지 않는다.

- JPQL에서 사용한 Member는 클래스가 아닌 엔티티 이름이다. 만약 `@Entity(name = ?)`처럼 이름을 바꾸었다면, 그걸 따라가야 한다.

  > 보통 엔티티 이름은 기본값인 클래스 이름으로 하는걸 추천한다.

- JPQL에서 `As m` 처럼 별칭은 필수다. 만약 없다면 잘못된 문법이라는 오류가 발생한다.

> JPA 구현체로 Hibernate를 사용하면 HQL 이라는걸 사용할 수 있는데, 여기선 별칭을 사용하지 않아도 된다.





### JPQL과 Criteria는 생략

#### QueryDSL

Criteria는 복잡하다는 큰 단점이 있었는데, 작성이 쉽고 간결하고, 쿼리와 비슷한 형태로 개발할 수 있는 프로젝트가 QueryDSL이다.

QueryDSL은 **JPQL 빌더** 역할을 수행하는 오픈소스 프로젝트로, 자바 컬렉션이나 JPA, JDBC, 몽고DB 등 다양하게 지원한다.

> 참고로 QueryDSL은 데이터를 조회하는 기능에 특화되어 있다.



#### QueryDSL 설정

필수 라이브러리는 다음과 같다.

``` groovy
compile("com.querydsl:querydsl-jpa")
annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jpa"
```

- querydsl-jpa

  QueryDSL의 JPA 라이브러리를 받아온다.

- querydsl-apt

  AnnotationProcessor 즉 컴파일 단계에서 쿼리 타입을 생성할 때 필요한 라이브러리 이다.

  QUser 과 같이, Q가 붙은 클래스들을 자동으로 생성해 준다.
  
  >  querydsl은 entity 클래스를 사용하지 않기 때문에 따로 생성해 주어야 한다.

쿼리타입은 사용의 편리를 위해 기본적인 인스턴스를 포함하고 있다.



쿼리 타입의 기본 인스턴스를 사용하면 쿼리 타입을 훨씬 간결하게 할 수 있다.

``` java
import static ㅁㅁㅁㅁ.ㅁㅁㅁㅁ.ㅁㅁㅁㅁ.QEntity.entity;
```

이렇게 하면 따로 생성자 호출 등의 작업이 필요 없다.



#### 검색 조건 쿼리

QueryDSL은 먼저 아래와 같은 형태를 가진다.

``` java
JPAQueryFactory jpaQueryFactory
```

