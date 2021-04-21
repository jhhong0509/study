# QueryDsl 기본 세팅

### build.gradle

``` gradle
compile 'com.querydsl:querydsl-jpa'
compile 'com.querydsl:querydsl-apt'
```

querydsl을 사용하기 위해 build.gradle 설정을 해야 한다.



### Configuration

``` java
@Configuration
public class QuerydslConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}

```

`@PersistenceContext`는 entityManager를 주입 받을 때 사용하는 어노테이션 이다.

`@PersistenceUnit`을 통해 entityManagerFactory를 주입받을 수도 있다.



`JPAQueryFactory`는 여러 querydsl의 종류 중 하나이다.

종류는 다음과 같다.

|                 | Singleton | use EntityManager  | use EntityClass | language |
| --------------- | --------- | ------------------ | --------------- | -------- |
| JPAQuery        | new       | yes                | yes             | JPQL     |
| JPAQueryFactory | yes       | yes                | yes             | JPQL     |
| JPASQLQuery     | new       | yes(not use cache) | No(Table)       | SQL      |
| SQLQuery        | new       | No(DataSource)     | No(Table)       | SQL      |
| SQLQueryFactory | yes       | No(DataSource)     | No(Table)       | SQL      |

JPASQLQuery는 추가적인 EntityManager와 매핑시키고 싶을 때 사용된다.

