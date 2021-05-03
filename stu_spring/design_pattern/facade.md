# Facade 패턴

### 개념

Facade(퍼사드)는 프랑스의 `Façade`에서 유래된 단어로, **건물 외관** 이란 뜻을 가지고 있다. 건물의 외관이라는건 반대로 말하면 건물의 내부를 볼 수 없다는 의미이다.

즉 Facade 패턴은 **외부에서 내부의 구조를 모르도록 하는 것이다.**

많은 내부 구조들을 거대한 클래스로 감싸서 편리한 인터페이스를 제공한다.

> 거창하게 말했지만 평소에 Service와 ServiceImpl로 나눈것 처럼 하면 된다.



### 예제

#### Interface

```java
public interface UserFacade {
    User createAuthUser();
    User createUser(String email);
}
```

구현체에서 구현할 메소드들을 인터페이스에서 추상 메소드로 정의한다.

#### 구현체

``` java
@RequiredArgsConstructor
@Component
public class UserFacadeImpl implements UserFacade {

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;

    public User createAuthUser() {
        if(!authenticationFacade.isLogin()) {
            throw new UserCannotAccessException();
        }
        return getUser(authenticationFacade.getEmail());
    }

    public User createUser(String email) {
        return getUser(email);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

}
```

실제 비지니스 로직에서 반복되던 작업들을 한 곳에서 작업해 준다.



즉, 이제 해당 Facade 인터페이스를 의존성 주입해오면 **유저를 찾는 작업을 `facade.createAuthUser()`과 같이 단순하게 할 수 있다.**

여기서 중요한건 **외부에서 내부의 작업을 알 수 없다는 것이다.**