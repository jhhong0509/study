# 영속성 컨텍스트 팁

## unique로 영속성 컨텍스트 캐시 사용

### 문제점

기본적으로 영속성 컨텍스트는 **`@Id` 필드를 Key로 캐싱**되기 때문에 **아무리 unique 필드라도 캐싱된 값으로 조회할 수 없다.**

하지만 보통 자연 키 보다는 외래 키를 추천하는 추세이다.

따라서 자연 키 값으로 조회를 하려 하면, 영속성 컨텍스트의 캐싱된 값을 찾을 수 없어 DB 조회를 여러번 해야 하는 문제가 발생한다.

### 해결법

Hibernate에는 자연 키를 표현하기 위한 **`@NaturalId`** 라는 어노테이션이 존재한다.

`@Id`에 비해선 **복잡한 과정을 거쳐서 식별**하게 된다.

기본적으로 `@NaturalId` 어노테이션이 붙은 자연 키 필드는 불변성을 가진다.

만약 mutable 하게 만들고 싶다면 `@NaturalId(mutable = true)`와 같이 설정해 주면 된다.

<br>

``` java
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NaturalIdCache										// 2차 캐시에 저장
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)		// 캐시를 READ/WRITE 함
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String email;

}
```

예제 엔티티는 위와 같다.

<br>

hibernate의 session은 기본적으로 byNaturalId와 같은 메소드를 지원한다.

hibernate는 **1차 캐시, 2차 캐시에 Primary Key와 함께 Natural Key를 Key로 저장**한다.

<br>

``` java
@Repository
public class CustomUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public User findByEmail(String email) {
        Session session = entityManager.unwrap(Session.class);
        return session.byNaturalId(User.class)
                .using("email", email)
                .load();
    }
}
```

예제 Repository는 위와 같다.

<br>

단점이라면 Spring Data JPA에서 따로 지원하지 않기 때문에 **직접 EntityManager를 주입받아서 사용해 주어야 한다.**

또한 **Natural Key는 다음과 같이 한번에 여러 쿼리를 발생**시킨다.

``` sql
select user_.id as id1_0_ from user user_ where user_.email=?
select user0_.id as id1_0_0_, user0_.email as email2_0_0_ from user user0_ where user0_.id=?
```

### 리팩토링

위와 같은 방법은 NaturalId를 사용할때마다 EntityManager를 주입받아 Session을 추출하고 NaturalId 찾는 로직을 만들어 주어야 한다.

이러한 반복적인 작업을 줄이기 위해 NaturalId를 조회하는 로직을 처리해주는 부모 Repository를 생성해 준다.

<br>

우선 NaturalId를 사용할 때 상속받을 Repository를 하나 만들어 준다.

``` java
@NoRepositoryBean
public interface NaturalRepository<T, ID extends Serializable, NID extends Serializable> extends JpaRepository<T, ID> {
    Optional<T> findBySimpleNaturalId(NID naturalId);
}
```

우선 실제 Bean에 등록될 Repository가 아니기 때문에 `@NoRepositoryBean` 어노테이션을 붙여준다.

타입 파라미터로는 엔티티 타입(T), PK 타입(ID), 자연 키의 타입(NID)를 받아온다.

> 사실 NID 없이 그냥 Object 타입을 사용해도 되겠지만, 타입 체크를 위해서 받아온다.
>
> 이렇게 NaturalId의 타입을 받아오는건 위험하다. 나중에 여러개의 NaturalId를 가지는 상황이 오면 위 Repository를 사용할 수 없다.

이제 해당 Repository를 구현하는 구현체를 만들어 보자

``` java
@Transactional(readOnly = true)
public class NaturalRepositoryImpl<T, ID extends Serializable, NID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements NaturalRepository<T, ID, NID> {
    private final EntityManager entityManager;
    public NaturalRepositoryImpl(JpaEntityInformation entityInformation,
                                 EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }
    @Override
    public Optional<T> findBySimpleNaturalId(NID naturalId) {
        Optional<T> entity = entityManager.unwrap(Session.class)
                .bySimpleNaturalId(getDomainClass())
                .loadOptional(naturalId);
        return entity;
    }
}
```

여기서는 readOnly를 켜서 해당 Repository에서의 변경을 막음과 동시에 flush 비용을 절약해 준다.

그리고 SimpleJpaRepository를 상속받아서 JpaRepository의 메소드들을 구현하지 않아도 알아서 들어가게 된다.



그리고 엔티티들의 정보를 담고있는 JpaEntityInformation과 EntityManager를 받아서 SimpleJpaRepository에 넘겨주고, 자신의 EntityManager도 초기화 해준다.

마지막으로 NaturalId로 조회를 한다.

참고로 `loadOptional()` 이라는 메소드와 `load()` 라는 메소드는 **그 즉시 객체를 초기화** 한다.

반대로 `getReference()` 라는 메소드는 다음 3가지 경우로 나뉘게 된다.

- 세션에 값이 있는 경우

  세션에서 값을 가져오고 초기화된 엔티티를 반환한다.

- 세션에 값은 없지만 Proxy를 지원하는 경우

  초기화되지 않은 Proxy객체가 반환된다.

- 세션에 값도 없고 Proxy도 지원하지 않는 경우

  DB에 접근해서 값을 가져오고 반환한다.

