# 토큰 검증

### 문제 발생

프로젝트 개발 도중 다른 언어에서 발급한 토큰을 검증할 일이 있었다.

하지만 계속해서 토큰을 파싱하는 도중 INVALID TOKEN Exception이 발생했다.

### 발생 이유

아래와 같은 reason이 출력됐다.

`JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.`

위를 보면, signature가 맞지 않다는 의미이다.

### 해결 과정

우선 JWT 토큰을 발급하는 쪽의 문제인가 싶어서 Python 코드를 봤는데, 아무 이상 없이 잘 되어 있었다.

jwt.io 사이트에서 secret을 바꾸고 base64 encoded 라는 체크를 한 후에 해당 토큰으로 시도해보니 잘 된다.

그래서 python 쪽에서 secret key가 base64 인코딩이 안된건가 싶었지만, **python은 토큰 생성시 secretKey를 base64로 인코딩 한다.**

### 해결 방법

spring boot쪽에서 다음과 같이 **secretKey를 base64로 인코딩 한다.**

``` java
Base64.getEncoder().encodeToString(secretKey)
```

전체 파싱 코드를 보면 다음과 같다.

``` java
Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey)).parseClaimsJws(token).getBody().getSubject();
```

이렇게 secretKey는 base64로 인코딩 되어야 하는데, 토큰 파싱 뿐만 아니라 토큰 발급 시에도 사용되어야  한다.

기존에는 secretKey로 혼자서 토큰 발급/파싱을 했기 때문에 문제가 발생하지 않았지만, **다른 언어와 토큰을 공유할 때엔 꼭 해주어야 한다.**

> 즉 우리는 평소에 secretKey를 base64 encoding을 안하고 사용한 것이다.

### 후기

토큰 secretKey를 base64 인코딩 하라는 이야기는 처음 들어봐서 당황했지만, 앞으로의 프로젝트에서는 꼭 base64 encoding을 한 secret key를 사용해야 겠다.

### 주의

`Base64.getEncoder()`에서 `Base64.getEncoder().encode()`가 아니라 꼭 `Base64.getEncoder().encodeToString()`을 사용해야 한다.