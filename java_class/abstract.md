# 추상

### 추상 클래스란

실체 간에 공통된 특성을 추출한 것이다.

> 상속과 비슷하지만, **new 연산자로 인스턴스를 생성하지 못한다.**

### 특징

- 추상 클래스는 실체 클래스와 상속의 관계를 가지고 있다.

- 실체 클래스는 추상 클래스의 모든 특성을 물려받는다.

- 실체 클래스는 추가적인 필드 또는 메소드를 가질 수 있다.

- 추상 클래스는 상속으로만 사용된다.

  > extends 뒤에만 올 수 있다.

### 추상 클래스의 용도

#### 실체 클래스들의 공통된 필드 및 메소드 이름 통일

실체 클래스를 여러 사람들이 만들수도 있는데, 그러면 각각 다른 사람들이 **자신이 원하는 이름을 사용하게 된다.**

이 때 추상 클래스를 사용하게 되면, **해당 이름을 사용하도록 강제할 수 있다.**

#### 실체 클래스 작성시 시간 절약

공통된 부분을 한번만 작성하면 되기 때문에 개발에 시간을 줄일 수 있다.

### 선언

추상 클래스는 아래와 같이 선언할 수 있다.

``` java
public abstract class Test {
    
}
```

추상 클래스는 **필드**, **생성자**, **메소드** 모두 올 수 있다.



추상 클래스는 new 키워드로 인스턴스를 만들어서 생성자를 호출할 수는 없지만,

**자식 클래스에서 super로 호출해서 추상클래스 객체가 생기기 때문에 생성자가 필요하다**

> 자식 클래스에서 super가 호출해야만 객체가 생긴다.
>
> new 키워드로는 만들 수 없다.

### 예제

#### 추상 클래스

``` java
package class_2021_04_12;

public abstract class Phone {
	
	public String owner;
	
	public Phone(String owner) {
		this.owner = owner;
	}
	
	public void turnOn() {
		System.out.println("폰 전원 ON");
	}

	public void turnOff() {
		System.out.println("폰 전원 OFF");
	}
}
```

> 보다싶이 일반적인 클래스와 상당히 비슷하다.

#### 실체 클래스

``` java
package class_2021_04_12;

public class SmartPhone extends Phone {
	
	public SmartPhone(String owner) {
		super(owner);
	}
	
	public void internetSearch() {
		System.out.println("인터넷 ON");
	}
}
```

추상 클래스인 Phone을 상속 받았고 생성자에서 super()를 통해 객체를 생성했다.

#### 메인 클래스

``` java
package class_2021_04_12;

public class Main {

	public static void main(String[] args) {
//		Phone phone = new Phone("aaa");
		SmartPhone smartPhone = new SmartPhone("aaa");
		
		smartPhone.turnOff();
		smartPhone.turnOn();
		smartPhone.internetSearch();
	}

}
```

Phone을 new 키워드로 인스턴스를 만들어 보면, 오류가 나는걸 확인할 수 있다.

또한 **smartPhone에서 부모인 Phone의 메소드에 접근한것**을 확인할 수 있다.

### 추상 메소드란

**추상 클래스에서만** 선언 가능한 메소드로,

`public abstract void method();`처럼 사용할 수 있다.

실제 메소드의 바디 부분이 없고 **오버라이딩 하도록 강요한다.**

해당 추상 클래스를 상속하면 **해당 메소드를 오버라이딩 해야만 하기 때문이다.**

> 하지 않으면 컴파일 도중 오류가 발생한다.