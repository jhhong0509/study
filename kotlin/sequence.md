# Sequence란

기존의 Kotlin Collection API에서는 **메소드 체이닝마다 새로운 Collection 객체가 필요**하다.

``` kotlin
val list = listOf(1, 2, -3)   // [1, 2, -3] 생성
val maxOddSquare = list
    .map { it * it }          // [1, 4, 9] 생성
    .filter { it % 2 == 1 }   // [1, 9] 생성
    .max()
```

이러한 작업은 데이터가 많아질수록 유의미한 성능 차이를 낼 것이다.

이것을 최적화 하기 위해 등장한 것이 Sequence로, **Java의 StreamAPI와 대응**된다.

StreamAPI에 비해 다음과 같은 차이가 있다.

- 더 많은 함수 지원
- 병렬 실행 미지원



<br>

Collection은 **Eager Evaluation으로 처리**되지만 Sequence는 **Lazy Evaluation으로 처리**된다.

``` kotlin
val list = listOf(1, 2, -3)   // [1, 2, -3] 생성
val maxOddSquare = list
    .asSequence()
    .map { it * it }
    .filter { it % 2 == 1 }
    .max()
```

위와 같이 `asSequence()`만 붙여주면 된다.

이렇게 하면 `map{ }`이나 `filter { }`과 같은 **중간 과정에서 새로운 객체가 반환되지 않는다.**

그 대신 처음에 있던 Collection에서 어떤 작업이 일어날지 저장해 두고, 그 작업들을 반환해 준다.

마지막으로 **결과가 필요한 시점에 연산을 수행**하게 되어 오버헤드를 줄일 수 있다.

<br>

여기서 특이한 점은 **저장했던 작업들을 한 번에 적용**시키게 된다.

```kotlin
fun map(i: Int): Int {
    print("mapped$i ")
    return i
}

fun filter(i: Int): Boolean {
    print("filtered$i ")
    return i % 2 == 0
}

val list = listOf(1, 2, 3, 4)

list.map(::map).filter(::filter)
// 1. (mapped1 mapped2 mapped3 mapped4 filtered1 filtered2 filtered3 filtered4)

list.asSequence().map(::map).filter(::filter).toList()
// 2. Sequence (mapped1 filtered1 mapped2 filtered2 mapped3 filtered3 mapped4 filtered4)

list.asSequence().map(::map).filter(::filter)
// 3. Sequence - 종료 연산이 없을 경우 출력되지 않음
```

 위와 같이 했을 때, Collection은 **map이 모두 일어난 후에 filter가 일어난다.**

하지만 sequence에서는 **각 요소에 대한 map이 일어나고, 그 후에 그 요소에 대한 filter가 이루어진다.**

또한 LAZY하게 동작하기 때문에 **끝 연산이 존재하지 않으면 아무 일도 일어나지 않는다.**

[참고한 글](https://kt.academy/article/ek-sequence)