# SocketController.java

### 설명

> 특이하게 생겼지만, 특정한 이름의 socket 요청이 들어오면 처리해 준다.

### 코드

```java
@Component
@RequiredArgsConstructor
public class SocketController {

    private final SocketIOServer socketIOServer;

    private final SocketService socketService;


    @PostConstruct
    public void setSocketIOMapping() {

        socketIOServer.addConnectListener(socketService::connect);
        socketIOServer.addDisconnectListener(socketService::disconnect);

        socketIOServer.addEventListener("joinRoom", JoinRoomRequest.class,
                ((client, room, ackSender) -> socketService.joinRoom(client, room)));

        socketIOServer.addEventListener("send", MessageRequest.class,
                ((client, data, ackSender) -> socketService.sendMessage(client, data)));
    }
}
```

``` java
@PostConstruct
```

> 클래스가 생겼을 때 해당 메소드를 실행하라는 의미이다.

``` java
socketIOServer.addConnectListener(socketService::connect);
```

> 소켓이 연결 되었을 때에 socketService의 connect 메소드를 실행한다.

``` java
socketIOServer.addDisconnectListener(socketService::disconnect);
```

> 소켓 연결이 끊어졌을 때에 실행한다.

``` java
socketIOServer.addEventListener("joinRoom", JoinRoomRequest.class,
                ((client, room, ackSender) -> socketService.joinRoom(client, room)));
```

> joinRoom 이라는 이름의 요청이 JoinRoomRequest와 함께 온다면 socketService의 joinRoom 메소드를 실행한다.

``` java
socketIOServer.addEventListener("send", MessageRequest.class,
                ((client, data, ackSender) -> socketService.sendMessage(client, data)));
```

> send 라는 이름의 요청이 MessageRequest와 함께 온다면 socketService의 sendMessage 메소드를 실행한다.