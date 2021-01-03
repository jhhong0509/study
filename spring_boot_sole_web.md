# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스 책
### 프로젝트 설정
- 프로젝트 생성 방법1
    - 프로젝트 생성은 Gradle 프로젝트에서 Java를 선택해야 한다.
    - ArtifactId는 프로젝트의 이름이 되기 때문에, 원하는 이름을 선택하면 된다.
    - 만약 Gradle JVM에서 JDK가 이상한게 잡혔다면, 설정해야 한다.
    - 다른건 다 기본설정으로 두면 된다.
    - 스프링 부트 프로젝트로 변경하기
        - 지금의  프로젝트는 스프링 부트가 아닌 그냥 일반적인 Gradle 프로젝트이기 때문에 spring boot 프로젝트로 바꿔야 한다.
        - 아래 코드를 build.gradle 에 넣어주면 된다.
        ```java 
        buildscript {
            ext {
                springBootVersion = '2.1.7.RELEASE' // build.gradle에서 springBootVersion을 전역변수로 설정하고, 그 값을 저장한다는 의미
            }
            repositories { // 의존성 주입을 어떤 원격 저장소에서 받을지 정하는 것
                mavenCentral() // 라이브러리를 업로드하는 난이도가 높아서 jcenter도 많이 쓰인다.
                jcenter()
            }
            dependencies {
                classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
            }
        }

        apply plugin: 'java'                               // 자바 플러그인
        apply plugin: 'eclipse'                            // ???
        apply plugin: 'org.springframework.boot'           // 스프링 부트를 위한 필수적인 플러그인
        apply plugin: 'io.spring.dependency-management'    // 스프링 부트 의존성 관리 플러그인

        group 'org.example'
        version '1.0-SNAPSHOT'
        sourceCompatibility = 1.8

        repositories {
            mavenCentral()
        }

        dependencies {

            testCompile group: 'junit', name: 'junit', version: '4.12'
        }

        dependencies { // 의존성 선언부분
            compile('org.springframework.boot:spring-boot-starter-web') // 버전을 명시할 수 있지만 하면 안된다. 위에서 선언한 전역변수를 버전으로 가지게 하기 위해서
            testCompile('org.springframework.boot:spring-boot-starter-test')
        }
        ```
        - 그 후에는 build를 눌러줘야 한다.
- 프로젝트 생성 방법2
    - https://start.spring.io/ 사이트에서 언어, 프로젝트 종류, dependencies 등을 설정하고 generate를 누르면 프로젝트를 자동으로 만들 수 있다.
- 스프링 부트 github 연동
    - ctrl+shift+a를 눌러 검색창을 킨다.
    - share project on Github 를 검색해서 찾는다.
    - 그러고 나면 Log in to Github 라고 나올텐데, 아이디 비밀번호를 입력해준다.
    - 그러면 Repository name 필드의 이름으로 깃허브 레포가 만들어진다.
    - share를 누르고 나면 프로젝트 커밋을 위한 팝업이 나온다.
    - 체크 해제를 통해 커밋하지 않을 파일을 선택할 수 있다.
    - .idea 폴더는 intelliJ에서 프로젝트 생성시 만들어지는 파일이기 때문에 커밋하지 않아야 한다.
    - ctrl+k를 통해 커밋창을 열 수 있다.
    - ctrl+shift+k를 통해 푸쉬창을 열 수 있다.
- gitignore으로 .idea 커밋 안되게 하기
    - gitignore은 따로 플러그인을 깔아줘야 한다.
    - ctrl+shift+a를 통해 plugins로 들어가고 .ignore 플러그인을 설치한다.
    - 설치 후 재부팅
    - 재부팅 후 프로젝트 바로 아래에 우클릭 -> new -> .ignore file -> .gitignore file을 선택한다.
    - 체크박스가 여러개 나오는데, 만약 전과 같은 ignore을 쓰려한다면 전의 ignore대로 쓸 수 있다.
    - 하지만 만든게 없으므로 generate를 쓴다.
    - 그리고 난 후에
    ```
    .파일
    .폴더
    ```
    - 처럼 지우고싶은 파일 또는 폴더를 .뒤에 써준다.
    - 그러면 커밋할 때 더이상 .idea와 .gradle은 나오지 않는다.
# 테스트코드
- 테스트코드는 옛날에는 무시받았지만, 최근들어 점점 필수요소가 되고있다.
- TDD
    - TDD가 없는 개발
        - 개발
        - 프로그램 실행
        - http 요청
        - print로 결과 확인
        - 아니면 프로그램 중지 후 수정
        - 너무 느림
        - 기존에 했던 테스트들을, 오류가 난 후에 똑같이 시도할 수 있음
    - TDD란 테스트 주도 개발(Test Driven Development)이란 뜻이다.
    - 그냥 테스트코드 짜고 개발하는거다.
    - 레드 그린 사이클
        - 레드
            - 항상 실패하는 테스트
        - 그린
            - 통과하는 코드 작성
        - 리팩토링
            - 통과하면 프로덕션 코드를 리팩토링
- 단위테스트
    - TDD의 첫번째 단계
    - 기능 단위의 테스트코드 작성
    - 문제 초기 발견
    - 코드 리팩토링과 라이브러리 업그레이드
    - 시스템의 실제 문서 제공(테스트가 곧 문서)
# WAS
- Web Server란
    - Web Server는 web 서버가 설치되어 있는 컴퓨터 이다.
    - 정적인 컨텐츠를 제공해 준다.
    - 기능
        - 요청에 따라 정적, 동적을 분류해서 반환해 준다.
        - 정적
            - WAS를 거치치 않고 바로 컨텐츠를 제공한다.
        - 동적
            - WAS에 요청을 전달한다.
            - WAS가 제공해준 결과를 받아 클라이언트에게 전달해 준다.
- WAS
    - Web Application Server의 약자이다
    - Web Server의 기능을 분할하자는 취지에서 나왔다.
        - 그냥 Web Server에서는 정적인 컨텐츠만을 반환한다.
        - 기존의 Web Server에서 사진과 같은 컨텐츠는 .html과 같은 파일과 같이 제공되는것이 아니다.
            - .html파일을 받은 후에 서버에 필요한 파일들을 요청하는 것이다.
        - 만약 동적인 컨텐츠들을 Web Server만으로 사용하려면, 사용자의 요청에 대한 결과값을 모두 미리 만든 후에 서비스를 해야한다.
            - 하지만 서버의 자원이 절대적으로 부족하기 때문에 불가능하다
        - WAS만 이용하지 않는 이유
            - 기능을 분리해서 서버의 부하를 방지해 준다.
            - 여러대의 Web Server를 WAS가 묶어줄 수 있다.
    - DB 조회와 같은 동적인 컨텐츠들을 수행하기 위해 사용된다.
    - 내장 WAS
        - spring boot에서 기본적으로 포함되어 있는 WAS
        - 웹 서버를 구동시켜주는 프로그램
        - 모든 서버에서 같은 환경으로 프로그램을 돌릴 수 있다.
        - 트래픽에 문제가 있다고 한다.
            - 하지만 쿠팡과 같은 높은 트래픽의 웹사이트에서도 내장 WAS를 사용하는데 지장이 없다고 한다.
    - 외장 WAS
        - 외부의, 즉 spring boot에서 기본적으로 포함되어 있지 않은 WAS이다.
        - 외장 WAS같은 경우에는 서버가 여러개 일때, 각각의 서버마다 환경을 구축해 줘야 한다.
# 간단한 코드 구현
### controller
- @RestController
    - 컨트롤러의 설정
    - 컨트롤러가 JSON을 반환하도록 해준다.
- @GetMapping(주소)
    - http 메소드중 get 메소드 요청을 받을 수 있도록 해준다
    - 해당 주소로 get 메소드 요청이 들어오면 해당 메소드 또는 클래스를 실행한다.
    - 중복 제거 
        - 만약 users/detail 이라는 URI에서 get 요청과 post 요청이 들어왔다는 가정하에
        - @GetMapping("users/detail"), @PostMapping("users/detail")처럼 할 필요는 없다.
        - 클래스에 @RequestMapping("/users/detail") 처럼 해 주고, @GetMapping과 @PostMapping을 사용하면 된다.
### 테스트코드
- 이름
    - 클래스 이름은 테스트할 컨트롤러의 이름뒤에 Test를 붙인 것
- HelloControllerTest
    - @RunWith
        - @RunWith는 테스트의 실행 방법을 학장시켜 준다.
        - Junit과 스프링 부트 테스트간의 연결자 역할
    - @WebMvcTest
        - 테스트를 Web에 집중하도록 해준다.
        - 장점
            - 몇몇 어노테이션을 제한함으로써 조금 더 가벼운 테스트가 가능하다.
        - 단점
            - 모두 Mock 기반으로 하기 때문에 동작하지 않을 수 있다.
    - @Autowired
        - 스프링이 관리하는 Bean을 주입받는다.
        - 원래 XML 파일에서 하나하나 직접 등록해야 했던것과 다르게, @Bean을 생성된 객체를 @Autowired를 통해 받아올 수 있다.
    - mvc.perform(get("/hello"))
        - hello라는 주소로 get 요청을 보낸다.
        - 뒤에 .을 통해 여러가지 기능을 사용할 수 있다.(체이닝)
    - .andExpect(status().isOk())
        - 반환된 http 상태코드가 내가 원하던 값인지 검증한다
        - 실패의 테스트 경우로 .isBadRequest()와 같이 사용할 수 있다.
        - HTTP 상태코드
            - 1xx
                - 계속 요청을 보내도 된다는 의미라고 하지만, 실험적인 경우를 제외하고는 서버에서 걸러야 한다고 한다.
                - 이 경우는 인터넷의 개발자들이 한번도 못봤다고 한다..
            - 2xx
                - 요청을 성공적으로 수행했을 경우
                - 200
                    - isOk()이다.
                    - 단순히 요청한 작업의 성공을 의미한다
                    - 대부분의 상황에서 클라이언트는 요청의 종류를 알기 때문에 200만 사용해도 문제가 없다.
                - 201
                    - isCreated()이다.
                    - 리소스의 생성이 성공했다는걸 의미한다.
                    - 게시글 생성, 회원가입 등이 있다.
                - 204
                    - isNoContent()이다.
                    - 요청을 성공적으로 수행했고, 요청과 관련된 컨텐츠가 존재하지 않음을 의미한다.
                    - 데이터 삭제 등에 사용된다.
            - 3xx
