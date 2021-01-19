# 배포

### GIT CLONE을 이용한 배포

- EC2에 깃을 설치한다.
  - EC2에 접속한다.
  - sudo yum install git 을 통해 깃을 깔아준다.
- 프로젝트를 저장할 폴더를 만들어 준다.
  - mkdir ~/app && mkdir ~/app/step1
- 해당 디렉토리로 이동한다.
  - cd ~/app/step1
- 본인 깃허브(배포하고 싶은 프로젝트)의 레포지토리로 간다.
- 해당 레포지토리에서 clone or download를 한 후에, 주소를 복사한다
  - 그냥 맨 위쪽의 본인 깃허브 레포 URL이 아니라, clone or download를 한 후에 나타나는 박스의 URL을 복사해야 한다.
- 그리고 git clone을 진행한다.
  - git clone 주소
- cd 프로젝트명 을 통해 자신의 프로젝트로 이동한다.
- ll을 통해 해당 디렉토리의 파일들을 찍어서 제대로 됬는지 확인한다.
- 테스트들을 잘 통과하는지 검사한다.
  - ./gradlew test 명령어를 통해 테스트를 실행할 수 있다.
  - 만약 실패했고, 수정해서 다시 푸쉬했다면 git pull을 이용하면 된다.
  - 만약 권한이 부족하다는 에러가 발생하면 chmod +x ./gradlew 를 통해 권한을 부여할 수 있다.

### 배포 스크립트 만들기

- git pull, 프로젝트 테스트, EC2에서 실행이라는 과정은 너무나도 귀찮다.
- 그러므로 쉘 스크립트를 통해 스크립트만 실행하면 앞선 과정이 모두 진행되도록 한다.
  - 쉘 스크립트란, sh라는 파일 확장자를 가진 파일이고, 빔(vim)은 CUI 환경에서도 사용할 수 있는 편집도구이다.
- 스크립트 구현

``` sh
#!/bin/bash

REPOSITORY=프로젝트 경로
PROJECT_NAME=프로젝트 이름

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"

git pull

echo "> 프로젝트 Build 시작"

./gradlew build

echo "> step1 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일복사"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/

echo "현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}*.jar)

echo "현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다"
else
	echo "> kill -15 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep *.jar | tail -n 1)

echo "> JAR NAME: $JAR_NAME"

nohup java -jar \
	-Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties \
	-Dspring.profiles.active=real \
	$REPOSITORY/$JAR_NAME 2>&1 &
```

- 이 스크립트에게 실행 권한을 준다
  - chmod +x ./deploy.sh
- 스크립트를 실행한다.
  - ./deploy.sh
- 로그를 열어볼 수 있다.
  - vim nohup.out
  - 실행에 실패한게 정상이다.

### 외부 Security 파일 등록하기

- 우리가 로컬에서 돌렸을땐, application-oauth.properties가 있어서 문제가 없었다.
- 하지만 .gitignore에 application-oauth.properties를 추가했기 때문에 문제가 발생한다.

- 그래서 직접 이 설정들을 넣어주면 된다.

- vim /home/ec2-user/app/application-oauth.properties를 통해 application-oauth.properties을 만들어 준다.

- 그리고 로컬의 내용을 그대로 붙여넣기 한다.

- 그리고 sh 파일에서 해당 properties를 사용하도록 해준다

  - ```sh
    nohup java -jar \
    		-Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties \
    		$REPOSITORY/$JAR_NAME 2>&1 &
    ```

- 그리고 다시 실행하면 잘 작동하는것을 확인할 수 있다.

### RDS 접근하기

- 테이블 생성

  - 원래 H2는 자동으로 테이블을 생성해 줬지만, 이제는 직접 쿼리를 이용해야 한다.
  - JPA가 사용하던 테이블
    - 그냥 테스트코드에서 찍히는 로그들을 복사해서 붙여넣기 하면 된다.
  - 세션 테이블
    - schema-mysql.sql 파일에서 확인할 수 있다.(ctrl+shift+n)
    - 여기서도 복사해서 가져가면 된다.

- 프로젝트 설정

  - build.gradle

    - compile("org.mariadb.jdbc:mariadb-java-client")

  - RDS환경의 profile 설정을 추가한다.

    - application-real.properties를 만든다.(real 이라는 프로필)

    - ```properties
      spring.profiles.include=oauth, real-db
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
      spring.session.store-type=jdbc
      ```

- EC2 설정

  - RDS 접속 정보 또한 보호해야 하니까 EC2 서버에 직접 설정파일을 올린다.

  - vim ~/app/application-real-db.properties

    - ```properties
      spring.jpa.hibernate.ddl-auto=none
      spring.datasource.url=jdbc:mariadb://엔드포인트:포트/db이름
      spring.datasource.username=db계정
      spring.datasource.password=db비번
      spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
      ```

    - ```sh
      nohup java -jar \
      	-Dspring.config.location=classpath:/application.properties,/home/ec2-user/app/application-oauth.properties,/home/ex2-user/app/application-real-db.properties,classpath:/application-real.properties \
      	application-real.properties \
      	-Dspring.profiles.active=real \
      	$REPOSITORY/$JAR_NAME 2>&1 &
      ```