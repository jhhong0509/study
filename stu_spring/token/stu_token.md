# 토큰 공부하며 나온 자잘한 지식
### jwt 토큰의 방식
#### 토큰 검증 과정
1. 로그인에 성공했을 때, refresh토큰과 access토큰을 생성해 준다.
2. 서버에서 사용자에게 토큰들을 넘겨준다.
3. api를 요청할 때, 토큰을 함께 보내준다.
4. Access토큰이 유효한지 검사한다.
5. 만약 유효하다면 값을 반환해 준다.
6. api를 계속해서 호출하는 도중 access토큰이 만료되어 유효하지 않게 된다.
7. 서버에서는 access토큰이 유효하지 않다는 내용을 반환한다.
8. 사용자는 access토큰과 refresh토큰을 서버에 넘기며 access토큰의 발급을 요청한다.
9. refresh 토큰이 유효한지 검사한다.
10. 만약 유효하다면 새로운 access토큰을 발급해 준다.
11. 유효하지 않다면 새로 로그인 해야 한다.
#### access토큰과 refresh토큰을 나누는 이유
- 만약 access토큰이 탈취되었을 때, 유효기간이 긴 것 보다 짧은 것이 더 보안에 좋다.
  - access토큰을 탈취했더라도 유효시간이 30분이라면 30분 후에는 사용할 수 없기 때문인 것 같다.
- 하지만 access토큰을 새로 발급받기 위해서는 새로 로그인을 해야 한다는 번거로움이 있다.
- 그래서 refresh토큰을 만들어 번거로움을 줄였다.(refresh토큰에는 access토큰과 동일한 값이 저장되어 있다.)
  - access토큰이 만료되었어도, refresh 토큰을 통해 다시 발급받을 수 있기 때문에, 사용자의 입장에서는 refresh토큰의 유효 기간동안 api를 호출할 수 있다.
  - refresh토큰 또한 탈취될 수 있는지만 access 토큰에 비해 전달되는 비율이 훨씬 낮아 탈취될 가능성이 낮다.
  - refresh토큰이 전달되는 상황
    - 로그인 할때
    - access토큰이 유효하지 않을 때
### 중요한 값 보안화
- 아이디나 비밀번호 같은 경우에는 깃허브 같은 곳에 올리면 문제가 생길 수 있다.
- 그래서 서버의 환경변수에 값을 넣는 방법을 사용한다.
``` java
@Value("{auth.jwt.secret}")
private String secretKey;
```
- 위와 같은 형태는 .yml 파일의 auth 밑의 jwt 밑의 secret의 값을 가져오는 것이다.
- 그래서 yml 파일을 따라가면
``` yml
auth:
  jwt:
    secret: ${JWT_SECRET}
    exp:
      access: ${JWT_ACCESS_TOKEN_EXP}
      refresh: ${JWT_REFRESH_TOKEN_EXP}
    header: ${JWT_HEADER}
    prefix: ${JWT_PREFIX}
```
- 위와 같은 형태가 있을 것이다.
- jwt
  - secret
    - 키를 정한다. 이건 탈취되면 안되기 때문에 조심해야 한다.
    - exp
      - exp란 대충 유효기간이란 뜻이다
      - access
        - access토큰의 유효기간를 정한다(초단위). 용성선배는 2시간정도로 한다고 한다.
      - refresh
        - refresh토큰의 유효기간를 정한다(초단위). 용성선배는 2주정도로 한다고 한다.
### 파일 구현
#### AuthenticationFacade
``` java
@Component
public class AuthenticationFacade {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUserEmail() {
        return this.getAuthentication().getName();
    }

    public boolean isLogin() {
        return getAuthentication() != null;
    }
}
```
- getAuthentication() 은 토큰 값을 반환한다.
- getUserEmail()
  - getAuthentication을 호출해서 토큰을 가져온 후, 내가 암호화 한 값을 가져온다.
#### JwtTokenProvider
``` java
public String generateAccessToken(String email) {
    return Jwts.builder()
            .setIssuedAt(new Date())
            .setSubject(email)
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
            .claim("type", "access_token")
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }
```
- setIssuedAt(날짜)는 생성일을 넣는 것이다.
- setSubject(문자)는 암호화할 문자열을 알려준다.
- setExpiration(날짜)는 이걸 파기할 날짜를 정한다.
- claim()은 이 토큰의 유형을 정한다.
- signWith(암호화 종류, 키)는 어떤 공개키를 통해 암호화 할 것인지, 어떤 규칙을 가지고 있는지 정한다.
``` java
public String generateRefreshToken(String email) {
    return Jwts.builder()
            .setIssuedAt(new Date())
            .setSubject(email)
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000))
            .claim("type", "refresh_token")
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
}
```
- refresh토큰 또한 access토큰과 동일한 구조로 만들어진다.
``` java
public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(header);
    if (bearerToken != null && bearerToken.startsWith(prefix)) {
        return bearerToken.substring(7);
    }
    return null;
}
```
- 기본적으로 토큰 앞에는 Bearer이라는 단어가 들어가기 때문에, 그 값으로 시작하는 토큰이라면 Bearer을 제외한 실제 토큰을 반환한다.
``` java
public String getEmail(String token) {
    try {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    } catch (Exception e) {
        throw new InvalidTokenException();
    }
}
```
- secretKey를 통해 암호화된 문자열을 다시 복호화 해서 반환해준다.
``` java
public boolean validateToken(String token) {
    try {
        Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getSubject();
        return true;
    } catch (Exception e) {
        throw new InvalidTokenException();
    }
}
```
- 만약 암호화할때 사용한 문자열을 통해 복호화하지 않았다면, 유효한 토큰임으로 true를 반환한다.
``` java
public boolean isRefreshToken(String token) {
    try {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("type").equals("refresh_token");
    } catch (Exception e) {
        throw new InvalidTokenException();
    }
}
```
- 토큰을 해당 키로 복호화를 하고, 오류가 발생하지 않았다면 타입을 가져오는데, 그게 refresh_token이라면 true를 반환한다.
``` java
public boolean isEmailAuthenticated(String token) {
    AuthDetails authDetails = authDetailsService.loadUserByUsername(getEmail(token));
    return authDetails.getAuthStatus();
}
```
- 토큰의 이메일을 통해 유저를 찾는다
- 해당 유저의 이메일이 이메일 인증을 받은 계정인지 확인한다.
- 만약 이메일 인증을 받지 않았다면 false를 반환하고 인증을 받았다면 true를 반환한다.
``` java
public Authentication getAuthentication(String token) {
    AuthDetails authDetails = authDetailsService.loadUserByUsername(getEmail(token));
    return new UsernamePasswordAuthenticationToken(authDetails, "", authDetails.getAuthorities());
}
```
- 공사예정
#### TokenFilter
```java
@RequiredArgsConstructor
public class TokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateToken(token) && jwtTokenProvider.isEmailAuthenticated(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            throw new InvalidTokenException();
        }
        chain.doFilter(request, response);
    }

}
```
- 실제 스프링 부트 필터에 들어갈 필터를 정의한다.
- http 요청으로 온 토큰을 jwtTokenProvider의 resolveToken을 통해 토큰만 가져온다.
- 만약 토큰이 있고 유효하고 그 이메일이 이메일 인증을 받았는지 검사한다.(이메일 인증은 아마도?)
- 위 조건중 하나라도 충족하지 못하면 유효하지 않은 토큰이기 때문에 Exception을 보낸다.
- 조건을 모두 통과했다면 토큰을 받아서 Authentication에 저장한다.(자세한건 잘 모르겠다)
- 그리고 SecurityContextHolder의 authentication을 방금 받은 auth로 설정한다.
- 자세히 이해하진 못했지만, 결국 request에서 토큰 값을 받고, 그 값에서 토큰 값을 추려서 SecurityContextHolder에 넣어주는 것 같다.(공사 예정)
#### JwtConfigurer
``` java
@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void configure(HttpSecurity httpSecurity) {
        TokenFilter tokenFilter = new TokenFilter(jwtTokenProvider);
        httpSecurity.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
}
```
- TokenFilter은 미리 만들어둔 토큰 필터를 생성한다.
- addFilterBefore이라고 되어있는 부분은 스프링 부트에서 기본적으로 지원하는 필터 이외에, 사용자가 지정한 필터를 추가해주는 역할을 한다.
  - TokenFilter을 스프링 부트의 필터 맨 뒤에 추가하는 것 같다.
### SecurityConfig
``` java
@Bean
@Override
public AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManagerBean();
}
```
- authenticationManagerBean을 bean으로 등록해준다.
- 토큰 관련되어 있는 것 들을 수정할 수 있게 해준다(아마도라서 공사 예정)