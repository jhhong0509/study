# 데이터베이스 유저

### 기본 유저

데이터베이스는 기본적으로 **`sys`와 `system` 계정을 갖는다.**

`sys` 계정이 `system` 보다 상위의 권한을 가지고 있는 계정으로, 주로 **DBA**에 의해 사용된다.

`system` 계정은 보통 **개발자**들이 접근하는 계정으로, 비밀번호는 기본값이 `oracle`이다.

### 사용자 확인

#### 현재 사용중인 사용자

```sql
SHOW 사용자;
```

위와 같이 사용하게 되면, 현재 어떤 사용자로 접근하고 있는지 보여준다.

#### DBA 권한으로조회

``` sql
SELECT * FROM dba_user
```

dba의 권한으로 user 테이블의 모든 유저들을 불러오게 된다.



만약 username만 불러오고 싶다면 아래와 같이 하면 된다.

``` sql
SELECT username FROM dba_user;
```

#### DBA가 아닌 권한으로 조회

``` sql
SELECT * FROM all_user;
```

위에서 했던 방법에서 테이블만 바뀌었다.

`*` 대신 `username`을 넣었을 때 username만 불러오는 것 또한 똑같다.

### 사용자 생성

``` sql
CREATE USER 유저이름 IDENTIFIED BY 비밀번호
DEFAULT TABLESPACE users
TEMPORARY TABLESPACCE temp;
```

> **유저이름은 모두 대문자로 저장된다.**

이러한 방식으로 만들게 되면 유저를 만들게 된다.

하지만 어떠한 권한도 없기 때문에 resource 접근이 불가능하고, 애초에 ORACLE에 접근이 불가능하기 때문에 아래와 같이 권한을 주어야 한다.

``` sql
GRANT connect, resource, dba TO dsm;
```

connect와 resource 권한은 각각 연결, 소스 접근을 위한 권한이기 때문에 필수적으로 주어야 한다.

그리고 dba는 dba와 같은 수준의 권한을 준다는 의미로, 공부할 때에는 어차피 DBA 계정을 사용하면 되기 때문에 dba 권한을 준다.



추가 후에는 아래와 같이 확인할 수 있다.

```sql
SELECT * FROM DBA_USERS WHERE USERNAME = 'DSM';
```

이렇게 하면 모든 유저중, username이 DSM인 user를 찾게 된다.

이제 DB CONNECTION을 만들 때 해당 user로 저장하면 잘 작동한다.