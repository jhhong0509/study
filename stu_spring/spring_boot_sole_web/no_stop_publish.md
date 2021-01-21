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
    
            return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
        }
    
    }
    ```

  - 코드 설명

    - ``` java
      private final Environment evn
      ```

      - 외부 설정파일을 가져와서 프로퍼티를 추가하거나 추출하는 역할

    - ``` java
      Arrays.asList(env.getActiveProfiles())
      ```

      - 액티브인 프로필들을 가져온다.
      - real, oauth 등 활성화된 프로필을 가져온다.

    - ``` java
      List<String> realProfiles = Arrays.asList("real", "real1", "real2")
      ```

      - 모두 배포에 필요한 프로필들이다.
      - 이중에 하나라도 활성화된 프로필이라면 그걸 반환한다.

    - ``` java
      String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0)
      ```

      - 만약 활성화 중인 프로필이 없다면 default를 반환한다.
      - 만약 활성화된 프로필이 있다면 profiles 리스트의 첫번째 값을 넣어준다.

    - ``` java
      return profiles.stream()
                  .filter(realProfiles::contains)
                  .findAny()
                  .orElse(defaultProfile);
      ```

      - profiles리스트에서 realProfiles중에 같은게 있으면 그걸 반환한다.
      - 만약 하나도 겹치는게 없다면(real,real1,real2 모두 활성화가 안되어 있다면) defaultProfile을 반환한다.

#### ProfileControllerUnitTest

- 이 테스트는 스프링 환경이 필요하지 않다.

- 그래서 @SpringBootTest를 선언하지 않아도 된다.

- 코드

  - ``` java
    public class ProfileControllerUnitTest {
    
        @Test
        public void real_profile이_조회된다() {
            //given
            String expectedProfile = "real";
            MockEnvironment env = new MockEnvironment();
            env.addActiveProfile(expectedProfile);
            env.addActiveProfile("oauth");
            env.addActiveProfile("real-db");
    
            ProfileController controller = new ProfileController(env);
    
            //when
            String profile = controller.profile();
    
            //then
            assertThat(profile).isEqualTo(expectedProfile);
        }
    
        @Test
        public void real_profile이_없으면_첫번째가_조회된다() {
            //given
            String expectedProfile = "oauth";
            MockEnvironment env = new MockEnvironment();
    
            env.addActiveProfile(expectedProfile);
            env.addActiveProfile("real-db");
    
            ProfileController controller = new ProfileController(env);
    
            //when
            String profile = controller.profile();
    
            //then
            assertThat(profile).isEqualTo(expectedProfile);
        }
    
        @Test
        public void active_profile이_없으면_default가_조회된다() {
            //given
            String expectedProfile = "default";
            MockEnvironment env = new MockEnvironment();
            ProfileController controller = new ProfileController(env);
    
            //when
            String profile = controller.profile();
    
            //then
            assertThat(profile).isEqualTo(expectedProfile);
        }
    }
    ```

  - 코드 설명

    - ``` java
      String expectedProfile = "real";
      MockEnvironment env = new MockEnvironment();
      env.addActiveProfile(expectedProfile);
      env.addActiveProfile("oauth");
      env.addActiveProfile("real-db");
      ```

      - Environment는 인터페이스 이기 때문에 가짜 구현체인 MockEnvironment를 통해 테스트할 수 있다.
      - 원하는 profile과 기본적인 oauth, real-db 프로필을 활성화 시킨다.

    - ```java
      ProfileController controller = new ProfileController(env);
      
      String profile = controller.profile();
      
      assertThat(profile).isEqualTo(expectedProfile);
      ```

      - 컨트롤러에 현재 환경을 넘겨준다.
      - 그리고 컨트롤러가 반환한 프로필이, 내가 원하는 프로필인지 검증한다.

#### SecurityConfig

- 인증 없이도 해당 컨트롤러가 호출될 수 있도록 해야 한다.

- 코드

  - ``` java
    .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**", "/profile").permitAll()
    ```

    - 맨 뒤에 , "/profile"이 추가되었다.

### real1, real2 프로필 생성

- application-real1.properties

```properties
server.port=8081
spring.profiles.include=oauth,real-db
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.session.store-type=jdbc
```

- application-real2.properties

```properties
server.port=8082
spring.profiles.include=oauth,real-db
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.session.store-type=jdbc
```

- 두 코드는 포트를 제외하고 다른점이 없다.

### 엔진엑스 설정 수정

- service-url.inc 파일 생성 후 수정

  - sudo vim /etc/nginx/conf.d/service-url.inc
  - set $service_url http://127.0.0.1:8080
  - :wq를 통해 종료

- service-url.inc를 사용하도록 nginx.conf 수정

  - sudo vim /etc/nginx/nginx.conf

  - location/ 부분을 찾아 수정한다.

    - ``` conf
      - include /etc/nginx/conf.d/service-url.inc;
      
      location / {	
      	proxy_pass $service_url;
      	.
      	.
      	.
      }
      ```

    - include를 한것과, 기존의 proxy_pass 값이 localhost로 되어있던 것을 $service_url로 바꿔주었다.

- 엔진엑스 재시작

  - sudo service nginx restart

### 배포 스크립트 작성

- 무중단 배포를 위한 스크립트
  - stop.sh
    -  기존의 엔진엑스와 연결되지 않은 스프링 부트 종료
  - start.sh
    - 신규 버전 스프링 부트 프로젝트를, 종료한 profile로 실행
  - health.sh
    - 정상적으로 스프링 부트가 실행되었는지 체크
  - switch.sh
    - 엔진엑스가 바라보고 있는 스프링 부트를 다른 스프링 부트로 변경
  - profile.sh
    - profile과 포트를 체크해 주는 로직

- 디렉토리가 겹치지 않도록 수정

  - appspec.yml의 destination 수정

    - ```yaml
      - destination: /home/ec2-user/app/step3/zip
      ```

- appspec.yml에서 위 스크립트들을 사용하도록 수정

  - ``` yaml
    hooks:
      AferInstall:
        - location: stop.sh
          timeout: 60
          runas: ec2-user
      ApplicationStart:
        - location: start.sh
          timeout: 60
          runas: ec2-user
      ValidateService:
        - location: health.sh
          timeout: 60
          runas: ec2-user
    ```

- 해당 스크립트 들을 생성

  - scripts 디렉토리 밑에 모두 생성

- profile.sh

  - ``` sh
    #!/usr/bin/env bash
    
    function find_idle_profile()
    {
        RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)
    
        if [ ${RESPONSE_CODE} -ge 400 ]
        then
            CURRENT_PROFILE=real2
        else
            CURRENT_PROFILE=$(curl -s http://localhost/profile)
        fi
    
        if [ ${CURRENT_PROFILE} == real1 ]
        then
          IDLE_PROFILE=real2
        else
          IDLE_PROFILE=real1
        fi
    
        echo "${IDLE_PROFILE}"
    }
    
    function find_idle_port()
    {
        IDLE_PROFILE=$(find_idle_profile)
    
        if [ ${IDLE_PROFILE} == real1 ]
        then
          echo "8081"
        else
          echo "8082"
        fi
    }
    ```

    - ``` sh
      $(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)
      ```

      - profile에 요청을 보내서, http 상태코드를 반환 받는다.
      - 400 이상은 모두 예외로 판단하고, 현재 프로필을 real2로 설정한다.

    - ``` sh
      if [ ${CURRENT_PROFILE} == real1 ]
          then
            IDLE_PROFILE=real2
          else
            IDLE_PROFILE=real1
      ```

      - 현재 놀고있는 프로필을 저장한다.

- stop.sh

  - ``` sh
    #!/usr/bin/env bash
    
    ABSPATH=$(readlink -f $0)
    ABSDIR=$(dirname $ABSPATH)
    source ${ABSDIR}/profile.sh
    
    IDLE_PORT=$(find_idle_port)
    
    echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
    IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})
    
    if [ -z ${IDLE_PID} ]
    then
      echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
    else
      echo "> kill -15 $IDLE_PID"
      kill -15 ${IDLE_PID}
      sleep 5
    fi
    ```

    - ``` sh
      ABSPATH=$(readlink -f $0)
      ```

      - 현재 해당 스크립트 파일의 경로를 찾는다.
      - profile.sh 파일을 찾기 위해 사용된다.

    - ``` sh
      source ${ABSDIR}/profile.sh
      ```

      - 자바의 import 처럼, profile.sh의 함수를 사용할 수 있게 해준다.

    - ``` sh
      IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})
      ```

      - 현재 구동중인 애플리케이션을 가져온다.

    - ``` sh
      if [ -z ${IDLE_PID} ]
      then
        echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
      else
        echo "> kill -15 $IDLE_PID"
        kill -15 ${IDLE_PID}
        sleep 5
      ```

      - 만약 애플리케이션이 실행중이 아니라면 넘어간다.
      - 실행중이라면 끝낸다.

- start.sh

  - ``` sh
    #!/usr/bin/env bash
    
    ABSPATH=$(readlink -f $0)
    ABSDIR=$(dirname $ABSPATH)
    source ${ABSDIR}/profile.sh
    
    REPOSITORY=/home/ec2-user/app/step3
    PROJECT_NAME=freelec-springboot2-webservice
    
    echo "> Build 파일 복사"
    echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"
    
    cp $REPOSITORY/zip/*.jar $REPOSITORY/
    
    echo "> 새 어플리케이션 배포"
    JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
    
    echo "> JAR Name: $JAR_NAME"
    
    echo "> $JAR_NAME 에 실행권한 추가"
    
    chmod +x $JAR_NAME
    
    echo "> $JAR_NAME 실행"
    
    IDLE_PROFILE=$(find_idle_profile)
    
    echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."
    nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
        -Dspring.profiles.active=$IDLE_PROFILE \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
    ```

    - deploy.sh와 비슷하다.
    - IDLE_PROFILE을 통해 properties 파일을 가져온다.
    - IDLE_PROFILE을 통해 active profile을 설정한다.

- health.sh

  - ``` sh
    #!/usr/bin/env bash
    
    ABSPATH=$(readlink -f $0)
    ABSDIR=$(dirname $ABSPATH)
    source ${ABSDIR}/profile.sh
    source ${ABSDIR}/switch.sh
    
    IDLE_PORT=$(find_idle_port)
    
    echo "> Health Check Start!"
    echo "> IDLE_PORT: $IDLE_PORT"
    echo "> curl -s http://localhost:$IDLE_PORT/profile "
    sleep 10
    
    for RETRY_COUNT in {1..10}
    do
      RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
      UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)
    
      if [ ${UP_COUNT} -ge 1 ]
      then # $up_count >= 1 ("real" 문자열이 있는지 검증)
          echo "> Health check 성공"
          switch_proxy
          break
      else
          echo "> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
          echo "> Health check: ${RESPONSE}"
      fi
    
      if [ ${RETRY_COUNT} -eq 10 ]
      then
        echo "> Health check 실패. "
        echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
        exit 1
      fi
    
      echo "> Health check 연결 실패. 재시도..."
      sleep 10
    done
    ```

    - 엔진엑스와 연결되지 않은 스프링 부트가 정상인지 확인한다.
    - 잘 떴는지 확인 되면 프록시 설정을 변경해 준다.
      - switch.sh가 해준다.

- switch.sh

  - ``` sh
    #!/usr/bin/env bash
    
    ABSPATH=$(readlink -f $0)
    ABSDIR=$(dirname $ABSPATH)
    source ${ABSDIR}/profile.sh
    
    function switch_proxy() {
        IDLE_PORT=$(find_idle_port)
    
        echo "> 전환할 Port: $IDLE_PORT"
        echo "> Port 전환"
        echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
    
        echo "> 엔진엑스 Reload"
        sudo service nginx reload
    }
    ```

    - ``` sh
      echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
      ```

      - 엔진엑스가 변경할 프록시 주소 생성
      - ""를 사용해야 한다.

    - ``` sh
      sudo service nginx reload
      ```

      - restart와 다르게 끊기지 않는다.
      - restart와 다르게 중요 설정 파일들은 반영되지 않는다.
        - 변경되는건 service-url이다.
        - 즉, 외부 설정 파일이기 때문에 reload로 반영시킬 수 있다.

- build.gradle 수정

  - 매 배포마다 하나하나 버전을 올리는것은 귀찮다.

  - 그러므로 자동으로 시간으로 버전값이 변경되도록 한다.

  - ```
    version '1.0.1-SNAPSHOT-'new Date().format("yyyyMMddHHmmss")
    ```

