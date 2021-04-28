# logback

### Logback이란

자바의 **오픈소스 로깅 프레임워크**로, SLF4J의 구현체 이다.

> SLF4J란 자바의 여러 로깅 프레임워크들을 사용할 때, 동일한 방식으로 사용하도록 방법을 제공하는 interface의 모음이다.
>
> 다른 라이브러리들의 추상 레이어를 제공한다.

다른 라이브러리들에 비해 좋은 성능을 가지고 있고, Spring boot의 기본으로 설정되어 있기 때문에 별다른 라이브러리를 추가하지 않아도 된다.

>  \- spring-boot-starter-web 안에 spring-boot-starter-logging에 구현체가 있다.

### 설정 참조

스프링 부트에서 설정 파일 (xml, yml) 을 읽는 순서는 다음과 같다.

1. resources에 logback-spring.xml 이 있으면 읽는다.
2. 만약 없다면 .yml  파일을 읽는다.
3. 둘 다 있다면, yml 파일을 읽은 후에 xml을 읽어서 적용한다.

### 로그 레벨

로그들의 단계는 총 5개로 나눌 수 있다.

> 아래로 갈수록 위험도가 낮다.

1. ERROR
   요청을 처리하는 도중 오류가 발생한 경우 표시한다.
2. WARN
   처리 가능한 문제로, 향후에 문제가 발생할 수도 있는 경고를 나타낸다.
3. INFO
   상태 변경과 같은 단순 정보를 나타낸다.
4. DEBUG
   프로그램을 디버그 하기 위한 정보를 표시한다.
5. TRACE
   DEBUG보다 훨씬 자세한 내용을 보여준다.

여기서 **상위 레벨로 설정할수록, 하위 레벨은 무시한다.**

즉 INFO 레벨이라면 ERROR과 WARN, INFO는 보여주지만, **DEBUG와 TRACE는 무시한다.**



Spring Boot에선 해당 레벨을 단순히 application.yml을 설정해주면 사용할 수 있다.

``` yaml
logging:
  level: warn
```

또한 패키지별로 따로따로 설정해 줄 수 있다.

