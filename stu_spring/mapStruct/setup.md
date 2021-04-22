# 설정

### gradle

MapStruct의 설정은 굉장히 간단하기 때문에 gradle 설정만 하면 사용할  수 있다.

``` gradle
ext {		// 변수같은 개념
	mapstructVersion = "1.4.0.Beta2"
	lombokVersion = "1.18.12"
}
dependencies {
	compileOnly "org.mapstruct:mapstruct:${mapstructVersion}", "org.projectlombok:lombok:${lombokVersion}"
	annotationProcessor "org.mapstruct:mapstruct-processor:${mapstructVersion}", "org.projectlombok:lombok:${lombokVersion}"
}
```

Lombok과 함께 사용하려면 꼭 위와 같은 형태로 해야 한다.

> 그냥 gradle을 받아오는 순서만 바꾸면 된다고 하는데, 나는 이런 형태 말곤 성공한적이 없다.
>
> 웬만하면 버전도 맞춰주는게 좋다.

Lombok의 어노테이션 프로세서가 작동한 후에 mapStruct의 어노테이션 프로세서가 작동해야 mapStruct가 Lombok 어노테이션을 사용할 수 있기 때문이다.

