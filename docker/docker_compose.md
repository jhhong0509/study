# Docker Compose

### Docker  Compose란

도커 파일을 열심히 작업을 했는데, docker run에서 옵션을 부여하기가 굉장히 귀찮다.

한번 사용한 run 명령어를 저장해 두거나, 처음부터 다시 짜야하는 일이 발생하게 된다.



그렇기 때문에 Docker Compose 가 등장하게 되었다.

간단하게 말해서 **Docker Compose란 도커의 실행 옵션을 미리 적어둔 문서** 라고 할 수 있다.

단순히 Docker Compose를 실행함으로써, 여러 태그들을 부여시킬 수 있다.

> Docker Compose는 yaml 확장자로 되어있다.
>
> 파일명은 `docker-compose.yml`이다.

### 예제

``` yaml
version: '3'

services:
  spring:
    image: gramo-build
    build: .
    container_name: gramo-backend
    ports:
    - "8001:8001"
    volumes:
    - /home/hong/projects/GRAMO-Backend-SpringBoot/log:/logs/*.log
```

### 설명

#### version

파일 규격 버전 정보를 적는데, version에 따라서 지원하는 옵션이 다르다.

> 지금은 3이 최신이다.

#### services

단순 도커에서 컨테이너와 같은 역할을 한다.

docker compose에서 실행하려 하는 컨테이너를 정의한다.

#### spring

단순히 해당 서비스의 이름이다.

#### image

해당 컨테이너에서 사용할 이미지다.

### build

docker build를 실행한다는 의미다.

도커 이미지를 만들게 된다.

#### environment

환경 변수에 등록할 값들을 설정한다.

해당 컨테이너 외부에선 환경 변수가 적용되지 않는다.

#### ports

`docker run`에서  사용하던 -p 옵션과 동일한 효과다.

#### volumes

해당 컨테이너가 실행중 나온 결과물을 저장할 파일을 선택한다.

### 주의

- 프로젝트의 루트에 위치해야 한다.