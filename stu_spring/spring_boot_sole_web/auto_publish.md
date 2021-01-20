# Travis CI를 이용한 배포 자동화

### 만들어야 하는 이유

- 매번마다 일일히 배포하기에는 귀찮다.
- 실수의 여지가 너무 많다.

### CI와 CD

#### CI

- VCS(Version Controll System)에 PUSH가 됬을 때, 자동으로 테스트와 빌드를 하는 배포 파일을 만드는 과정
  - VCS란, 버전 관리 시스템의 약자로 대표적으로 Git과 SVN이 있다.
- CI가 만들어진 계기
  - 여러 개발자가 함께 개발을 진행한다.
  - 각자 개발한 코드를 병합하는 것은 큰 일이었다.
  - 그렇기 때문에 매주 병합일을 정해서 한번에 합쳤다.
  - 생산성이 좋지 않았다.
- CI의 규칙
  - 모든 소스가 살아있고, 누구든 접근할 수 있는 단일 지점 유지해야 한다.
  - 빌드 프로세스를 자동화 해서 누구든 시스템을 빌드하는 하나의 명령어를 사용하도록 해야 한다.
  - 테스트 자동화를 통해 단일 명령어로 시스템에 대한 건전한 테스트를 실행할 수 있도록 해야 한다.
  - 누구나 실행 파일을 얻으면 가장 완전한 실행 파일을 얻었다는 확신을 줘야 한다.

#### CD

- 빌드 결과를 자동으로 무중단 배포까지 진행하는 과정

- CD가 만들어진 계기
  - 서버가 1, 2개면 크게 문제될게 없지만, 서버가 늘어나면 수동으로 배포하기 힘들다.
  - 개발에 집중할 수 없다.

### Travis CI 연동하기

#### 다른 CI의 단점

- 젠킨스
  - 설치형이다.
  - EC2 인스턴스를 새로 만들어야 한다.
  - 이제 시작하는 서비스 배포를 위한 EC2 인스턴스는 부담스럽다.
- CodeBuild
  - AWS에서 지원한다.
  - 빌드 시간만큼 요금이 부과된다.
  - 비용 부담을 최소화 하는게 좋다.

#### 웹 서비스 설정

- <a href="https://travis-ci.org">링크</a>에서 깃허브 계정으로 로그인 한다.
- 오른쪽 위 계정 -> Settings를 눌러준다.
- 아래쪽에 깃허브 저장소 검색창으로 간다.
- 원하는 레포 이름을 검색하고, 상태바를 활성화 시킨다.
- 해당 레포를 누른다.

#### 프로젝트 설정하기

- Travis CI 설정은 .travis.yml 파일을 통해 할 수 있다.

  - .yml은 .properties에 비해 계층형 구조를 보기 쉽다.

- 구현하기

  - build.gradle 옆에 .travis.yml을 만들어 준다.

  - 코드를 추가한다.

    - ``` yaml
      language: java
      jdk:
      	- openjdk8
      
      branches:
      	only:
      		- master
      		
      cache:
      	directories:
      		- '$HOME/.m2/repository'
      		- '$HOME/.gradle'
      
      script: "./gradlew clean build"
      
      notifications:
      	email:
      		recipients: 이메일 주소
      ```

    - 코드 분석

    - ```yaml
      language: java
      jdk:
      	- openjdk8
      ```

      -  언어를 설정해 준다.
      - 만약 자바 버전이 다르다면 다르게 설정하면 된다.

    - ``` yaml
      branches:
      	only:
      		- master
      ```

      - 어느 브랜치에 PUSH 되었을 때 Travis CI를 작동시킬지 결정한다.
      - 현재 master 브랜치에서만 작동한다.
      - 여러개를 설정할수도 있고, master가 아닌 main이나 develop 등 모두 된다.

    - ``` yaml
      cache:
      	directories:
      		- '$HOME/.m2/repository'
      		- '$HOME/.gradle'
      ```

      - 의존성을 한번 받은 후에, 같은 의존성을 다음 배포때 받지 않도록 한다.

    - ``` yaml
      script: "./gradlew clean build"
      ```

      - 푸쉬되었을때 수행될 명령어

    - ```yaml
      notifications:
      	email:
      		recipients: 이메일 주소
      ```

      - Travis CI 실행시 알림이 가도록 한다.
      - 꼭 이메일일 필요는 없고, 만약 slack에 올리고 싶다면 slack: 슬랙아이디 처럼 사용하면 된다.

#### Travis CI와 AWS S3 연동하기

- S3란
  - S3란 AWS에서 지원하는 파일서버이다.
  - 보통 파일 업로드와 같은 서비스를 구현하면 S3를 이용한 것이다.
  - 하지만 지금처럼 배포 파일을 관리할수도 있다.

- 프로젝트 구조

<img src="https://doorisopen.github.io/assets/images/2020/spring/freelec-springboot-chap9-1.JPG">

- 실제 배포는 AWS에서 지원하는 Code Deploy를 이용한다.
- CodeDeploy에는 저장 기능이 없기 때문에 CodeDeploy가 빌드 결과물을 가져갈 수 있도록 S3를 이용해야 한다.
  - CodeDeploy가 빌드, 배포 모두 할 수 있다.
  - 하지만 빌드 없이 배포만 할 때 대응하기 어렵다.
  - 또한 확장성이 떨어진다.
- 