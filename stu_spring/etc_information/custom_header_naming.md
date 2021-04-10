# 사용자 지정 HEADER Naming 규칙

### Prefix

기존의 커스텀 헤더의 이름은 **`X-` 키워드를 붙여서 일반적인 헤더와 커스텀 헤더를 구분했다.**

하지만 이것이 불편함을 유발한다는 이유로 <a href = "https://tools.ietf.org/html/rfc6648">RFC 6648</a>에서 폐지되어 더이상 **표준이 아니다.**

공식 문서에는 다음과 같이 설명한다.

`In practice, that convention causes more problems than it solves.  Therefore, this document deprecates the convention for newly defined parameters with textual (as opposed to numerical) names in application protocols.`

> 이러한 협약은 이것이 문제를 해결하는 것 보다 야기하는게 많습니다. 그러므로,  이 문서에선 새로운  application protocol에서 사용자 정의 헤더에 대해 그 협약을 배척한다.

즉 더이상 `X-`는 표준이 아니고, prefix 없이 자유롭게 사용하면 된다.

> ex) Refresh-Token

### 헤더 Naming

헤더에는 다양한 종류가 있고, 모두 정리하기엔 너무 많다.

그렇기 때문에 <a href = "https://developer.mozilla.org/ko/docs/Web/HTTP/Headers">문서</a>를 참고하는걸 추천한다.