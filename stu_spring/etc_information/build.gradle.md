# build.gradle

### build.gradle이란?

Kotlin, Spring 등에서 사용되는 강력한 Build Tool로, **groovy 문법**을 사용한다.

> Kotlin 문법으로도 작성이 가능하다.

최근에 많이 사용되고 있는 Build Tool이 Gradle이다.



#### Build Tool 이란?

Build Tool이란 **실행 가능한 애플리케이션**을 **자동**으로 만들어주는 프로그램을 지칭한다.

Build Tool은 코드의 컴파일, 패키징하여 **사용 또는 실행 가능한 프로그램**으로 만들어 준다.

> Build에 대해 좀 더 자세히 말하자면, 단순히 실행 가능한 애플리케이션을 만드는 것 만을 의미하는 것이 아니다.
>
> 소프트웨어가 제품이 되기 위한 컴파일, 테스트, 배포, 문서화 등의 작업을 포함하게 된다.



#### gradle의 문법

Gralde은 위에서 groovy 문법을 사용한다고 소개했다.

하지만 엄밀히 말하면, groovy 문법을 사용하는게 아니라 **groovy 기반의 DSL(Domain-Specific Language)**를 사용한다.

> groovy는 JVM 에서 실행되는 동적 타이핑 언어로, 스크립트 언어의 일종이다. 문법이 Java에 가깝기 때문에 Java 개발자들이 쉽게 배울 수 있다.
>
> Java와 호환이 가능해서 Java 클래스를 그대로 groovy 클래스로 사용할 수 있다.



> 여기서 DSL이라는 언어를 사용한다는게 아니다.
>
> DSL은 **어떤 목적이 있고, 그 목적을 달성할 수 있는 언어**들을 지칭하는 용어이다.



#### 기본 사용

우리는 Spring Boot를 개발할 때 build.gradle을 자주 마주치게 된다.

IDEA에서 Spring Boot 프로젝트를 생성하게 되면 자동으로 build.gradle을 생성하게 되는데, 기본적인 예제는 아래를 보자

> maven 프로젝트로 만들었다면 gradle 대신 maven이 들어가게 된다.
>
> 최근엔 Maven보다 gradle이 인기가 많아 gradle 사용을 권장한다.



``` groovy
plugins {
    id 'org.springframework.boot' version '2.3.1.RELEASE'
    id 'io.spring.dependency-management' version '1.0.9.RELEASE'
    id 'java'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '1.8'

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.kafka:spring-kafka'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.h2database:h2'
    runtimeOnly 'mysql:mysql-connector-java'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
    testImplementation 'org.springframework.kafka:spring-kafka-test'
    testImplementation 'org.springframework.security:spring-security-test'
}

test {
    useJUnitPlatform()
}
```



##### 문법 설명

- plugins

  프로젝트를 빌드하기 위해선 여러가지 작업을 해 주어야 하는데, 이런 작업들을 해주는 plugin들을 가져오도록 해준다.

  plugins 안에 필요한 plugin 들을 지정하게 되면 해당 plugin이 필요로 하는 과정들을 task로 포함한다.

  빌드 시에 꼭 필요한 과정을 plugin의 내부 task로 실행해 준다.

- repositories

  repositories는 저장소 정보를 관리하는 property 이다.

  소프트웨어를 등록해서 관리하는 장소를 나타내는데, 로컬 혹은 네트워크에 해당 라이브러리를 공개하고 그 주소를 저장소로 등록하면 해당 저장소의 라이브러리들을 gradle이 취득해서 사용하도록 한다.

  

  쉽게 말해서 라이브러리들이 모여있는 장소로, 이걸 해줘야 dependency에서 `(저장소).(라이브러리)` 처럼 사용해서, 해당 저장소에 접속한 후 원하는 라이브러리를 찾아서 가져올 수 있다.

  

  주로 `mavenCentral()` 또는 `jCenter()` 를 사용하는데, `mavenCentral()`은 아파치 재단에서 운영되고 있다.

  > jCenter()는 유지보수를 하던 JFrog가 지원을 중단하고 2022년 2월 1일부터 기존의 라이브러리들을 다운로드 할 수 없다고 한다.
  >
  > 그러니 `mavenCental()` 혹은 다른 저장소를 이용하자.

- dependencies

  dependencies는 의존성 관련 설정을 관리하는 property다.

  여기서 필요한 라이브러리들을 명시하게 되면 해당 라이브러리를 사용할 수 있게 된다.
  


  dependency를 작성하는 방법에는 두가지가 있는데, 어느 방법을 사용해도 무방하다

  ``` groovy
  dependencies {
      compile group: 'org.hibernate', name: 'hibernate-core', version: '3.6.7.Final'
      // 아래는 축약형으로, 같은 의미다.
      compile 'org.hibernate:hibernate-core:3.6.7.Final'
  }
  ```

  이렇게 `group:주소, name:라이브러리 이름, version:버전` 처럼 하는 방법과

  `compile '주소:라이브러리:버전'` 처럼 사용하는 것도 가능하다.

  > 추천되는건 딱히 없고, 자신이 원하는걸 사용하면 된다.


  또한 `compile`과 `implementation` 두가지 모두 사용되는걸 볼 수 있는데, 이상하게도 둘 다 바꿔도 잘 작동한다.

  이유는 짧게 설명해서, `compile`은 모듈이 수정되었을 때 **직, 간접적으로 의존하고 있는 모든 모듈**들이 다시 컴파일 된다. 또한 **연결된 모든 모듈의 API가 노출되게 된다고 한다.**

  반대로 `implementation`은 모듈이 수정되었을 때 **직업 의존하고 있는 모듈**만 다시 컴파일 되게 되며, 연결된 모듈들의 API가 노출되지 않는다.

  따라서 `implementation`은 빠르고, 모듈들의 API들을 노출시키지 않을 수 있기 때문에 `implementation`이 추천된다.

  > 아무리 `compile`이 비 추천 된다고 해도, compile 처럼 사용하고 싶다면 `api` 키워드를 사용하면 된다.

  

  다음과 같이 변경하면 된다.

  | 변경 전      | 변경 후             |
  | ------------ | ------------------- |
  | compile      | implementation      |
  | testCompile  | testImplementation  |
  | debugCompile | debugImplementation |


  키워드를 정리하면 다음과 같다.

  > compile은 정리되지 않았다.

  

  | 이름                | 적용되는 때                                                  |
  | ------------------- | ------------------------------------------------------------ |
  | testImplementation  | 테스트 시                                                    |
  | compileOnly         | 빌드에는 추가되지 않고, 컴파일 클래스 경로에만 추가된다.     |
  | runtimeOnly         | runtime class path에만 추가된다.                             |
  | annotationProcessor | 컴파일 될 때, 사용자의 코드를 수정해 준다.<br />대표적으로 Lombok이 있는데 어노테이션에 따라 사용자의 코드에 생성자 등을 추가해 준다. |

- ext

  모든 task에서 참조할 수 있는 전역변수를 선언하는 블록이다.

- buildscript

  빌드하는 동안 필요한 처리들을 모아둔 property다.