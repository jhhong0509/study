# 챕터5 연관 관계 매핑

> 페러다임 불일치를 설명할 때 말했듯이, 객체와 테이블은 연관 관계의 매핑을 할 때 다른 점이 있다.
>
> 이번 챕터는 객체의 참조와 테이블의 외래 키를 매핑하는 것이 목적이다.

## 단방향 연관 관계

테이블의 구조

- 회원과 팀 테이블이 있다.

- 회원은 하나의 팀에만 속할 수 있다.

- 하나의 팀에는 여러 회원들이 올 수 있다.

  > 일대다 관계

![mapping_basic](.\images\mapping_basic.png)

- 객체 연관관계

  회원 객체는 Member의 team 필드로 팀 객체와 관계를 갖는다.

  회원 객체와 팀 객체는 **단방향 관계**다.

  즉, Member가 있으면 Team을 알 수 있지만 Team이 있다고 해서 Member를 알 수 없다.

- 테이블 연관관계

  회원 테이블은 MEMBER의 TEAM_ID를 통해 관계를 갖는다.

  회원 테이블과 팀 테이블은 **양방향 관계**이다.

  > 테이블은 기본적으로 양방향 관계를 갖는다.

  즉, TEAM_ID가 있으면 MEMBER들과 TEAM 모두 찾을 수 있다.

이러한 차이로 인해서 우리는 **객체를 양방향 관계로 만들어 준다.**

> 참조를 통해 연관 관계를 참조하고, 참조하고, 참조할 수 있는데 이걸 **객체 그래프 탐색** 이라고 한다.

Team과 Member 엔티티를 JPA의 Join을 통해 연관관계를 만들어 주면 아래와 같은 형태가 된다.

> 해당 코드는 Member -> Team으로 단방향 매핑이다.

``` java
@Getter
@Builder
@Entity
public class Member {
    @Id
    @Column(name="MEMBER_ID")
    private String id;
    
    private String username;
    
    // 연관관계 부분
    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;
}
```

``` java
@Getter
@Builder
@Entity
public class Team {
    @Id
    @Column(name="TEAM_ID")
    private String id;
    
    private String name;
}
```

##### @ManyToOne

다대일 관계라는 뜻 이다.

이렇게 **다중성을 나타내는 어노테이션은 필수**다.

| 속성         | 기능                                                         | 기본값                                               |
| ------------ | ------------------------------------------------------------ | ---------------------------------------------------- |
| optional     | 연관된 엔티티가 없어도 되는지 여부<br />true면 연관된 엔티티가 없어도 된다. | true                                                 |
| fetch        | 글로벌 페치 전략 설정                                        | @ManyToOne 에서는 EAGER,<br />@OneToMany 에서는 LAZY |
| cascade      | 영속성 전이 기능                                             |                                                      |
| targetEntity | 연관된 엔티티 타입 정보 설정. 거의 사용하지 않음.            |                                                      |

##### @JoinColumn

외래 키를 매핑 시킬 때 사용된다.

| 속성                                                         | 설명                                                         | 기본값                             |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ---------------------------------- |
| name                                                         | 매핑할 외래 키의 이름<br />즉, **매핑될 테이블의 PK**        | 필드명_참조테이블의 기본 키 컬럼명 |
| referencedColumnName                                         | 외래 키가 참조하는 대상 테이블의 컬럼명<br />**참조하는 테이블의 PK가 아닌 다른 컬럼과 매핑시키고 싶을 때 사용**한다. | 참조하는 테이블의 기본 키 컬럼명   |
| foreignKey                                                   | 외래 키 제약 조건을 지정한다.                                |                                    |
| unique<br />nullable<br />insertable<br />updatable<br />columnDefinition<br />table | @Column과 같은 효과다.                                       |                                    |

`@JoinColumn` 을 생략하면 기본 전략으로 **자동으로 외래 키를 찾는다.**

> 필드_참조테이블 PK
>
> team_TEAM_ID와 같은 형태이다.

referencedColumnName을 생략하면 **참조 테이블의 PK를 매핑시켜 준다.**

그러므로 다른 컬럼과 매핑시키고 싶다면 꼭 설정해 주어야 한다.

`@JoinColumn`은 생략할 수 있다.

### 연관 관계의 JPQL

연관 관계가 있는 엔티티는 2가지 방법으로 조회할 수 있다

- 객체 그래프 탐색
- 객체지향 쿼리 _(JPQL)_

우선 JPQL에서 연관 관계를 어떤 방식으로 사용하는지 알아본다.

#### 조회

JPQL은 JOIN을 지원하기 때문에 SELECT 시에 JOIN으로 가져온다.

> FetchType.LAZY인 경우에는 SELECT 시에 JOIN을 실행하지 않고 실제 객체가 사용될 때 쿼리가 실행된다.

Member 테이블을 조회하는 경우엔 다음과 같은 JPQL이 발생한다.

``` sql
select m from Member m
	join m.team t
	where t.name=:teamName
```

> :teamName은 파라미터를 바인딩 받는 문법이다.
>
> 쉽게 말해서, 내가 넘겨준 값이 :teamName 대신 들어간다.

#### 삭제

Member 객체의 team을 null로 바꾸면 연관 관계가 끊어지게 된다.

엔티티를 삭제하기 위해선 연관 관계를 먼저 제거하고 삭제해야 한다.

> 외래 키 제약 조건에 의해 DB에 오류가 발생한다.
>
> team_id에 매핑되는 team 테이블이 없기 때문이다.

즉, 연결된 모든 member 객체에서 team을 null로 바꾼 후에 team을 삭제할 수 있다.

``` java
member1.setTeam(null);
member2.setTeam(null);
.
.
.
em.remove(team)
```

## 양방향 연관 관계

지금까진 Member 객체에서 Team 으로만 접근할 수 있는 단방향 매핑을 사용했다.

단방향 연결이기 때문에, Member는 자신의 Team을 찾을 수 있었지만 Team은 자신에게 속한 Member를 찾을 수 없었다.

그렇기 때문에 양방향 연관 관계를 사용한다.

> 양방향 연관 관계를 이용한다 해서 DB에 변화가 생기는건 아니다.
>
> 원래 DB는 양방향 연관 관계를 가지기 때문이다.

양방향 연관 관계를 위해 Team 엔티티를 수정해야 한다.

> Member 엔티티는 똑같이 둬도 괜찮다.

``` java
@Getter
@Builder
@Entity
public class Team {
    
    @Id
    @Column(name="TEAM_ID")
    private Stirng id;
    
    private String name;
    
    
}
```

