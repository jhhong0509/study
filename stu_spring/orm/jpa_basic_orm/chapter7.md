# 챕터7 고급 매핑

### 상속 관계 매핑

관계형 데이터베이스는 상속이라는 개념이 없다.

하지만 비슷한 개념으로 **슈퍼타입 - 서브타입 관계(Super-Type Sub-Type Relationship)** 라는게 있다.

![super_sub](.\images\super_sub.png)

#### 슈퍼타입 서브타입 관계

간단하게 말하자면, 위 그림처럼 **하나의 테이블이 다른 여러 테이블이 공통으로 가지는 속성**을 가지고있을 때, 상속과 비슷하게 **하나의 테이블에서 겹치는 속성을 관리하는 것을 말한다.**

이러한 관계가 객체의 상속과 가장 비슷한 형태인데, 저런 형태의 테이블과 객체는 3가지 방법으로 구현할 수 있다.

#### 조인 전략으로 구현

조인 전략은 위 그림의 엔티티 하나 하나를 **모두 테이블로 만들고**, 자식이 **부모 테이블의 PK를 받아서 PK + 외래 키로 사용하는 전략**이다.

하지만, 객체는 타입으로 구분이 가능하지만 **테이블은 타입의 개념이 없다.**

그렇기 때문에 **타입을 구분하는 컬럼을 추가**하고, 타입에 따라서 다르게 JOIN하면 된다.

![super_sub_join](.\images\super_sub_join.png)

DTYPE이란 컬럼을 통해 어느 테이블과 매핑될지 정하고, 매핑될 테이블과 연결시켜 주는 형태이다.

엔티티 코드로 보면 아래와 같다.

``` java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    
    private String name;
    
    private int price;
}
```

``` java
@Entity
@DiscriminatorValue("A")
public class Album extends Item {
    private String artist;
}
```

``` java
@Entity
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;
    private String actor;
}
```

우선 코드 설명은 아래와 같다.

- `@Inheritance(strategy = InheritanceType.JOINED)`

  해당 어노테이션은 부모 클래스에 **상속 매핑이라고 명시**하는 어노테이션이다.

  슈퍼타입 - 서브타입 관계를 구현할 때에 부모 클래스에 항상 써 주어야 하는 어노테이션이다.

  > 여기선 JOIN 방법을 사용했기 때문에 JOINED로 설정했다.

- `DiscriminatorColumn(name="DTYPE")`

  부모 클래스에서 자식을 구분할 컬럼을 지정한다.

  이 컬럼으로 **어떤 테이블과 매핑될지 구분**할 수 있다.

  > 아까 말했듯이 테이블은 객체와 달리 타입이 없어서 구분할 수 없다.

  > 기본값은 "DTYPE"이다.

- `@DiscriminatorValue()`

  위 어노테이션은 **DTYPE에 어느 값이 있을 때 자신과 매핑될 것인지 지정**할 수 있다.

  만약 DTYPE에 M이 저장되어 있다면, `@DiscriminatorValue("M")`이라는 어노테이션이 붙어있는 테이블과 매핑된다.

##### JOIN 전략의 장점

- 테이블이 정규화 된다.

- 저장공간을 효율적으로 사용한다.

- 외래 키 참조 무결성 제약 조건을 활용할 수 있다.

  > 참조 무결성이란 **외래 키는 참조할 수 없는 값을 가질 수 없는 것**으로, 데이터베이스의 무결성 제약 조건중 하나이다.
  >
  > 즉, member와 team이 FK로 연결되어 있을 때, member의 FK 값을 team에서 찾았을 때, 꼭 있어야 된다는 의미이다.
  >
  > 다른 무결성 제약 조건은 다음과 같다.
  >
  > - 개체 무결성
  >
  >   **PK는 null값을 가질 수 없다.**
  >
  > - 참조 무결성
  >
  >   위에서 설명했다.
  >
  > - 도메인 무결성
  >
  >   **특정 속성 값은 그 속성이 정의된 도메인에 속한 값 이어야 한다.**
  >
  >   쉽게 말해서, 테이블을 만들 때 원했던 값 외에는 오면 안된다는 의미다.
  >
  >   NULL이면 안되는 속성에 NULL이 오거나, 주민등록번호에 알파벳이 오는 등의 상황이 도메인 무결성이 깨진다고 할 수 있다.
  >
  > - null 무결성
  >
  >   **특정 속성 값에 null을 가질 수 없다는 규칙**
  >
  >   단순히 NOT NULL이 필요한 속성엔 NOT NULL을 붙이라는 의미다.
  >
  > - 고유 무결성
  >
  >   **특정 속성 값은 서로 달라야 한다.**
  >
  >   쉽게 말하면 하나에 저장된 데이터중 완전히 똑같은 데이터가 있으면 안된다는 의미이다.
  >
  >   자기소개나 이름과 같은 경우에는 겹칠수도 있지만, 모두 겹치는 경우는 없어야 한다.

##### JOIN전략의 단점

- 조회할 때 JOIN이 많이 발생하기 때문에 성능이 저하된다.

- 조회 쿼리가 복잡하다.

- 데이터 INSERT 시에 INSERT QUERY가 두번 발생한다.

  > 부모 테이블과 자식 테이블 모두에 저장되기 때문이다.`

#### 단일 테이블 전략

테이블 하나만 사용하는 전략이다.

![super_sub_one](.\images\super_sub_one.png)

``` java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    
    private String name;
    
    private int price;
}
```

> 다른 엔티티는 건드릴 필요가 없다.

단순히 InheritanceType을 바꾸어 주면 JPA가 알아서 바꿔준다.

이제 JPA는 DDL 생성 시에, ITEM 이란 테이블에서 모든 속성들을 수합해서 하나의 테이블로 만들어 준다.

##### 단일 테이블 전략의 장점

- JOIN이 발생하지 않아서 일반적으로 **가장 빠르다**
- **조회 쿼리가 단순**하다.

##### 단일 테이블 전략의 단점

- **NULL 값을 허용해야 한다.**

  만약 ARTIST로 저장하고 싶다면 다른 DIRECTOR이나 ACTOR과 같은 속성은 NULL이 들어가야 한다.

- 단일 테이블에 모두 저장하기 때문에, 테이블이 커질 수 있다. 따라서 **오히려 느려질 수 있다**.

### 구현 클래스마다 테이블 전략

말 그대로 **구현 클래스마다 테이블을 만든다.**

우선 코드부터 확인하면, InheritanceType 외에도 `@DiscriminatorColumn(name = "DTYPE")`이 사라진걸 확인할 수 있다.

따로 컬럼을 만들어서 구분할 필요가 없기 때문이다.

또한 구분을 위해 사용되던 `@DiscriminatorValue()`도 사라졌다.

``` java
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;
    
    private String name;
    
    private int price;
}
```

``` java
@Entity
public class Album extends Item {
    private String artist;
}
```

``` java
@Entity
public class Movie extends Item {
    private String name;
    
    private String actor;
}
```



이런식으로 만들면 아래와 같은 테이블 구조를 갖는다.

![super_sub_every](.\images\super_sub_every.png)

이런 식으로 **Item 테이블는 생성되지 않고, ITEM의 속성들을 가지고 있는 3개의 엔티티가 생긴다.**

##### 구현 클래스마다 테이블 전략 장점

- **NOT NULL 제약 조건을 사용할 수 있다.**
- 서브 타입을 구분하여 처리할 때 좋다.

##### 구현 클래스마다 테이블 전략 단점

- **여러 테이블을 함께 조회할 때 느리다.**

  UNION을 사용해야 한다.

- 자식 테이블을 통합해서 **쿼리를 작성하기 힘들다.**

> 이 전략은 ORM 전문가, 데이터베이스 설계자 **모두 추천하지 않는 방법이다.**
>
> 웬만하면 조인이나 단일 테이블 전략을 사용하자.

### @MappedSuperclass

상속 관계 매핑에선 **부모 클래스와 자식 클래스 모두 DB의 테이블과 매핑되었다.**

하지만 자식 클래스에게 매핑 정보만 제공하고 싶다면 `@MappedSuperclass` 어노테이션을 사용하면 된다.

> **`@MappedSuperclass`는 실제 테이블과 매핑되지 않는다.**
>
> 즉 그냥 필드 선언의 중복을 줄이기 위해 따로 빼내는 것이다.

<img src=".\images\mapped_super_class_basic.png" alt="mapped_super_class" style="zoom: 80%;" />

위 사진과 같이 **공통된 속성이 있다면, 계속해서 중복해서 선언해 주어야 한다.**

id나 생성일 같은 경우에는 계속해서 사용될 가능성이 높다. 그렇기 때문에 따로 부모 클래스로 구현하고 상속하는 것이 훨씬 깔끔하다.

``` java
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
}
```

``` java
@Entity
public class Member extends BaseEntity {
    // id와 name이 상속된다.
    private String email;
}
```

``` java
@Entity
public class Seller extends BaseEntity {
    // id와 name이 상속된다.
    private String shopName;
}
```

위와 같은 형태에서 Member 엔티티는 email 밖에 없는듯 하지만, 실제로는 **id, name, email 속성을 갖게 된다.**

> 따로 **BaseEntity 테이블은 생성되지 않고 무시된다.**

#### 부모의 속성 재정의

부모의 속성 재정의는 `@AttributeOverride` 어노테이션을 사용하면 된다.

실제 코드로 보자

``` java
@Entity
@AttributeOverride(name = "name", column = @Column(name = "sirname", nullable = true))
public class Member extends BaseEntity {
    private String email;
}
```

이런 식으로 사용하면 name 이라는 이름의 속성을 sirname 이라는 이름의 nullable 필드로 변경해 준다.

여러개를 재정의 하고 싶다면 `@AttributeOverrides`를 사용하면 딘다.

``` java
@Entity
@AttributeOverrides({
    @AttributeOverride(name = "name", column = @Column(name = "sirname", nullable = true)),
    @AttributeOverride(name = "id", column = @Column(name = "member_id"))
})
public class Member extends BaseEntity {
    private String email;
}
```

@MappedSuperclass는 **자식 클래스에 엔티티 매핑 정보를 상속하기 위해** 사용된다.

그렇기 때문에 JPQL에서 사용될 수  없으며, 클래스를 직접 생성해서 사용할 일은 거의 없으므로 **추상 클래스로 만드는걸 권장한다.**

> 참고로 `@Entity`는 `@Entity` 또는 `@MappedSuperclass`로 지정된 클래스만 상속받을 수 있다.