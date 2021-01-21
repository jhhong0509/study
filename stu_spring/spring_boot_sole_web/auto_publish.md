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

#### S3를 이용하는 이유

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

#### Travis CI와 AWS S3 연동

- AWS Key
  - 기본적으로 AWS 서비스에 외부 서비스는 접근할 수 없다.
  - 그렇기 때문에 접근 권한을 가진 Key를 생성해서 사용해야 한다.
  - AWS는 인증 관련 기능인 IAM을 지원한다.
- Travis CI의 접근 허용하기
  - AWS 콘솔에서 IAM을 검색한다.
  - 왼쪽 사이드바에서 사용자를 누른다.
  - 사용자 추가를 누른다.
  - 사용자 이름, 엑세스 유형을 선택한다.
    - 엑세스 유형은 프로그래밍 방식 엑세스로 한다.
  - 권한 설정은 기존 정책 직접 연결을 선택한다.
  - 아래 검색화면에서 s3full로 검색해서 체크한다.
  - 그리고 codedeployf를 검색해서 체크한다.
  - 태그는 Name값을 자신이 인지할 수 있을 정도로 선택해 준다.
  - 권한 설정 항목을 확인한다.
  - 엑세스 키를 확인한다.
  - Travis CI 설정 화면으로 이동한다.
  - 내리다보면 Environment Variables 항목이 있다.
  - AWS_ACCESS_KEY 라는 이름으로 엑세스 키를 등록한다.
  - AWS_SECREY_KEY 라는 이름으로 비밀 엑세스 키를 입력한다.

- S3 만들기
  - AWS 웹 콘솔에서 S3를 검색한다.
  - 버킷 만들기를 누른다.
  - 이름을 작성해 준다.
  - 다 넘기고, 버킷 보안과 설정 부분으로 간다.
  - 버킷의 보안과 권한 설정에서는 모든 퍼블릭 엑세스 차단을 해야 한다.
    - Jar 파일이 Public이라면, 코드, 설정값 등 모두 탈취될 수 있다.

- .travis.yml 변경

  - 밑의 코드를 .travis.yml에 추가한다.

    ``` yaml
    before_deploy:
      - zip -r zip 파일의 이름 *
      - mkdir -p deploy
      - mv 파일 이름.zip deploy/파일 이름.zip
    deploy:	
      - provider: s3
        access_key_id: $AWS_ACCESS_KEY
    
      secret_access_key: $AWS_SECREY_KEY
    
      bucket: 버킷 이름
      region: 지역
      skip_cleanup: true
      acl: private
      local_dir: deploy
      wait-until-deployed: true
    ```

  - 코드 해석

    - ``` yaml
      before_deploy:
        - zip -r zip 파일의 이름 *
        - mkdir -p deploy
        - mv 파일 이름.zip deploy/파일 이름.zip
      ```

      - deploy 실행 전에 실행된다.
      - CodeDeploy가 Jar 파일을 인식하지 못해서 Jar+설정파일 을 zip파일로 묶어준다.

    - ``` yaml
      access_key_id: $AWS_ACCESS_KEY
      	
      secret_access_key: $AWS_SECRET_KEY
      ```

      - 아까 환경변수로 설정했던 값을 가져온다.

    - ``` yaml
      skip_cleanup: true
      ```

      - 빌드가 끝난 후에, 결과물을 삭제하지 않겠다는 의미이다.
      - cleanup을 스킵하겠단 의미
    
    - ``` yaml
      local_dir: deploy
      ```
    
      - 해당 디렉토리의 파일만 전송된다.
      - 위에서 만들었던 deploy 디렉토리로 정한다.

- EC2와 CodeDeploy 연동하기
  - IAM 역할 생성하기
    - 역할과 사용자 차이
      - 역할은 AWS 서비스에만 할당할 수 있는 권한
      - 사용자는 AWS 서비스 외에 사용할 수 있는 권한
  - IAM을 검색한다.
  - 역할을 누른다.
  - 역할 만들기를 누른다.
  - 서비스 선택에서 AWS 서비스를 누른다.
  - EC2를 누른다.
  - 정책에서는 EC2RoleForA를 검색한다.
  - 태그는 원하는 이름으로
  - 역할의 이름을 정하고, 정보를 확인한다.
  - EC2와 인스턴스 목록으로 이동한다.
  - 인스턴스를 우클릭 한다.
  - 인스턴스 설정 - IAM 역할 연결/바꾸기 를 선택한다.
  - 방금 생성한 역할을 선택해 준다.
  - EC2를 재부팅 해 준다.
  - EC2가 CodeDeploy 의 요청을 받도록 에이전트를 설치한다.
    - EC2에 접속한다.
    - aws s3 cp s3://aws-codedeploy-ap-northeast-2/latest/install .--region ap-northeast-2를 입력한다.
    - install 파일에 권한이 없으니 chmod +x ./install을 통해 실행 권한을 준다.
    - sudo ./install auto 로 설치를 진행한다.
    - sudo service codedeploy-agent status 를 통해 실행 상태를 확인한다.

- CodeDeploy와 EC2 연동하기
  - CodeDeploy에서 EC2에 접근하기 위한 권한이 필요하다.
  - 이것 또한 IAM 역할을 생성한다.
  - IAM을 검색한다.
  - 역할을 누른다.
  - 역할 만들기를 누른다.
  - 서비스 선택에서 AWS 서비스를 누른다.
  - CodeDeploy를 선택한다.
  - 권한이 하나뿐이기 때문에 바로 넘어간다.
  - 태그를 원하는 이름으로 한다.
  - 역할 이름과 선택 항목을 작성한다.

- CodeDeploy 생성하기
  - AWS에서 지원하는 배포
    - Code Commit
      - 깃허브와 비슷하다.
      - 깃허브가 무료이기 때문에 잘 안쓰인다.
    - Code Build
      - Travis CI처럼 빌드용 서비스 이다.
      - 보통 젠킨스/팀시티를 이용해서 잘 사용되지 않는다.
    - CodeDeploy
      - AWS의 배포 서비스 이다.
      - 대체제가 없다.
      - 많은 기능을 지원한다.
  - CodeDeploy 서비스로 이동한다.
  - 애플리케이션 생성을 누른다.
  - 컴퓨팅 플랫폼은 EC2/온프레미스를 선택한다.
  - 생성 후 배포 그룹 생성을 누른다.
  - 배포 그룹 이름을 등록한다.
  - 서비스 역할은 아까 만들어둔 CodeDeploy용 IAM을 선택한다.
  - 서비스가 1대라면 현재위치, 2대 이상이라면 블루/그린을 선택한다.
  - 환경 구성에서 Amazon EC2 인스턴스를 선택해 준다.
  - 배포 구성을 CodeDeployDefault.AllAtOnce를 선택한다.
    - 한번에 몇대의 서버에 배포할지 결정한다.
    - 30% 또는 50%로 나누어서 배포할지 등을 결정할 수 있다.
    - 우리는 한번에 전부 다 배포하는 AllAtOnce를 선택한다.
  - 로드밸런싱은 체크를 해제한다.

### Travis CI, S3, CodeDeploy

- S3의 zip파일을 저장할 디렉토리 생성
  - mkdir ~/app/step2 && mkdir ~/app/step2/zip
  - &&를 통해 여러개의 명령어를 연속해서 사용할 수 있다.
  - 해당 디렉토리에 zip 파일이 복사되어 압축을 풀게 할 예정

- AWS CodeDeploy 설정

  - CodeDeploy 설정은 appspec.yml로 할 수 있다.

  - 코드

    - ``` yaml
      version: 0.0
      os: linux
      file:
        - source: /
          destination: 파일을 받을 경로
          overwrite: yes
      ```

    - 코드 분석

      - ``` yaml
        version: 0.0
        ```

        - CodeDeploy 버전

      - ``` yaml
        source: /
        ```

        - CodeDeploy에서 받은 파일중, 목적지로 이동시킬 대상
        - /로 하면 모든 파일

      - ``` yaml
        destionation: 파일을 받을 경로
        ```

        - source에서 받아온 파일을 받을 위치
        - Jar을 실행하는 등 작업은 여기서 옮긴 파일들로 진행된다.

      - ``` yaml
        overwrite: yes
        ```

        - 기존의 파일을 덮어쓸지 여부
        - yes 이니 덮어쓴다는 의미이다.

  - .travis.yml에서도 CodeDeploy에 관한 내용을 추가해야 한다.

    - ``` yaml
      deploy:
        - provider: codedeploy
          access_key_id: $AWS_ACCESS_KEY
      	secret_access_key: $AWS_SECRET_KEY
      
        bucket: S3 버킷이름
        key: zip 파일이름
      
        bundle_type: zip
        application: 웹 콘솔의 CodeDeploy 애플리케이션
      
        deployment_group: CodeDeploy 배포 그룹 이름
      
        region: ap-northeast-2
        wait-until-deployed: true
      ```

- 배포 자동화 만들기

  - scripts 디렉토리를 만든다.

  - 그 아래에 deploy.sh 파일을 만들어 준다.

    - 코드

    - ``` sh
      #!/bin/bash
      
      REPOSITORY=/home/ec2-user/app/step2
      PROJECT_NAME=freelec-springboot2-webservice
      
      echo "> Build 파일 복사"
      
      cp $REPOSITORY/zip/*.jar $REPOSITORY/
      
      echo "> 현재 구동중인 애플리케이션 pid 확인"
      
      CURRENT_PID=$(pgrep -fl freelec-springboot2-webservice | grep jar | awk '{print $1}')
      
      echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"
      
      if [ -z "$CURRENT_PID" ]; then
          echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
      else
          echo "> kill -15 $CURRENT_PID"
          kill -15 $CURRENT_PID
          sleep 5
      fi
      
      echo "> 새 어플리케이션 배포"
      
      JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
      
      echo "> JAR Name: $JAR_NAME"
      
      echo "> $JAR_NAME 에 실행권한 추가"
      
      chmod +x $JAR_NAME
      
      echo "> $JAR_NAME 실행"
      
      nohup java -jar \
          -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
          -Dspring.profiles.active=real \
          $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
      ```

    - 코드 설명

      - ``` sh
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
        ```

      - 기본적으로 nohup 실행시에, CodeDeploy는 무한으로 대기한다.

      - 이것을 해결하기 위해 nohup.out 파일을 표준 입출력으로 별도 사용한다.

  - .travis.yml 파일 수정

    - ``` yaml
      before_deploy:
        - mkdir -p before-deploy
        - cp scripts/*.sh before-deploy/
        - cp build/libs/*.jar before-deploy
        - cd before-deploy && zip -r before-deploy *
      
        - cd../ && mkdir -p deploy
        - mv before-deploy/before-deploy.zip deploy/이름.zip
      ```

    - 코드 해석
      
      ``` yaml
mkdir -p before-deploy
      ```
  
- zip에 포함시킬 파일들을 모아놓은 디렉토리를 만들어 준다.
      - Travis CI에서 S3로 업로드 할때, 디렉토리 단위로만 가능하다.
      
    - ``` yaml
  cp scripts/*.sh before-deploy/
      ```

      - 스크립트 폴더 밑의 모든 쉘 스크립트 파일들을 복사해간다.
    
    - ``` yaml
  cp appspec.yml before-deploy/
      ```

      - 만들어둔 CodeDeploy 설정 파일들을 복사해간다.
    
    - ``` yaml
  cp build/libs/*.jar before-deploy
      ```

      - 빌드된 jar 파일도 복사해 간다.

    - cd before-deploy && zip -r before-deploy *

      - before-deploy로 이동한 후에 모든 파일을 압축시킨다.
    
    - ``` yaml
  mv before-deploy/before-deploy.zip deploy/이름.zip
      ```
    
      - 압축된 파일을 새로 만든 deploy 폴더로 옮겨준다.
    
  - appspec.yml 수정
  
    - ``` yaml
      permissions:
        - object: /
          pattern: "**"
          owner: ec2-user
          group: ec2-user
      hooks:
        ApplicationStart:
          - location: deploy.sh
            timeout: 60
            runas: ec2-user
      ```
  
    - 코드 분석
  
      - ``` yaml
        permissions:
          - object: /
            pattern: "**"
            owner: ec2-user
            group: ec2-user
        ```
  
        - 가져온 파일들에 대해서 모두 ec2-user의 권한을 가지게 한다.
  
      - ``` yaml
        hooks:
          ApplicationStart:
            - location: deploy.sh
              timeout: 60
          	  runas: ec2-user
        ```
  
        - CodeDeploy에서 배포할 때 실행할 명령어를 지정해 준다.
        - ApplicationStart 단계에서 스크립트 파일을 ec2-user 권한으로 실행한다.
        - 만약 60초 이상 수행이 끝나지 않으면 실패한다.

### 로그 보기

- AWS가 지원하는 서비스에서 로그를 볼 줄 모른다면, 해결하기 매우 어려워 진다.

- 배포에 실패했을 때, 어느 로그를 봐야 할지 소개하려 한다.

- CodeDeploy 에 관한 내용들의 위치로 이동해 준다.

  - cd /opt/codedeploy-agent/deployment-root

- ll을 통해 내용을 확인해 준다.

  - 코드처럼 생긴 디렉토리
    - 영문과 -로 이루어진 디렉토리 명은 CodeDeploy의 ID이다.
    - 본인의 서버에는 본인의 코드로 되어있다.
    - 배포 파일이 정상적으로 있는지 확인할 수 있다.

  - deployment-logs
    - CodeDeploy의 로그들이 저장되는 파일이다.
    - 배포중 표준 입/출력은 모두 포함된다.
    - echo 내용도 포함된다.