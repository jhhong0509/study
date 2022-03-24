## 서론

HashMap은 **Key - Value가 한 쌍이 되어 매핑되는 자료구조**이며, **저장, 삽입, 삭제의 시간 복잡도가 `O(1)`일 정도로 매우 빠른 자료구조**이다.

`hashCode()`함수를 통해 만든 Key는 중복을 허용하지 않되, Value는 중복될 수 있다.

여기까지가 모두가 알고있는 HashMap의 기본적인 상식일 것이다.

하지만 이번에는 조금 더 깊게 들어가서 HashMap이 어떻게 동작하는지에 대해 알아보자.

## HashMap과 HashTable

HashTable은 JDK 1.0, HashMap은 JDK 2에서 처음으로 공개되었다.

기본적으로는 HashMap과 HashTable이 거의 같은데, HashTable은 하위 호환성을 위해 업데이트가 없는반면, HashMap은 지속적으로 개선되어 있다.

또한, HashMap은 보조 해쉬 함수를 사용하기 때문에 성능상의 이점이 있다.

## HashMap의 구조

HashMap에서 데이터가 저장되는 곳을 버킷이라고 부른다.

HashMap은 내부적으로 배열로 되어있고, Key를 직접 배열의 인덱스로 사용할 수 있다.

Key를 구하기 위해서는 `hashCode()`함수를 통해 `hashCode() % 적재율`로 구할 수 있다.

## 해쉬 충돌

`hashCode() % 적재율`이 같은 경우를 *해쉬 충돌*이라고 한다.

Hash함수를 사용할 때, **버킷이 적을수록 메모리를 절약할 수 있지만 그 대신 충돌의 빈도가 잦아진다.**

![img](https://media.vlpt.us/images/adam2/post/be2893d0-dd35-4557-ade0-6e3490cbc924/image.png)

![img](https://media.vlpt.us/images/adam2/post/227cf384-58c3-46c4-9993-78578add4226/image.png)

이렇게 같은 공간에 두 개의 Value가 저장되기 때문에 HashMap의 규칙에서 벗어나게 된다.

### 해쉬 충돌 해결하기 - Separate Chaining

Java에서 채용한 방식으로, **충돌이 발생하면 기존 값과 새로운 값을 연결리스트를 통해 연결**시켜주는 방식이다.

한정된 저장소를 효율적으로 사용할 수 있다는 장점이 있다.

하지만 한 Hash에 여러 Value가 들어가게 되면 검색 성능이 떨어지게 된다.

### 해쉬 충돌 해결하기 - Open Addressing

빈 Hash를 찾아 데이터를 저장하는 방법이다.

![img](https://media.vlpt.us/images/adam2/post/a0880019-83ec-44b2-ae32-67ab4d536445/image.png)

위에서 볼 수 있다싶이 충돌이 발생하게 되면 다음에 해당하는 위치에 저장한다.

자세한건 Java에서 사용되지 않기 때문에 자세히는 다루지 않는다.

## HashMap의 크기

키의 개수와 동일한 크기의 Bucket을 가진 HashMap을 `Direct-address table`이라고 부른다.

`Direct-address table`의 장점으로는 **Hash 충돌이 발생하지 않는 것**이다.

하지만 전체 키 중 실제로 사용되는 Key가 많지 않은 경우에는 메모리 낭비가 된다.

기본적으로 16개의 크기를 가지며, 적재율을 통해 `전체 크기 * 적재율`을 계산해서 현재 요소의 개수가 이것보다 많으면 2배씩 늘어난다.

[참고](https://velog.io/@adam2/%EC%9E%90%EB%A3%8C%EA%B5%AC%EC%A1%B0%ED%95%B4%EC%8B%9C-%ED%85%8C%EC%9D%B4%EB%B8%94#division-method)

[참고](https://d2.naver.com/helloworld/831311)