# Messege.java

### 설명

> 메세지를 관리해주는 entity 클래스 이다.

```java
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String room;

    private String sender;

    private String message;

    private boolean isDeleted;

    private boolean isShow;

    public String getMessage() {
        if(isDeleted == true) {
            return "";
        }
        return message;
    }
    
}
```

``` java
extends BaseTimeEntity
```

> 이전에 BaseTimeEntity라는 CreatedAt과 UpdatedAt을 관리해 주기 위해 만들어둔 클래스로, extends만 해주면 createdAt과 updatedAt이 생긴다.

``` java
public String getMessage() {
    if(isDeleted == true) {
        return "";
    }
    return message;
}
```

> 만약 메세지가 삭제되지 않았다면 반환하고, 삭제된 메세지라면 공백을 반환한다.