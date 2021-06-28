# 코틀린 기본 문법

## 특징

- `;`을 붙이지 않는다!
- 변수는 컴파일 되었을 때 wrapper 타입을 사용해야할 때와 원시 타입을 사용할 때를 알아서 판단한다.
- Java의 Object 처럼 Kotlin에는 모든 클래스의 부모 클래스인 Any라는 클래스가 있다.

## 변수

Java에서 존재하는 var 키워드 처럼 **타입 추론**을 한다.

하지만 타입 추론을 사용하지 않고 **정적으로 타입을 지정**할 수 있다.

``` kotlin
var temp: Int = 10
var temp = 15
var a: Int
```

Java의 타입 추론과 비슷하게 **선언 시 초기화를 하지 않으면 추론이 불가능하다**

따라서 **직접 타입을 지정**해줄 수 있다.

### var

일반적인 변수처럼 **변경 가능**

### val

Java에서 final과 같이 **변경 불가능**한 **로컬 변수**

## 배열

Kotlin은 특이한 배열을 지원한다.

```kotlin
val array = arrayOf("asdf", "dasd")
```

혹은 Null로 이루어진 배열은 다음과 같이 만들 수 있다.

``` kotlin
val array = arrayOfNulls<String>(10)
```

**여기서는 타입 파라미터를 지정해 줘야 한다.**

또한 Any 타입을 통해 python처럼 특이한 배열이 가능하다.

``` kotlin
val array = arrayOf("asdf", 1, "dasd", 3)
```

## 반복문

kotlin에서는 특이한 형태의 for문을 지원한다.

> while문은 Java와 같다.

``` kotlin
for (num in 0..9 step 2) {}
```

위 for문은 0부터 9까지 2만큼 증가하는 for문이다.

Java로 표현하면 다음과 같다.

``` java
for (int num = 0; num <= 9; num += 2)
```

<br>

``` kotlin
for (num in 0 until 9 step 2)
```

until 키워드는 조금 다른데, `num <= 9`가 아니라 `num < 9`이다.

<br>

``` kotlin
for (num in 9 downTo 0 step 2)
```

downTo 키워드는 말 그대로 **num을 점점 낮추며 반복**한다.

즉 다음과 같다.

``` java
for (int num = 0; num >= 0; num -= 2)
```

## 클래스

### Init

Init 키워드는 **매개변수와 생성자가 없는 특별한 함수**이다.

``` kotlin
class A {
    init {
        println("asdf")
    }
}
```

위 print문은 **생성자보다 먼저 실행**되며, 생성자의 역할이 제한적이기 때문에 필요하다.

### 생성자

코틀린은 **주 생성자와 보조 생성자**로 나뉜다.

#### 주 생성자

주 생성자는 **클래스 이름 뒤**에 `constructor` 키워드로 선언할 수  있다.

``` kotlin
class A private constructor(var a: Int)
```

a 라는 필드를 만들고, 생성자에서 a를 받아 초기화 한다는 의미이다.

> 참고로 생성자에 default값을 미리 입력해 둘 수 있다.
>
> 그러면 Java에서 처럼 **해당 필드들은 선택 사항**으로 **입력하지 않으면 default 값**이 들어가게 된다.

또, 만약 접근 제한자를 바꾸지 않는다면 다음과 같이 간소화 할 수 있다.

``` kotlin
class A(var a: Int)
```

만약 부모 클래스의 생성자를 호출하고 싶다면 다음과 같이 할 수 있다.

``` kotlin
open class MotherClass(var a: Int) {}

class A(b: Int) : MotherClass(b) {}
```

#### 보조 생성자

주 생성자 외에 **오버라이딩 등의 이유로 추가적인 생성자**를 사용하고 싶을 때 보조 생성자를 사용한다.

다음과 같이 클래스 안에 Java처럼 생성자를 선언해줄 수 있다.

``` kotlin
constructor(b: Int, c: Int) : this(b){
    this.c = c
}
```

여기서 this(b)는 다른 생성자를 호출하는 것이다.

<br>

참고로 **모든 보조 생성자는 주 생성자를 호출**한다.

또한 **`init` 키워드는 주 생성자에 포함**되기 때문에 **항상 호출**된다.

## 메소드

Java의 간단한 sum 이라는 메소드를 만들어 보자

``` java
public int sum(int a) {
    return a + 10;
}
```

이걸 코틀린으로 바꾸면 다음과 같다.

``` kotlin
fun sum(a: Int):Int {
    return a + 10
}
```

하지만 코틀린은 여기서 더 단순화 시킬 수 있다.

``` kotlin
fun sum(a:Int) : Int = a  + 10
// or
fun sum(a:Int) = a + 10
```

위와 같이 타입 추론을 이용해서 바로 리턴도 가능하고, 타입을 지정해 줄수도 있다.

정리하면 다음 형태라고 할 수 있다.

`fun 메소드이름(변수이름: 타입) : 반환타입`

조건식을 추가해도 다음과 같이 간단하게 가능하다.

``` kotlin
fun sum(a:Int) = if (a > 10) a else a + 10
```

## Nullable

기본적인 코틀린 변수는 **Null을 담을 수 없다.**

따라서 `?`를 붙여서 Nullable 임을 명시해야 한다.

간단한 메소드로 보자

``` kotlin
fun test(a: Int?): Int? {
    return a?.minus(2)
}
```

a를 ?를 붙여서 nullable로 선언해 주고, null이면 null이, 값이 있으면 2를 뺀 값이 반환되는 메소드 이다.

<br>

그리고 만약 null일 경우의 처리를 따로 해주고 싶다면 **`?:`**을 사용하면 된다.

``` kotlin
fun test(a: Int?): Int {
    return a?.minus(2) ?: 0
}
```

위와 같이 하면 null이 아니면 2를 뺀 값, null이면 0이 반환되는 메소드 이다.

> 참고로 반환 타입이었던 Int? 가 Int가 되었는데, Null을 반환하지 않게 되었기 때문이다.

만약 Exception을 발생시키고 싶다면 다음과 같이 하면 된다.

``` kotlin
fun test(a: Int?): Int {
    return a?.minus(2) ?: throw NullPointerException()
}
```

## 형 변환

코틀린에서 기본형에 대한 형 변환은 내장 함수로 가능하다.

``` kotlin
"1234".toInt()
```

`toDouble()`, `toInt()`, `toByte()`, `toLong()` 등 여러 메소드 들이 존재하고, `toIntOrNull()`과 같이 **형 변환 실패 시 Null을 반환**하는 메소드도 있다.

<br>

객체의 형 변환은 `as`를 통해 할 수 있다.

``` kotlin

class KotlinUtil : MotherClass() {}

open class MotherClass {}

val a = KotlinUtil()
val b = a as MotherClass
```

여기서 

as에 ?를 붙여서 **Null일수도 있다는걸 명시**해줄 수 있다.

``` kotlin
val b = a as? MotherClass
```

<br>

코틀린에서는 형 변환에 실패했을 때에 처리를 돕는 **안전한 형 변환**을 지원한다.

만약 형 변환이 실패한다면 ? 뒤가 반환된다.