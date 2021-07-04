# 완전한 Anti-Facade

## 소개

우리는 평소에 Decoupling을 이용해서 객체들간의 의존성을 낮추고, 유지보수에 용이하도록 한다.

> 같은 메소드를 가지는 여러개의 클래스가 있을 때, 그 클래스들에게 추상 메소드를 상속하게 하는 등

하지만 이러한 방식은 어떤 상황에서는 복잡도를 증가시키고 유지보수를 어렵게 할 수 있다.

기능 향상을 하는 경우에는 로직 등 모든 방향에서 유지보수하고, 향상시켜야 한다.

<br>

Service Facade에 도메인 객체를 숨기는 것 대신, 데이터베이스로부터 직접 객체를 끌어오고 UI에게 그것들을 직접적으로 드러낼 수 있다.

심지어 프레젠테이션 계층은 영속 객체를 바인딩 할 수 있다.

<br>

이러한 작업은 STSB에게 적합하다.

Extended Entity Manager는 **영속 상태의 모든 객체를 저장**한다.

이러한 특징은 **동기화나 수동으로 Lazy로딩을 할 필요가 없어진다.**

Entity Manager는 **dirty 엔티티들을 데이터베이스에 flush한다.**

> dirty 객체들이란 스냅샷과 비교했을 때 정보가 다른 객체들을 의미한다.

트랜잭션은 빈 save 메소드를 시작하고, EJB gateway는 domain repository와 비슷하다.

<br>

EJB gateway는 rich domain object와 persistent domain object를 Presentation 계층에게 노출한다.

하지만 도메인 객체는 이미 캡슐화 되어있기 때문에 오히려 장점으로 작용한다.

<br>

단순함과 내장된 관점에서, EJB는 Gateway를 구현하기 위한 가장 간단하고 효율적인 방법 중 하나이다.

> `Because of simplicity and built-in aspects, an EJB 3.1 happens to be the simplest and leanest candidate for a Gateway implementation.`
>
> lean Programming이란 효율을 최대한 올리고, 애플리케이션의 낭비를 최소화 하자는 소프트웨어 방법론

``` java
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class OrderGateway{

    @PersistenceContext(type=PersistenceContextType.EXTENDED)
    EntityManager em;

    private Load current;

    public Load find(long id){
       this.current = this.em.find(Load.class, id);
       return this.current;
    }

    public Load getCurrent() {
        return current;
    }

    public void create(Load load){
        this.em.persist(load);
        this.current = load;
    }

    public void remove(long id){
        Load ref = this.em.getReference(Load.class, id);
        this.em.remove(ref);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void save(){
        //nothing to do
    }
}
```

Gateway는 root entity를 기억하고 getter를 통해 접근을 가능하게 한다.

이것은 선언형으로 데이터를 바인딩할 때 사용될 수 있다.

## 사용하는 이유

### Local 이유

> 설명하기 앞서, Rich Domain Object 라는 단어가 나오는데, Rich Domain Object란 Anemic Domain Object의 반대로 **도메인 객체에 비지니스 로직이 존재하는 객체**를 의미한다.
>
> Anemic Domain Object는 **도메인 객체에 비지니스 로직이 존재하지 않는 객체**를 의미한다.

Rich Domain 객체는 **정의될때마다 비지니스 로직을 포함하고 있다.**

메소드를 호출하게 되면 해당 엔티티 뿐만 아니라 **모든 연결된 객체들을 변경**할 수 있다.

Local의 경우, EntityManager는 **변경 사항을 인식, 적용**해서 **트랜잭션 종료 시 변경사항을 커밋**한다.

하지만 Remote의 경우 같은 객체를 어디서 소유하고 있는지에 대해 찾는 기능을 구현해야 한다.

### Stateful 이유

복잡한 시나리오에서 계층간(service-repository 등)의 데이터 동기화는 굉장히 어렵다.

하지만 **만약 요청마다 managed된 Entity를 유지한다면, 따로 동기화를 할 필요가 없어진다**

또한 요청마다 domain graph를 만들고 만들어야 한다. 이건 복잡할 뿐만 아니라 Stateful 방법보다 확장성이 좋지도 않다.

