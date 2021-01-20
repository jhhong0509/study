# controller
- @RestController
    - 컨트롤러의 설정
    - 컨트롤러가 JSON을 반환하도록 해준다.
- @GetMapping(주소)
    - http 메소드중 get 메소드 요청을 받을 수 있도록 해준다
    - 해당 주소로 get 메소드 요청이 들어오면 해당 메소드 또는 클래스를 실행한다.
    - 중복 제거 
        - 만약 users/detail 이라는 URI에서 get 요청과 post 요청이 들어왔다는 가정하에
        - @GetMapping("users/detail"), @PostMapping("users/detail")처럼 할 필요는 없다.
        - 클래스에 @RequestMapping("/users/detail") 처럼 해 주고, @GetMapping과 @PostMapping을 사용하면 된다.
```java
@RequestParam("넘어온 이름") String name
```
- @RequestParam
    - 외부에서 넘긴 파라미터를 가져오는 어노테이션
    - 단일 파라미터를 전달받을때 사용된다.
    - 위 코드는 해당 파라미터가 name에 저장된다.
    - required
        - 기본적으로 true이다.
        - 파라미터 값이 안들어와도 되는지 여부
- @PathVariable
    - /posts/1 처럼, URI에 정보를 담을 수 있다.
    - @RequestMapping("/경로/{변수}/{변수}") 와 같이 선언해 놔야 한다.
    - restapi에서 자주 사용된다.
- @RequestBody
    - HTTP 요청의 body를 자바의 객체로 받을 수 있다.
    - URL을 통해 넘어온 값은 받을 수 없다.
