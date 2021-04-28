# 영속성 전이 PERSIST 저장 안됨

### 발생 이유

PERSIST를 해놨는데, 엔티티들이 저장이 안되었다.

찾아보니, 저장되는 엔티티와 연관된 엔티티 모두 관계를 가지고 있어야 했다.

### 해결 과정

아무리 생각해도, 둘 다 객체 생성 시에 관계를 가지는 방법이 없었다.

그래서 불변 객체였던걸, 연관 관계 부분만 수정하는 메소드를 만들어 주었다.

``` java
public void addMember(Member member) {
    this.members.add(member);
}

public void addLanguage(Language language) {
    this.languages.add(language);
}
```

이렇게 하면, 해당 엔티티만 저장해도 연관된 엔티티를 모두 저장해 준다.

### 후기

JPA 책에서 읽기만 하다가 실제 작업이랑 다르다는걸 느꼈다.

### 주의

### 기타 지식

REMOVE는 그냥 지우면 다 지워진다.