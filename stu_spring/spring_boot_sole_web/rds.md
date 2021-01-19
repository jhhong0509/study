# AWS RDS

### RDS를 사용하는 이유

- 규모가 큰 프로젝트/회사에는 DBA라는 전문 직종이 따로 존재한다.
- 하지만 규모가 작은 서비스나 스타트업 회사 같은 경우에는 개발자가 데이터베이스를 다뤄야 한다.
- 그렇기 때문에 개발자 또한 어느정도의 데이터베이스를 알아야 하는데, 직접 데이터베이스를 사용하진 않는다.
- 왜냐하면 모니터링, 알람, 백업, HA 구성 등 모두 직접 해야만 하는데, 처음 하기에는 매우 어려운 일이다.
- 그렇기 때문에 앞에서 말한 모든 작업들을 지원하는 RDS(Relational Database Service)를 이용할 것이다.
  -  클라우드 기반 관계형 데이터베이스 이다.
  - 잦은 운영 작업을 자동화 해 준다.
  - 예상치 못한 많은 데이터가 쌓이게 되어도 비용만 내면 정상적으로 이용할 수 있다.

### RDS 인스턴스 생성

- RDS 검색

- 데이터베이스 생성

  - MariaDB와 Mysql, PostgreSQL을 추천한다.

  - 그중에서 MariaDB가 가장 추천된다.

    - 가격이 싸다

    - Amazon Aurora로 교체되기 쉽다.

      - AmazonAurora는 AWS에서 여러 DB를 클라우드 기반에 맞게 재구성한 DB이다.

      - 다른 DB보다 훨씬 빠르다.
      - AWS에서 직접 엔지니어링 하고있기 때문에 계속 발전하고 있다.
      - 하지만 월 10만원 이상이기 때문에 부담스럽고, 그렇기 때문에 프리티어의 MariaDB를 이용하다가 교체되기 쉽다.

    - 모두들 가장 인기있는 오픈소스 DB를 고르라고 하면 MySQL을 고른다.

    - 2010년 오라클과 썬마이크로 시스템즈가 합병되면서 개발자들이 떠나게 된다.

    - 떠난 mysql 개발자들이 만들어낸 DB가 MariaDB이다.

    -  MySQL 기반이기 때문에 사용법이 비슷하다.

    - 성능이 향상되어 있다.

- 템플릿에서 프리티어를 선택한다.

- 스토리지는 20GIB로 설정해 준다.

- 마스터 사용자 이름과 마스터 암호를 설정해 주고, 식별자(이름)을 지정해 준다.

- 그리고 퍼블릭 엑세스 기능을 예 로 설정해야 외부에서 접근할 수 있다.

### 파라미터 설정

- 우선 파라미터 왼쪽의 파라미터 그룹 탭으로 이동한다.
- 기존의 파라미터를 선택하는게 아니라, 새로 생성해 준다.
- DB 엔진을 선택해야 하는데, 방금 생성한 DB와 버전을 맞춰줘야 한다.
- 생성된 파라미터 그룹을 선택해 준다.
- 오른쪽 위 편집 버튼을 누른다.
- 그리고 time_zone을 검색해서 Asia/Seoul로 변경해 준다.
- 그리고 character_set을 검색해서 모두 utf8mb4로 변경해 준다.
  - character_set_client
  - character_set_connection
  - character_set_database
  - character_set_filesystem
  - character_set_results
  - character_set_server
- 그리고 collation을 검색해서 utf8mb4_general_ci로 변경해 준다.
  - collation_connection
  - collation_server
- 마지막으로 max_connections를 검색해서, 조금 더 넉넉하게 지정해 준다.
- 그리고 데이터베이스 탭으로 이동한 후에 DB를 선택하고, 수정을 눌러준 다음 파라미터 그룹을 바꿔준다.
- 아직 서비스를 오픈하지 않았으니 즉시 적용을 눌러준다.

### 보안그룹 설정

- 우선 EC2의 보안그룹 id를 복사해서 그 ID를 RDS 보안그룹의 인바운드로 추가한다.
- 그리고 자신의 IP도 추가해 준다.

### 데이터베이스 GUI 클라이언트

- 대표적으로 Mysql Workbench, SQLyog 등이 있지만, 여기서는 IntelliJ의 플러그인(Database Navigator)을 설치해서 한다.
- RDS로 가서 엔드포인트(접근하기 위한 URL 같은거)
- ctrl+shift+a 를 통해 검색을 연다.
- Database Browser 를 검색해서 왼쪽 사이드바에 DB Browser를 띄워준다.
- \+버튼을 눌러 MySQL을 선택한다(MariaDB 또한 MySQL을 선택한다)
- Host에는 엔드포인트, 포트에는 3306, user에는 아이디, password에는 비밀번호를 입력하고 Test Connection을 눌러 연결 테스트를 한다.
- 성공했다면 Apply, OK 버튼을 눌러 저장을 한다.
- Open SQL Console을 눌러주고, New SQL Console을 눌러서 콘솔창을 만들어 준다.
- 콘솔창에서 useDB명 을 치고, 드래그 한 후에 Execute Statement 클릭해 준다.

### EC2에서 RDS 접근 확인하기

- sudo yum install mysql을 통해 mysql을 설치한다.
- 설치했다면 mysql -u 아이디 -p -h 엔드포인트 를 친다.
- 그 이후에 비밀번호를 입력하라고 나오는데, 비밀번호를 입력한다.