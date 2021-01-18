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

``` yml
spring:
	security:
		oauth2:
			client:
				registration:
					google:
						client-id: 클라이언트 아이디
						client-secret: 클라이언트 시크릿
						scope:
							- openid
							- email
							- profile
```

- properties

``` pro
spring.security.oauth2.client.registration.google.client-id=
spring.security.oauth2.client.registration.google.client-secret=
spring.security.oauth2.client.registration.google.scope=profile,email
```