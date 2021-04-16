# 챕터9 값 타입

JPA는 **엔티티 타입**과 **값 타입**으로 나눌 수 있다.

쉽게 말하면 엔티티 타입은 **`@Entity`로 정의된 객체**이고, 값 타입은 int나 String 같은 **자바 기본 타입이나 객체**를 의미한다.



엔티티와 값 타입의 가장 큰 차이점은 **식별자의 존재 여부**이다.

엔티티는 값이 변경되어도 식별자를 통해 계속해서 같은 회원이 수정된다.

하지만 값 타입은 변경되는 아예 다른 것으로 인식된다.

> 예를 들어 회원 엔티티에서 나이, 이름 등 값을 수정해도 식별자만 잘 존재한다면 항상 같은 회원이다.
>
> 하지만 값 타입은 나이를 10에서 20으로 바꾸게 되면 완전히 다른 값이 되어버린다.

## 값 타입

### 기본값 타입

우선 알기 쉽게 단순한 엔티티를 만들어 보자

``` java
@Entity
public class Member {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private int age;
}
```

해당 엔티티에서 값 타입은 name과 age다.

Member 엔티티는 생명주기를 갖고, 식별자를 갖는다.

하지만 name과 age는 식별자도 없고 생명주기도 엔티티에 의존하게 된다.



또한 값 타입은 **공유되면 안된다.**

값 타입을 공유하게 되면 A 엔티티를 수정했을 때 B도 함께 수정되는 일이 발생하게 된다.

> 자바의 기본 타입들은 공유되지 않는다.
>
> int b = 10;
>
> int a = b;
>
> 를 하면 10 이라는 값이 새로 생겨서 a에 저장된다

### 임베디드 타입(복합 값 타입)

**사용자가 직접 정의한 값 타입**을 JPA에서 임베디드 타입 이라고 한다.

> 값 타입 이란걸 기억해 두자.

``` java
@Entity
public class Member {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private String city;
    
    private String street;
    
    private String zipCode;
}
```

이렇게 Member에 집 주소와 이름, 시작/끝 날짜가 저장되게 된다.

하지만 이렇게 전무 늘여놓는건 객체지향적이지 않고, 응집력을 떨어뜨린다.

근무 기간이나 주소같이 묶을 수 있는건 묶는편이 좋다.

``` java
@Embeddable
@NoArgsConstructor
public class Period {

    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
}

@Embeddable
@NoArgsConstructor
public class Address {
    
    @Column(name = :"city")
    private String city;
    
    private String street;
    
    private String zipCode;
}

@Entity
public class Member {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    @Embedded Preiod workPeriod;
    @Embedded Address address;
}
```

위와 같이 하면 훨씬 간결하고 가독성이 좋아진다.

> `@Embeddable`을 붙여줘야 한다.

<img src="./images/embedded_uml.jpg" alt="embedded" style="zoom:50%;" />

위와 같이 엔티티가 응집력 있게 변하게 된다.

이런식으로 값 타입을 의미 있게 만들어줄 수 있는데다가, 재사용도 가능하다.

> `@Embeddable`이 붙은 클래스는 기본 생성자가 필수다.

이러한 관계를 UML로 표현하면 **Composition 관계** 라고 한다.

> hibernate는 임베디드 타입을 components 라고 부른다.

<img src="./images/embedded_mapping.jpg" alt="proxy" style="zoom: 80%;" />

임베디드 타입은 위와 같이 매핑된다.

임베디드 타입은 테이블의 기준에서 단순히 값 타입일 뿐이다. 그렇기 때문에 모든 속성을 늘여썼을 때와 같은 구조를 갖는다.

> 테이블에 점선은 그냥 구분 표시라서 무시해도 된다.

이러한 이유들 때문에 JPA에선 실제 테이블보다 클래스가 많은 경우가 많다.

##### @MappedSuperclass와 @Embeddable의 차이

설명만 들었을 때에는 `@MappedSuperclass`와 `@Embeddable`은 거의 동일한 기능을 제공한다.

하지만 `@MappedSuperclass`와 `@Embeddable`는 목적이 전혀 다르다.

- @MappedSuperclass

  `@MappedSuperclass`는 공통된 엔티티에서 사용하는 속성들을 모아둔 것으로, PK 속성이나 생성일 등이 포함된다.

​	공통된 엔티티에서 사용되는 속성들의 모임이기 때문에 **중복 제거**를 위해 사용된다.

​	그리고 `@MappedSuperclass`는 상속을 통해 가져온다.

- `Embeddable`

  `Embeddable`은 엔티티의 **일부 속성을 클래스로 추출**해 내는 것이다.

  그렇기 때문에 **엔티티 클래스의 가독성을 높히기 위해** 사용되며, 재사용성과는 크게 관련이 없다.

  그리고 `Embeddable`은 엔티티의 필드로써 동작하기 때문에 가독성을 높이는게 목적이다.