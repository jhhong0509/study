# JWK

## JWK란?

Json Web Key는 **암호호 Key를 나타내는 Json**으로, Key에 대한 속성을 담고있다.

OAuth처럼 JWT를 사용하는 서비스에서는 JWT를 암호화 할 때 사용했던 Public Key를 제공하는 편이다.

해당 URL에 접근하면 JSON형태의 JWK를 받을 수 있다.

기존의 JWT에서 JWT Token을 검증하려면 모든 Secret을 Client에게 넘겨줘야 했던 것에 반해, Public Key이기 때문에 누가 발행했는지를 보장해 준다.

OAuth처럼 외부 서비스에게 API를 제공해 줄 때에는 Secret을 줄수 없기 때문에 보통 RSA와 같은 비대칭 키 알고리즘을 사용한다.

## JWK의 주요 필드

### kty(key type)

**Required** 타입으로, RSA나 EC가 들어올 수 있다.

### use (Public Key Use)

Public Key의 용도를 나타낸다.

### alg(algorithm)

이 Json Web Key를 통해 만들 토큰이 어떤 암호화 기법을 따를것인지에 대한 명시이다.

### kid(Key ID)

동시 시점에 여러 Key가 존재할수도 있기 때문에 Key를 구분하는 Unique 값이다.

JWT Token의 Header에 JWT를 생성할 때의 kid 값을 넣고, 검증할 때 JWT의 Header에서 kid 값을 찾고, 해당 kid에 맞는 Public Key를 받아온다.

## JWKS란?

Json Web Key Set라는 이름에 걸맞게 단순히 JWK 리스트를 나타낸다.

# RSA

## RSA란?

**JWK와 함께 자주 사용되는 비대칭 알고리즘**이다.

Public Key와 Private Key 두 가지를 모두 가지며, 자세한 수학적 공식까지 다룰 필요는 없을 것 같아 넘긴다.

## Flow

1. Server에서 Public Key와 Private Key를 생성한다.
2. Client는 Server의 JWK Endpoint에 요청을 보내 JWKS를 받아온다.
3. JWT를 파싱하고, kid를 꺼내와 해당 JWT에 맞는 JWK를 찾는다.
4. x5c Property로 JWT Signature를 검증하기 위한 인증서를 만든다.
5. JWT의 Property들을 검증한다.

