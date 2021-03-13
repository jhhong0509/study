# 1장 오브젝트와 의존관계

### DAO

> DAO란 DB와 연결되어 데이터 조회, 조작의 기능을 전담하도록 만든 객체이다.

우선 가장 기본적인 User라는 이름의 DAO를 만들어 준다.

``` java
@Getter
@Setter
public class User {
    String id;
    String name;
    String password;
}
```

또한, Database에서도 위와 같은 형태의 테이블을 만들어 준다.

| 필드     | 타입        | 설정     |
| -------- | ----------- | -------- |
| id       | varchar(10) | PK       |
| name     | varchar(20) | Not Null |
| password | varchar(20) | Not Null |

- 자바빈

> 자바빈이란 원래 비주얼 툴에서 조작 가능한 컴퍼넌트를 이야기 한다.
>
> 자바의 주력 개발 플랫폼이 웹 기반의 엔터프라이즈 방식으로 바뀌어서 비주얼 컴퍼넌트로서의 자바빈은 인기를 잃었다.
>
> 하지만 자바빈의 코딩 관례는 JSP 빈을 통해 자바빈 스타일의 오브젝트를 사용하는 기술이 이어져 왔고, 이제는 자바빈이라 하면 두가지 관례를 따른 오브젝트를 가리킨다.(빈 이라고도 부름)
>
> - 디폴트 생성자
>
>   > 자바빈은 디폴트 생성자를 가지고 있어야 한다. 프레임워크에서 리플렉션을 통해 오브젝트를 생성하기 때문
>
> - 프로퍼티
>
>   > 자바빈이 노출하는 이름을 가진 속성을 프로퍼티 라고 한다.
>   >
>   > getter와 setter로 수정/조회 할 수 있다.
>   >
>   > instance 변수와 getter, setter로 이루어져 있다.

### JDBC

> JDBC의 작업 순서
>
> 1. DB 연결을 위한 Connection을 가져온다.
> 2. SQL을 담은 Statement를 만든다.
> 3. 만들어진 Statement를 실행한다.
> 4. 조회일 경우 쿼리 결과를 ResultSet으로 받아서 정보를 저장할 오브젝트(DAO)에 옮겨준다.
> 5. 생성된 Connection, Statement, ResultSet은 작업 후 반드시 닫아준다.
> 6. JDBC API가 만들어낸 exception을 직접 처리하거나, 메소드에 throws를 선언하면 메소드 밖으로 던진다.
>    - 일반적으로 예외는 모두 메소드 밖으로 던지는게 편하다.

- JDBC를 통해 생성/조회 하기

``` java
public class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection(
        		"jdbc:mysql://localhost/dbname", "id", "pwd");
        PreparedStatement ps = c.prepareStatement(
        	"insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());								// 첫번째 ?에 값을 넣어준다.
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        
        ps.executeUpdate();											// 만들어진 쿼리를 실행한다.
        
        ps.close();
        c.close();													// jdbc 사용중에 생긴 리소스는 반드시 닫아준다.
    }
    
    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");						// jdbc 드라이버를 mysql로 설정해줌
        Connection c = DriverManager.getConnection(
        		"jdbc:mysql://localhost/dbname", "id", "pwd");
        
        PreparedStatement ps = c.prepareStatement(
        	"select * from users where id = ?");
        ps.setString(1, id);
        
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setId(rs.getString("name"));
        user.setId(rs.getString("password"));
        
        rs.close();
       	ps.close();
        c.close();
        
        return user;
    }
}
```

위 코드를 테스트하기 위해서 가장 간단한 방법은 웹 애플리케이션을 만들어 서버에 배치하고 웹 브라우저를 통해 테스트하는 것이다.

하지만 이런 방법은 너무 부담이 크게 때문에, main 클래스를 통해 테스트코드를 짜준다.

- main의 테스트코드

``` java
public Class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserDao dao = new UserDao();
        
        User user = new User();
        user.setId("aaa");
        user.setName("이름");
        user.setPassword("비번");
        
        dao.add(user);						// add 메소드 실행(DB에 저장)
        
        System.out.println(user.getId() + "등록 성공");
        
        User user2 = dao.get(user.getId());	// 아이디를 통해 user 검색
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        System.out.println(user2.getId());
    }
}
```

> 만약 위 메소드를 실행했을 때 성공한다면 성공 메세지를 얻을 수 있을 것이고, 실패한다면 사용할 DB의 드라이버가 클래스패스에 있는지, 드라이버 버전은 맞는지 확인해 봐야 한다.

### 코드 개선 - 메소드 추출

> 사실 위에서 짠 코드는 욕먹어도 할 말 없을 정도로 개판으로 짠 코드로, 좋은 코드와 교집합이 전혀 없는 코드이다.

지금부터는 위 코드를 객체지향 원리에 맞는 코드로 고쳐나갈 것이다.

- 객체지향이 원하는 코드

  객체지향 코드에서는 설계와 그를 구현할 코드는 애플리케이션이 폐기처분될 때 까지 끊임없이 변화하고, 요구사항 또한 끊임없이 바뀌고 발전한다.

  그렇기 때문에 개발자는 객체를 설계할 때 나중에 변화에 대비가 잘 되어 있는지 생각해 보아야 한다.

  우리가 객체지향이 기존의 절차지향보다 더 번거로운 작업을 요구함에도 이용하는 이유는 객체 지향이 주는 변화에 대처하기 쉽다 라는 장점 때문이다.

  변화에 대처하기 위해서는 분리와 확장을 고려하여 설계해야 한다.

  만약 DB의 비밀번호를 바꾼다고 생각해 보자. 만약 각 DAO마다 DB 접속을 따로 하게 된다면, 모든 DAO에 가서 비밀번호를 일일히 수정해 줘야 한다.

  그런데 만약 하나라도 수정이 되지 않는다면 오류가 발생하게 되는데, 이렇게 작은 변화만으로 잘 되던 코드가 안되는 상황이 일어나게 된다.

  프로그래밍의 개념중, 관심사의 분리 라는 것이 있다. 이를 객체지향에 적용하면 비슷한 성질의 객체들 끼리 묶어주고, 다른 객체들은 따로 두어서 서로 영향을 주지 않도록 하는 것이다.

- 코드의 문제점
  - DB 커넥션을 생성하는 코드가 DAO마다 계속해서 중복될 것이다.

- 코드의 개선

  > 위 클래스에서 우리는 DB 커넥션 생성하는 코드가 반복되는 것을 찾을 수 있었는데, 이렇게 반복되는 코드는 좋지 않기 때문에 고칠 필요가 있다.

``` java
public class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(
        	"insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        
        ps.executeUpdate();
        
        ps.close();
        c.close();
    }
    
    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        
        PreparedStatement ps = c.prepareStatement(
        	"select * from users where id = ?");
        ps.setString(1, id);
        
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setId(rs.getString("name"));
        user.setId(rs.getString("password"));
        
        rs.close();
       	ps.close();
        c.close();
        
        return user;
    }
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection(
        		"jdbc:mysql://localhost/dbname", "id", "pwd");
        return c;
    }
}
```

> 단순히 connection을 생성하는 코드를 메소드로 만든것 뿐이다.

- 위 코드의 변화에서 얻은 장점
  - 관심 내용이 독립되어 있으므로 수정이 간단해 졌다.
  - 코드의 수정이 필요할 때 해당 메소드만 수정하면 된다.
  - 다른 관심이 없는 메소드에게 영향을 주지 않는다.

위에서 우리가 한 작업은 리팩토링 이라고 하고, 리팩토링 중에서도 메소드 추출 이라고 한다.

> 리팩토링이란 기존의 코드를 외부 동작 방식에는 변화 없이 내부 구조만 변경해서 재구성하는 작업을 말한다.
>
> 즉, 로직은 변하지 않아도 메소드를 따로 추출하는 등 내부 코드만 바뀌는걸 말한다.
>
> - 리팩토링의 장점
>   - 코드 이해가 쉬워짐
>   - 변화에 대한 효율적 대응
>   - 코드의 품질 증가
>   - 유지보수 용이

### 코드 개선 - 상속을 통한 확장

``` java
public abstract class UserDao {
	.
	.
	.
	.
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
	
}
```

위와 같은 방법을 사용하면, connection을 만드는 메소드는 사용자가 직접 아래와 같이 수정할 수 있다.

``` java
public class AUserDao extends UserDao {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // 오라클의 connection 생성 코드
    }
}

public class BUserDao extends UserDao {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // MariaDB의 connection 생성 코드
    }
}
```