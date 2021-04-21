# 디폴트 메소드

### 등장 이유

디폴트 메소드는 JDK 1.8 이전에는 존재하지 않았다.

하지만 기존의 인터페이스를 확장하기가 불가능해서 디폴트 메소드가 등장했다.



#### 기존의 방식

기존에 인터페이스와 해당 인터페이스를 implements한 구현 클래스가 있다고 했을 때,

메소드를 하나 추가하려 하면 인터페이스에 추상 메소드를 추가하고, 구현 클래스에서 이를 재정의 해 주어야 한다.



#### 디폴트 메소드 사용

단순히 인터페이스 메소드에 디폴트 메소드를 추가하면 하나의 메소드를 추가할 수 있다.

이러한 디폴트 메소드는 추상 메소드로 재선언이 가능하고, 구현 클래스에서 재정의 할 필요가 없다.

## 3가지 기능

### 단순 상속

단순히 부모 인터페이스에서 생성한 디폴트 메소드를 가지고 자식 인터페이스로 내려온다.

``` java
public interface ParentInterface {
    public default void method() {
        System.out.println("aa");
    }
}

public interface ChildInterface extends ParentInterface {
}
```

이렇게 하면 자식 인터페이스 에서도 디폴트 메소드를 이용할 수 있다.

### 재정의

부모의 디폴트 메소드를 재정의 시켜줄 수 있다

``` java
public interface ParentInterface {
    public default void method() {
        System.out.println("aa");
    }
}

public interface ChildInterface extends ParentInterface {
    @Override
    public void method() {
        System.out.println("bb");
    }
}
```

### 디폴트 -> 추상 메소드로 변환

디폴트 메소드는 상속을 시킨 자식 인터페이스에서 디폴트 메소드를 다시 추상 메소드로 바꿀 수 있다.

``` java
public interface ParentInterface {
    public default void method() {
        System.out.println("aa");
    }
}

public interface ChildInterface extends ParentInterface {
    @Override
    public void method();
}
```

