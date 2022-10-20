## ConfigMap과 Secret의 차이

### ConfigMap이란?

Kubernetes에서 사용되는 키 - 값이다.

비밀번호나 API Key처럼 중요 데이터를 저장하는데에 사용되지는 않고, 환경변수나 이름 그대로 설정 파일들을 저장하는데에 주로 사용된다.

ConfigMap은 런타임에 간편하게 애플리케이션에게 값을 전달하기 위한 기능이다.

각 환경마다 정적으로 Manifest를 정의하는게 아니라, 환경별로 설정을 다르게 할 수 있다.

하지만 ConfigMap을 사용할 때에는 몇가지 고려할 사항들이 있다.

- 데이터가 개발, 운영환경마다 바뀌는가?
- AWS나 Azure 등 실행중인 다른 환경이 있는가?
- 값이 변할 가능성이 있는가?

<br>

간단한 예제를 보자.

``` yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: testConfigMap # ConfigMap의 이름
data:
  profile: production # key: profile, value: production
  maintainer: HongJeongHyeon
```

## Secrets

Secrets는 이름 그대로 알려지면 안되는 비밀번호나 API Key와 같은 정보를 위한 Kubernetes 리소스이다.

ConfigMap과 동작도, 역할도 비슷하지만 가장 큰 차이점은 **기밀성**이다.

ConfigMap은 Plain Text로 데이터를 저장하지만, Secrets는 Base64 인코딩되어 저장된다.

Secrets는 환경변수로, 볼륨에 마운트된 파일로, 도커에 로그인 할 때 처럼 다양하게 활용될 수 있다.

> Kubernetes In Action에서 Secrets는 환경변수로 사용하지 않기를 권장한다.
>
> 어떤 경로에서든 로그 등에 남을 가능성이 있기 때문이다.

하지만 기본적으로 Kubernetes의 etcd에 저장될 때에는 암호화되지 않은채로 저장된다.

즉, **API에 간접적으로라도 접근할 권한이 있는 사용자는 Secret을 조회 및 수정할 수 있다.**

모든 데이터는 Base64로 인코딩 되어 저장되지만, 사용할 땐 디코딩된 데이터를 제공해준다.

또한 디스크에 시크릿을 적재하는건 위험할 수 있기 때문에 시크릿은 tmpfs라는 메모리에 상주시킨다.