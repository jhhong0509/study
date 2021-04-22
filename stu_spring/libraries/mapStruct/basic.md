# MapStruct

### MapStruct란?

기존의 개발 도중, Entity에서 Response나, Request에서 Entity로 변환하는 작업이 잦았다.

우리는 이 작업을 빌더패턴을 통해 좀 더 직관적으로 바꿨지만 여전히 반복되는 변환은 실수가 생길 확률이 높고, 지루하다.



그래서 등장한게 클래스와 클래스 간의 매핑을 지원하는 APT가 등장하게 되었고, 그 중 하나가 MapStruct 이다.

> APT란 Annotation Processor Tool 의 약자로, 자바 컴파일 단계에서 유저가 정의한 어노테이션의 코드를 분석 및 처리하기 위해 쓰이는 도구이다.
>
> 컴파일 에러나 경고를 만들기도 하고, 소스코드와 바이트코드를 생성하기도 한다.



> 쉽게 말해  사용자의 코드를 뜯어서 필요한 코드를 추가해 주며, 도중에 오류가 발생하면 오류를 띄워준다.





### MapStruct를 사용하는 이유

여러가지 매핑 라이브러리가 등장하게 되었는데, ModelMapper나 jmapper 등 많은 라이브러리들이 등장하였다.

> 대표적으로 ModelMapper가 MapStruct 등장 이전에 많이 쓰였다.

하지만 굳이 MapStruct를 사용하는 이유는 **빠르고, 읽기 쉽고, 많이 쓰이기 때문이다.**

![main_oriented](graph.jpg)

> 하늘색이 MapStruct의 통계다

위 구글의 통계를 보면 MapStruct의 사용량이 가장 많은걸 볼 수 있다



성능을 보자면, 누군가가 각 라이브러리마다 성능을 비교한 표가 있다.

**평균 실행 시간**

| 프레임워크  | 평균 실행시간 |
| ----------- | ------------- |
| MapStruct   | 10의 -5승 ms  |
| JMapper     | 10의 -5승 ms  |
| Orika       | 0.001 ms      |
| ModelMapper | 0.001 ms      |
| Dozer       | 0.002 ms      |

보다싶이 다른 라이브러리들 보다 빠른걸 볼 수 있다.

가장 비교가 많이 되는 ModelMapper와 차이가 많이 나는걸 볼 수 있다.



**단위 시간에 처리할 수 있는 양**

> 클수록 좋다.

| 프레임워크  | 처리량 |
| ----------- | ------ |
| MapStruct   | 133719 |
| JMapper     | 106978 |
| Orika       | 1800   |
| ModelMapper | 978    |
| Dozer       | 471    |

여기서도 볼 수 있듯이, ModelMapper의 성능이 다른 프레임워크에 비해 굉장히 뛰어난걸 볼 수 있다.



**실행시간**

> 하나의 작업이 시작하고, 끝날 때 까지 얼마나 걸렸는지에 대한 표

| 프레임워크  | 시간  |
| ----------- | ----- |
| JMapper     | 0.015 |
| MapStruct   | 0.450 |
| Dozer       | 2.094 |
| Orika       | 2.898 |
| ModelMapper | 4.837 |

여기서도 MapStruct는 빠른 편에 속한다.



이렇듯 JMapper와 MapStruct 외의 프레임워크들은 차이가 크게 나는걸 확인할 수 있다.

하지만 JMapper의 설정은 굉장히 복잡하다.

아래 코드로 비교해보자



- JMapper

``` java
public class JMapperConverter implements Converter {
    JMapper realLifeMapper;
    JMapper simpleMapper;
 
    public JMapperConverter() {
        JMapperAPI api = new JMapperAPI()
          .add(JMapperAPI.mappedClass(Order.class));
        realLifeMapper = new JMapper(Order.class, SourceOrder.class, api);
        JMapperAPI simpleApi = new JMapperAPI()
          .add(JMapperAPI.mappedClass(DestinationCode.class));
        simpleMapper = new JMapper(
          DestinationCode.class, SourceCode.class, simpleApi);
    }

    @Override
    public Order convert(SourceOrder sourceOrder) {
        return (Order) realLifeMapper.getDestination(sourceOrder);
    }

    @Override
    public DestinationCode convert(SourceCode sourceCode) {
        return (DestinationCode) simpleMapper.getDestination(sourceCode);
    }
}


@JMapConversion(from = "paymentType", to = "paymentType")
public PaymentType conversion(com.baeldung.performancetests.model.source.PaymentType type) {
    PaymentType paymentType = null;
    switch(type) {
        case CARD:
            paymentType = PaymentType.CARD;
            break;

        case CASH:
            paymentType = PaymentType.CASH;
            break;

        case TRANSFER:
            paymentType = PaymentType.TRANSFER;
            break;
    }
    return paymentType;
}
```

- MapStruct

``` java
@Mapper
public interface MapStructConverter extends Converter {
    MapStructConverter MAPPER = Mappers.getMapper(MapStructConverter.class);

    @Mapping(source = "status", target = "orderStatus")
    @Override
    Order convert(SourceOrder sourceOrder);

    @Override
    DestinationCode convert(SourceCode sourceCode);
}
```

이렇게 한눈에 보이듯이, 훨씬 간단해 진다.

이러한 이유 때문에, MapStruct를 사용하게 된다.

> TMI로, ModelMapper는 원래 Reflection을 사용하기 때문에 성능에 이슈가 있었다.