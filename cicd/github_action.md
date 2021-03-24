# github action 작성법

### github action이란?

github action이란 github에서 무료로 제공하는 CI 툴 이다.

언제, 어떤 작업을 수행해 줄지 선택할 수 있고, 여러가지 환경에서 해볼 수 있다.

### github action 작성

##### name:

name: 뒤에는 해당 workflow의 이름이 들어온다.

> workflow란 CI 전체를 의미한다.

``` dockerfile
name: 이름
```

##### on:

on: 뒤에는 언제 해당 workflow를 실행시킬지 정한다.

push, pull_request 등이 가능하다.