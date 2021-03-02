# 리플렉션

### 리플렉션 이란

- 간단한 정의

  > 구체적인 클래스의 타입을 몰라도, 그 클래스의 필드에 접근할 수 있도록 하는 것.

- 예시

```java
public class Car {
    public void drive() {
        System.out.println("drive");
    }
}

public class Main {
    public static void main(String[] args) {
        Object car = new Car();
        car.driver();
    }
}
```

> 위 코드는 컴파일 에러가 발생한다.
>
> Car 클래스에 접근하는 것이 아니라, Object에 접근하기 때문에, Object의 필드에만 접근할 수 있다.

> 위와 같은 상황에서 사용할 수 있는 것이 Reflection이라는 API이다.
>
> 자바의 클래스는 바이트코드로 컴파일되어 static 영역에 위치하게 되는데, 클래스의 이름만 안다면 해당 클래스를 가져올 수 있다.
>
> 해당 기능을 지원하는 API가 Reflection인 것이다.