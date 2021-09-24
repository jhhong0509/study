# 챕터10 객체지향 쿼리 언어

## 객체지향 쿼리

하나 하나 EntityManager의 메소드를 호출해서 사용할 수 있다.

하지만 이걸로 애플리케이션을 개발하기엔 기능이 너무 빈약하다.

결국 SQL로 필요한 내용을 걸러서 조회해야 하는데, ORM을 사용하면 엔티티 객체를 대상으로 개발하기 때문에 검색할 때 언어가 필요하다.

그래서 나온게 JPQL이다.

## JPQL 특징

- 테이블이 아닌 객체를 대상으로 검색한다.
- SQL을 추상화 했기 때문에 특정 DB에 의존하지 않는다.
- SQL보다 간결하다.

## Criteria

JPQL의 생성을 돕는 빌더 클래스다.

- 컴파일 시점에서 오류를 발견할 수 있다.
- IDE가 코드 자동완성을 지원한다.
- 동적 쿼리를 작성하기 편하다.

## Native Query

JPQL을 사용하더라도, 특정 DB의 함수를 사용하고 싶을 수 있다.

그래서 JPA는 정말 생 쿼리문을 작성하도록 할 수 있다.

## QueryDsl

QueryDsl도 Criteria처럼 JPQL 빌더 역할을 한다.

QueryDsl은 코드 기반이면서, 단순해서 사용하기 쉬운데다, JPQL과 코드가 비슷해서 한눈에 들어온다.

> QueryDsl은 오픈소스 프로젝트인데, Spring Data Project의 지원을 받고있기 때문에 많은 사람들이 이용하며, 발전 가능성도 충분히 있다.

## JPQL 사용 방법

JPQL은 객체 지향 쿼리 언어이기 때문에 엔티티 객체를 대상으로 쿼리한다.

### 기본 문법

JPQL도 SQL과 비슷하게 SELECT, UPDATE, DELEETE를 사용할 수 있다.

참고로 INSERT는 PERSIST 메소드로 대체할 수 있기 때문에 없다.

### TypeQuery

JPQL을 실행하기 위해선 쿼리 객체를 만들어야 한다.

쿼리 객체는 TypeQuery와 Query가 있는데, TypeQuery는 **반환 타입을 명확히 지정할 수 있을 때 사용**한다.

``` java
TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
```

위와 같이 createQuery에 파라미터로 반환 타입을 명시해 주면 `TypedQuery<>` 타입을 반환해 준다.

``` java
Query query = em.createQuery("SELECT m.username, m.age FROM Member m");
```

만약 위와 같이 반환 타입이 명확하지 않다면 Query 객체를 사용해야 한다.

> String타입 + Integer타입

따라서 이럴땐 getResultList()를 했을 때 Object 타입을 반환한다.

만약 위와 같이 String + Integer라면 Object를 Object[]로 강제 형변환 해주면 0번째에 이름, 첫번째에 나이와 같이 매핑된다.

<br>

여기서 쿼리 결과를 Collection 타입으로 반환해주는  `getResultList()` 외에도 결과가 하나임을 보장하는 `getSingleResult()`도 있다.

### 파라미터 바인딩

> 파라미터 바인딩은 동적 쿼리를 작성하도록 돕는걸 의미한다.
>
> 예를 들면 이름을 기준으로 검색하고싶을 때 `"name" + username`과 같이 하는게 아니라 "name = :username"과 같이 변수 값을 넣어주는걸  의미한다.

파라미터 바인딩을 사용하지 않고 단순히 문자열을 이어붙여서 쿼리를 만들수도 있다.

하지만 그렇게 하게되면 SQL 인젝션 공격도 당할 수 있고, 성능이슈도 있다.

<br>

SQL 인젝션이 발생하는 이유는 클라이언트가 요청에 직접 쿼리를 보내게 되면 그 쿼리가 실제 DB에 날아갈 쿼리에 포함된다는 문제가 있다.

따라서 이는 **쿼리를 사용자 마음대로 수정**할수도 있게된다.

<br>

또한 파라미터 바인딩을 사용하면 파라미터로 들어온 값이 다르더라도 **같은 쿼리로 인식**한다.

따라서 JPA에서나 DB에서나 쿼리의 파싱 시간을 아껴줄 수 있게된다.

#### 이름 기준 파라미터

Named Parameters 는 **파라미터 이름으로 구분**하는 방법이다.

이름 기준 파라미터는 앞에 `:`을 붙여서 구분한다.

``` java
String usernameParam = "hello";

List<Member> result = em.createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class)
  .setParameter("username", usernameParam)
  .getResultList();
```

위와 같이 쿼리에는 `:파라미터이름`과 같이 사용하고 밑에서 `setParameter(key, value)` 와 같은 형식으로 사용하게 되면 `:`을 붙여줬던 파라미터는 value로 바뀌게 된다.

#### 위치 기준 파라미터

``` java
String usernameParam = "hello";

List<Member> result = em.createQuery("SELECT m FROM Member m WHERE m.username = ?1", Member.class)
  .setParameter(1, usernameParam)
  .getResultList();
```

쉽게 알 수 있다싶이 첫번째 파라미터 값으로 usernameParam을 넣어주고, 쿼리에서는 첫번째 파라미터를 받는다.

위치 기준 파라미터는 간단하다는 장점이 있지만 **이름 기준 파라미터 바인딩이 더 직관적이고 명확하다**.

### SELECT

#### SELECT

``` sql
SELECT m FROM Member AS m WHERE m.username = 'hello'
```

- 엔티티와 속성은 대소문자를 구분한다. 예를 들어 Member, username은 대소문자를 구분한다.

  반대로 다른 JPQL 키워드는 대소문자를 구분하지 않는다.

- JPQL에서 사용한 Member는 클래스가 아닌 엔티티 이름이다. 만약 `@Entity(name = ?)`처럼 이름을 바꾸었다면, 그걸 따라가야 한다.

  보통 엔티티 이름은 기본값인 클래스 이름으로 하는걸 추천한다.

- JPQL에서 `As m` 처럼 별칭은 필수다. 만약 없다면 잘못된 문법이라는 오류가 발생한다.

  JPA 구현체로 Hibernate를 사용하면 HQL 이라는걸 사용할 수 있는데, 여기선 별칭을 사용하지 않아도 된다.

#### 프로젝션

프로젝션이란 **SELECT절에 조회할 대상을 지정**하는것을 의미한다.

`SELECT 대상 FROM` 과 같이 사용할 수 있다.

- **엔티티 프로젝션**

  엔티티 프로젝션은 반환 타입이 엔티티인 것을 의미한다.

  ``` sql
  SELECT m FROM Member m;
  SELECT m.team FROM Member m;
  ```

  위와 같이 Member와 Team 엔티티를 각각 조회할 수 있고, 조회의 결과는 **영속성 컨텍스트에서 관리**된다.

- **임베디드 타입 프로젝션**

  JPQL에서 임베디드 타입은 엔티티와 거의 비슷하다.

  하지만 임베디드 타입은 조회의 시작점이 될 수 없다. 즉 FROM 절에서는 사용할 수 없다는 의미이다.

  ``` sql
  SELECT o.address FROM Order o
  ```

  하지만 **임베디드 타입은 값 타입이기 때문에 영속성 컨텍스트는 관리되지 않는다.**

- **스칼라 타입 프로젝션**

  숫자나 문자같은 기본 데이터 타입들을 스칼라 타입 이라고 한다.

  예를 들어 회원의 이름만 조회하려면 다음과 같이 할 수 있다.

  ``` java
  List<String> usernames = em.createQuery("SELECT username FROM Member m", String.class)
  		.getResultList();
  ```

- **여러 값 조회**

  엔티티 전체를 조회하면 편하겠지만 꼭 필요한 데이터만 선택해야 할 때도 있다.

  여러 값을 선택하면 **TypedQuery를 사용할 수 없고 Query를 사용해야 한다.**

  ``` java
  Query query = em.createQuery("SELECT m.username, m.age FROM Member m");
  List resultList = query.getResultList();
  
  for (Object[] objects : resultList) {
      String username = (String) objects[0];
    	Integer age = (Integer) objects[1];
  }
  ```

  이렇게 객체를 변환하는 작업은 귀찮다.

  따라서 New 명령어를 사용하면 훨씬 간단해진다.

  ``` java
  TypedQuery<UserDTO> query = em.createQuery("SELECT new this.is.package.UserDTO(m.username, m.age) FROM Member m", UserDTO.class);
  List<UserDTO> resultList = query.getResultList();
  ```

  이렇게 자신이 만든 DTO 클래스에 담아서 반환해줄 수 있다.

  new를 사용하려면 패키지명을 포함한 전체 클래스명과 순서와 타입이 올바른 생성자가 필요하다.

#### 페이징

페이징용 SQL은 반복적이고 지루하다.

또한 **DBMS마다 페이징 SQL 문법이 다르다.**

그렇기 때문에 JPA는 페이징을 `setFirstResult()`와 `setMaxResults()`로 추상화했다.

각각 조회의 시작 위치, 조회할 데이터의 수를 의미한다.

``` java
TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m ORDER BY m.username DESC", Member.class)
    .setFirstResult(10)
    .setMaxResults(20);
query.getResultList();
```

위 쿼리에선 `10개부터 20개`라는 의미이기 때문에 11~30번의 데이터를 조회한다.

> firstResult는 **0부터 시작**하기 때문에 11번부터 조회할 수 있다.

#### 집합

집합 함수의 종류는 아래와 같다.

| 함수     | 설명                                                        |
| -------- | ----------------------------------------------------------- |
| COUNT    | 결과의 개수를 Long 타입으로 반환한다.                       |
| MAX, MIN | 문자나 숫자 등의 최대, 최소 값을 구한다.                    |
| AVG      | 숫자 값의 평균값을 Double로 반환한다.                       |
| SUM      | 숫자 타입의 합을 구한다. Long, Double 등 타입으로 반환된다. |

**특징**

- 집합 함수에서 NULL값은 무시하기 때문에 통계에 잡히지 않는다.
- 값이 없는데 COUNT가 아닌 집합 함수를 사용하면 값이 NULL이 된다. COUNT는 0이 된다.
- DISTINCT를 집합 함수 안에서 중복을 제거한 후 집합을 구할 수 있다.
- DISTINCT를 COUNT에서 사용할 때 임베디드 타입으로 DISTINCT는 불가능하다.

#### GROUP BY, HAVING

GROUP BY는 통계 데이터를 구할 때 특정 그룹끼리 묶어준다.

또한 HAVING은 GROUP BY에서 그룹화된 통계데이터로 필터링을 할 수 있다.

``` sql
SELECT t.name, COUNT(m.age), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age)
FROM Member m LEFT JOIN m.team t
GROUP BY t.name
HAVING AVG(m.age >= 10);
```

#### 정렬

ORDER BY는 **결과를 정렬**할 때 사용된다.

`ORDER BY 필드 ASC`나 `ÒRDER BY 필드 DESC`와 같이 할 수 있다.

### JOIN

#### INNER JOIN

INNER JOIN에서 JOIN은 생략할 수 있다.

``` java
String teamName = "팀A";
String query = "SELECT m FROM Member m INNER JOIN m.team t WHERE t.name = :teamName";
List<Member> members = em.createQuery(query, Member.class)
    .setParameter("teamName", teamName)
    .getResultList();
```

#### OUTER JOIN

OUTER JOIN은 다음과 같이 사용할 수 있다.

``` sql
SELECT m FROM Member m LEFT JOIN m.team t
```

#### 컬렉션 조인

일대다 또는 다대일처럼 컬렉션을 사용하는 곳에 조인하는 것을 의미한다.

예를 들어 team은 여러 member를 가지게 되고, member는 하나의 team을 가지게 된다.

``` sql
SELECT t, m FROM Team t LEFT JOIN t.members m
```

> IN 키워드도 사용할 수 있지만, 별로 추천되진 않는다.

#### 세타 조인

INNER JOIN의 WHERE 절에서 세타 조인을 사용할 수 있다.

> 세타 조인이란 `>`, `<`와 같이 비교 연산 등을 이용해서 관계가 없는 엔티티끼리 JOIN을 해주는 것이다.

``` sql
SELECT COUNT(m) FROM Member m, Team t where m.username = t.name
```

위와 같이 team의 이름과 member의 이름을 join해줄 수 있다.

#### JOIN ON

JPA 2.1부터 JOIN ON을 지원한다.

ON을 이용하면 JOIN 대상을 필터링하고 조인해줄 수 있다.

참고로 INNER JOIN의 ON은 WHERE에서 사용할때와 겨로가가 같기 때문에 보통 OUTER JOIN에서만 사용한다.

#### Fetch JOIN

Fetch JOIN은 SQL에 있는게 아니라, JPQL에서 성능 최적화를 하기 위해서 사용되는 것이다.

연관된 엔티티를 **한 번에 조회**하는 기능이다.

##### Entity Fetch JOIN

Entity Fetch JOIN을 사용하면 Member와 Team을 함께 조회해 준다.

엔티티에서 설정한 FetchType.EAGER와 같은 의미지만, JPQL에서의 설정이 우선순위가 더 높다.

``` sql
select m from Member m join fetch m.team
```

참고로 fetch join에선 별칭을 사용할 수 없다.

위 JPQL에서는 **DB에 JOIN을 해서 한번에 결과를 가져오게 된다.**

그렇기 때문에 Member 엔티티가 Detached 상태가 되더라도 Team 엔티티는 여전히 영속성 컨텍스트에 존재할 수 있다.

#### Collection Fetch JOIN

// todo...

// criteria는 스킵 / jpql은 조금만 나중에

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

  > querydsl은 entity 클래스를 사용하지 않기 때문에 따로 생성해 주어야 한다.

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

