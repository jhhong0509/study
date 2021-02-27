# SocketConfig.java

### 설명

> 기본적으로 소켓에 대한 설정을 해주는 클래스 이다.

### 코드

```java
@Configuration
public class SocketConfig {

    @Value("${server.socket.port}")
    private Integer port;

    private SocketIOServer socketIOServer;

    @Bean
    public SocketIOServer webSocketServer() {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setPort(port);

        SocketIOServer server = new SocketIOServer(configuration);
        server.start();
        this.socketIOServer = server;

        return server;
    }

    @PreDestroy
    public void socketStop() {
        socketIOServer.stop();
    }
}
```

``` java
@Value("${server.socket.port}")
private Integer port;
```

> 소켓 통신을 할 포트를 application.yml에서 가져온다.

``` java
com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
configuration.setPort(port);
```

> Configuration을 만들고, 포트를 가져온 포트로 설정한다.

``` java
server.start();
this.socketIOServer = server;
```

> 소켓 서버를 실행하고, SocketIOServer를 방금 만든 server로 바꿔준다.

``` java
@PreDestroy
public void socketStop() {
    socketIOServer.stop();
}
```

> 끝나기 전에, socket을 멈춘다.