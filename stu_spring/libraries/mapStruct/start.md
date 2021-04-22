# 사용하기

우선 MapStruct를 사용하려면 매핑될 클래스, 매핑할 클래스가 필요하기 때문에 예제로 Entity와 Response 클래스를 만든다.

### Entity

``` java
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "homework_tbl")
public class Homework {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String studentEmail;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Major major;

    @Column(nullable = false)
    private String teacherEmail;

    @Embedded
    @Builder.Default
    private Status status = new Status();

    @Embedded
    private Term term;

}

@Getter
@AllArgsConstructor
@Builder
@Embeddable
public class Status {

    @Column(nullable = false)
    private Boolean isSubmitted;

    @Column(nullable = false)
    private Boolean isRejected;

    public Status() {
        this.isSubmitted = false;
        this.isRejected = false;
    }

    public void submitHomework() {
        this.isSubmitted = true;
    }

    public void rejectHomework() {
        this.isRejected = true;
        this.isSubmitted = false;
    }
}

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Term {

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Builder.Default
    private LocalDate startDate = LocalDate.now();

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
```

> Embedded 타입을 사용했다.

여기서 `@Builder.Default`는 빌더 패턴에서 기본 값을 넣도록 도와준다.

### Response

``` java
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyHomeworkResponse {

    private Long homeworkId;

    private String studentName;

    private String teacherName;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Major major;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Boolean isRejected;

}
```

### MapperClass

Mapper 인터페이스를 만들기만 하면 바로 DI를 받아서 사용할 수 있다.

``` java
@Mapper(componentModel = "spring")
public interface HomeworkMapper {
    @Mapping(source = "homework.id", target = "homeworkId")
    @Mapping(source = "homework.term.startDate", target = "startDate")
    @Mapping(source = "homework.term.endDate", target = "endDate")
    @Mapping(source = "homework.status.isRejected", target = "isRejected")
    @Mapping(source = "homework.description", target = "description")
    MyHomeworkResponse toHomeworkResponse(Homework homework, String studentName, String teacherName);
}
```

> source는 파라미터로 넘어온 매핑에서 값을 줄 필드, target은 매핑 될 클래스의 필드를 의미한다.

- `@Mapping(source = "homework.id", target = "homeworkId")`

  source를 보면, 파라미터가 여러개이기 때문에 MapStruct에서 인식을 돕기 위해서 homework.필드 처럼 매핑시켜 주었다.

- `@Mapping(source = "homework.term.startDate", target = "startDate")`

  Homework 엔티티에는 Term이라는 Embedded 타입을 사용했기 때문에 term으로 한번 더 접근했다.

  중요한건, **term을 소문자로 써야 한다.**

- `String studentName, String teacherName`

  파라미터로 넘어온 값들도 알아서 매핑된다.

이렇게 하기만 하면 매핑을 위한 매퍼 클래스 제작이 모두 끝났다.

### 사용

간단하게 DI를 받아와서 사용하면 된다.

``` java
private final HomeworkMapper homeworkMapper;

.
.
.

homeworkMapper.toHomeworkResponse(homework, "asdf@dsm", "asdf@dsm");
```

이렇게 하면 알아서 response로 변환해 준다.

### TMI

생성된 클래스들은 

`build\generated\sources\annotationProcessor\java\main\com\gramo\gramo\mapper` 와 비슷한 위치에 있을 것이다.

직접 열어서 확인하면 다음과 같다.

``` java
import com.gramo.gramo.entity.homework.Homework;
import com.gramo.gramo.entity.homework.Homework.HomeworkBuilder;
import com.gramo.gramo.entity.homework.embedded.Status;
import com.gramo.gramo.entity.homework.embedded.Term;
import com.gramo.gramo.entity.homework.embedded.Term.TermBuilder;
import com.gramo.gramo.payload.request.HomeworkRequest;
import com.gramo.gramo.payload.response.MyHomeworkResponse;
import com.gramo.gramo.payload.response.MyHomeworkResponse.MyHomeworkResponseBuilder;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-22T12:37:32+0900",
    comments = "version: 1.4.0.Beta2, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.7.1.jar, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class HomeworkMapperImpl implements HomeworkMapper {		// MapperClassImpl처럼 뒤에 impl 이 붙는 형태로 생성된다.

    @Override
    public MyHomeworkResponse toHomeworkResponse(Homework homework, String studentName, String teacherName) {
        if ( homework == null && studentName == null && teacherName == null ) {
            return null;
        }		// 셋 다 null이면 null 반환

        MyHomeworkResponseBuilder myHomeworkResponse = MyHomeworkResponse.builder();

        if ( homework != null ) {
            myHomeworkResponse.homeworkId( homework.getId() );
            myHomeworkResponse.startDate( homeworkTermStartDate( homework ) );
            myHomeworkResponse.endDate( homeworkTermEndDate( homework ) );
            myHomeworkResponse.isRejected( homeworkStatusIsRejected( homework ) );
            myHomeworkResponse.description( homework.getDescription() );
            myHomeworkResponse.title( homework.getTitle() );
            myHomeworkResponse.major( homework.getMajor() );
        }
        if ( studentName != null ) {
            myHomeworkResponse.studentName( studentName );
        }
        if ( teacherName != null ) {
            myHomeworkResponse.teacherName( teacherName );
        }

        return myHomeworkResponse.build();
    }

    @Override
    public Homework toHomework(HomeworkRequest request, String teacherEmail) {	// 파라미터를 모두 넣지 않아도 작동한다.
        if ( request == null && teacherEmail == null ) {
            return null;
        }

        HomeworkBuilder homework = Homework.builder();

        if ( request != null ) {
            homework.term( homeworkRequestToTerm( request ) );
            homework.description( request.getDescription() );
            homework.studentEmail( request.getStudentEmail() );
            homework.title( request.getTitle() );
            homework.major( request.getMajor() );
        }
        if ( teacherEmail != null ) {
            homework.teacherEmail( teacherEmail );
        }

        return homework.build();
    }

    private LocalDate homeworkTermStartDate(Homework homework) {
        if ( homework == null ) {
            return null;
        }
        Term term = homework.getTerm();
        if ( term == null ) {
            return null;
        }
        LocalDate startDate = term.getStartDate();
        if ( startDate == null ) {
            return null;
        }
        return startDate;
    }

    private LocalDate homeworkTermEndDate(Homework homework) {	// Homework의 Endate를 추출해 주는 메소드
        if ( homework == null ) {
            return null;
        }
        Term term = homework.getTerm();
        if ( term == null ) {
            return null;
        }
        LocalDate endDate = term.getEndDate();
        if ( endDate == null ) {
            return null;
        }
        return endDate;
    }

    private Boolean homeworkStatusIsRejected(Homework homework) {
        if ( homework == null ) {
            return null;
        }
        Status status = homework.getStatus();
        if ( status == null ) {
            return null;
        }
        Boolean isRejected = status.getIsRejected();
        if ( isRejected == null ) {
            return null;
        }
        return isRejected;
    }

    protected Term homeworkRequestToTerm(HomeworkRequest homeworkRequest) {
        if ( homeworkRequest == null ) {
            return null;
        }

        TermBuilder term = Term.builder();

        term.endDate( homeworkRequest.getEndDate() );

        return term.build();
    }
}
```

