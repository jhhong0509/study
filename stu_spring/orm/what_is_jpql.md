#  JPQL

### JPQL이란?

JPQL이란 Java Persistence Query Language의 약자로, JPA의 일부분이다.

JPQL은 데이터베이스의 쿼리를 만드는데 사용되며, JPA가 쿼리로 변환시켜 준다.

JPQL은 테이블에 대해 알지 못하며, 오직 객체와 객체간의 관계를 통해 쿼리를 만든다.

JPQL은 엔티티 **객체를 대상**으로 쿼리를 작성한다.

> SQL은 테이블을 대상으로 쿼리를 작성한다.

jpa에서 @Query 어노테이션을 붙이면 JPQL을 직접 작성할 수 있다.

> nativeQuery=true인 경우에는 그냥 SQL문 또한 작성할 수 있다.

JPQL은 대소문자를 구분한다.

### JPQL의 문제점

1. JPQL은 그저 문자열일 뿐이기 때문에, Type을 체크해줄 수 없다.
2. 또한, 실제 실행 전에는 쿼리에 문제가 있는지 확인이 불가능 하다

