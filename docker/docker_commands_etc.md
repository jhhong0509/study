# 도커의 자잘한 명령어들

### docker 이미지 검색

``` shell
docker search 이미지
```

이미지를 검색해볼 수 있다.

### 이미지 조회 및 삭제

``` shell
docker images
```

자신이 다운받은 이미지들을 보여준다.

### 이미지 받아오기

``` shell
docker pull 이미지:태그
```

도커 이미지를 도커허브에서 가져올 수 있다.

### 이미지 삭제

``` shell
docker images rm 이미지ID
```

해당 id에 속하는 이미지를 지워준다.

### 도커 프로세스 보기

``` shell
docker ps # or
docker container ls
```

도커에서 돌아가고 있는 프로세스 목록을 보여준다.

-a 옵션을 붙이면 모든 프로세스를 볼 수 있다.

### docker 프로세스 종료

```shell
docker stop 프로세스 이름
```

위 명령어로 도커 프로세스를 종료할 수 있다.

하지만, 도커는 기본적으로 stop 되어도 끝이 아니라, STOPPED 라는 상태로 계속해서 실행된다.

> docker ps -a 를 통해 확인할 수 있다.
>
> docker ps 에서는 더이상 보이지 않는다.

### 실행중인 프로세스에 접근

``` shell
docker exec -it [컨테이너] [명령어]
```

해당 컨테이너에 접속하도록 돕는 명령어 이다.

다른 옵션들을 넣을 수 있고, -it가 필수인건 아니다.