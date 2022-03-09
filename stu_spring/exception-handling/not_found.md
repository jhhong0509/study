# Not Found Exception Handling하기

## ControllerAdvice가 잘 동작하지 않는 이유

원래 Spring Boot에서는 BasicErrorController에 의해 Exception이 던져진다.

따라서 이 Exception이 NotFoundException 임을 특정할 수 없기 때문에, 따로 설정을 해주어야 한다.

## 해결 방법

``` yaml
spring:
  mvc:
    throw-exception-if-no-handler-found: true
  web:
    resources:
      add-mappings: false
```

### throw-exception-if-no-handler-found: true

Dispatcher Servlet에서 **요청 Handler를 찾을 수 없을 때, NoHandlerFoundException을 던져준다.**

하지만 이 설정만으론 부족하다.

왜냐하면 요청을 정적 자원에 대한 요청일수도 있기 때문에 SimpleUrlHandlerMapping으로 Handler를 찾기 때문이다.

그렇게 되면 다음과 같은 과정을 거친다.

1. Controller에서 Handler를 찾는다.
2. 정적 파일 요청이라고 판단하고 `classpath:/static/{endpoint}`에서 파일을 찾는다.
   1. 만약 파일이 존재하면 파일을 반환한다.
3. 만약 파일이 존재하지 않으면 `/error`로 요청을 한다.
4. 그 요청을 RequestMappingHandlerMapping가 받아 최종적으로 BasicErrorController가 Exception을 처리한다.

### add-mappings: false

위 문제를 해결하기 위한 설정이 `add-mappings` 설정이다.

`add-mappings`를 false로 설정하게 되면 **정적 자원에 대한 요청을 무시**하게 된다.

따라서 NoHandlerFoundException이 정상적으로 동작할 수 있다.

