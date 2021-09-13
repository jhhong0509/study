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