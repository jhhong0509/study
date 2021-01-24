# 파일 업로드 정리

### 기본 설정

``` yaml
spring:
  servlet:
    multipart:
      enabled: true
      file-size-threshold: 2KB
      location: C:/Temp
      max-file-size: 500MB
      max-request-size: 515MB

file:
  upload-dir: /uploads
```

- 코드 해설

  - ``` yaml
    enabled: true
    ```

    - multipart 업로드를 허용할지 여부
    - 기본값이 true이다.

  - ```yaml
    file-size-threshold: 2KB
    ```

    - 스프링 부트에서 파일을 처리할 때, 임시 파일을 만들어서 처리 후 삭제한다.
    - 만약 처리할 파일이 일정 크기보다 작으면 임시파일을 만들지 않고 처리한다.
    - 일정 크기를 정해줄 수 있다.
      - 즉, 이 크기보다 작은 파일들은 임시파일을 만들지 않는다.
    - 임시파일을 생성하지 않으면 더 빠른 처리가 가능하다.
    - 하지만 작업을 수행하는 동안 메모리에서 처리하기 때문에, 
    - 기본값은 0B

  - ```yaml
    location: C:/Temp
    ```

    - 임시 파일을 저장할 위치를 고른다.
    - 임시 파일이 지워지도록 되어있지만, 결국 어떤 이유로든 파일이 남아있게 된다.
      - 그 파일들은 손수로 지워줘야 한다.
    - 나중에 관리를 쉽게 하기 위해서 위치를 지정해 놓으면 좋다.
    - 기본값은 System.getProperty("java.io.tmpdir");으로 확인할 수 있다.
      - 매우 더럽다.

  - ```yaml
    max-file-size: 500MB
    ```

    - 업로드할 파일의 최대 크기를 결정한다.
    - 기본값은 1MB이다.

  - ``` yaml
    max-request-size: 515MB
    ```

    - 업로드할때 요청의 최대 크기를 결정한다.
    - 기본값은 10MB이다.

- max-file-size와 max-request-size의 차이

  - max-file-size는 말 그대로 파일의 크기를 의미한다.
  - 단 하나의 파일에만 적용된다.
  - max-request-size는 말 그대로 모든 요청, 즉 모든 파일들을 합친 크기이다.
  - 5MB짜리 파일2개와 12MB짜리 파일을 업로드 할때, max-file-size는 12MB 이상이어야 하고, max-request-size는 22MB 이상이어야 한다.

### 파일 업로드 구현

``` java
@PostMapping("")
@ResponseStatus(HttpStatus.CREATED)
public List<String> upload(@RequestPart List<MultipartFile> files) throws Exception {
	List<String> list = new ArrayList<>();
	for (MultipartFile file : files) {
		String originalfileName = file.getOriginalFilename();
		File dest = new File("C:/Image/" + originalfileName);
		file.transferTo(dest);
	}
	return list;
}
```

- 코드 해설

  - ``` java
    @RequestPart List<MultipartFile> files
    ```

    - 파일 업로드를 할때, 파일을 받아온다.
    - @RequestParam으로 해도 잘 작동한다.
      - @RequestPart가 정석이라고 한다.
      - 차이점
        - @RequestParam은 Converter 또는 PropertyEditor에 의존한다.
        - @RequestPart는 Content-type 을 고려해서 HttpMessageConverters에 의존한다.

  - ``` yaml
    for (MultipartFile file : files)
    ```

    - 넘어온 파일들 만큼 반복을 돌린다.
    - 파일이 하나라면 있어도 작동은 하겠지만, 필요없다.

  - ``` yaml
    String originalfileName = file.getOriginalFilename()
    ```

    - 기존의 파일 이름을 저장한다.
    - 보통 파일을 업로드 할때엔, UUID와 같은걸로 파일의 이름을 바꿔주기 때문에 필요하다.
      - 하지만 예제에선 사용하지 않았다.

  - ``` java
    File dest = new File("C:/Image/" + originalfileName);
    ```

    - 실제 파일이 저장될 위치+파일이름을 해준다.
    - 실제 파일이 저장될 위치를 나타낸다.

  - ``` java
    file.transferTo(dest);
    ```

    - 파일을 실제로 업로드 하는 구문이다.
  
- ``` java
  file.transferTo(new File("C:\\Users\\Administrator\\Desktop\\img" + file.getOriginalFilename()));
  ```

  - 간단하게 위와 같이 구현할 수 있다.