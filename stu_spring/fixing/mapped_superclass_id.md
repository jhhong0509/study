# @MappedSuperClass로 @Id 묶기

### 발생 이유

거의 모든 엔티티에서 `@Id`를 `GeneratedValue`로 사용하고 있었는데, 중복을 최소화 하기 위해서 `@MappedSuperclass`로 PK를 묶어줬다.

``` java
@Getter
@MappedSuperclass
public abstract class BaseId {
    @Id
    @GeneratedValue
    private Long id;
}
```

하지만 POST시에 ID가 중복되었다는 오류가 발생한다.

### 해결 과정

`@MappedSuperclass`를 사용하지 않고 엔티티마다 따로따로 `@Id`를 생성하니 해결되었다.

혹은 DB 내용을 한번 지운 후에 다시 INSERT하면 잘 작동한다.

### 후기

`@MappedSuperclass`가 단순히 필드를 상속하도록 돕는건줄 알았는데 생각보다 복잡한 것 같았다.

### 주의

**`@MappedSuperclass`에 `@Id`를 사용하면 AUTO_INCREMENT가 공유된다.**

쉽게 말해서 보통 A 테이블에 데이터 두개, B 테이블에 하나 넣으면 아래와 같이 ID가 개별적으로 들어간다.

- A테이블

| id   | name   |
| ---- | ------ |
| 1    | 홍정현 |
| 2    | 장현용 |

- B테이블

| id   | character |
| ---- | --------- |
| 1    | 망함      |
| 2    | 이게뭐람  |

하지만 `@MappedSuperclass`를 사용하게 되면 아래와 같이 데이터가 들어가게 된다.



- A테이블

| id   | name   |
| ---- | ------ |
| 1    | 홍정현 |
| 2    | 정창용 |

- B테이블

| id   | character |
| ---- | --------- |
| 3    | 망함      |
| 4    | 이게뭐람  |

보다싶이, PK가 모두 공유해서 올라가게 된다.

### 기타 지식

`@Embedded`를 참고하면 좋다.