# 서버 템플릿 엔진
- 템플릿 엔진이란
    - 지정된 템플릿 양식과 데이터가 합쳐져 문서를 출력하는 소프트웨어
    - 대표적으로 JSP 그리고 리액트, 뷰 등이 있다.
        - JSP는 서버 템플릿 엔진이다.
        - 리액트와 뷰는 클라이언트 템플릿 엔진이다.
- 서버 템플릿 엔진의 화면 생성
    - 서버 사이드 렌더링 Server Side Rendering(SSR)
        - 성능 이슈가 있다.
            - 매 요청마다 새로고침을 해야한다.
            - 매 페이지 로딩마다 서버로부터 리소스를 전달받아야 한다.
        - 검색엔진 최적화 가능
        - 처음 렌더링된 html을 전달해주기 때문에 초기 로딩을 줄일 수 있다(?)
    - 자바 코드를 통한 문자열 생성
    - 문자열을 HTML로 변환하여 브라우저로 전달
- 클라이언트 템플릿 엔진의 화면 생성
    - 클라이언트사이드렌더링 Single Page Application(SPA)
        - 페이지가 로딩된 후에, 데이터만 변경될 수 있다.
        - 트래픽 감소
        - 새로고침 발생 x
        - 다음, 네이버와 같은 곳에서 만들어진 크롤러(데이터 수집)가, SPA에서 데이터를 수집하지 못하기 때문에, 검색 엔진이 제대로 작동하지 않을 수 있다.
    - 브라우저 위에서 작동한다.
    - 코드의 실행장소는 브라우저 이다.
    - 즉, 브라우저에서 작동될 때, 서버에서 코드가 이미 벗어났기 때문에 손댈 수 없다.
    - 그래서 서버는 해당 클라이언트 템플릿 엔진에 JSON처럼 일정한 형식을 가진 데이터를 전달만 한다.
        - 최근 리액트나 뷰는 서버 사이드 렌더링
# 머스테치 템플릿
### 다른 템플릿들의 단점
- JSP와 Velocity
    - spring boot에서 권장하지 않음.
- Freemarker
    - 기능이 너무 과하다.
    - 높은 자유도로 인해, 초보자는 비지니스 로직이 Freemaker에 들어갈 가능성이 높다.
- Thymeleaf
    - spring boot에서 밀어주는 템플릿.
    - 문법이 어렵다.
    - Vue.js를 사용해 봤다면 사용해도 좋다.
    - intelliJ 커뮤니티 버전에서 지원하지 않는다.
### Mustache의 장점
- 현존하는 대부분의 언어를 지원한다.
- 문법이 심플하다.
- 로직코드를 사용할 수 없어, View와 서버를 명확히 분리할 수 있다.
- 서버 사이드 렌더링과 클라이언트 사이드 렌더링 모두 지원한다.
- intelliJ 커뮤니티 버전에서도 지원한다.
### Mustache 적용
- 플러그인 설치에서 Mustache를 설치한다
    - 문법 체크 지원
    - HTML 문법 지원
    - 자동완성 지원
``` java
compile('org.springframework.boot::spring-boot-starter-mustache')
```
- 위 코드를 build.gradle에 추가하기만 하면, 간편하게 이용할 수 있다.
    - 버전 관리를 하지않아도 된다.
    - 추가설정이 필요없어 간편하다.
- 머스테치의 기본 파일 위치는 src/main/resources/templates 이다.
- controller에서 String을 return하면, 알아서 기본 파일 위치에 있는 String.mustache를 찾아간다.
- Mustache를 적용하는 방법
    - 외부 CDN을 이용
        - 실제 서비스에선 잘 이용되지 않는다.
        - CDN에서 문제가 발생하면, 서비스에 문제가 발생할 수 있기 때문
        - 또한 CDN의 속도가 느려 성능 이슈 또한 발생할 수 있다.
        - 이 책에서 공부할때 사용되는 방식이다.
    - 직접 라이브러리를 받아서 사용
        - 실제 서비스에서 잘 이용된다.
        - CDN에 문제가 발생해도 서비스에는 문제가 발생하지 않는다.
### 실제 Mustache 코드

- 코드의 반복을 줄이기 위해, header과 footer를 만들어 준다.
  

#### header.mustache

``` html
<!doctype html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>스프링 부트 웹 서버</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/app/bgimg.css">
</head>
```

- bootstrap을 사용하기 위해 CDN을 이용한다.

- <html>과 같이 파일 맨 위에서 단순히 반복되는 코드들

#### footer.mustache

``` html
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</body>
</html>
```

- </html>과 같이 파일 맨 밑에서 단순히 반복되는 코드들

#### index.mustache

``` html
{{>layout/header}}
    <h1>스프링 부트로 시작하는 웹 서비스 ver.2</h1>
    <div class="col-md-12">
        <!-- 로그인 기능 영역 -->
        <div class="row">
            <div class="col-md-6">
                <a href="/posts/save" role="button" class="btn btn-primary">글 등록</a>
            </div>
        </div>
    </div>

{{>layout/footer}}
```

- {{>layout/footer}}과 같은 형태는, layout 폴더의 footer를 불러온다 와 같은 형태이다.
- 위에서 header와 footer의 코드들을 해당 위치에 불러온다.
- /posts/save 라는 URI로 요청을 보낸다.
- 해당 URI에서는 단순히 posts-save.mustache 파일을 불러와 준다.

#### posts-save.mustache

``` html
{{>layout/header}}

<h1>게시글 등록</h1>
<div class="col-md-12">
    <div class="col-md-4">
        <form>
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" class="form-control" id="title" placeholder="제목을 입력하세요">
            </div>
            <div class="form-group">
                <label for="author"> 작성자</label>
                <input type="text" class="form-control" id="author" placeholder="작성자를 입력하세요">
            </div>
            <div class="form-group">
                <label for="content"> 내용</label>
                <textarea class="form-control" id="content" placeholder="내용을 입력하세요"></textarea>
            </div>
        </form>
        <a href="/" role="button" class="btn btn-secondary">취소</a>
        <button type="button" class="btn btn-primary" id="btn-save">등록</button>
    </div>
</div>
{{>layout/footer}}
```

- bootstrap에서 간편하게 이용할 수 있도록 만들어둔 여러 css를 이용한다.
  - 간편하게 예쁜 UI를 디자이너 없이 구현할 수 있다.
- 제목, 작성자, 내용을 입력하는 텍스트 박스와 제출 버튼을 포함한다.

#### index.js

```javascript
var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        })
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
};

main.init()
```

- 게시글 등록 

- var main과 같이 선언한 이유
  - js 특성상, 이름이 같은 함수가 2개라면 나중에 선언된 함수가 실행된다
  - init과 save라는 이름은 언제든지 중복 사용될 우려가 있기 때문에, main으로 묶어주는 것이다.
  - namespace 기법이라고 한다.
- 만약 등록 버튼이 눌렸다면, save 함수를 호출한다
- data 변수에 각각 title, author, content를 저장한다.
- /api/v1/posts 라는 URI로 POST 요청을 보낸다.
- 이때, 데이터 타입은 json 형식을 이용한다.
- JSON.stringify(data)라는 메소드를 통해, 저장해둔 data 변수를 json 형식으로 변환한다.
- 또한, 위 작업들이 완료된 후에, 글이 등록되었다는 메세지를 보낸 후에 / (인덱스 페이지)로 이동한다.
- 만약 실패했다면 에러를 띄운다.

#### index.mustache 수정

- 보고서 보기를 위해 index.mustache를 수정해야 한다.

``` html
{{#posts}}
    <tr>
        <td>{{id}}</td>
        <td>{{title}}</td>
        <td>{{author}}</td>
        <td>{{modifiedDate}}</td>
	</tr>
{{/posts}}
```

- {{#posts}}
  - 자바의 for each 문 이라고 생각하면 된다.
  - posts라는 배열에 담긴 값들을 다 꺼내며 반복을 한다.

- {{이름}}
  - 우리가 만든 API가 정상적으로 요청을 받았고, 값을 반환했다면 해당 이름에 맞는 값들을 알아서 꺼내와 준다.

#### Mustache에 값을 전달하는 방법

``` java
@GetMapping("/")
public String index(Model model){
    model.addAttribute("posts", postsService.findAllDesc());
    return "index";
}
```

- 위와 같이, Model을 이용하면 된다.
- posts 라는 이름으로, post들을 모두 담은 리스트를 넘겨준다.