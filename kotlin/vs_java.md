# Kotlin vs Java

## 소개

코틀린은 **자바 플랫폼에서 돌아가는** 프로그래밍 언어이다.

코틀린의 목적은 **자바가 사용되고 있는 모든 용도에 적합**하며, **간결**하고 **생산적**인 대체 언어이다.

<br>

### 장점

- 간결하고 실용적이다.
- 자바 코드로 변경될 수 있다.
- 안드로이드, 서버 등 자바가 가능한 대부분의 프로젝트를 대체할 수 있다.
- 자바와 비슷한 성능
- Null 체크나 타입 검사, 타입 변환 등 안정성이 높다.

<br>

### 특징

#### 정적 타입 지정 언어

Java처럼 **컴파일 시 모든 프로그래밍 구성 요소를 알 수 있다.**

즉, 객체나 필드, 메소드를 사용할 때 **컴파일러가 타입을 검증**해 준다.

<br>

**장점**

- **메소드 호출의 속도가 빠르다**
- **프로그램이 오류로 중단될 가능성을 줄여준다.**
- **객체의 타입을 알기 쉽기 때문에 코드를 다루기 쉽다.**
- **IDE의 지원이 더 잘 이루어질 수 있다.**

<br>

#### Null-Safety

Kotlin은 Nullable 타입을 지원하는데, 이 타입을 통해 **Null 안정성을 확보**할 수 있다.

그래서 java와 달리 Optional이 존재하지 않는다.

<br>

#### 코루틴

기존의 자바에서 Future나 CompletableFuture를 사용해서 복잡하게 비동기 처리를 했다면,

코틀린에서는 **코루틴**을 지원해서 **간결한 비동기 처리**를 지원한다.

<br>

#### Delegation

Delegation이란 interface의 구현이나 Property의 Accessor의 구현을 **다른 객체에 위임**하도록 해주는 패턴이다.

Delegator -> Delegate 형태로 책임이나 처리를 넘긴다.

Composition + Forwarding 이라고도 할 수 있는데, Composition이란 **상속 대신 객체를 내부 private 변수**로 두어 **구성요소**로써 동작하게 하는 것을 의미한다.

또한 Forwarding은 **부모 메소드의 전달**이라고 할 수 있다.

<br>

상속과 비슷하게 **한 객체의 기능 일부를 다른 객체에 넘겨서 대신 수행**하도록 한다.

하지만 위임을 활용하면 **결합도를 낮출 수 있다.**

<br>

코틀린에서는 클래스가 기본적으로 **final**을 붙이기 때문에, 상속을 허용하려면 open을 붙여줘야 한다.

하지만 **open으로 확장을 허용하는건 위험하다.** 갑자기 sum 이라는 메소드가 덧셈에서 곱셈이 되는 등 **이상 현상이 발생할 가능성을 열게된다.**

하지만 반대로 Delegation을 한다고 해서 **부모 클래스에 영향을 주지 않는다.**

또한 상위 클래스의 API에 결함이 있을 때 **Delegation은 이를 숨길 수 있다.** Override를 통해 동작을 변경하는건 가능하지만, **접근 제한자를 더 private하게 바꿀 수  없다.**

**코틀린은 상속보다 Delegation을 추천**한다.

<br>

by 키워드로 위임시킬 수 있고, 다음과 같이 사용할 수 있다.

``` kotlin
interface BaseInterface {
    fun printMessage()
    fun printTest()
}

class BaseInterfaceImpl(private val x : Int) : BaseInterface {
    override fun printMessage() { print(x) }
    override fun printTest() { println(x) }
}

class KotlinDeligation(b : BaseInterface) : BaseInterface by b {
    override fun printMessage() { print("asdf") }
}

fun main() {
    val a = BaseInterfaceImpl(10)
    KotlinDeligation(a).printTest()			// asdf
    KotlinDeligation(a).printMessage()		// 10

    a.printTest()						  // 10
    a.printMessage()					  // 10 즉 오버라이딩이 부모 클래스에게 영향 X
}
```

여기서 by는 BaseInterface를 KotlinDeligation 안에 private 객체로 저장될 것이고, b로 보내는 모든 메소드를 생성할 것

<br>

**장점**

1. 객체 크기에 따라 증가시키는 비용이 없다.
2. interface를 정의해야 한다.
3. 여러 interface를 delegate할 수 있다.

**단점**

1. protected인 메소드나 properties에는 사용할 수 없다.
2. 관련 지식 없이는 이해가 어려워진다.

