# 채팅서버 만들기(2) STOMP 사용하기

#### build.gradle 수정

``` java
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-freemarker'
    implementation 'org.springframework.boot:spring-boot-devtools'
    implementation 'org.webjars.bower:bootstrap:4.3.1'
    implementation 'org.webjars.bower:vue:2.5.16'
    implementation 'org.webjars.bower:axios:0.17.1'
    implementation 'org.webjars:sockjs-client:1.1.2'
    implementation 'org.webjars:stomp-websocket:2.3.3-1'
    implementation 'com.google.code.gson:gson:2.8.0'
    .
    .
    .
```

> 위의 라이브러리를 추가해 준다.

> freemaker, view.js는 프론트 개발을 위한 것이다.

> sockjs는 websocket을 지원하지 않는 브라우저에서 websocket처럼 사용하도록 해주는 라이브러리이다

#### application.yml

``` yaml
spring:
  devtools:
    livereload:
      enabled: true
    restart:
      enable: true
  freemaker:
    cache: false
```

> static 파일, 즉 프론트 파일들을 굳이 서버 재시작을 하지 않아도 수정이 반영되도록 한 것이다.

#### WebSocketConfig 수정

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*")
                .withSockJS();
    }
}
```

``` java
registry.enableSimpleBroker("/sub");
```

> 구독 즉, 채팅방 입장을 하는 엔드포인트의 prefix는 /sub로 정해준다.
>
> 엔드포인트가 /sub로 시작한다면 채팅방 입장이라는 의미이다.

> pub/sub 모델의 subscribe 부분이다.

``` java
registry.setApplicationDestinationPrefixes("/pub");
```

> 메세지 발행 즉, 메세지 보내기의 엔드포인트의 prefix는 /pub로 정해준다.
>
> 엔드포인트가 /pub로 시작한다면 메세지 보내기 라는 의미이다.

> pub/sub 모델의 publish 부분이다.

``` java
registry.addEndpoint("/ws-stomp").setAllowedOrigins("*")
                .withSockJS();
```

> stomp websocket의 연결 엔드포인트를 ws-stomp로 설정해 주고, CORS를 열어준다.

> /ws-stomp에 접속하면 stomp  websocket에 연결할 수 있다.

#### ChatRoom 수정

```java
@Getter
@Setter
public class ChatRoom {
    private String roomId;

    private String name;

    public static ChatRoom createRoom(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}
```

> 기존에 세션 관리는 pub/sub 방식을 이용하기 때문에 모두 자동으로 해준다.
>
> 그렇기 때문에 훨씬 간소화 되었다.

> 또한 발송 구현도 알아서 해결되기 때문에 더욱 간소화 된다.

#### ChatMessageType

``` java
public enum MessageType {
    JOIN,
    TALK
}
```

> 기존에는 ENTER, TALK 이었지만 ENTER은 이제 직접 URI로 관리되기 때문에 필요 없다.

#### ChatRoomRepository

```java
@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    public void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRoomMap.get(roomId);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.createRoom(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

}
```

> 기존의 ChatRoomService를 대신하기 때문에 없애도 좋다.

> 실습용이기 때문에 간단한 Map으로 관리되고 있지만 실제 서비스는 DB 등을 이용해야 한다.

#### ChatController

```java
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/message")
    public void meessage(ChatMessage chatMessage) {
        if(ChatMessage.MessageType.JOIN.equals(chatMessage.getMessageType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
        }
        simpMessageSendingOperations.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
    }
}
```

``` java
if(ChatMessage.MessageType.JOIN.equals(chatMessage.getMessageType())) {
    chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
}
```

> 메세지가 JOIN 타입이면 메세지를 ~님이 입장하셨습니다 로 바꿔준다.

``` java
simpMessageSendingOperations.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
```

> 메세지를 보내는 것인 것 같다.(확실하지 않음)

> 해당 채팅방으로 채팅 메세지를 보내준다.

#### ChatRoomController

```java
// @RestController 는 JSON 형태로 객체 데이터를 반환하는 역할을 하고, @Controller 는 주로 View 를 반환하기 위해 사용된다.
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomDetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
```

``` java
@GetMapping("/room")
public String rooms(Model model) {
    return "/chat/room";
}
```

> 채팅 리스트를 띄워주는 컨트롤러이다.

> Model은 뷰에 정보를 넘겨주는 역할을 한다.
>
> HashMap 형태이기 때문에 key-value 형태를 가지고 있다.

> static/chat/밑의 room 이라는 파일을 열도록 해준다.

```java
@GetMapping("/rooms")
@ResponseBody
public List<ChatRoom> room() {
    return chatRoomRepository.findAllRoom();
}
```
> 채팅방 목록을 반환하는 컨트롤러이다.

> @ResponseBody는 값을 json으로 반환하기 위해 사용된다.

```java
@PostMapping("/room")
@ResponseBody
public ChatRoom createRoom(@RequestParam String name) {
    return chatRoomRepository.createChatRoom(name);
}
```

> 채팅방을 생성해 주는 컨트롤러

```java
@GetMapping("/room/enter/{roomId}")
public String roomDetail(Model model, @PathVariable String roomId) {
    model.addAttribute("roomId", roomId);
    return "/chat/roomDetail";
}
```
> 채팅방에 입장하는 컨트롤러

```java
@GetMapping("/room/{roomId}")
@ResponseBody
public ChatRoom roomInfo(@PathVariable String roomId) {
    return chatRoomRepository.findRoomById(roomId);
}
```
> 특정 채팅방을 조회하는 컨트롤러
>
> 채팅방 구독을 하도록 해준다.

> sub/chat/room 과 같은 형태로 요청을 보내면 된다.

## 프론트

> 그냥 정말 블로그에서 복붙했다

#### /resources/template/chat/room.ftl

```javascript
<!doctype html>
<html lang="en">
  <head>
    <title>Websocket Chat</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
      [v-cloak] {
          display: none;
      }
    </style>
  </head>
  <body>
    <div class="container" id="app" v-cloak>
        <div class="row">
            <div class="col-md-12">
                <h3>채팅방 리스트</h3>
            </div>
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <label class="input-group-text">방제목</label>
            </div>
            <input type="text" class="form-control" v-model="room_name" v-on:keyup.enter="createRoom">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button" @click="createRoom">채팅방 개설</button>
            </div>
        </div>
        <ul class="list-group">
            <li class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.roomId" v-on:click="enterRoom(item.roomId)">
                {{item.name}}
            </li>
        </ul>
    </div>
    <!-- JavaScript -->
    <script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
    <script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
    <script>
        var vm = new Vue({
            el: '#app',
            data: {
                room_name : '',
                chatrooms: [
                ]
            },
            created() {
                this.findAllRoom();
            },
            methods: {
                findAllRoom: function() {
                    axios.get('/chat/rooms').then(response => { this.chatrooms = response.data; });
                },
                createRoom: function() {
                    if("" === this.room_name) {
                        alert("방 제목을 입력해 주십시요.");
                        return;
                    } else {
                        var params = new URLSearchParams();
                        params.append("name",this.room_name);
                        axios.post('/chat/room', params)
                        .then(
                            response => {
                                alert(response.data.name+"방 개설에 성공하였습니다.")
                                this.room_name = '';
                                this.findAllRoom();
                            }
                        )
                        .catch( response => { alert("채팅방 개설에 실패하였습니다."); } );
                    }
                },
                enterRoom: function(roomId) {
                    var sender = prompt('대화명을 입력해 주세요.');
                    if(sender != "") {
                        localStorage.setItem('wschat.sender',sender);
                        localStorage.setItem('wschat.roomId',roomId);
                        location.href="/chat/room/enter/"+roomId;
                    }
                }
            }
        });
    </script>
  </body>
</html>
```

#### /resources/template/chat/roomdetail.ftl

``` javascript
<!doctype html>
<html lang="en">
  <head>
    <title>Websocket ChatRoom</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
      [v-cloak] {
          display: none;
      }
    </style>
  </head>
  <body>
    <div class="container" id="app" v-cloak>
        <div>
            <h2>{{room.name}}</h2>
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <label class="input-group-text">내용</label>
            </div>
            <input type="text" class="form-control" v-model="message" v-on:keypress.enter="sendMessage">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button" @click="sendMessage">보내기</button>
            </div>
        </div>
        <ul class="list-group">
            <li class="list-group-item" v-for="message in messages">
                {{message.sender}} - {{message.message}}</a>
            </li>
        </ul>
        <div></div>
    </div>
    <!-- JavaScript -->
    <script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
    <script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
    <script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
    <script>
        //alert(document.title);
        // websocket & stomp initialize
        var sock = new SockJS("/ws-stomp");
        var ws = Stomp.over(sock);
        var reconnect = 0;
        // vue.js
        var vm = new Vue({
            el: '#app',
            data: {
                roomId: '',
                room: {},
                sender: '',
                message: '',
                messages: []
            },
            created() {
                this.roomId = localStorage.getItem('wschat.roomId');
                this.sender = localStorage.getItem('wschat.sender');
                this.findRoom();
            },
            methods: {
                findRoom: function() {
                    axios.get('/chat/room/'+this.roomId).then(response => { this.room = response.data; });
                },
                sendMessage: function() {
                    ws.send("/pub/chat/message", {}, JSON.stringify({type:'TALK', roomId:this.roomId, sender:this.sender, message:this.message}));
                    this.message = '';
                },
                recvMessage: function(recv) {
                    this.messages.unshift({"type":recv.type,"sender":recv.type=='ENTER'?'[알림]':recv.sender,"message":recv.message})
                }
            }
        });

        function connect() {
            // pub/sub event
            ws.connect({}, function(frame) {
                ws.subscribe("/sub/chat/room/"+vm.$data.roomId, function(message) {
                    var recv = JSON.parse(message.body);
                    vm.recvMessage(recv);
                });
                ws.send("/pub/chat/message", {}, JSON.stringify({type:'ENTER', roomId:vm.$data.roomId, sender:vm.$data.sender}));
            }, function(error) {
                if(reconnect++ <= 5) {
                    setTimeout(function() {
                        console.log("connection reconnect");
                        sock = new SockJS("/ws-stomp");
                        ws = Stomp.over(sock);
                        connect();
                    },10*1000);
                }
            });
        }
        connect();
    </script>
  </body>
</html>
```

> 뷰가작동하지 않아 포기했고, spring boot만 개발하기로 했다.
>
> 또한 view를 사용하지 않는 방법을 이용하려 한다.