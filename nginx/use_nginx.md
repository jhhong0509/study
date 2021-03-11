# NGINX 적용

### 설치

아래 명령어를 통해 nginx를 설치할 수 있다.

- Ubuntu 기준

> sudo apt-get install nginx

또한, 방화벽으로 인해 작동하지 않을 가능성이 있기 때문에 아래 명령어를 통해 80포트의 방화벽을 내려준다.



### 확인

아래 명령어를 통해 설치를 확인한다.

> nginx -v

해당 명령어를 쳤을 때 아래와 같은 문장이 출력되면 성공한 것 이다.

<img src="C:\Users\user\AppData\Roaming\Typora\typora-user-images\image-20210310165019559.png" alt="image-20210310165019559" align="left" />

### 명령어

- 버전 확인

  > nginx -v

- 재시작

  > sudo service nginx restart

- 중지

  > sudo service nginx stop

  > nginx -s stop

- 설정 반영(reload)

  > sudo service nginx reload

  > nginx -s reload

- 설정 체크

  >  nginx -t

##### nginx 옵션

> nginx -v 처럼 nginx -옵션 의 종류이다.

| 명령어 | 설명                |
| ------ | ------------------- |
| -v     | 버전                |
| -V     | nginx를 만들었을 때 |

