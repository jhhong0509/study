# MapStruct에서 겪은 에러들

### Unmapped target propertiesUnmapped target properties

target 부분이 매핑이 안된게 있다는 의미다.

즉, **변환될** 데이터 클래스의 필드중에, 값이 안들어간게 있다는 의미다.

`@Mapper`의 속성에 `unmappedTargetPolicy = ReportingPolicy.IGNORE`을 추가하거나, 매핑시켜주면 된다.

### source 및 target을 못 찾는 경우

대부분 gradle 설정 문제일 것이다.

[setup](./setup.md)대로 build.gradle을 바꿔주면 된다.

이런식으로 바꿔주면 잘 된다.

### 임베디드 값 타입 찾기

만약 다음과 같이 엔티티에 임베디드 값 타입이 있다면

``` java
@Entity
public class Homework {
    @Embedded
    private Term term;
}
```

`타입.필드`처럼 접근하면 된다.

즉 위 예제같은 경우는 아래와 같이 둘 수 있다.

``` java
@Mapping(source = "homework.term.endDate", target = "endDate")
MyHomeworkResponse toHomeworkResponse(Homework homework);
```

> term 안에 endDate가 있다.

여기서 중요한 점은, `Term` 처럼 대문자로 시작하는게 아니라 `term`이라고 써야 한다.

### Several possible source properties for target property

파라미터로 여러 엔티티를 등록했을 때, 이름이 같은게 있으면 발생한다.

``` java
MyHomeworkResponse toHomeworkResponse(Homework homework, User student, User teacher);
```

이런 식일 때, User의 필드들이 겹치게 되어 mapStruct가 특정할 수 없게 된다.

그래서 `파라미터.필드` 처럼 사용하고, 안쓰는 것들은 ignore에 등록해 주어야 한다.

``` java
@Mapping(source = "studnet.major", target = "major")
@Mapping(target = "major", ignore = true)
MyHomeworkResponse toHomeworkResponse(Homework homework, User student, User teacher);
```

이렇게 하면 문제 없이 잘 된다.