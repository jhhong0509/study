# 파이어 베이스

### 파이어 베이스란

파이어베이스란, 구글에서 데이터베이스, 메세지 전송 등을 지원하도록 만들어진 기능이다.

주로 소켓 연결 등에 사용되는데, 이러한 기능등을 훨씬 간단하게 사용할 수 있도록 도와준다.

### Firebase Cloud Messaging

Firebase Cloud Messaging은 firebase에서 지원하는 기능중 하나로, 푸쉬 알림을 보내주는 역할을 한다.

줄여서 FCM이라고 부른다.

FCM과 GCM이 자주 비교되는데, GCM은 Google Cloud Message의 약자로 **FCM의 이전 버전**이라고 생각하면 된다.

FCM은 GCM을 포함하고 있고, **GCM의 로직을 단순화**시켜준다.

### 방식

1. FCM으로 메세지를 전송한다.

   payload에는 notification, data 등이 포함된다.

2. FCM에서 