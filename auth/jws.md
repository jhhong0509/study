# JWS

### JWS란?

OAuth의 근간이 되는 기술로, **JWT라는 추상 객체를 구현한 구현체** 이다.

우리가 사용하는 JWT는 대부분 JWS 이다.

> 보통 JWT 라고 하면 JWS를 의미한다.

서버에서 JWT를 JWS 체계로 서명해서 signature와 함께 전송한다.

signature는 JWT의 claim이 위조 또는 변경되지 않았음을 보증한다.

> 쉽게 말해서 JWT는 그냥 문자열일 뿐이고 JWS가 JWT를 보증해 준다.
>
> 그렇기 때문에 JWS가 없는 JWT는 보안이 별로다.