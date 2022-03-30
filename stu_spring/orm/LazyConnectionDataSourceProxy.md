## 문제 상황

Spring에서는 **트랜잭션에 진입하는 순간, DB Connection을 가져온다.**

따라서 다음과 같은 단점들이 생기게 된다.

- Ehcache로 2차 캐시를 구현해서 DB 조회가 필요가 없어도 DB Connection을 점유함
- 외부 서비스를 조회해서 그 결과를 DB에 저장한다면, 불필요하게 오랫동안 Connection을 점유함
- Multi Datasource의 경우 Transaction에 들어간 후에는 이미 DB Connection을 가져오기 때문에 분기처리가 불가능

## 해결 방안

이를 해결하기 위한 방법이 `LazyConnectionDataSourceProxy`이다.

`LazyConnectionDataSourceProxy`은 실제로 Connection이 필요할 때 Connection을 가져오기 때문에 최적화 시킬 수 있다.

[원본](https://sup2is.github.io/2021/07/08/lazy-connection-datasource-proxy.html)

[참고 및 추후 공부하면 좋은 글](http://egloos.zum.com/kwon37xi/v/5364167)

