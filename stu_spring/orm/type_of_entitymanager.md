# Entity Manager의 타입

> [참고](http://www.piotrnowicki.com/2012/11/types-of-entitymanagers-application-managed-entitymanager/)

## 소개

JPA 명세는 몇가지 Entity Manager와 Persistence Context에 대한 타입을 정의했다.

- extended and transactional-scoped Entity Manager
- Container-managed or application-managed Entity Manager
- JTA or resource - local Entity Manager

<br>

## Extended and Transactional Scoped Entity Manager

> EntityManager의 연산이 여러 트랜잭션들에 포함될 수 있다는 것을 알려준다.

기본적으로 **Transactional 영속성 컨텍스트는 트랜잭션이 커밋될 때 모든 변경사항이 flush되고, 영속 상태의 객체들은 분리된 상태가 된다.**

Extended scope는 **Stateful한 EJBs**에서만 가능하다.

> **상태가 존재**하는 Enterprise Java Beans 라는 의미이다.

여기서 Stateful한 EJBs는 완벽하게 **SFSB**라고 할 수 있다.

> StateFul Session Bean이라는 의미로, 상태가 존재하는 Session Bean을 의미한다.

SFSB는 상태를 저장할 수 있기 때문에 **비지니스 메소드의 종료는 트랜잭션의 종료를 의미하지 않는다.**

<br>

하지만 SLSB는 상황이 다르다.

> StateLess Session Bean 이라는 의미로, 상태가 존재하지 않는 Session Bean을 의미한다.

SLSB에서는 비지니스 메소드가 종료됨이 따라 다음 메소드 호출에서 이전 정보를 가질 수 없기 때문에 **하나/트랜잭션 스코프의 EntityManager**가 필요하다.

<br>

만약 이런것을 설정해 주고 싶다면 다음과 같이 하면 된다.

```java
@PersistenceContext(type=javax.persistence.PersistenceContextType.EXTENDED)
EntityManager em;
```

> 기본값은 `javax.persistence.PersistenceContextType.TRANSACTION`이다.

<br>

**Spring Data JPA는 transaction-scoped다**

추가로, Extended를 사용하게 되면 흥미로운 점이 하나 있다.

[참고](./jpa-anti-facade.md)

## Container-managed vs Application-managed

다수의 java EE 애플리케이션은 다음과 같이 EntityManager를 주입받는다.

``` java
@PersistenceContext
EntityManager em;
```

이건 **컨테이너에게 EntityManager를 주입하도록**한다는걸 의미한다.

> 컨테이너는 EntityManagerFactory를 통해 EntityManager를 만든다.

이것은 **EntityManager가 컨테이너에 의해 관리**된다는걸 의미한다.

<br>

혹은 EntityManager를 EntityManagerFactory로 부터 직접 생성할 수 있다.

다음과 같이 EntityManagerFactory를 생성

``` java
@PersistenceUnit
EntityManagerFactory emf;
```

EntityManager를 받기 위해서는 다음 메소드를 실행해야 한다.

``` java
emf.createEntityManager()
```

이렇게 하면 **EntityManager가 애플리케이션에게 관리**된다는걸 의미한다.

즉, **EntityManager를 생성하고 삭제할 책임**을 지게된다.

모든 Application-managed 영속성 컨텍스트는 extended scope를 가진다.

> extended scope = 트랜잭션 종료와 EntityManager의 생명주기가 관계가 없음
>
> 즉 직접 EntityManager를 지워야 하므로 extended scope 라고 함.

<br>

**이미 생성한 EntityManager에 대한 통제**를 위해서 if문을 사용할 수도 있다.

예를 들어, 내부 JPA 실행자에 property를 설정하거나, 단순히 비지니스 메소드가 EntityManager를 갖는것을 막고싶을 수 있다.

하지만 트랜잭션의 여러개 bean들로 생성된 entity manager를 옮겨야 한다. 하지만 **컨테이너는 이러한 작업을 항상 해주진 않는다.**

따라서 **같은 영속성 컨텍스트에 연결된 새로운 Entity Manager를 만들게 된다.**

EntityManager의 공유를 위해 CDI를 이용할수도 있다

### JTA vs resource-local

> 여기서는 EntityManager의 트랜잭션을 JTA로 관리하거나 JTA의 직접적인 API를 커밋이나 롤백에 사용하고 싶을 때 필요한 것들을 설명한다.

만약 Container-managed EntityManager를 사용하고 있다면 **자동으로 JTA EntityManager를 사용해야 한다.**

만약 Application-managed EntityManager를 사용하고 있다면 **JTA 또는 resource-local을 사용할 수 있다.**

<br>

실제 개발에서 JTA EntityManager를 이용한다면 아래 두가지 중 더 높은 수준의 Transaction일때만 신경쓰면 된다.

- 선언형: annotation을 사용하거나 XML로 attribute를 설정
- 프로그래밍적: `javax.transaction.UserTransaction`를 사용한다.

<br>

만약 resource-local EntityManager를 사용하고 있다면 조금만 깊게 `EntityManager.getTransaction()`을
사용해서 `javax.persistence.EntityTransaction`을 반환받을 수 있다.

해당 트랜잭션을 받아서 commit, rollback, begin 등의 작업을 수행할 수 있다.