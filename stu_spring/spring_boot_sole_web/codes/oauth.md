# Spring boot 에서 사용하는 OAuth2.0

### OAuth 1.5와 2.0의 차이

- 버전 설정이 훨씬 단순화 되었다.
- OAuth 1.5에서 직접 입력했던 많은 값들을 OAuth 2.0에서는 기본적으로 지원한다.
- OAuth2.0에서는 구글, 깃허브, 페이스북, 옥타의 설정값을 지원하기 때문에 훨씬 간편하다.

#### OAuth 1.5의 코드

``` yml
google:  
  client:  
    clientId: 인증정보  
    clientSecret: 인증정보  
    accessTokenUrl: {url}  
    userAuthorizationUrl: {url}  
    clientAuthenticationScheme: form  
    scope: email, profile  
  resource:  
    userInfoUrl: {url}
```

#### OAuth 2.0에서의 코드

``` yml
spring:  
  security:  
    oauth2:  
      client:  
        clientId: 인증정보  
        clientSecret: 인증정보
```

### 구글, 네이버 OAuth 등록

- 서비스를 시작하기 위해서는 우선 인증 정보를 발급받아야 한다.
- 구글
  - 해당 <a href="https://console.cloud.google.com">주소</a>로 이동한다.
  - 프로젝트 선택 탭을 클릭한다.
  - 새 프로젝트 버튼을 누른다.
  - 프로젝트 이름
    - 다른 이름과 관련 없이 자유로운 이름
  - 해당 프로젝트로 이동한다.
  - 왼쪽 위의 메뉴 탭을 누른다.
  - API및 서비스 카테고리에 마우스를 올려둔다.
  - 사용자 인증 정보 를 클릭한다.
  - 사용자 인증 정보 만들기를 눌러준다.
  - OAuth 클라이언트 ID를 선택해 준다.
  - 동의 화면 구성을 눌러준다.
    - 애플리케이션 이름을 적어준다.
      - 구글 로그인 시에, 사용자가 보게 될 이름
      - 이 역시 그냥 이름이니 맘대로 지어도 된다.
    - 지원 이메일
      - 사용자 동의 화면에서 노출될 이메일
      - 주로 help 이메일같은걸 사용한다.
      - 하지만 여기서는 그냥 본인의 이메일을 적으면 된다.
    - Google API의 범위
      - 구글 서비스에서 사용할 범위의 목록
      - 구글 서비스에서 정보를 가져올 때, 어떤것들을 가져올지 여부
  - 모든 설정이 끝났다면 OAuth 클라이언트 ID 만들기 화면으로 이동한다.
  - 애플리케이션 유형을 웹 애플리케이션으로 설정해 준다.
  - 승인된 리디렉션 URI
    - 서비스에서 인증을 성공 했을 때 리다이렉트할 URL
    - 스프링 부트2 시큐리티에서는 /login/oauth2/code/{소셜 서비스 코드}를 지운하고 있다.
      - 해당 URL은 시큐리티에서 규현되어 있다.
    - 아직 개발중이니 http://localhost:8080/login/oauth2/code/google 로 등록해 준다.
- 네이버
  - 해당 <a href="https://developers.naver.com/apps/#/register?api=nvlogin">주소</a>로 이동해 준다.
  - 빈칸들을 채운다.
    - 애플리케이션 이름
      - 그냥 이름
    - 사용 API
      - 네이버 아이디로 로그인
    - 제공 정보 선택
      - 이름, 이메일, 프로필 사진을 필수로 가져온다.
      - 우리가 네이버로 부터 받아올 정보를 의미한다.
    - 환경
      - PC 웹
    - 서비스 URL
      - 현재 개발 단계 이기 때문에 https://localhost:8080/ 을 적어준다.
    - Callback URL
      - 구글의 리다이렉션 URL과 같다.
      - http://localhost:8080/login/oauth2/code/naver 로 등록해 준다.

### OAuth 2.0 구현

``` java
spring-security-oauth2-autoconfigure
```

- 우리가 OAuth 1.5에서 OAuth 2.0으로 넘어오게 되면서, 연동 방법이 크게 변경되었다.
- 하지만,  위 라이브러리를 사용함으로써, 1.5의 방식을 가져올 수 있다.
  - 많은 개발자가 기존의 방식을 추구하기 때문에 해당 방식을 이용한다
- 우리가 공부할 방법은 위 코드를 사용하지 않고, OAuth 2.0을 직접 사용할 예정이다.
  - 스프링 팀에서 OAuth 1.5는 버그 수정 정도만 할 예정이고, 신규 기능은 OAuth 2.0에서만 지원할 것이라고 선언했다.
  - 스프링 부트용 라이브러리가 출시되었다.
  - 확장이 간편하다

#### yml 또는 properties 파일 구현

- yml

``` yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: 클라이언트 아이디
            client-secret: 클라이언트 시크릿
            scope:
              - email
              - profile
```

- properties

``` properties
spring.security.oauth2.client.registration.google.client-id=값
spring.security.oauth2.client.registration.google.client-secret=값
spring.security.oauth2.client.registration.google.scope=profile,email
```

- scope의 의미
  - 원래 기본값은 openid, email, profile이다.
  - 하지만 openid가 scope의 값으로 들어가면 Open Id Provider로 인식하게 된다.
  - 그렇게 된다면 openid 서비스를 지원하는 구글과 같은 서비스와, 그렇지 않은 네이버와 카카오 등의 서비스를 나눠서 따로 만들어 줘야 한다.
  - 그렇기 때문에 강제로 openid를 제외한 email과 profile값만 넣어준다.

#### security config 구현

``` java
complie('org.springframework.boot::spring-boot-starter-oauth2-client')
```

- 소셜 기능 구현시 꼭 필요한 의존성이다.

- SecurityConfig 전체 코드

``` java
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable().headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

    }
}
```

##### 코드 설명

```java
@EnableWebSecurity
```

- Spring Security 설정들을 활성화 시켜 준다.

``` java
http.csrf().disable().headers().frameOptions().disable()
```

- Spring Security에서, h2의 접근을 차단하는것을 방지하기 위해 해줘야 한다.

``` java
.authorizeRequests()
```

- 권한 관리 옵션의 시작점이다.

```java
.antMatchers("url1","url2")
```

- andMatchers가 아니라 antMatchers다.
- URL, HTTP 메소드 별로 관리할 수 있다.
- .permitAll()을 통해, 권한 확인 없이 모두가 이용 가능하도록 할 수 있다.
- .hasRole()을 통해, 해당 권한이 있는 사람들만 접근할 수 있도록 할 수 있다.

```java
.anyRequest()
```

- 따로 정의해주지 않은 모든 요청들을 나타낸다.
- .authenticated()를 통해 인증된 사용자만 이용하게 할수도 있고, .permitAll()을 통해 모두 공개시킬 수 있다.

``` java
.logout().logoutSuccessUrl("/")
```

- 로그아웃에 성공했을 때, / 주소로 이동한다.
- 로그아웃 설정의 시작점이다.

``` java
.oauth2Login()
```

- OAuth2에서 지원하는 로그인 기능의 설정 시작점이다.

``` java
.userInfoEndpoint()
```

- 로그인 성공시 사용자 정보를 가져올 때 설정을 담당한다.

``` java
.userService(customOAuth2UserService)
```

- 소셜 로그인에 성공했을 때, UserService에서 후속 조치를 진행한다.
  - 해당 클래스는 직접 구현해 줘야 한다.

#### User 구현

- ENUM
  - spring security에서 권한을 나타내는 ENUM은 항상 ROLE_GUEST처럼 ROLE_를 붙여야 한다.
- Repository
  - 꼭 PK가 아닌, email처럼 unique한 값을 PK처럼 사용해도 된다.

#### CustomOAuth2UserService 구현

- 위 코드 분석에서 말했듯이, 소셜 로그인 성공 시에 할 조치이다.

``` java
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
```

##### 코드 해석

```java
OAuth2User oAuth2User = delegate.loadUser(userRequest);
```

- 요청중 유저 정보만 추출하는걸로 추정된다..

``` java
String registrationId = userRequest.getClientRegistration().getRegistrationId();
```

- request에서 어떤 요청인지 추출해 준다.
  - 구글인지, 네이버 인지 등 어떤 소셜 로그인을 이용했는지에 대한 정보

``` java
String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
```

- 구글은 유니크값의 필드 이름이 sub 이고, 네이버는 id가 필드 이름 이므로, 필드를 찾을 때 필요하다.

``` java
OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
```

- OAuth2User 클래스의 attribute들을 담을 클래스
- 다른 소셜 로그인도 이 클래스를 이용한다.
- 우리가 직접 정의할 클래스 이다.

``` java
httpSession.setAttribute("user", new SessionUser(user));
```

- 사용자 정보를 세션에 저장하기 위한 DTO
- User 클래스를 사용하면, 직렬화 문제가 발생하기 때문에 SessionUser를 만들었다.
  - DB와 직접적으로 연결되는 엔티티는, 직렬화시에 자식 엔티티를 가질 수 있기 때문에 성능 이슈가 발생할 수 있다.

``` java
private User saveOrUpdate(OAuthAttributes attributes){
    User user = userRepository.findByEmail(attributes.getEmail())
            .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
            .orElse(attributes.toEntity());
    return userRepository.save(user);
}
```

- 요청 속성중, email을 통해 user 에서 찾는다.
- 만약 user에서 값을 찾을 수 없다면, attributes 값을 통해 엔티티를 생성한다.
- 만약 찾았다면, 이름과 사진을 새로 업데이트 한다.
- 위 과정을 거친 엔티티는 저장된다.

#### OAuthAttributes

``` java
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        System.out.println("registration="+registrationId);
        return ofGoogle(userNameAttributeName, attributes);
    }
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
```

- toEntity
  - User 엔티티를 생성한다.
  - 처음 가입할 때 생성된다.
  - 기본 권한은 GUEST 이기 때문에, role에는 GUEST값을 넣는다.

``` java
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
```

- 이름, 이메일, 사진만 필요함으로 필드에 name, email, picture만 선언해 준다.

### 어노테이션 기반으로 개선하기

#### LoginUser

``` java
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
```

- 커스텀 어노테이션 이다.
- config.user 패키지에 들어간다.
- @Target()
  - 이 어노테이션을 적용시킬 위치
  - 범위 종류
    - PACKAGE
      - 패키지 선언할 때
    - TYPE
      - 클래스, 인터페이스, ENUM 선언할 때
    - CONSTRUCTOR
      - 생성자 선언할 때
    - FIELD
      - 멤버 변수 선언할 때
    - METHOD
      - 메소드 선언할 때
    - ANNOTATION_TYPE
      - 어노테이션 타입 선언할 때
    - LOCAL_VARIABLE
      - 지역변수 선언할 때
    - PARAMETER
      - 파라미터 선언할 때
    - TYPE_PARAMETER
      - 파라미터 타입 선언할 때
- @Retention()
  - 해당 어노테이션이 어디까지 유효할지 선언해 주는 것.
  - 유효 범위 종류
    - SOURCE
    - CLASS
    - RUNTIME
- @interface
  - 이 파일을 어노테이션 클래스로 지정한다.
  - 해당 이름(LoginUser)을 가진 어노테이션이 생성된다.

#### LoginUserArgumentResolver

``` java
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    
    private final HttpSession httpSession;
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }
    
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
    
}
```

##### 코드 해석

``` java
@Component
```

- 개발자가 작성한 클래스를 Bean으로 등록하기 위한 어노테이션
- @Component vs @Bean
  - @Component는 말했듯이, 개발자가 직접 작성한 클래스를 Bean에 등록한다.
  - @Bean은 개발자가 직접적으로 제어가 불가능한 외부 라이브러리 같은 것 들을 Bean에 등록한다.

``` java
implements HandlerMethodArgumentResolver
```

- 해당 클래스는 HandlerMethodArgumentResolver 라는 인터페이스를 구현한 클래스 이다.

  - ```java
    boolean supportsParameter(MethodParameter var1)
    ```

    - 쉽게 말해, 해당 resolver가 이걸 하려고 만들어 진건지 여부를 검사한다.
    - 이 클래스 에서는, @LoginUser 어노테이션이 붙어있고 파라미터 클래스 타입이 SessionUser.class 인 경우에만 true를 반환한다.

  - ```java
    Object resolveArgument(MethodParameter var1, @Nullable ModelAndViewContainer var2, NativeWebRequest var3, @Nullable WebDataBinderFactory var4) throws Exception
    ```

    - 실제 객체를 리턴한다.

    - 여기서는 세션에서 객체를 가져와서 반환해 준다.

- HandlerMethodArgumentResolver 은 한가지 기능을 지원하는데, 조건이 맞을 경우 메소드가 있다면 해당 인터페이스의 구현체가 지정한 값을 파라미터로 넘길 수 있다.

``` java
private final HttpSession httpSession;
```

- 우리가 반환할 값이 세션에서 값을 꺼내오는 것이기 때문에, HttpSession이 필요하다.
- 장점
  - 상태값의 종류, 크기, 개수에 제한이 없다.
  - 보안상 유리하다
- 단점
  - 서버에 무리가 갈 수 있다.

- 메소드
  - setAttribute(키, 값)
    - 키는 나중에 해당 세션을 불러오기 위한 이름
    - 값은 자료형을 예측할 수 없어서 Object이다.
  - getAttribute(키)
    - 키를 통해 값을 찾는다.
    - 키로 값을 찾았는데, 없다면 null이 반환된다.
  - getSession(boolean)
    - true
      - 세션이 있다면 돌려주고,없다면 생성한다.
    - false
      - 세션이 있다면 돌려주고, 없으면 null을 돌려준다.
  - getCreationTime()
    - 해당 세션이 생성된 시간
    - mile second 단위
  - getLastAccessedTime()
    - 마지막 세션 시간
  - setMaxInactiveInterval(int second)
    - 해당 시간(초) 동안 클라이언트에서 요청이 없다면 세션이 만료된다.
  - getMaxInactiveInterval()
    - 세션 만료 시간을 가져온다.
  - invalidate()
    - 세션 종료
    - 유효하지 않아짐
  - getId()
    - 처음 요청했을 때 생긴 세션의 아이디를 문자열로 반환

``` java
parameter.getParameterAnnotation(LoginUser.class)
```

- 해당 파라미터의 어노테이션이 LoginUser.class인지 확인해주는 역할

``` java
SessionUser.class.equals(parameter.getParameterType())
```

- 파라미터의 타입이 세션유저인지 확인해주는 역할

``` java
httpSession.getAttribute("user")
```

- user 라는 이름의 세션 값을 가져온다.

#### WebConfig

- 방금 만들었던 resolver를, 스프링에서 인식하도록 만들어야 한다.
- 그러기 위해서 WebMvcConfigurer에 추가해야 한다.

``` java
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
```

``` java
implements WebMvcConfigurer
```

- 기존 세팅에 사용자의 세팅을 추가할 수 있도록 해준다.

``` java
argumentResolvers.add(loginUserArgumentResolver)
```

- argumentResolvers 에 만들었던 loginuserArgumentResolver를 추가시켜 준다.

#### 기존의 컨트롤러 수정

``` java
(User)httpSession.getAttribute("user")
```

``` java
@LoginUser SessionUser user
```

- 위와 같은 방법으로 가져오던 세션 값이 아래와 같이 개선되었다.
- 이러한 방식은 어느 컨트롤러에서든지 사용할 수 있다.

#### 세션 저장소 개선하기

- 기존의 세션은 WAS(내장 톰캣)에 저장되고, 호출된다.
- 하지만 내장 톰캣의 경우 배포를 할때마다 초기화 된다.
- 또한, 2대 이상의 서버를 사용하고 있다면, 톰캣마다 세션 동기화 설정을 해야 한다.
- 해결법
  - 그냥 톰캣 세션을 사용한다.
    - 기본적인 방식
    - 2대 이상의 WAS가 구동될 때엔, 톰캣들간의 세션 공유를 위한 설정이 필요하다.
  - 데이터베이스를 세션 저장소로 사용한다.
    - 가장 쉬운 방법이다.
    - 로그인 요청마다 DB에 IO가 발생하기 때문에, 성능 이슈가 발생할 수 있다.
    - 우리가 사용할 방법이다.
    - 비용 절감을 위해서 이다.
  - Redis, Memcached와 같은 메모리 DB를 사용한다.
    - 실제 서비스에서는 Embedded Redis와 달리, 외부 메모리 서버를 사용해야 한다.

##### 구현

- build. gradle에 의존성을 추가해 준다.

``` java
compile('org.springframework.session:spring-session-jdbc')
```

- application.properties에서 세션 저장소를 jdbc로 이용하도록 한다.

``` yaml
spring:
  session:
    store-type: jdbc
```

``` properties
spring.session.store-type=jdbc
```

- 아직은 계속해서 초기화 된다.
  - H2를 이용하고 있는데, H2가 재시작 되기 때문
- 실제 배포에선 RDS를 이용할 것이기 때문에 문제가 되지 않음

### 네이버 로그인 구현

#### properties 또는 yml 구현

- 기존에 CommonOAuth2Provider에서 설정해주던 값들을 모두 수동으로 입력해 줘야 한다.

``` yaml
spring:
  security:
    oauth2:
      client:
        registration:
          naver:
            client-id: 클라이언트 아이디
            client-secret: 클라이언트 시크릿키
            redirect-uri: {baseUrl}/{action}/oauth2/code/{registrationId}
            authorization_grant_type: authorization_code
            scope: name, email, profile_image
            client-name: Naver
        provider:
          naver:
            authorization_uri: https://nid.naver.com/oauth2,0/authorize
            token_uri: https://nid.naver.com/oauth2.0/token
            user-info-url: https://openapi.naver.com/v1/nid/me
            user_name_attribute: response
```

- user_name_attribute가 response인 이유는 회원조회시 JSON 형태로 반환되기 때문이다.

#### 기존의 OAuthAttributes 추가

``` java
return ofGoogle(userNameAttributeName, attributes);
```

``` java
if("naver".equals(registrationId)) {
    return ofNaver("id",attributes);
}
return ofGoogle(userNameAttributeName, attributes);
```

- 위와 같던 코드에서 그냥 if로 naver라면 naver를 호출해 준다.

``` java
private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
    Map<String,Object> response = (Map<String, Object>) attributes.get("response");
    
    return OAuthAttributes.builder()
        .name((String) response.get("name"))
        .email((String) response.get("email"))
        .picture((String) response.get("profile_image"))
        .attributes(response)
        .nameAttributeKey(userNameAttributeName)
        .build();
}
```

#### OAuth 테스트 적용하기

##### OAuth properties 파일 못가져 오는 오류

- 기존의 테스트들은 작동하지 않는다.
- 설정값들을 가져올 수 없기 때문이다.
- test에 application.properties 파일이 없다면 main에서 가져오게 되는데, 정말 application.properties 만을 가져오기 때문에, application-oauth.properties 파일은 가져오지 않기 때문이다.
- 그래서 테스트를 위한 가짜 설정값들을 넣어주면 된다.

``` yaml
spring:
  jpa:
    show_sql: true
  properties:
    hibernate:
      dialect: org.hibernate.dialect.MySQL5InnoDBDialect
  h2:
    console:
     enabled: true
  session:
    store-type: jdbc
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: test
            client-secret: test
            scope: profile, email
```

##### 사용자 인증 오류

- 인증되지 않은 사용자의 요청은 리다이렉션 시키기 때문에, 임의로 인증된 사용자를 만들어줘야 한다.

- 그러기 위해서 spring에서 공식적으로 지원하는 방법을 사용한다.
- 우선 build.gradle에 다음 코드를 추가한다.

``` java
testCompile("org.springframework.security:spring-security-test")
```

- 또한, @Test 어노테이션 밑에 다음 코드를 추가한다.

``` java
@WithMockUser(roles="USER")
```

- 이 테스트는, USER의 권한을 가진 사용자의 테스트로 인식한다.

- 하지만 아직 SpringBootTest를 통해 테스트를 하고 있고, MockMvc를 사용하지 않기 때문에 작동하지 않는다.
- 그렇기 때문에 다음을 테스트코드에 추가한다.

``` java
@Before
public void setup() {
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
}
```

- 위 코드는 매 테스트의 시작 전에 실행된다.
- MockMvc 인스턴스를 생성해 준다.

##### CustomOAuth2UserService 스캔 오류

- @WebMvcTest가 스캔하는 것
  - WebSecurityConfigurerAdapter
  - WebMvcConfigurer
  - @ControllerAdvice
  - @Controller
- 즉, @Repository, @Service, @Component를 스캔하지 않는다.
- 그렇기 때문에 SecurityConfig를 스캔했지만, SecurityConfig에 필요한 CustomOAuth2UserService를 읽어오지 못해서 발생한 문제이다.
- 그러므로, SecurityConfig를 스캔하지 않도록 한다.

``` java
@WebMvcTest(controllers = HelloController.class,
           excludeFilters = {
               @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
           })
```

- 별로 추천되지 않는 방식이다.

##### @EnableJpaAuditing 에러

- @EnableJpaAuditing 어노테이션은 최소한 하나의 @Entity 클래스가 필요하다.
- 하지만 @WebMvcTest에는 존재하지 않는다.
- @EnableJpaAuditing을 @SpringBootApplication과 함께 뒀기 때문에 @WebMvcTest가 스캔하게 된다.
- 그러므로, @EnableJpaAuditing을 분리시켜줘야 한다.
- 그리고 JpaConfig를 만들어 준다.

``` java
@Configuration
@EnableJpaAuditing
public class JpaConfig {}
```