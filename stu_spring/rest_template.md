# RestTemplate 공부

### 주요 메소드

| 메소드          | 요청   | 매개변수                                                     | 설명                                                     |
| --------------- | ------ | ------------------------------------------------------------ | -------------------------------------------------------- |
| getForObject    | GET    | String url, Class 반환 타입, Object 매개변수                 | url로 요청을 보내고, 결과값을 객체로 저장한다..          |
| getForEntity    | GET    | String url, Class 반환 타입, Object 매개변수                 | url로 요청을 보내고, 결과값을 ResponseEntity로 저장한다. |
| postForLocation | POST   | String url, Object request, Object 매개변수                  | url로 요청을 보내고, header의 uri를 가져온다.            |
| postForObject   | POST   | String url, Object request, Object 매개변수                  | url로 요청을 보내고, 결과값을 객체로 저장한다.           |
| postForEntity   | POST   | String url, Object request, Object 매개변수                  | url로 요청을 보내고, 결과값을 ResponseEntity로 저장한다. |
| delete          | DELETE | String url, Object uriVariables                              | url로 요청을 보내고, DELETE 메소드를 실행한다.           |
| put             | PUT    | String url, Object request, Object 매개변수                  | url로 요청을 보내고, PUT 메소드를 실행한다.              |
| patchForObject  | PATCH  | String url, Object request, Class 반환 타입, Object 매개변수 | url로 요청을 보내고, 결과값을 객체로 저장한다.           |
| exchange        | ?      | String url 또는 RequestEntity, Class 반환 타입, http 메소드  | 다른 메소드와 비슷하지만, header를 수정할 수 있다.       |

> exchange 외에는 기본 http 헤더를 사용한다.

### 구현

#### build.gradle

``` java
implementation 'org.apache.httpcomponents:httpclient'
```

#### RestfulConfig

``` java
@Configuration
public class RestfulConfig {

    @Value("${restTemplate.factory.readTimeout}")
    private int readTimeout;

    @Value("${restTemplate.factory.connectTimeout}")
    private int connectTimeout;

    @Value("${restTemplate.httpClient.maxConnTotal}")
    private int maxConnTotal;

    @Value("${restTemplate.httpClient.maxConnPerRoute}")
    private int maxConnPerRoute;

    @Bean
    public RestTemplate restTemplate() {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(readTimeout);
        factory.setConnectTimeout(connectTimeout);

        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(maxConnTotal)
                .setMaxConnPerRoute(maxConnPerRoute)
                .build();

        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        return restTemplate;
    }
}
```

- 기본적으로 HttpClient는 connection pool을 제공하지 않기 때문에 설정하지 않으면 계속해서 handshake가 발생한다.

  > 연결된 객체들을 저장하는 곳을 pool 이라고 한다.
  >
  > 요청이 오면 connection을 빌려주고, 끝나면 다시 connection을 반환해서 pool에 저장하게 된다. 이러한 것을 connection pool 이라 한다.T
  >
  > ![image-20210211160922050](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20210211160922050.png)
  >
  > 이러한 방법을 사용하는 이유는 매번 드라이버를 로드하고 커넥션 객체를 받아오는 것이 비효율적이기 때문이다.

  > handshake란 TCP 에서 연결을 확인하는 방법이다.
  >
  > 3개의 과정을 거쳐 연결을 확인하게 된다.

- 그렇기 때문에 위와 같은 방법을 통해 커스텀한 RestTemplate를 Bean에 등록할 수 있다.

``` java
setConnectTimeout(int timeout);
setConnectionRequestTimeout(int connectionRequestTimeout);
```

- connectionTimeout을 지정해 준다.

> 연결을 시도할 시간을 설정하게 된다.
>
> 이 시간동안 연결하지 못하면 연결을 실패한다.

``` java
setReadTimeout(int timeout);
```

- readTimeout을 지정해 준다.

> readTimeout이란, connection은 이루어 졌지만 작업이 길어지거나 요청이 처리되지 못하고 있을 때 클라이언트가 커넥션을 끊는 시간이다.

``` java
setBufferRequestBody(boolean bufferRequestBody)
```

- requestBody에 버퍼링을 지원할지 말지 정한다.

> Default는 false지만, 매우 큰 바디가 들어올 경우에는 true를 권장한다.
>
> 대충 뭔지 모르겠으니, 크면 true 작으면 false로 하자

``` java
setMaxConnTotal(int size)
```

- connection pool의 최대 개수를 지정해 준다.

``` java
setMaxConnPerRoute(int size)
```

- port, ip당 연결 제한 갯수이다.

> size가 1이라면, 하나의 ip,port에서는 하나만 연결할 수 있다.

