# 기초 지식

### 테이블 생성

``` sql
CREATE TABLE student (
    sno CHAR(4) NOT NULL,
    sname CHAR(20) NOT NULL,
    age INT,
    height INT,
    weight INT
);
```

이렇게 테스트용 테이블을 만들 수 있다.

**모두 소문자로 썼지만, 모두 대문자로 저장된다.**

> 나중에 select나 drop에서 소문자로 써도 알아서 찾게된다.

대부분 mysql과 비슷하다.

### TAB

tab 뷰에는 테이블들이 저장된다.

만약 system 유저로 접근했다면 이미 기본적인 테이블들이 존재할 것이다.

하지만 사용자가 직접 지정해서 만든 유저에선 아무런 정보도 없다.

> 따로 만든 유저들은 시스템에서 중요한 테이블들에 접근할 수 없기 때문이다.

즉 해당 유저가 접근 가능한 테이블들이 정리된 뷰다.

### INSERT

``` SQL
INSERT INTO STUDENT VALUES ('2402', '이순신', 17, 160, 80);
```

SQL 표준을 따르기 때문에 다른 DB들과 거의 똑같다.

### DROP

``` sql
DROP TABLE STUDENT;
```

이렇게 하면 테이블을 삭제할 수 있다.

하지만 tab에서 SELECT를 해보면 삭제 대신 이름이 이상한 문자열로 바뀌어 있는걸 볼 수 있다.

> recycle을 위해 recycle bin에 임시로 저장된다.

만약 아예 삭제하려면 `PURGE` 키워드를 추가하면 된다.

``` sql
DROP TABLE STUDENT PURGE;
```



그리고 tab에서 이미 삭제된 테이블이 나오는게 불편하다면 아래와 같이 하면 된다.

> recyclebin 즉 휴지통에서 값들을 비우게 된다.

``` sql
PURGE RECYCLEBIN;
```

