# Stateless Session Bean

## 소개

Stateless Session Bean이란 **비지니스 로직에만 존재**하는 객체로, **상태(값)가 존재하지 않는다.**

다른 말로, Stateless Session Bean은 여러 메소드간의 호출들은 **Container에 의해 유지되지 않는다.**

<br>

Stateless Bean 객체들은 서비스 계층의 요구에 따라 EJB 컨테이너가 풀링한다.

> 풀링한다는건 Connection Pool처럼 저장하고, 꺼내서 사용하고, 돌려놓는 것을 의미한다.

이 객체들은 **각각 하나의 클라이언트에서만 접근 가능**하다.

동시 접근의 경우 **EJB Container가 다른 인스턴스로 라우팅**해준다.

## 생명주기

다음과 같은 생명 주기를 갖는다.

![lifecycle](https://www.javatpoint.com/ejbpages/images/statelessbeanlifecycle.png)

우선 EJB Container는 **Session Bean pool을 생성하고, 유지**한다.

DI를 하게 되면, **@PostConstruct를 호출**하게 된다.

> 존재한다면

그리고 나면 모든 준비가 마쳐져서 이제 **클라이언트에게 호출**된다.

그리고 마지막으로 **@PreDestroy를 호출**하여, Garbage Collector에게 지워질 준비가 되고, Garbage Collector에게 지워진다.