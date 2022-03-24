## 방어적 복사란?

생성자에서 초기화 해줄 때, 새로운 객체로 복사해서 초기화 해주는 것이다.

주소 값을 공유하지 않도록 하기 위함이다.

``` java
public Cars(Racers racers) {
    this.cars = new ArrayList<>(generateCars(racers));
}
```

이렇게 해주지 않으면, 하나의 객체가 변경되었을 때 주소값을 공유한 모든 객체가 변경될 위험이 있기 때문이다.

## 방어적 복사 이용하기

생성자에서 유효성 검사를 할 때, 보통 다음과 같이 한다.

``` java
public Period(Date start, Date end) {
    if (validation(start, end)) {
        throw new IllegalArgumentException("");
    }
    this.start = start;
    this.end = end;
}
```

하지만 Date는 불변 객체가 아니기 때문에 복사본을 이용해 검증하는게 좋다.

만약 multi thread에서 원본 객체를 복사한 후에 객체가 변경될 수도 있기 때문에 validation이 뒤로 가는게 맞다.

> 이걸 TOCTOU 공격이라고 부른다.

``` kotlin
public Period(Date start, Date end) {
    this.start = new Date(start.getTime()); // 방어적 복사
    this.end = new Date(end.getTime()); // 방어적 복사
    
    if (validation(this.start, this.end)) {
        throw new IllegalArgumentException("");
    }

}
```

파라미터가 final이 아니기 때문에 clone을 사용하면 재정의 되었을 수도 있다.

<br>

하지만 이걸 했다고 해서 getter에서도 불변성을 유지할 수 있는건 아니다.

다음과 같이 객체는 아직 변경될 수 있다.

``` java
List<Car> carList = cars.getList();
carList.add(); // 값이 변경될 수 있다.
```

따라서 getter에서도 복사해서 반환해 주어야 한다.

``` java
public List<Car> toList() {
    return Collections.unmodifiableList(cars);
}
```

``` java
public Date getStart() {
    return new Date(start.getTime());
}
```

> 참조 - Effective Java