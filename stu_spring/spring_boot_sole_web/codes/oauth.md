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

- 전체 코드

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
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**", "/profile").permitAll()
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