# NGINX 적용

### 설치

아래 명령어를 통해 nginx를 설치할 수 있다.

- Ubuntu 기준

> sudo apt-get install nginx

또한, 방화벽으로 인해 작동하지 않을 가능성이 있기 때문에 아래 명령어를 통해 80포트의 방화벽을 내려준다.



### 확인

아래 명령어를 통해 설치를 확인한다.

> nginx -v

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

| 명령어      | 설명                                                         |
| ----------- | ------------------------------------------------------------ |
| -v          | 버전                                                         |
| -V          | nginx를 만들었을 때 옵션을 표신                              |
| -t          | 설정 파일이 정상인지 체크. nginx가 정지되었을 때 사용        |
| -q          | -t와 같지만 -q는 에러메세지만 출력                           |
| -c 설정파일 | 해당 설정파일로 nginx 실행                                   |
| -p 경로     | nginx의 prefix 경로를 설정.default값은 /usr/local/nginx/이다. |
| -s stop     | nginx 강제종료                                               |
| -s quit     | 현재 하고있던 request를 모두 처리한 후에 nginx 종료          |
| -s reload   | 설정파일을 재시작 없이 읽어들임. 매우 빠름.                  |
| -s reopen   | nginx 재기동중일때 로그파일을 다시 오픈함.                   |

### 설정

nginx 공식 문서에서 예제 nginx.conf는 아래와 같이 되어 있다.

```conf
user       www www; # 워커프로세스의 권한을 지정. 되도록 root는 사용하지 말자.
worker_processes  5;  # 요청을 처리할 때 몇개의 프로세스를 사용할지. 보통 auto
error_log  logs/error.log;	# 로그를 위치
pid        logs/nginx.pid;	# 프로세스 id 설정
worker_rlimit_nofile 8192;	# worker processes를 위해 열 수 있는 파일 개수 제한

events {					# connection 관련 처리
  worker_connections  4096;  # 하나의 프로세스에서 처리할 최대 connection 수
}

http {			# 웹 트래픽 처리 블록
  include    conf/mime.types;
  include    /etc/nginx/proxy.conf;
  include    /etc/nginx/fastcgi.conf;
  index    index.html index.htm index.php;	# 서버에 접속했을 때 index로 보여줄 이름 설정

  default_type application/octet-stream;	# response의 default mime.type 값 지정
  log_format   main '$remote_addr - $remote_user [$time_local]  $status '	# 로그형식 지정
    '"$request" $body_bytes_sent "$http_referer" '
    '"$http_user_agent" "$http_x_forwarded_for"';
  access_log   logs/access.log  main;		# 접속 로그 관리 파일 설정
  sendfile     on;			# sendfile() 설정 관리
  server_names_hash_bucket_size 128; # 최대 호스트 개수
  keepalive_timeout   65;	# 서버 연결 유지를 위한 지속시간 설정

  server {	# 가상 서버 설정. IP 기반 설정, 도메인 기반 설정 가능
    listen       80;	# 80포트 요청을 들음
    server_name  domain1.com www.domain1.com;	# 가상 서버 이름 섲렁
    access_log   logs/domain1.access.log  main;	# 요청에 대한 로그를 찍어줄 곳
    root         html;		# root 디렉토리

    location ~ \.php$ {	# 요청 URI에 따른 구성 설정
      fastcgi_pass   127.0.0.1:1025;
    }
  }

  server {
    listen       80;
    server_name  domain2.com www.domain2.com;
    access_log   logs/domain2.access.log  main;

    # 정적 파일
    location ~ ^/(images|javascript|js|css|flash|media|static)/  {
      root    /var/www/virtual/big.server.com/htdocs;
      expires 30d;
    }

    location / {
      proxy_pass      http://127.0.0.1:8080;
    }
  }

  upstream big_server_com {
    server 127.0.0.3:8000 weight=5;
    server 127.0.0.3:8001 weight=5;
    server 192.168.0.1:8000;
    server 192.168.0.1:8001;
  }

  server { # simple load balancing
    listen          80;
    server_name     big.server.com;
    access_log      logs/big.server.access.log main;

    location / {
      proxy_pass      http://big_server_com;
    }
  }
}
```

``` conf
upstream smoothbear {
        server localhost:8082 weight=5 max_fails=3 fail_timeout=10s;	# 서버의 8082 포트로 보내준다는 의미. 
        keepalive 100;		# keepalive가 꺼져있다면 매 요청마다 핸드쉐이크가 발생하기 때문에, 최대 몇개의 커넥션을 유지할건지 설정
}

server {
        listen 80;
        server_name api.smooth-bear.live;

        location ~ /\.ht {
                deny all;
        }

        location / {
                proxy_pass http://smoothbear;
                proxy_redirect off;
                proxy_set_header Host $host;
                proxy_set_header   X-Real-IP $remote_addr;
                proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        }
}
```

