# Spring Data Jpa 오류 수정

### 문제 발생

객체를 수정한 후에 당연히 flush()가 호출되어 수정 감지가 작동되었을 것이라 예상하고, 변경되었을 것이라 예상했다.

하지만 값이 수정되지 않았다.

### 발생 이유

나는 Spring Jpa를 공부하며, 하나의 작업이 끝나면 `flush()` 메소드가 자동으로 발생하는걸로 알고 있었다.

하지만 수정 API에서 엔티티가 수정되었는데, 변경점이 `flush()`가 안되었다.

### 해결 과정

인터넷을 찾아보니 Spring Data Jpa에서는 자동으로 flush를 해주지 않고, `repository.flush()`를 호출하거나 `@Transactional` 어노테이션을 붙여야지만 `flush()`가 발생한다고 한다.

그래서 `@Transactional` 도 붙여보고 `repository.flush()`도 호출해 봤는데 둘 다 잘 작동한다.

### 후기

생각보다 Jpa를 공부하고 나니, 전에 Spring Data Jpa로만 생각하던 코드를 고칠게 많다.

### 주의

딱히..

### 기타 지식

`repository.saveAndFlush()` 라는 기능도 있다.

save 후 flush하게 된다는 의미이다.