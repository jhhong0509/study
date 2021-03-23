# 우아한 테크세미나 영상 정리

### application.properties

> application.properties는 환경변수 등 외부에서 설정을 받아올 수 있다.

- encoding

> application.properties는 기본적으로 utf-8로 인코딩 되지 않는다.

> 즉, @Value를 통해 값을 가져올때, 한글이 깨질 가능성이 있어 인코딩을 바꿔줘야 한다.

- 우선순위

> 기본적으로 application.properties는 다음과 같은 우선순위를 가진다.
>
> > jar 파일에서 가까운 config 디렉토리
> >
> > jar 파일에서 가까운 application.properties
> >
> > config 디렉토리 밑에 있는 application.properties
> >
> > 그냥 아무것도 없는 application.properties
>
> > 우선순위가 낮다고 해서 아예 덮어씌워지는 것이 아니다.
> >
> > 즉, 우선순위가 높은 파일에서 낮은 파일 순으로 해당 이름의 값을 찾는 것이다.

### 도커 이미지 빌드

> spring boot는 기본적으로 계층형으로 이미지를 만들어 준다.

- jvm 을 jar 파일이 덮는 형태

> 매번마다 library를 다운받기 때문에 비효율적이다.

- 계층형 형태

> library가 분리되어 있기 때문에 훨씬 가볍고 효율적이다.
>
> 기본적으로 바뀌는 부분은 코드가 전부인데, library를  모두 이미지로 만드는것보다 훨씬 효율적이다.

- 도커 이미지 빌드하기

``` shell
./mvnw spring-boot:build-image
```

> 위와 같은 명령어를 통해 이미지를 빌드할 수 있다.

> 처음 하는 것 이라면 오래 걸릴 수 있다.