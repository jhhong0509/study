# 토큰 필터에서 발생한 Exception이 무조건 500 발생

### 발생 이유

우리가 사용하는 Exception은 당연히 Spring의 영역`(Dispatcher Servlet)`에서 작동한다.

하지만 **Spring Security는 Spring 이전에 작동하게 된다.**

즉 우리가 평소에 하던대로 Exception 처리를 하게 되면 정상적으로 작동하지 않는다.

그래서 항상 Internal Server Error 라고만 뜬 것이다.

### 해결 과정

정말 간단하게 `GenericFilterBean`을 `OncePerRequestFilter`로 바꿔주면 된다.

### 후기

간단해 보이지만 굉장히 큰 차이가 있다는걸 알았다.

### 주의

### 기타 지식

- 필터

servlet은 ServletContext를 사용한다.

여기서 Filter 클래스는 `servlet-api` 또는 `tomcat-embed-core`를 사용하면 제공되는 interface 이다.

Spring은 ApplicationContext를 사용하기 때문에 ServletContext와 연동시켜 주어야 하는데, Spring Boot는 알아서 해준다.

> Spring Boot 없이 사용한다면 WebApplicationInitializer를 따로 구현해야 하지만 Spring Boot를 사용한다면 SpringBootServletInitializer에서 ServletContextApplicationContextInitializer를 사용해 ApplicationContext를 ServletContext에 등록하게 된다.



- 필터 인터페이스의 종류

  - Filter

    가장 기본적인 Filter로, 해야할 일의 전처리/후처리를 설정해줄 수 있다.

  - GenericFilterBean

    Filter를 상속받아 확장시킨 인터페이스로, 거의 비슷한데 `getFilterConfig()`나 `getEnvironment()` 정도를 추가로 지원한다.

  - OncePerRequestFilter

    GenericFilterBean을 상속받아 확장시킨 필터.

    의도치 않게 GenericFilterBean의 필터가 중첩요청 되었을 때 중첩 요청을 예방한다.

    `doFilterInternal`을 구현해야 한다.



Java doc에서 `OncePerRequestFilter`는 다음과 같이 설명된다.

> As of Servlet 3.0, a filter may be invoked as part of a `javax.servlet.DispatcherType REQUEST` or `javax.servlet.DispatcherType ASYNC` dispatches that occur in separate threads. A filter can be configured in `web.xml` whether it should be involved in async dispatches. However, in some cases servlet containers assume different default configuration. Therefore sub-classes can override the method `shouldNotFilterAsyncDispatch()` to declare statically if they should indeed be invoked, **once**, during both types of dispatches in order to provide thread initialization, logging, security, and so on. This mechanism complements and does not replace the need to configure a filter in `web.xml` with dispatcher types.

해석하면 다음과 같다.

> 현재 서블릿 3.0 버전에서, 필터는 javax.servlet.DispatcherType request 또는 async의 별도의 스레드에서 발생했을 때 호출된다.
>
> 필터는 async dispatches 와 연루되었는지에 따라 web.xml 에 의해 설정된다.
>
> 하지만 서블릿 컨테이너들은 상황에 따라 다른 기본 설정값을 가지게 된다.
>
> 따라서 서브 클래스들은 `shouldNotFilterAsyncDispatch()` 라는 메소드를 정적이게 오버라이드 함으로써 실제 호출되어야 할 때 어떤 dispatch 타입으로 초기화 될건지 한번 선언해줄 수 있다.
>
> 이 방법은 `web.xml`의 dispatcher type을 바꾸지 않는다.

