# Docker run

### Docker run 이란?

컨테이너를 실행시키기 위한 커맨드로, 여러가지 옵션들을 가지고 있고, 여러 옵션들을 조합해서 사용할 수 있다.

### 기본 포맷

``` shell
docker run 옵션 이미지 명령어 인자
```

위와 같은 형태를 기본으로, 여러 옵션들과 명령어들을 추가하는 방식이다.

### -d 옵션

간단하게 백그라운드에서 컨테이너를 작동시키겠다는 의미이다.

-d 옵션을 통해 생성하지 않으면 컨테이너를 실행시키는 도중에 다른 명령어들을 입력할 수 없고, ctrl+c를 누르면 컨테이너가 종료된다.

- 예제

  ``` shell
  docker run -d python:38-alpine python -m http.server
  ```

  위 예제는 python 이미지로 python -m http.server 명령어를 백그라운드로 실행시킨다.

  이렇게 -d 옵션으로 생성된 컨테이너는 docker ps 를 통해 확인할 수 있다.

이렇게 생겨난 컨테이너는 아래 명령어를 치면 지울 수 있다.

``` shell
docker stop 이름
docker rm 이름
```

### -it 옵션

위 옵션은 -i 옵션과 -t 옵션을 합친 것이다.

-i 옵션과 -t 옵션은 대부분 같이 쓰인다.

- -i 옵션

  -i 옵션은 interactive, 즉 컨테이너와 상호작용 하겠다는 의미이다.

- -t 옵션

  -t 옵션은 tty, 즉 터미널과 비슷한 환경을 조성해 주겠다는 의미이다.

  하지만, -i 옵션이 없기 때문에 컨테이너가 출력한 결과를 사용자가 볼 수 없다.

-it 옵션을 사용하면, 터미널 환경으로 컨테이너와 상호작용 하겠다는 의미다.

- 예제

  ``` shell
  docker run -it python:3.8-alpine
  ```

  위 예제는 실제 python 처럼 대화형 방식으로 코드를 작성할 수 있다.

### --name 옵션

docker를 제어할 때, 기본적으로 컨테이너 id가 주어진다.

![docker_container_id](..\images\docker_container_id.png)

이러한 컨테이너를 멈추기 위해서는 다음과 같은 명령어를 사용하면 된다.

``` shell
docker stop 7899108d467c
```

하지만 매우 복잡한 문자열의 조합이기 때문에 컨테이너를 멈추거나 지울때 외워서 사용하기 매우 어렵고 실수하기 쉽기 때문에 이름을 지어줄 수 있다.

- 예제

  ```shell
  docker run -d --name test-server python:3.8-alpine python -m http.server
  ```

  위 명령어는 python -m http.server 라는 명령을 백그라운드로 돌리되, 이름은 test-server 로 지은 것이다.

  이제 해당 컨테이너를 멈추기 위해서는 다음과 같은 명령어를 사용할 수 있다.

  ``` shell
  docker stop test-server
  ```

  기존의 container id를 이용한 방식보다 훨씬 더 간편해 졌다.

### -e 옵션

docker의 환경 변수를 설정해주는 옵션이다.

기존에 Dockerfile에서 설정한 환경변수가 있다면, -e 옵션이 우선시 되어서 Dockerfile의 환경변수를 덮어쓴다.

아래와 같은 형태로 사용할 수 있다.

``` shell
docker run -e 키=값
```

### -p 옵션

외부에 노출될 포트와 내부 포트 사이를 연결시켜 준다.

즉, 외부에서 해당 포트로 접근했을 때, 어떤 포트의 컨테이너와 연결시켜줄지 정의한다.

아래와 같은 형태이다.

``` shell
docker run -p 80:8080
```

위와 같은 명령어는 80포트로 웹서버에 접근했다면 8080포트로 보내주라는 의미이다.

### -c 옵션

이 옵션은 자주 쓰일진 모르겠지만, CPU의 할당을 관리하는 옵션이다.

기본 설정값은 1024이다.

``` shell
docker run -c 2048
```

### --cpuset 옵션

이 옵션은 컨테이너가 실행될 코어를 정해준다.

즉, 0,1을 하면 첫번째와 두번째 코어를 사용하고, 0-3 처럼 하면 첫번째 부터 네번째 코어를 사용한다.

``` shell
docker run --cpuset=0,1,2
```

### --dns 옵션

웹 서버에서 사용할 dns 서버를 명시한다.

``` 
docker run --dns="8.8.8.8"
```

### --rm 옵션

주로 1회성 컨테이너에서 사용하게 되는데, 컨테이너 종료 시 모든 데이터를 깔끔히 지워준다.

``` dockerfile
docker run --rm
```