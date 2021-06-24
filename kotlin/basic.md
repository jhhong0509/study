# 코틀린 기본 문법

## 특징

- `;`을 붙이지 않는다!

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

위와 같이 a를 ?를 붙여서 nullable로 선언해 주고, null이면 null이, 값이 있으면 2를 뺀 값이 반환되는 메소드 이다.	