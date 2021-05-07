# CONTEXT

---

## CONTEXT란

context란 직역하면 문맥 이라는 뜻을 가지지만 context의 역할을 설명하기 힘들다.

일반적으로 **멀티테스킹이 가능**한 운영체제에서 **각각의 수행 상태에 대한 정보를 저장**하는 것 들을 지칭한다.



## Spring 에서의 Context

### Application Context

Web Application의 **최상단에 위치한 Context**로, 스프링에서의 Application Context는 **Bean Factory를 상속받는 Context**를 의미한다.

트랜잭션 관리, AOP처리 및 **Bean을 관리**하는 곳으로, **어플리케이션 구동 시 로딩된다.**

