# Spring Bean

### SpringBean이란

스프링은 자바 프로그램이 제어권을 갖기 위해 **자바 객체를 자신만의 형태**로 바꾸는데, 그걸 **Bean** 이라고 한다.

> Spring에서 POJO를 Bean 이라고 지칭한다.

이러한 Bean은 **스프링이 생명주기를 관리하는 객체** 라고 생각하면 편하다.



### Bean Scope

기본적으로 Singleton 객체가 된다.

> 즉 한번 생성되어 모든 객체가 동일한 객체를 참조하는 방식이다.

이런게 맘에 들지 않는다면, 바꿀 수 있다.

| 이름      | 설명                                        |
| --------- | ------------------------------------------- |
| prototype | 요청마다 새로운 객체 생성                   |
| request   | http request 하나마다 객체 생성             |
| session   | http session과 동일한 생명 주기를 갖는 객체 |



### Bean Factory

**Bean을 등록, 생성, 조회, 삭제 하도록 돕는걸 BeanFactory 라고 한다.**

우리는 이걸 직접 사용하진 않고, **BeanFactory를 상속받은 Application Context로 사용한다.**

