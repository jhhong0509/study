# Business Exception을 피해야 하는 이유

> Repository Layer에서 발생하는 RuntimeException을 말하는게 아니다.
>
> 비지니스 로직 도중 `유저가 이미 존재함` 등의 이유로 예외처리를 해줄 때를 이야기 한다.
>
> Business Exception은 **여러 이유에 의해 발생하면 안된다.**

<br>

## Technical Exception

우선 Technical Exception으로 시작하자.

Technical Exception은 **무언가가 잘못되었고, 현명한 대처가 불가능**할 때 발생한다.

<br>

예를 들어 Java에서 부정확한 매개변수를 제공할 때 발생하는 `IllegalArgumentException`이 있다.

여기서 우리는 다른 방법은 없고, **코드를 고쳐야만 한다.**

<br>

이건 프로그래밍 오류로, 만약 유저가 이상한 값을 제공해서 이러한 예외는 발생하지 않아야 한다.

따라서 이러한 상황에는 **요청이 미리 검증되고 에러 메세지가 반환**되었어야 한다.

혹은 개발자의 실수라면 **코드를 수정**해야 한다.

<br>

Technical Exception은 주로 RuntimeException을 상속한다.

즉 **Method Signature로 선언될 필요가 없다.**

<br>

## Business Exception

Business Exception은 **Application의 비지니스 규칙을 어겼을 때 발생**되어야 한다.

예를 들면 다음과 같다.

```java
class Rocket {

  private int fuel;

  void takeOff() throws NotEnoughFuelException {
    if (this.fuel < 50) {
      throw new NotEnoughFuelException();
    }
    lockDoors();
    igniteThrusters();
  }
  
}
```

위 예제에선 연로가 50보다 작으면 `NotEnoughFuelException()`을 발생시켰다.

위 예외가 발생했을 때의 처리는 Client에게 달려있고, Client는 해당 예외를 처리해줘야 한다.

> Client는 해당 메소드를 호출하는 부분이다.

<br>

이제 Business Exception과 Technical Exception의 차이를 알았으니 왜 Business Exception을 피해야 하는지 알아보자.

<br>

### 1. 예외는 예상된 결과가 아니어야 한다.

먼저, Exception이란 단어의 뜻을 알아보자.

- 사람 또는 물건이 정상적인 상태에서 벗어나거나 규칙을 따르지 않음

즉 예외란 **무언가가 규칙을 따르지 않아 예기치 못한 상태가 된 것**을 의미한다.

<br>

하지만 위 예제에서 Business Exception은 **예외라고 하기 어렵다.**

예제에서 `NotEnoughFuelException()`은 *50 이상의 연료를 가지고 있어야 한다.* 라는 뜻을 가지고 있다.

따라서 우리는 *연료가 50 밑으로 떨어지면 안된다*  라는 규칙을 세운 것이다.

<br>

어쨋든 우리는 `takeOff()` 라는 메소드에 Method Signature를 추가했다.

만약 클라이언트가 알아야 할 결과를 정의하지 않으면 어떻게 될까?

<br>

요약하면, **예외는 예외여야 한다.** 따라서 **예외는 예상된 결과면 안된다.**

<br>

### 2. 예외는 비용이 비싸다

만약 해당 메소드를 호출하는 부분이 Exception을 처리하려면 어떻게 해야할까?

위 예제를 따르면 아마 연료를 다시 채운 후 시도해야 할 것이다.

```java
class FlightControl {

  void start(){
    Rocket rocket = new Rocket();
    try {
      rocket.takeOff();
    } catch (NotEnoughFuelException e) {
      rocket.fillTanks();
      rocket.takeOff();
    }
  }
  
}
```

위와 같이 다른 비지니스 코드를 실행함으로써 발생한 예외에 Client가 반응하게 되면 **우리는 예외를 Flow Control 이라는 목적으로 오용**한게 되어버린다.

흐름제어에 try/catch 구문을 사용하게 되면 다음과 같은 문제가 발생한다.

- 코드를 이해하기 어려워진다.

- JVM이 catch에 대한 stacktrace를 준비해야 하기 때문에 실행 비용이 비싸다

  > 참고로 Java Exception의 생성자에는 `writableStackTrace`라는게 존재하는데, false로 설정하면 stacktrace를 준비하지 않는다.

<br>

### 3. 예외는 재사용성을 줄인다.

`takeOff()` 메소드는 위에서 구현한 바와 같이 **항상 연료를 체크**한다.

위 예제를 들어서 설명해보자

우리가 연료를 충전할 수 없게 된다면 우리는 더 적은 연료로 우주선을 발사시켜야 한다.

<br>

이렇게 우리의 비지니스 규칙이 수정되었다.

이 상황에 대처해서 우리 코드는 어떻게 바뀌어야 할까?

```java
class Rocket {

  private int fuel;

  void takeOff(boolean checkFuel) throws NotEnoughFuelException {
    if (checkFuel && this.fuel < 50) {
      throw new NotEnoughFuelException();
    }
    
    lockDoors();
    igniteThrusters();
  }
  
}
```

위와 같이 연료 체크를 할지에 대한 파라미터를 받아서, 예외를 일으키지 않도록 했다.

하지만 이런 코드는 **보기 좋지 않으며**, 만약 checkFuel을 false로 줬다면 **여전히 예외처리를 해야한다.**

<br>

이렇듯 **비지니스 로직에 대한 예외처리는 예외처리가 없어야 하는 곳에 대한 재사용을 금지**시킨다.

또한 해결한다 하더라도 코드는 보기 좋지 않으며, 가독성은 떨어진다.

<br>

### 4. 예외는 Transaction에 간섭할수도 있다.

Spring에서 `@Transaction` 어노테이션을 사용해본적이 없다면, 어떻게 간섭하는지 모를것이다.

<br>

Spring에서 예외를 처리하는 방법을 요약하면 다음과 같다.

- `@Transaction` 어노테이션이 붙은 메소드에서 Runtime Exception이 발생하게 되면 해당 트랜잭션은 롤백의 대상이 된다.

- `@Transaction` 어노테이션이 붙은 메소드에서 Checked Exception이 발생하게 되어도 **해당 트랜잭션은 롤백 대상이 아니다.**

  즉 아무 일도 발생하지 않는다는 것이다.

  > Checked Exception이란 `NotEnoughFuelException()`과 같이 Client에서 처리해주어야 하는 Exception을 지칭한다.

<br>

예상되지 못한 Runtime Exception과 달리 Checked Exception의 트랜잭션이 롤백되지 않는 이유는 **메소드의 반환값이 유효한 값**이기 때문이다.

`NotEnoughFuelException()`은 try/catch문에 의해 **예상된대로 동작**할 것이고, 따라서 현재 트랜잭션을 롤백하지 않는 것이다.

<br>

만약 `NotEnoughFuelException()`이 Runtime Exception이었다면 처리할수는 있지만 `TransactionRolledBackException()`같은 예외를 처리해야 한다.

<br>

트랜잭션의 처리가 `@Transactional`이라는 쉬운 사용법 때문에 간단해 보이지만, **우리는 예외가 발생했을 때의 처리를 제대로 알지 못한다.**

누군가가 Checked Exception을 Runtime Exception으로 바꿔버리면 **예외가 발생할때마다 트랜잭션은 롤백**되게 된다.

이러한 처리는 위험하기 때문에 피해야 한다.

<br>

### 5. 예외는 공포감을 조성한다.

비지니스 로직의 실패를 예외로 표시하면, **기존의 코드를 이해하려는 개발자들이 무섭게 느낀다.**

아무튼 각각의 예외들은 문제들을 발생시킬 수 있다.

하지만 우리가 실무에서 처리해야할 예외들은 너무 많기 때문에 개발자들은 위축된다.

<br>

만약 뭔지 모르겠는 Exception을 try/catch 하고 있는 코드와 몇년동안 일할 생각을 하면 어떤 생각이 들 것 같은가?

<br>

## Business Exception 대신 무엇을 써야할까

그렇다면 Business Exception 대신 어떤 방법을 사용해야 할까?

Business Exception의 대안은 매우 간단하다. 단지 Exception 대신 plain code를 작성하면 된다.

<br>

> 선언부

```java
class Rocket {

  private int fuel;

  void takeOff() {
    lockDoors();
    igniteThrusters();
  }
  
  boolean hasEnoughFuelForTakeOff(){
    return this.fuel >= 50;
  }
  
}
```

> 호출부

```java
class FlightControl {

  void startWithFuelCheck(){
    Rocket rocket = new Rocket();
    
    if(!rocket.hasEnoughFuel()){
      rocket.fillTanks();
    }
    
    rocket.takeOff();
  }
  
  void startWithoutFuelCheck(){
    Rocket rocket = new Rocket();
    rocket.takeOff();
  }
  
}
```

위와 같이 **호출부에게 예외 처리를 강요하지 않고 client에서 요청을 validation 한다.**

<br>

우리가 얻는 장점은 다음과 같다.

- 예외를 미리 발견하기 때문에 기대한 Flow Control이 변경되지 않는다.
- 가독성이 좋다
- 다른곳에서 재사용이 더 간단해졌다. 단지 검증을 하지 않으면 된다.
- 예외가 없기 때문에 Transaction 걱정을 하지 않아도 된다.

<br>

이 방법은 Rocket 클래스에서 FlightControl 클래스로 **처리를 떠넘긴다.**

Rocket이 비지니스 규칙을 검증해야하기 때문에 Rocket이 비지니스 규칙에 대한 검증을 하지 않는 것 같을 수 있다.

하지만 **여전히 `hasEnoughFuel()` 메소드를 통해 직접 검증하고 있다.**

호출부는 단지 **비지니스 규칙을 검증하는 메소드를 호출**하기만 하면 되서 **전체를 알 필요는 없다.**

<br>

우리가 책임을 domain에서 다른곳으로 책임을 떠넘기긴 했지만, 이로 인해서 **유연함**과 **가독성**을 얻었다.