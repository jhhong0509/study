# 파일 관련 지식

### multipart/form-data

- multipart/form-data
  - 파일 업로드가 있는 양식 요소중 하나이다.
  - multipart는 form-data가 여러 부분으로 나뉘어서 서버에 전송되는 것을 의미한다.
  - 모든 문자를 인코딩하지 않음을 의미한다.

### Controller

- Multipart/form-data에서는 @RequestBody를 사용할 수 없다.
- 그렇기 때문에 대체제로 @ModelAttribute를 사용하면 된다.

### @RequestPart

- Multipart/form-data 를 바인딩(파라미터 값 가져오기)할때 사용된다.
- @RequestParam과 비슷하다.
- @RequestPart는 @RequestParam과 바꿔 쓸 수 있다.
  - 차이점
    - @RequestPart는 Content-Type을 고려해서 HttpMessageConverters에 의존한다.
    - @RequestParam은 등록된 Converter 또는 PropertyEditor를 통한 유형 변환에 의존한다.