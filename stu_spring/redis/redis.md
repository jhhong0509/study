# Spring Data Redis 공부

### Redis란?

- NoSQL의 일종인 데이터베이스 관리 시스템이다(DBMS)

  > NoSQL은 단순한 검색/추가 작업에 매우 최적화된 데이터베이스이다.

  > 값은 키:값 형태로 저장되고, 레이턴시와 스루풋에서 성능 이익을 내는것이 목적이다.

- REmote Dictionary Server의 약자이다.

  > 번역하면 원격 사전 서버 라고 한다.

  > 검색에서 빠르다는 것을 강조한 것 같다.

- 오픈 소스 기반으로, 많은 언어를 지원한다.

  > ActionScript, C, C++, C#, Clojure, 커먼 리스프, Dart, Erlang, Go, Haskell, Haxe, Io, Java, Node.js, Lua, 오브젝티브-C, 펄, PHP, Pure Data, Python, R[7],Ruby, Scala, Smalltalk, Tcl을 지원한다. 언어 지원 면에선 걱정하지 않아도 된다.

- 메모리 기반 DBMS이다.

  > 메모리 기반 DBMS란, 메모리에서 작동하는, 즉 저장공간이 아니기 때문에 재시작하면 사라지는 정보들을 관리할 수 있다.

- 메모리 기반 DBMS의 장점

  > 데이터가 메모리에 있으므로 매우 빠르다. 저장 공간을 거치지 않기 때문
  >
  > 단, 용량은 적다.

  >  장비가 꺼지면 데이터가 사라진다.

  >  Cache이기 때문에, 만료일이 지나면 사라진다.

  >  Memcache는 만약 메모리가 부족하면 LRU(Least Recently Used) 알고리즘을 통해 데이터가 사라진다.

### Redis의 장점

1. 리스트, 배열 형식의 데이터 처리가  MySQL에 비해 10배정도 **빠르다**.

2. 스냅샷 기능을 제공하기 때문에, 어떠한 시점으로 **복구할 수 있다**.

3. Expires를 설정하지 않으면, 데이터를 삭제하지 않는다.

4. 동시에 같은 키에 대한 갱신을 요청할 시, Atomic 처리(원자성을 지키기 위한 처리)를 통해 **원자성**을 지켜줄 수 있다.

   > 원자성 이란, 돈 송금을 예시로 들었을 때, 송금과 돈을 받는것이 한쪽만 이루어지면 안된다.
   >
   > 이렇게 하나의 작업은 모두 성공하거나 모두 실패해야 하는 것이 원자성 이다.

5. 정수, 실수형이 따로 없다. 즉, String에 이진 데이터를 넣을 수 있다.

6. 5가지 데이터 타입이 있다.

7. Consistent Hashing을 지원한다.

   - Consistent Hashing이란, 웹 캐시를 구현하기 위한 알고리즘으로, 노드의 추가시에 **데이터 재할당을 최소화** 한다.

8. Master-Slave 구조를 지원한다. 즉, 한 장치가 다른 장치를 **제어**할 수 있도록 해준다.

9. 데이터 분실 위험을 없애준다.

### Redis Collections

> Collection 이란 목록형 데이터를 처리하는 자료구조 이다.

> 이전까지의 Key-Value 형태의 저장소는 String-String 형태만 지원했다.
>
> 하지만 Redis는 여러가지 형태를 지원한다.

#### String

- **Key-value** 형태
- Key를 어떻게 잡느냐에 따라 분산이 바뀔 수 있다.

#### List

- **Key-Value[]** 형태

- Lpush와 Rpush를 지원

  > 시작과 끝에 데이터 삽입 지원

- Lpop과 Rpop 지원

  > 시작과 끝에서 데이터 꺼내기를 지원

#### Sets

- **Key-Set\<value>** 형태이다

  > A라는 value가 10번 저장되어도, 한번만 남는다.

- 집합의 형태이다.

- 중복 제거를 지원한다.

- 순서가 없다.

- find가 매우 빠르다.

#### Sorted Set

- **Key-Set<value(with score)>** 형태이다.
- 순서를 갖는 집합이다.
- key score value 형태로 저장된다.
- 하지만 score는 double이기 때문에 정확하지 않을 수 있다.

#### Hash

- **Key-\<field-value>** 형태
- Key-value 안에 key-value가 존재

#### Collections의 특징

- 하나의 컬렉션에 최대 요소 개수는 4,294,967,295개, 대충 43억개 좀 안된다.

- Expire, 즉 만료시간은 Collections의 개별 아이템에 걸리지 않고 전체에만 걸린다.

  > 즉, A라는 Sets에 B라는 데이터가 있을 때, 만료 시간을 정하고 싶다면 A에 만료 시간을 걸어야 한다.

- 하나의 컬렉션에 너무 많은 아이템(데이터)를 담지 않는게 좋다.

  > 1만개 이하로 유지하는게 좋다고 한다.

### Spring Boot에서 사용되는 Redis

- Spring Data Redis란

  > Redis를 마치 우리가 평소에 사용하던 Repository처럼 사용하도록 해주는 스프링의 모듈이다.

- Lettuce란

  > Redis Java Client로, Spring Data Redis에서 지원하는 Client.

- Embedded Redis란

  > H2와 같은 내장 Redis

### Spring Boot에서 Redis 구현

#### build.gradle

```groovy
compile('org.springframework.boot:spring-boot-starter-data-redis')
compile group: 'it.ozimov', name: 'embedded-redis', version: '0.7.2'
```

#### EmbeddedRedisConfig

> 로컬에서 사용될 Redis의 설정을 해준다.
>
> RedisServer는 import 할 때 redis.embedded.RedisServer 를 가져와야 한다.

``` java
@Slf4j
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
            redisServer = new RedisServer(redisPort);
            redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
```

``` java
@Slf4j
```

> lombok 어노테이션 이다.
>
> 로그를 남기기 위해 사용한다.

``` java
@Profile("local")
```

> 프로필중 로컬이 활성화 되어 있을때만 실행된다.
>
> 즉, 로컬에서 할때만 실행될 예정이다.

``` java
@PostConstruct
public void redisServer() throws IOException {
    redisServer = new RedisServer(redisPort);
    redisServer.start();
}
```

> Redis 서버를 해당 포트를 통해 연다는 의미
>
> @PostConstructor는 객체의 초기화 부분으로, 생성 후 초기화를 위해 실행되는 메소드에 사용한다.

``` java
@PreDestroy
public void stopRedis() {
    if (redisServer != null) {
        redisServer.stop();
    }
}
```

> 소멸 단계에서 사용된다.
>
> 즉 모든 작업이 끝나고 객체(빈)을 삭제할 때 실행될 메소드 이다.
>
> RedisServer가 켜져있다면, 닫아버린다.

### RedisRepositoryConfig

``` java
@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
```

``` java
@EnableRedisRepositories
```

> RedisRepositories를 활성화 한다.

``` java
@Bean
public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(redisHost, redisPort);
}
```

> Redis와 연결 해 준다.

> 테스트에선 redisHost가 localhost이다.

``` java
@Bean
public RedisTemplate<?, ?> redisTemplate() {
    RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    return redisTemplate;
}
```

> RedisConnection에서 넘겨준 값을 직렬화 해 준다.

#### Redis 객체

``` java
@Getter
@RedisHash("point")
public class Point implements Serializable {

    @Id
    private String id;
    private Long amount;
    private LocalDateTime refreshTime;

}
```

``` java
@RedisHash("point")
```

> Redis에 저장될 Key값을 지정해 준다.
>
> 최종적으론 point 라는 값과, @Id가 붙어있는 필드가 합쳐져서 나타난다.
>
> 즉, pointId 라는 키를 갖는다.

#### Repository

``` java
public interface PointRedisRepository extends CrudRepository<Point, String> {
}
```

> 정말 간단하게 평소에 만들던 Repository처럼 만들어 주면 된다.

#### 테스트코드의 포트 문제

> 서로 다른 property를 가진 테스트코드 실행 시에, 새로운 Context를 생성하기 때문에 EmbeddedRedis를 한번 더 실행한다.

> 그렇기 때문에 같은 포트의 Redis가 2번 실행되기 때문에 충돌이 발생한다.

#### EmbeddedRedisConfig 파일 수정

> 윈도우에선 할 수 없다.
>
> 직접 exe 프로세스를 찾는 코드를 작성해야 한다.

``` java
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        int port = isRedisRunning()? findAvailablePort() : redisPort;
        redisServer = new RedisServer(port);
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    /**
     * Embedded Redis가 현재 실행중인지 확인
     */
    private boolean isRedisRunning() throws IOException {
        return isRunning(executeGrepProcessCommand(redisPort));
    }

    /**
     * 현재 PC/서버에서 사용가능한 포트 조회
     */
    public int findAvailablePort() throws IOException {

        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }

        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
    }

    /**
     * 해당 port를 사용중인 프로세스 확인하는 sh 실행
     */
    private Process executeGrepProcessCommand(int port) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    /**
     * 해당 Process가 현재 실행중인지 확인
     */
    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }

        } catch (Exception e) {
        }

        return !StringUtils.isEmpty(pidInfo.toString());
    }
}
```

> 변경사항은 많지만, 결국 현재 포트로 실행중인 프로세스가 있으면 다른 포트로 실행시키고, 없다면 지정된 포트로 실행시킨다.