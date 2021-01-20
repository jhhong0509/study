# NGINX를 이용한 무중단 배포

### 무중단 배포 소개

- 이전의 배포
  - 하루 배포일을 정해서 새벽시간에 개발자들이 모여서 했다.
  - 치명적인 문제가 발생했다면 새벽에 해결하다가, 사용자 유입이 많은 시간이 되면 긴급점검 공지를 올려야 했다.
  - 서비스를 정지해야 하면 롤백도 어렵다.
- 무중단 배포 방법
  - AWS의 블루 그린 무중단 배포
  - 도커를 이용한 웹서비스 무중단 배포
  - 엔진엑스(Nginx)를 이용한 무중단 배포
    - 우리가 사용할 방법이다.

### Nginx란

- 웹 서버, 로드 밸런싱, 리버스 프록시, 미디어 스트리밍 등을 위한 오픈소스 소프트웨어
  - 리버스 프록시란 외부의 요청을 받아 백엔드 서버로 전달하는 행위이다.
  - 실질적인 처리는 웹 애플리케이션 서버가 한다.
  - 이것을 이용해 무중단 배포를 만든다.
    - 저렴하고 쉽기 때문에
- 기존의 아파치의 자리를 빼앗은 유멍한 웹서버
- 고성능 웹서버이기 때문에 대부분 엔진엑스를 이용중이다.

### 무중단 배포의 구조

- ![구조1](https://t1.daumcdn.net/cfile/tistory/997A14375A73F91D04)
- 위와 같은 구조를 이루게 된다.
- 운영 과정
  - 사용자는 주소로 접속한다.
    - 80 또는 443 포트를 통해서
  - 엔진엑스는 사용자의 요청을 받아 현재 연결된 스프링 부트로 요청을 보낸다.(8081)
  - 스프링 부트2는 연결되지 않아서 요청을 받지 못한다.
- 신규 배포가 필요할 때 운영
  - 스프링 부트2를 새로 배포한다.
    - 스프링 부트1이 작동중이기 때문에 서비스는 중단되지 않는다.
  - 배포 후 정상적으로 스프링 부트2가 작동중인지 확인한다.
  - 스프링 부트2가 정상적으로 구동되는 중이면, nginx reload를 통해 8082포트(스프링 부트2)를 바라보게 한다.
    - nginx reload는 0.1초 이내에 완료된다.

### 엔진엑스 설치와 세팅

- EC2에 접속한다
- 엔진엑스를 설치한다.
  - sudo yum install nginx
- 엔진엑스를 실행한다.
  - sudo service nginx start
- 엔진엑스의 포트를 보안 그룹에 추가해 준다.
  - 기본적으로 엔진엑스는 80이다.
  - EC2 - 보안그룹 - EC2 보안 그룹 선택 - 인바운드 편집으로 이동한다.
  - 80번 포트를 열어준다.
- 구글/네이버 리다이렉션 주소를 추가해 준다.
  - 80번 포트로 변경되기 때문
  - 8080 포트를 제거한 URL을 추가하면 된다.
    - 기본적으로 포트번호가 제거된 상태가 80번 포트이기 때문이다.

- 엔진엑스 설정 파일을 열어서 수정한다.

  - sudo vim /etc/nginx/nginx.conf 를 쳐서 수정시켜 준다.

  - location / 를 찾아서 괄호 맨 윗줄에 내용을 추가해 준다.

    - `````` 
      proxy_pass http://localhost:8080
      ``````

  - :wq를 통해 저장 후 종료한다.

- sudo service nginx restart를 통해 nginx를 재시작한다.

### 사용중인 프로필 검증 API

#### ProfileController

- 코드

  - ``` java
    @RequiredArgsConstructor
    @RestController
    public class ProfileController {
        private final Environment env;
    
        @GetMapping("/profile")
        public String profile(){
            List<String> profiles = Arrays.asList(env.getActiveProfiles());
            List<String> realProfiles = Arrays.asList("real", "real1", "real2");
            String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);
    
            return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
        }
    
    }
    ```

  - 코드 설명

    - 

