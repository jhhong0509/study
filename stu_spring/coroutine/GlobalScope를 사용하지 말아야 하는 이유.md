## 서론

우리는 Kotlin Coroutine의 Structured Concurrency를 이용하기 때문에, **모든 Coroutine은 `CoroutineScope` 또는 명시된 Scope에서 실행된다.**

그런데 왜 `GlobalScope`를 사용하지 말아야 하는걸까?

## GlobalScope를 사용하지 말아야 하는 이유

다음 예제를 보자.

``` kotlin
fun work(i: Int) {
    Thread.sleep(1000)
    println("Work $i done")
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                launch {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms")
}
```

작업을 완료하는데 몇 초가 걸리게 될까?

1초짜리 작업을 동시에 2개를 실행할 수 있다면 1초면 충분할 것이다.

하지만 **동시에 실행되지 않기 때문에 2초가 걸리게 된다.**

이유는 `runBlocking`은 Single Thread로 현재는 Main Thread에서 동작하기 때문이다.

<br>

이를 해결하기 위해 다음과 같이 `launch(Dispatchers.Default)`를 이용할 수 있다.

``` kotlin
fun work(i: Int) {
    Thread.sleep(1000)
    println("Work $i done")
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                launch(Dispatchers.Default) {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms")
}
```

이렇게 하면 1초 안에 끝나게 된다.

<br>

그렇다면 `GlobalScope`를 이용하면 어떻게 될까?

기본적으로 Background에서 `Dispatchers.Default`로 실행되기 때문에 똑같을 것이다.

``` kotlin
fun work(i: Int) {
    Thread.sleep(1000)
    println("Work $i done")
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                GlobalScope.launch {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms")
}
```

하지만 위 코드는 **`Work x done`을 한 번도 출력하지 않고 종료된다.**

왜 이런 결과가 나왔을까?

`launch(Dispatchers.Default)`는 **`runBlocking`의 CoroutineScope를 물려받아 자식으로써 동작**하게 된다.

따라서 `runBlocking`은 자식이 모든 작업을 끝낼 때까지 기다려 주게 된다.

하지만, `GlobalScope`로 coroutine을 만들게 되면 **global coroutine을 만들게 된다.**

즉, 어느 coroutine의 자식도 아니게 되어 **lifecycle을 모두 개발자가 책임져야 한다.**

<br>

`GlobalScope`의 lifecycle을 정상적으로 관리하기 위해서는 `join()`을 사용할 수 있다.

``` kotlin
fun work(i: Int) {
    Thread.sleep(1000)
    println("Work $i done")
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            val jobs = mutableListOf<Job>()
            for (i in 1..2) {
                jobs += GlobalScope.launch {
                    work(i)
                }
            }
            jobs.forEach { it.join() }
        }
    }
    println("Done in $time ms")
}
```

`join()`은 **해당 작업이 종료될 때 까지 Coroutine을 suspend시켜준다.**

이제 위 코드는 아까 전의 `launch(Dispatchers.Default)`와 비슷하게 동작하는 것 처럼 보인다.

하지만 훨씬 많은 부수 효과들이 일어나며, 더 많은 코드를 작성해야 한다.

그렇기 때문에 웬만하면 `GlobalScope`를 사용할 때, 정말 사용해야 할지 한 번 더 고민해 보는게 좋다.

혹은 메모리 누수가 없도록 정말 코드를 잘 설계해서 짜는 수 밖에 없다.

하지만 큰 Application에서는 `launch(Dispatchers.Default)`도 사용하면 안된다.

<br>

우리가 work함수를 조금만 바꾸게 되면 훨씬 깔끔하게 코드를 만들 수 있다.

``` kotlin
suspend fun work(i: Int) = withContext(Dispatchers.Default) {
    Thread.sleep(1000)
    println("Work $i done")
}

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            for (i in 1..2) {
                launch {
                    work(i)
                }
            }
        }
    }
    println("Done in $time ms")
}
```

