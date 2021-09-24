# 챕터 12 Spring Data JPA

대부분의 Repository는 단순한 CRUD만 반복된다.

이렇게 반복되는 코드는 개발자를 지루하게 하고 실수를 유발하게 된다.

## Spring Data JPA 소개

Spring Data JPA는 Spring에서 **JPA를 편리하게 사용할 수 있도록 지원하는 프로젝트**이다.

Spring Data JPA는 반복되는 CRUD 작업을 세련되고 간결하게 해결해 준다.

우선 **CRUD를 위한 공통적인 인터페이스를 제공**한다.

다음과 같이 인터페이스를 상속하기만 하면 된다.

``` java
public interface TestRepository extends JpaRepository<Test, Long> {
}
```

또한 해당 인터페이스는 **Application 구동 시점에서 Spring Data JPA가 알아서 구현체를 주입**해준다.

따라서 직접 구현체를 개발하지 않아도 된다.

<br>

그렇다면 기본적인 CRUD 외에 다른 필드로 조회를 하는 등의 작업은 어떻게 처리해야 할까?

신기하게도 Spring Data JPA는 메소드의 이름을 파싱해서 직접 쿼리를 만들어준다.

### Spring Data Project

Spring Data JPA는 Spring Data 프로젝트의 일부분이다.

Spring Data REDIS, Spring Data JPA, Spring Data HADOOP 등 다양한 종류가 존재한다.

## Spring Data JPA 설정

전에 했던 build.gradle 설정을 사용하면 된다.

또한 Spring Boot는 따로 설정할게 없다.

## 공통 인터페이스 기능

Spring Data JPA는 간단한 CRUD를 지원하는 JpaRepository라는 인터페이스를 지원한다.

Repository는 다음과 같은 구조를 가진다.

![1](https://t1.daumcdn.net/cfile/tistory/995883335CC5630C16)

또한 상속관계는 다음과 같다.

<img src="https://blog.kakaocdn.net/dn/KcVAb/btqT7XbZscj/CKSFUNj6a64iZ7auAZxVJk/img.png" alt="img" style="zoom:50%;" />

사용하는건 다음과 같다.

``` java
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByTitle(String title);
}
```

> 여기서 Test는 엔티티 이름이다.

위와 같이 JpaRepository에 제네릭으로 엔티티, PK 필드의 타입을 넣어준다.

쿼리를 짜는건 다음 키워드와 필드를 조합해서 만들 수 있다.

| 키워드           | JPQL                                                         |
| ---------------- | ------------------------------------------------------------ |
| And              | where 조건A and 조건B                                        |
| Or               | where 조건A or 조건B(And가 우선순위가 더 높다)               |
| Is, Equals       | where 필드 = :title                                          |
| Between          | where 날짜 between :startAt and :endAt                       |
| LessThan         | where 필드 < 파라미터                                        |
| LessThanEqual    | where 필드 <= :age                                           |
| GreaterThan      | where 필드 > :age                                            |
| GreaterThanEqual | where 필드 >= :age                                           |
| After            | where 날짜 > :beforeDate                                     |
| Before           | where 날짜 < :afterDate                                      |
| IsNull           | where 필드 is null                                           |
| IsNotNull        | where 필드 is not null                                       |
| Like             | where 필드 like :title                                       |
| NotLike          | where 필드 not like :title                                   |
| StartingWith     | where 필드 like :title<br />(파라미터 맨 뒤에 %가 자동으로 붙는다) |
| EndingWith       | where 필드 like :title<br />(파라미터 맨 앞에 %가 자동으로 붙는다) |
| Containing       | where 필드 like :title<br />(파라미터 앞 뒤에 %가 자동으로 붙는다) |
| Not              | where 필드 <> :title                                         |
| In               | where 리스트 in :listParam                                   |
| NotIn            | where 리스트 not in :listParam                               |
| TRUE             | where 불린 = true                                            |
| FALSE            | where 불린 = false                                           |
| IgnoreCase       | where UPPER(필드) = UPPER(:param)<br />(대소문자 구분 없이 비교하기 위해 모두 대문자로 변환해서 비교) |

### JPA NamedQuery

Spring Data JPA는 JPA NamedQuery를 호출하는 기능을 제공한다.

JPA NamedQuery는 쿼리에 이름을 부여해서 사용하는 방법으로, 다음과 같이 사용할 수 있다.

``` java
@Entity
@NamedQuery(
    name="Member.findByUsername",
    query="select m from Member m where m.username = :username"
)
public class Member {}
```

이렇게 정의한 쿼리를 호출하는 방법은 다음과 같다.

``` java
List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
    .setParameter("username", "회원1")
    .getResultList();
```

### 파라미터 바인딩

파라미터 순서로 바인딩하는 방법은 다음과 같이 사용하면 된다.

``` java
@Query("select m from Member m where m.username = :name")
Member findByUsername(@Param("name") String username);
```

@Param에서 정의한 이름대로 바인딩된다.

### 벌크성 수정 쿼리

벌크성 수정 쿼리란 **2차캐시나 영속성 컨텍스트를 무시**하고 **DB에 직접 쿼리**하는걸 의미한다.

한번에 대량의 데이터를 한 번에 수정할 때 사용된다.

영속성 컨텍스트를 무시하기 때문에 **영속성 컨텍스트와 DB간의 차이가 생길 수 있다.**

다음과 같이 사용할 수 있다.

``` java
String query = "update Product p set p.price = p.price * 1,1 where p.stockAmount < :stockAmount";
int resultCount = em.createQuery(query)
    .setParameter("stockAmount", stockAmount)
    .executeUpdate();
```

여기서 executeUpdate() 메소드를 통해 벌크성 수정을 구현할 수 있다.

<br>

Spring Data JPA는 다음과 같이 사용할 수 있다.

``` java
@Modifying(clearAutomatically = true)
@Query("UPDATE Test t SET t.title = :title where id > :startId")
int updateBulk(@Param("title") String title, @Param("startId") Long id);
```

여기서 clearAutomatically는 영속성 컨텍스트와 DB간의 차이를 해결하기 위해 해당 메소드가 호출된 후에 영속성 컨텍스트를 Clear해준다.

flushAutomatically는 해당 쿼리를 실행하기 전에 영속성 컨텍스트를 flush해준다.

### 반환 타입

Spring Data JPA는 **유연한 반환 타입**을 지원한다.

예를 들어 반환 타입이 여러개라면 자동으로 Collection 타입으로 치환해 주고, 하나라면 단일 엔티티를 반환해 준다.

``` java
List<Member> findByName(String name);		// List 반환
Member findByName(String name);				// 단일 엔티티 반환
```

만약 List 타입인데 결과가 비었다면 빈 컬렉션을 반환해 주고, 단일 엔티티 타입일 때 결과가 비었다면 null을 반환해 준다.

> 추가로 Null처리가 가능한 Optional 타입으로 반환시켜줄 수도 있다.

만약 단일 엔티티를 기대했는데 여러건이 조회되면 NonUniqueResultException이 발생한다.

추가로 단일 엔티티를 반환타입으로 지정하면 Spring Data JPA는 JPQL의 getSingleResult()를 호출한다.

하지만 해당 메소드는 결과값이 없으면 Exception이 발생하게 되는데, Spring Data JPA는 이를 무시하고 null을 반환해 준다.

### 페이징

Spring Data JPA는 페이징과 정렬을 간단하게 이용할 수 있도록 2가지 파라미터를 지원한다.

- Sort
- Pageable

Pageable을 쿼리의 파라미터로 넣게 되면 반환 타입으로 List 외에 Page타입을 사용할 수 있게 된다.

Spring Data JPA는 페이징 기능을 제공하기 위해 검색된 건수를 제공하는 count 쿼리를 추가로 발생시킨다.

<br>

검색 조건, 정렬 조건, 페이징 조건을 포함하고 있는 서비스를 만들어 보자

**Repository**

``` java
public interface MemberRepository extends Repository<Member, Long> {
    Page<Member> findByNameStartingWith(String name, Pageable pageable);
}
```

``` java
PageRequest pageRequest = new PageRequest(0, 10, new Sort(Direction.DESC, "name"));
Page<Member> result = memberRepository.findByNameStartingWith(name, pageRequest);

List<Member> members = result.getContent();
int totalPages = result.getTotalPages();
boolean hasNextPage = result.hasNextPage();
Long totalElements = result.getTotalElements();
```

> PageRequest는 Pageable을 구현한 구현체 이다.

우선 `(0,10, Sort)`는 한 페이지에 10개씩 띄우고, 현재 0페이지 라는 의미이다. 또한 Sort는 name 기준으로 DESC한다는 의미이다.

### QueryHint

QueryHint는 JPA에게 제공하는 주석이라고 생각하면 된다.

이를 통해 로깅에서 사용할 이름을 추가해줄수도 있고, readOnly 설정을 해줄수도 있다.

``` java
@QueryHints(value = {@QueryHint(name = "org.hibernate.readOnly", value = "true")}, forCounting = true)
Page<Member> findByName(String name, Pageable pageable);
```

위에서는 readOnly속성을 켜고, 동시에 count쿼리에도 동일하게 적용할지 여부를 선택해 준다.

### Lock

쿼리 시 Lock을 걸기 위해선 @Lock 어노테이션을 이용하면 된다.

자세한건 16장을 참고하면 된다.

``` java
@Lock(LockModeType.PESSIMISTIC_WRITE)
List<Member> findByName(String name);
```

## 명세

도메인 주도 설계 책 에서는 명세라는 개념을 소개하는데, Spring Data JPA는 Criteria로 이 개념을 사용할 수 있도록 한다.

명세를 이해하기 위한 핵심 단어는 술어인데, 단순히 참/거짓으로 나뉠 수 있다.

그리고 이들은 AND 또는 OR등의 연산자로 조합을 할 수도 있다.

예를 들면 **데이터를 검색하기 위한 제약 조건 하나하나를 술어** 라고 할 수 있다.

이러한 술어를 Spring Data JPA는 Specification 클래스로 정의되었다.

<br>

Specification은 컴포지트 패턴으로 구성되어 있어서 여러 Specification으로 구성되어 있어서 여러 Specification을 조합할 수 있다.

> Composite Pattern이란 **복합 객체를 단일 객체와 동일하게 취급**하는 것을 목적으로  **객체들의 관계를 트리 구조로 구성**하여 전체와 부분 관계를 표현하는 것이다.
>
> 다음 3가지로 구분된다.
>
> ![img](https://blog.kakaocdn.net/dn/bgrkyh/btqB8PPdkZv/RkZq7OjpP7sBd1hJ7Z7pk0/img.png)
>
> - **Component: **Leaf와 Composite를 위한 공통된 인터페이스 작성
> - **Leaf: **Component의 인터페이스를 구현한 Composite 클래스의 부품 클래스이다.
> - **Composite: **집합 객체로 Leaf 객체나 다른 Composite를 가진다.
>
> 컴퓨터를 예로 들면 다음과 같다.
>
> ![img](https://gmlwjd9405.github.io/images/design-pattern-composite/composite-solution2.png)
>
> 위와 같이 Computer 라는 Component는 Keyboard, Body, Monitor, Speaker와 같은 Leaf를 가진다.
>
> 그리고 Computer 라는 집합 클래스(Composite)는 Component를 가지며, computer는 새로운 Component를 추가하는 메소드를 가진다.
>
> 이렇게 하면 새로운 외부 장치가 생겨도 인터페이스를 추가하기만 하면 되기 때문에 변화에 능동적인 대응이 가능하다.

따라서 다양한 검색 조건을 조합해서 복잡한 쿼리도 가능하다.

<br>

명세 기능을 사용하려면 JpaSpecificationExecutor 인터페이스를 상속받으면 된다.