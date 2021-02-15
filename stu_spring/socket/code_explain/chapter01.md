# 채팅 서버 만들기(1)

### build.gradle

``` java
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-websocket'
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
```

> 기본적인 lombok, starter-web과 socket 사용을 위한 것만 추가하면 된다.

### WebSocketHandler

``` java
@Slf4j
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession socketSession, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload{}",payload);
        TextMessage textMessage = new TextMessage("Welcome to the server");
        socketSession.sendMessage(textMessage);
    }
}
```

- 코드 해설

  ```java
  @Slf4j
  ```

  > lombok에서 지원하는 로그를 찍기 위한 어노테이션.

  ``` java
  @Component
  ```

  > 해당 클래스를 빈에 등록하기 위한 어노테이션.

  ``` java
  WebSocketSession socketSession
  ```

  > Spring에서 WebSocket Connection이 연결된 세션을 가리킨다.
  >
  > 해당 세션을 통해 sendMessgae를 할 수 있다.

  ``` java
  String payload = message.getPayload();
  log.info("payload{}",payload);
  ```

  > Client로부터 받은 메세지를 console에 찍는다.

  ``` java
  TextMessage textMessage = new TextMessage("Welcome to the server");
  socketSession.sendMessage(textMessage);
  ```

  > Welcome to the server 라는 메세지를 만들어서 socketSession을 이용해 보낸다.

### WebSocketConfig

``` java
@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
```

- 코드 해설

  ```java
  @Configuration
  @EnableWebSocket
  ```

  > WebSocket을 활성화 해 주고, 빈에 등록한다.
  >
  > @Configuration이 아닌 @Bean도 등록할 수 있지만, 싱글톤을 보장할 수 없기 때문에 @Configuration을 사용해야 한다.

  > 싱글톤이란 하나의 프로그램 내에서 하나의 인스턴스만을 사용할 때 사용되는 디자인 패턴이다.
  >
  > 단 하나의 객체만을 만들도록 하며, static으로 선언된 하나의 인스턴스를 계속 참조함으로써 new 키워드로 인한 메모리 낭비를 줄일 수 있다.