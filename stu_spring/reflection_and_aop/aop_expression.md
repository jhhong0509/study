# 포인트컷 표현식

### 포인트컷 지정자

- 포인트컷 지정자란?

  > 뒤에 올 적용자의 패턴을 이야기 한다.

- execution

  > 가장 정교한 포인트컷을 만들 수 있는 지정자.

  - 표현

    > execution(수식어 리턴타입 클래스.이름(파라미터)

    > 접근 제한자(private, public 등)을 명시할 수 있음(생략 가능)

    > 리턴 타입을 명시해 줄 수 있음. *은 모든 반환 타입

    > 클래스는 패키지를 com.edu. 처럼 지정해 줄 수 있다.
    >
    > com.edu..*.get\*과 같은 형태는, edu 밑의 모든 클래스에서 get으로 시작하는 모든 메소드를 의미한다.
    >
    > com.edu.Start.homework(..)는 start 클래스 밑의 homework라는 이름의 모든 메소드를 의미한다.

    > 파라미터는 (..)을 통해 파라미터에 상관 없이 적용시킬 수 있다.

- within

  > 패키지 또는 클래스에 포함되는 모든 메소드를 적용시킬 때 사용됨.
  >
  > 클래스, 인터페이스 단위 까지만 지정이 가능하다.

  - 표현

    > within(클래스)

    > 클래스는 위 execution에서 패키지/클래스를 지정하는 것 처럼 사용할 수 있다.

- @within

  > 해당 어노테이션을 사용한 모든 메소드를 지정할 때 사용됨.

- args()

  > 해당 타입을 메소드 인자로 가지고 있는 모든 메소드를 지정함

  - 표현

    > args(java.io.Serializable)

    > Serializable을 인자로 갖는 모든 메소드를 지정

- @args()

  > 해당 어노테이션 타입을 인자로 갖는 모든 메소드를 지정함

- @annotation

  > 특정 어노테이션을 붙인 모든 메소드

### 포인트컷 표현식

- 수식어

  > private, public 등 접근 제어 지시자를 설정할 수 있다.

  > 원한다면 비워도 된다.

- 리턴 타입

  > 반환 타입을 지정해 줄 수 있다.

  > *은 모든 반환 타입을 말한다.

- 클래스

  > 패키지를 따라 찾아가며 클래스를 찾을 수 있다.

  > com.test.stu.Spring 처럼 사용한다면, stu 패키지 밑의 Spring 클래스를 지정하는 것이다.

  > com.test..* 처럼 사용한다면, test 밑의 **모든** 클래스를 지정해 준다.

  > com.test..Redis* 처럼 사용한다면, test 패키지 밑에 Redis로 시작하는 모든 클래스를 지정해 준다.

- 파라미터

  > 메소드의 인자값을 지정해 줄 수 있다.

  > ()처럼 빈칸으로 두면 인자가 없는 메소드만 적용된다.

  > (..)처럼 ..을 넣으면 인자에 상관 없이 모든 메소드에 적용된다.

- 관계연산자

  > 여러개의 조건을 넣고 싶다면 ||, &&, ! 과 같은 연산자를 이용할 수 있다.

### 포인트컷 변수처럼 사용하기

```java
@Aspect
@Component // @Bean과 동일하게 Spring Bean 등록 어노테이션
public class UserHistory {

    @Autowired
    private HistoryRepository historyRepository;

    @Pointcut("execution(* com.blogcode.user.UserService.update(*)) && args(user)")
    public void updateUser(User user){}

    @AfterReturning("updateUser(user)")
    public void saveHistory(User user){
        historyRepository.save(new History(user.getIdx()));
    }
}
```

``` java
@Pointcut("execution(* com.blogcode.user.UserService.update(*)) && args(user)")
public void updateUser(User user){}
```

> PointCut을 updateUser라는 이름으로 적용해 준다.

``` java
@AfterReturning("updateUser(user)")
```

> Return 후에 해당 어드바이스를 실행한다.