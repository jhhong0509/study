## StandardTestDispatcher

현재(Kotlin 1.6)에서 추천되는 `runTest` scope에서 기본적으로 제공되는 Dispatcher이다.

이 Dispatcher는 **task를 자동으로 실행하지 않는다.**

만약 우리가 이 dispatcher에서 coroutine을 시작하면 **즉시 실행되는 대신 pending(대기) 상태가 된다.**

이를 통해 언제, 무엇이 실행되어야 하는지에 대해 온전한 제어권을 가질 수 있기 때문에 우리 코드를 더 세부적으로 검증시킬 수 있다.

<br>

그렇다면 이 pending 상태의 coroutine을 어떻게 실행시켜야 할까?

`StandardTestDispatcher`에 연결된 `TestCoroutineScheduler`을 통해 pending 상태의 coroutine에 대한 여러 작업을 수행할 수 있다.

### runCurrent()

- 현재 가상시간에 실행되도록 예약된 pending coroutine을 실행한다.
- Coroutine의 실행 순서가 보장된다.

### advanceUntilIdle()

- pending중인 모든 coroutine을 실행한다.

### advanceTimeBy(ms)

- ms만큼 가상 시간을 진행시킨다.
- 그동안 실행되어야 하는 coroutine들을 실행시킨다.

## UnconfinedTestDispatcher

`UnconfinedTestDispatcher`는 **coroutine이 실행되는 순서를 보장하지 않는 Dispatcher**이다.

우리가 coroutine을 통제할 능력을 잃은 대가로, `runCurrent()`이나 `advanceUntilIdle()`을 호출하지 않아도 된다.

이 Dispatcher에서 생성된 coroutine은 **즉시 실행**된다.

순서 상관 없는 테스트에서 이걸 사용하면 `StandardTestDispatcher`보다 훨씬 코드가 간결해 진다.

[출처](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/MIGRATION.md)