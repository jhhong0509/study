# DOCKER에서 ORACLE 설치

맥북의 경우 ORACLE을 지원하지 않기 때문에 docker를 이용해야 한다.

> window에서도 docker로 깔 수 있다.

### 이미지 받기

가장 먼저 oracle의 이미지를 내려와야 한다.

``` dockerfile
docker search oracle
```

위와 같이 명령어를 호출하면 oracle이 포함된 매우 많은 이미지들을 볼 수 있다.

이 중에서 원하는걸 깔면 되는데, 여기선 12c 버전을 설치한다.

``` dockerfile
docker pull truevoly/oracle-12c
```

한참 후에 image를 내려받게 된다.

### 시작

이제 받아온 이미지를 실제 컨테이너로 구동시켜주면 된다.

아래와 같이 하면 된다.

``` java
docker run -d -p 3307:3307 -p 1521:1521 truevoly/oracle-12c --name=local_oracle
```

여기서 `3307:3307`은 포트를 의미하는데, 보통 8080을 사용한다.

> 스프링 개발을 해야하는데 톰캣이 8080을 사용하고 있어서 3307로 했다.

그리고 `truevoly/oracle-12c`는 이미지를 의미하기 때문에 다른 이미지를 받았다면 다른 이름을 사용하면 된다.

`local_oracle`은 해당 컨테이너의 이름이다.

나중에 자주 사용할 것 이기 때문에 외우기 쉬운걸로 설정하면 된다.

> 설정하지 않으면 이상한 숫자&문자 조합으로 만들어진다.

참고로 oracle은 사용하면 안된다.

### 접근

도커 컨테이너에서 열었기 때문에 따로 접근해 주어야 한다.

``` dockerfile
 docker exec -it 컨테이너 sqlplus
```

`-it`는 해당 컨테이너에 터미널을 킨것처럼 접근한다는 의미다.

그렇기 때문에 CLI의 ORACLE 처럼 사용하면 된다.

### 재시작

docker에 설치했기 때문에 재시작 시에 Exited 상태가 된다.

아래 명령어로 확인할 수 있다.

``` dockerfile
docker ps -a
```

여기서 만약 내 oracle이 exited 상태라면 다시 켜주어야 한다.

> 오라클에서 오류가 난 것 일수도 있으니 아래 명령어로 확인해보는게 좋다.
>
> ``` dockerfile
> docker logs 컨테이너이름
> ```
>
> 로그를 확인하는 명령어 이기 때문에 외워두면 좋다.

가장 간단한 방법은 아래와 같이 EXITED를 다시 UP 상태로 바꿔주는 것이다.

``` dockerfile
docker start 컨테이너
```

혹은 아예 docker container를 지워주고 다시 시작하는 방법도 있다.