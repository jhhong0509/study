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

  ``` java
  registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
  ```

  > /ws/chat 이라는 엔드포인트에 대해 hander를 지정해 주고, 해당 엔드포인트에 대한 핸들러를 지정해 준다.
  >
  > 그리고 CORS 오류를 방지하기 위해 다른 출처를 허용한다.

> 지금까지 만든 코드를 통해 테스트하면, 잘 작동하는 것을 알 수 있다.
>
> 하지만 지금은 /ws/chat 이라는 채팅방 하나뿐인 서버이기 때문에, 바꿔줄 필요가 있다.

> WebSocketSession의 정보를 채팅방에 저장시켜 놓으면 개별의 채팅방을 구현할 수 있다.

### DTOs

#### ChatMessage

``` java
@Getter
@Setter
public class ChatMessage {
    public enum MessageType {
        ENTER,
        TALK
    }
    private MessageType messageType;

    private String roomId;

    private String sender;
    
    private String message;
}
```

> 해당 요청이 메세지 보내기인지, 방에 들어가는 것인지 구분하는 것과 메세지, 전송한 사람 등을 선언한다.

#### ChatRoom

```java
@Getter
public class ChatRoom {
    private String roomId;

    private String name;

    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if(chatMessage.getMessageType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
        }
        sendMessage(chatMessage, chatService);

    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}
```

``` java
public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
    if(chatMessage.getMessageType().equals(ChatMessage.MessageType.ENTER)) {
        sessions.add(session);
        chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
	}
	sendMessage(chatMessage, chatService);
}
```

> 만약 요청 종류가 채팅방 입장이라면 메세지를 ~님이 입장하셨습니다로 설정한다.
>
> 만약 종류가 TALK면 받아온 메세지를 바로 보낸다.

``` java
public <T> void sendMessage(T message, ChatService chatService) {
    sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
}
```

> 채팅룸의 모든 세션에 메세지를 보내준다.
>
> 즉, 채팅방에 참가한 모든 사람들에게 메세지를 보낸다는 의미이다.

#### ChatService

```java
@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllChatRoom() {
        return new ArrayList<>(chatRooms.values());
    }
    
    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name) {
        String roomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .roomId(roomId)
                .build();
        chatRooms.put(roomId, chatRoom);
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
```

``` java
private final ObjectMapper objectMapper;
private Map<String, ChatRoom> chatRooms;
```

> message를 변환시켜줄 ObjectMapper를 선언한다.

> 모든 채팅방을 Map 형태로 저장한다.

``` java
@PostConstruct
private void init() {
    chatRooms = new LinkedHashMap<>();
}
```

> 채팅방 목록 초기화

``` java
public List<ChatRoom> findAllChatRoom() {
    return new ArrayList<>(chatRooms.values());
}
```

> 현재 chatRooms의 모든 값들을 반환해 준다.
>
> 채팅방 목록을 위한 메소드

``` java
public ChatRoom findRoomById(String roomId) {
    return chatRooms.get(roomId);
}
```

> 채팅방의 Id를 받아 해당 id를 가진 채팅방을 반환해 준다.

``` java
public ChatRoom createRoom(String name) {
    String roomId = UUID.randomUUID().toString();
    ChatRoom chatRoom = ChatRoom.builder()
            .name(name)
            .roomId(roomId)
            .build();
    chatRooms.put(roomId, chatRoom);
    return chatRoom;
}
```

> 이름을 받아서 그 이름으로 채팅방을 만들어 준다.

> 방의 고유아이디는 UUID를 통해 String 타입의 고유 ID를 만들어 준다.

> ChatRoom을 만들고, chatRooms에 넣어준다. 그리고 만들어진 chatRoom을 반환한다.

``` java
public <T> void sendMessage(WebSocketSession session, T message) {
    try {
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    }catch (IOException e) {
        log.error(e.getMessage(), e);
    }
}
```

> 지정한 WebSocketSession에 메세지를 보낸다.

#### ChatController

```java
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllChatRoom();
    }
}
```

> 평범한 Rest 컨트롤러이다.

> 요청에 따라 채팅방 생성, 채팅방 목록 보여주기를 하도록 한다.

#### WebSocketChatHandler

> 채팅방이 생겼기 때문에, Handler를 수정해 주어야 한다.

``` java
@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession socketSession, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload{}",payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        chatRoom.handleActions(socketSession, chatMessage, chatService);
    }
}
```

``` java
ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
```

> TextMessage에서 payload를 가져온 후에, ChatMessage에 대입해 준다.

``` java
ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
```

> payload에서 가져온 roomId를 통해 채팅방을 찾는다.

``` java
chatRoom.handleActions(socketSession, chatMessage, chatService);
```

> chatRoom 클래스에 만들어둔 handleActions를 통해 메세지를 보내준다.

#### 테스트

> 테스트는 우선 postman으로 채팅방을 만든 후에, 해당 roomId를 복사해 둔다.

> 그리고 Chrome의 Simple websocket을 실행해서 type, roomId, sender, message를 json으로 값을 넣어준다.

``` json
{
    "messageType": "ENTER",
    "roomId": "5ebdfb7a-4312-463d-8c46-190ef8912ce3",
    "sender": "hi!",
    "message": ""
}
```

> Simple webSocket에 요청을 보내고, log를 본다.