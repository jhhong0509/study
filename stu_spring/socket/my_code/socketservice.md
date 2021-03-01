# SocketService

### 설명

> SocketService에서 사용할 메소드 들을 정의한 클래스.
>
> connect를 예로 들면, 소켓이 연결된 후에 메소드가 실행된다.

### Connect 메소드

```java
@Override
public void connect(SocketIOClient client) {
    String token = client.getHandshakeData().getSingleUrlParam("token");
    if(!jwtTokenProvider.validateToken(token)) {
        clientDisconnect(client, "invalidToken");
        throw new InvalidTokenException();
    }

    User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
            .orElseThrow(UserNotFoundException::new);
    client.set("user", user);

}
```

``` java
client.getHandshakeData().getSingleUrlParam("token");
```

> handShake에서 token을 가져온다.

``` java
if(!jwtTokenProvider.validateToken(token)) {
    clientDisconnect(client, "invalidToken");
    throw new InvalidTokenException();
}
```

> 가져온 토큰이 정상적인지 검사한다.

``` java
User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
            .orElseThrow(UserNotFoundException::new);
```

> 토큰을 통해 유저를 찾고, 없으면 Exception을 발생시킨다.

``` java
client.set("user", user);
```

> 해당 클라이언트에 user란 이름으로 user 정보를 넣는다.

### Disconnect 메소드

```java
@Override
public void disconnect(SocketIOClient client) {
    System.out.printf("Socket Disconnected Session: %s%n",client.getSessionId());
}
```

> 소켓 연결이 끊어질 때, 끊어졌다는 로그를 찍는다.

### JoinRoom 메소드

```java
@Override
public void joinRoom(SocketIOClient client, JoinRoomRequest room) {
    User user = client.get("user");
    try {
        Arrays.stream(room.getRoom().split(":")).filter(member -> member.equals(user.getEmail()));
    } catch (Exception e){
        throw new UserNotMemberException();
    }
}
```

``` java
User user = client.get("user");
```

> user란 이름으로 User를 찾는다.

``` java
try {
    Arrays.stream(room.getRoom().split(":")).filter(member -> member.equals(user.getEmail()));
} catch (Exception e){
    throw new UserNotMemberException();
}
```

> room에서 방 Id를 :기준으로 자르고, 가져온 유저가 해당 방에 없다면 Exception을 발생시킨다.

### SendMessage 메소드

```java
@Override
public void sendMessage(SocketIOClient client, MessageRequest request) {
    User user = client.get("user");
    boolean isMine = false;
    for(String name : request.getRoom().split(":")) {
        if(name.equals(user.getEmail())) {
            isMine = true;
        }
    }
    if(isMine || !client.getAllRooms().contains(request.getRoom())) {
        throw new UserNotMemberException();
    }

    Message message = messageRepository.save(request.toEntity(client));

    socketIOServer.getRoomOperations(request.getRoom()).sendEvent("receive", MessageResponse.builder()
            .createdAt(message.getCreatedDate())
            .isDeleted(message.isDeleted())
            .isShow(message.isShow())
            .sender(message.getSender())
            .message(message.getMessage())
            .build());

}
```

```java
for(String name : request.getRoom().split(":")) {
    if(name.equals(user.getEmail())) {
        isMine = true;
    }
}
```

> 방에서 해당 유저가 있는지 검사한다.

``` java
if(isMine || !client.getAllRooms().contains(request.getRoom())) {
    throw new UserNotMemberException();
}
```

> 클라이언트에서 방들을 가져오고, 해당 방이 없거나 해당 유저가 방에 참가하지 않았다면 Exception을 발생시킨다.

``` java
socketIOServer.getRoomOperations(request.getRoom()).sendEvent("receive", MessageResponse.builder()
            .createdAt(message.getCreatedDate())
            .isDeleted(message.isDeleted())
            .isShow(message.isShow())
            .sender(message.getSender())
            .message(message.getMessage())
            .build());
```

> socketIOServer에서 room을 찾은 후에, 해당 방에 receive라는 이벤트를 보낸다.
>
> 해당 이벤트는 값으로 MessageResponse를 보낸다.