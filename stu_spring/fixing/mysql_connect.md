# Mysql Connect 오류

### 발생 이유

docker로 spring boot 프로젝트를 배포하는 도중 mysql 연결 도중 오류가 발생했다.

패킷을 전송에는 성공했지만, Driver가 해당 패킷을 받지 못했다는 오류가 발생했다.

`The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.`

### 해결 과정

여러가지를 시도했지만 인터넷 검색 결과가 너무 광범위해서 고치지 못했다.

1. docker 관련 오류인걸 찾았고, docker에서 mysql에 localhost로 접근하지 못하는 것 같아서 localhost 대신 직접 URI를 대입해 줬다.

2. mysql URI에서 파라미터가 부족해서 추가해 줬다.

   mysql 8.0 이후부터는 allowPublicKeyRetrieval를 true로 설정해 줘야 한다.

### 후기

전부터 찾던 오류인데, 생각보다 이유가 단순해서 어이없었다.

### 주의

docker에선 localhost를 사용하면 안되고, Dockerfile에서 환경변수 등록을 해 주어야 한다.

### 기타 지식

X