# SQL

### SQL이란

관계형 데이터베이스를 위한 **표준**질의어로, 비 절차적 데이터 언어다.

> 표준이긴 하지만, 대부분 완전히 따르진 않고 데이터베이스마다 독자적인 SQL을 가진다.

### 발전

SEQUEL에서 유래되었다.

ANSI와 ISO에서 표준화를 진행해서 나온게 SQL이다.

### 분류

- DDL

  데이터 정의어

  테이블을 생성하고, 변경 및 제거 하는 기능을 제공한다.

- DML

  데이터 조작어

  테이블에 새 데이터를 넣고, 수정하는 등 데이터를 변경시켜 준다.

- DCL

  데이터 제어어

  말 그대로 데이터를 특정 시점에 저장하고, 그 시점으로 돌리는 등 제어할 수 있다.

### 처리 과정

1. 문법 검사

2. 권한 체크

3. 메모리 공간을 확인한다.(shared pool check)

4. 쿼리를 어떻게 실행할지 계획을 짠다.(optimization)

   SQL을 더 빠르고 효율적이게 만드려면 optimization에 대해 잘 알고 있어야 한다.

5. row source generator

6. 실행

### DDL

테이블명은 객체를 의미할 수 있는 이름을 사용해야 한다.

**단수형을 추천한다.**

테이블 명은 중복되면 안된다.

한 테이블에 중복된 이름의 컬럼은 있을 수 없다.

> 테이블이 다르면 중복될 수 있다.

각 컬럼들은 `,`로 구분되고, 쿼리의 끝은 항상 `;`로 끝난다.

컬럼에 대해 다른 테이블까지 고민해서, 일관성 있게 하는게 좋다.

> 데이터  표준화 관점 - 공통성 컬럼은 도메인으로 관리

컬럼 뒤에 데이터 유형은 필수다.

테이블, 컬럼명은 문자로 시작해야 하고, 길이에 한계가 있다.

예약어는 사용할 수 없다.

> 보통 스네이크 케이스를 사용한다.

#### 제약조건

이러한 제약조건은 `데이터 사전`에 저장된다.

`CONSTRAINT` 라는 키워드를 통해 제약조건을 해줄 수 있다.

> 없어도 작동하지만, 해당 키워드가 없으면 제약조건의 이름을 지정할 수 없다.

- NOT NULL

  값에 NULL을 허용하지 않겠다는 의미.

  ORACLE에서는 ` `처럼 공백도 NULL로 처리한다.

- DEFAULT

  만약 INSERT 시에 값을 주지 않았다면 어떤 값을 넣어줄지 정한다.

- CHECK

  `CONTRAINT TEST_OK1 CHECK(DEL_YN IN ('Y','N'))` 처럼 사용할 수 있고, 해당 조건에 맞는 값들만 넣을 수 있도록 한다.

- UNIQUE

  해당 컬럼은 값이 중복될 수 없음을 의미하는 제약 조건.

  NULL은 여러개 저장될 수 있다.

- PRIMARY KEY

  하나의 튜플을 정의할 수 있는 컬럼

  NOT NULL & UNIQUE 속성을 가지고 있다.

> USER_CONSTRAINTS 라는 테이블에서 사용자가 정의한 제약조건을 확인할 수 있다.

#### FK

FK란, Foreign Key의 약자로 **다른 테이블들과 관계를 맺는 컬럼**이다.

- FOREIGN KEY 생성

  외래 키 제약 조건을 만드는건 아래와 같다.

  ``` sql
  CREATE TABLE CUSTOM(
      C_ID CHAR(2),
      C_NAME VARCHAR(4),
      CONSTRAINT PK PRIMARY KEY (C_ID)
  );
  
  CREATE TABLE ORDER(
      ORD_NO NUMBER,
      ORD_ID CHAR(2),
      PRIMARY KEY (ORD_NO),
      FOREIGN KEY (ORD_ID) REFERENCES CUSTOM(C_ID)
  )
  ```

  이렇게 CUSTOM - ORDER 간의 관계를 가질 수 있다.

  ORD_ID 란 컬럼은 CUSTOM 테이블의 C_ID 컬럼과 관계를 맺게 되고, ORD_ID에는 CUSTOM의 PK인 C_ID가 저장되게 된다.

  FOREIGN KEY()

>`DROP TABLE CASCADE CONSTRAINTS`
>
>연관된 모든 테이블들을 삭제한다.



#### ALTER TABLE

- 추가

  ``` SQL
  ALTER TABLE 테이블명
  ADD 추가할컬럼 VARCHAR(10) DEFAULT 'AA';
  ```

  이렇게 하면 컬럼을 추가할 수 있다.
  제약 조건이나 FK 등을 설정해 줄수도 있다.

- 변경

  ``` sql
  ALTER TABLE 테이블명
  MODIFY 변경할컬럼 VARHAR(20) DEFAULT 'BB';
  ```

  기존에 존재하는 컬럼들을 변경해 줄 수 있다.

  `변경할 컬럼`에는 기존에 존재하는 컬럼명을 적어줘야 한다.

  

  만약 기존에 데이터가 있을 때 해당 데이터의 사이즈보다 작은 크기로 바꾸려 한다면 컬럼 사이즈를 줄일 수 없다.

  > 늘리는건 가능하다.

- 삭제

  ``` sql
  ALTER TABLE 테이블명
  DROP COLUMN 컬럼명;
  ```

  컬럼을 삭제할 수 있다.

  만약 해당 컬럼에 속하는 데이터들이 있다면, 모두 사라진다.

- 이름 변경

  ``` sql
  ALTER TABLE 테이블명
  RENAME COLUMN 기존컬럼 TO 바꿀이름
  ```

#### 제약조건 ALTER

- 추가

  ``` sql
  ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건이름 FOREIGN KEY(외래키 속성 이름)
  REFERENCES 매핑될테이블(해당 테이블 속성)
  ```

  이렇게 하면 기존에 존재하던 테이블에 제약조건 또는 외래 키 등을 추가해줄 수 있다.

- 삭제

  ``` sql
  ALTER TABLE 테이블명 DROP CONSTRAINT 제약조건이름;
  ```

  이렇게 하면 제약조건을 삭제할 수 있다.

- 이름 변경

  ``` sql
  ALTER TABLE 테이블명 RENAME CONSTRAINT 제약조건이름 TO 바꿀이름
  ```

- 수정

  **제약 조건은 수정이 불가능하다.**

  따라서 삭제 후 다시 추가해 주어야 한다.

#### 테이블명 수정

``` sql
ALTER TABLE 테이블 RENAME TO 바꿀이름;
```

이렇게 하면 테이블 이름을 변경해줄 수 있다.

#### 테이블 초기화

``` sql
TRUNCATE 테이블
```

**테이블의 데이터들을 모두 삭제해 준다.**

DELETE는 한번에 하나씩 제거하고, 내용을 로그에 기억한다.

즉 **롤백이 가능하다.**

하지만 TRUNCATE는 데이터가 저장되는데 사용되는 데이터 페이지를 취소해 버리기 때문에, 롤백이 불가능하다.

DELETE는 삭제할 행들을 LOCK걸고, 삭제된 데이터들을 HEAP 영역의 빈 페이지에 남아둔다.