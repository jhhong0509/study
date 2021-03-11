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
    }
}
```

