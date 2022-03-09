# CodeCoverage

## CodeCoverage란

CodeCoverage란 **테스트 케이스가 얼마나 충족되었는지 나타내는 지표**중 하나이다.

테스트가 실행되었을 때 **코드가 얼마나 실행되었는지**를 나타내 준다.

### 어떤 기준으로 측정되는가?

코드 커버리지는 **화이트 박스 테스트**를 통해 측정된다.

먼저 코드는 3가지 구조로 이루어져 있다.

#### 구문

**코드 한 줄이 한 번 이상 실행**되면 충족된다.

``` java
void foo (int x) {
    system.out("start line"); // 1번
    if (x > 0) { // 2번
        system.out("middle line"); // 3번
    }
    system.out("last line"); // 4번
}
```

위와 같은 코드가 있다고 가정해보자.

메소드의 파라미터로 `x = -1`을 넣어주게 되면 **3번 구문은 실행되지 못한다.**

즉 4 줄 중 3줄이 실행되므로 **Test Coverage는 75%**가 된다.

<br>

#### 조건

**모든 조건식의 내부 조건문들이 true/false가 실행**된다면 충족된다.

``` java
void foo (int x, int y) {
    system.out("start line"); // 1번
    if (x > 0 && y < 0) { // 2번
        system.out("middle line"); // 3번
    }
    system.out("last line"); // 4번
}
```

즉, **각각의 `x > 0`, `y < 0` 조건이 true/false인 경우**를 충족하게 되면 인정된다.

하지만 각각의 조건문에 대한 검증만 이루어지기 때문에 **전체적인 if문을 검증하지 않는다.**

따라서 **조건을 기준으로 테스트를 진행할 경우 구문/결정을 충족하지 않을수도 있다.**

<br>

#### 결정

> Branch Coverage 라고도 부른다.

**모든 조건식이 true/false가 실행**된다면 충족된다.

``` java
void foo (int x, int y) {
    system.out("start line"); // 1번
    if (x > 0 && y < 0) { // 2번
        system.out("middle line"); // 3번
    }
    system.out("last line"); // 4번
}
```

즉, **`x > 0 && y < 0` 조건이 true/false인 경우**를 충족하게 되면 인정된다.

<br>

위에서 소개한 방법 중에서는 구문 커버리지가 가장 많이 사용되는데, **로직의 시나리오에 대한 테스트에 가장 가까운 방법**이기 때문이다.

조건문이 없다면 다른 커버리지에서는 **아예 테스트를 수행하지 않는다.**

반대로 구문 커버리지는 **if문 내부가 실행되었는지 테스트**한다. 따라서 **If문 내부의 코드가 정상적으로 동작**한다는 것 까지는 보장해 준다.

## 왜 필요한가?

테스트 커버리지의 필요성은 테스트 코드의 필요성과 같다고 생각하면 된다.

우리는 테스트 코드를 작성함으로써 **제품의 안정성을 높여주고, Side Effect를 줄여준다.**

테스트 커버리지는 **테스트 코드가 얼마나 잘 짜여졌는지 검증**해준다.

# Jacoco

## Jacoco란?

**Java Code Coverage**의 약자로, Java 기반 Application의 테스트 커버리지를 측정해 주는 툴 이다.

다른 툴 보다 **자료가 많고, 사용하기가 쉽다.**

## Jacoco 사용하기

### 기본 설정

```kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
    jacoco	// 1
    groovy
}

.
.
.

jacoco {					// 2
    toolVersion = "0.8.7"
    reportsDirectory.set(file("$buildDir/customJacocoReportDir"))
}

tasks.jacocoTestReport {	// 3
    reports {
        html.required.set(true)
        xml.required.set(false)
        csv.required.set(false)
    }
}
```

1. **jacoco plugin 추가**

2. **jacoco 기본 설정 추가**

   [버전 확인](https://www.eclemma.org/jacoco/)

   또한, jacoco가 만들 Report의 위치를 설정해줄 수 있다.

   기본값은 `$project.reporting.baseDir/jacoco`이다.

3. **output 설정**

   Report를 어떤 타입으로 출력할지 결정할 수 있다.

   참고로 여기서 파일별 저장 경로를 설정해줄 수도 있다.

### jacocoTestCoverageVerification Task

여기서는 **최소 Coverage**를 설정해 줄 수 있는데, **이 조건을 만족하지 못하면 Task는 실패**하게 된다.

```kotlin
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            // 'element'가 없으면 프로젝트의 전체 파일을 합친 값을 기준으로 한다.
            limit {
                // 'counter'를 지정하지 않으면 default는 'INSTRUCTION'
                // 'value'를 지정하지 않으면 default는 'COVEREDRATIO'
                minimum = "0.30".toBigDecimal()
            }
        }

        rule {
            // 룰을 간단히 켜고 끌 수 있다.
            enabled = true

            // 룰을 체크할 단위는 클래스 단위
            element = "CLASS"

            // 브랜치 커버리지를 최소한 90% 만족시켜야 한다.
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }

            // 라인 커버리지를 최소한 80% 만족시켜야 한다.
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }

            // 빈 줄을 제외한 코드의 라인수를 최대 200라인으로 제한한다.
            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "200".toBigDecimal()
            }

            // 커버리지 체크를 제외할 클래스들
            excludes = listOf(
//                    "*.test.*",
            )
        }
    }
}
```

#### **element**

Coverage를 측정할 단위로, 6가지 종류가 있다.

1. **BUNDLE:** 프로젝트 파일을 모두 합친 것
2. **CLASS:** 클래스
3. **GROUP:** 논리적인 그룹
4. **METHOD:** 메소드
5. **PACKAGE:** 패키지
6. **SOURCEFILE:** 소스 파일

Default 값은 BUNDLE이다.

#### include

**rule의 적용 대상을 패키지 단위로 설정**할 수 있다.

예를들어 auth 패키지 내부의 rule을 설정하고 싶다면 다음과 같이 하면 된다.

```kotlin
includes = mutableListOf("com.example.forsubmit.auth.*")
```

#### Counter

limit 메소드를 통해 지정할 수 있고, **커버리지 측정 최소 단위**이다.

- **BRANCH:** 조건문들의 수
- **CLASS:** 클래스의 수로, 내부에 있는 메소드가 한 번이라도 실행되면 실행된걸로 간주한다.
- **COMPLEXIBLE:** 복잡성 측정
- **INSTRUCTION:** Java 바이트코드의 수를 측정한다. 코드의 Format에 구애받지 않는다.
- **LINE:** 빈 줄을 제외한 줄 수를 센다. 코드의 Format에 따라 Coverage가 바뀌게 된다.
- **METHOD:** 실행된 메소드의 수를 센다.

기본 값은 INTRODUCTION이다.

#### Value

**Coverage를 어떤 식으로 표기할지에 대한 설정**이다.

- **COVEREDCOUNT:** Cover된 개수
- **COVEREDRATIO:** 커버된 비율, 0부터 1사이의 숫자로 1이 100%이다.
- **MISSEDCOUNT:** 커버되지 않은 개수
- **MISSEDRATIO:** 커버되지 않은 비율, 0부터 1사이의 숫자로 1이 100%이다.
- **TOTALCOUNT:** 전체 개수

기본 값은 COVEREDRATIO이다.

#### minimum

minimum은 **counter값의 최솟값**을 의미한다.

만약 이 조건을 충족하지 못하면 `jacocoTestCoverageVerification`가 실패하게 된다.

<br>

주의할 점은 **표기한 자릿수 만큼 value가 출력**된다.

즉 여기서 `0.800`을 minimum으로 설정하게 되면 `0.721`과 같이 표시되고, `0.8`로 설정하게 되면 `0.7`과 같이 표시된다.

#### excludes

커버리지를 측정할 때 불가피한 이유로 **특정 패키지를 제외**해야할 상황이 있을 수도 있다.

``` kotlin
excludes = listOf("*.test.*")
```

<br>

### Task간의 의존성

`jacocoTestReport` Task가 완료되어 Report 파일이 생기기 전에 `test` Task가 실행되어야 하고, `jacocoTestReport` Task 이후에 `jacocoTestCoverageVerification`을 실행해야 한다.

즉 Task들의 실행 순서는 다음과 같다.

1. test
2. jacocoTestReport
3. jacocoTestCoverageVerification

이제 `finalizedBy`를 통해 의존성을 추가해야 한다.

``` kotlin
finalizedBy '테스크 명'
```

위와 같이 하게되면 task 이후에 해당 task를 실행하겠다는 의미이다.

[코드 분석 적용기](https://velog.io/@lxxjn0/%EC%BD%94%EB%93%9C-%EB%B6%84%EC%84%9D-%EB%8F%84%EA%B5%AC-%EC%A0%81%EC%9A%A9%EA%B8%B0-2%ED%8E%B8-JaCoCo-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0)

[우아한 형제들 기술 블로그](https://techblog.woowahan.com/2661/)