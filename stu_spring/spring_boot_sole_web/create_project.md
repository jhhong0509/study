# 프로젝트 설정

### 프로젝트 설정 방법 1

- 프로젝트 생성은 Gradle 프로젝트에서 Java를 선택해야 한다.

- ArtifactId는 프로젝트의 이름이 되기 때문에, 원하는 이름을 선택하면 된다.

- 만약 Gradle JVM에서 JDK가 이상한게 잡혔다면, 설정해야 한다.

- 다른건 다 기본설정으로 두면 된다.

- 스프링 부트 프로젝트로 변경하기

  - 지금의 프로젝트는 스프링 부트가 아닌 그냥 일반적인 Gradle 프로젝트이기 때문에 spring boot 프로젝트로 바꿔야 한다.
  - 아래 코드를 build.gradle 에 넣어주면 된다.

  ```gradle
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

### 프로젝트 생성 방법 2

- https://start.spring.io/ 사이트에서 언어, 프로젝트 종류, dependencies 등을 설정하고 generate를 누르면 프로젝트를 자동으로 만들 수 있다.

### 프로젝트를 깃허브에 연동하기

- ctrl+shift+a를 눌러 검색창을 킨다.
- share project on Github 를 검색해서 찾는다.
- 그러고 나면 Log in to Github 라고 나올텐데, 아이디 비밀번호를 입력해준다.
- 그러면 Repository name 필드의 이름으로 깃허브 레포가 만들어진다.
- share를 누르고 나면 프로젝트 커밋을 위한 팝업이 나온다.
- 체크 해제를 통해 커밋하지 않을 파일을 선택할 수 있다.
- .idea 폴더는 intelliJ에서 프로젝트 생성시 만들어지는 파일이기 때문에 커밋하지 않아야 한다.
- ctrl+k를 통해 커밋창을 열 수 있다.
- ctrl+shift+k를 통해 푸쉬창을 열 수 있다.

### .gitignore 설정하기

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