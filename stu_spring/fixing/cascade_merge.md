# CascadeType.MERGE

### 발생 이유

CascadeType을 PERSIST로 지정해 두었는데, detached 상태의 엔티티를 수정하려 하면 아래와 같은 오류가 발생한다.

`detached entity passed to persist`

발생 이유는 detached 상태 즉 영속성 컨텍스트로부터 분리된 상태의 엔티티를 수정하려 했기 때문이다.

### 해결 과정

간단하게 CascadeType 타입을 PERSIST에서 MERGE로 바꿔 주면 된다.

### 후기

CascadeType을 제대로 공부하지 않았던게 느껴졌다.

### 주의

### 기타 지식

- MERGE란?

  영속성 전이에서 MERGE는 어떤 의미일까?

  MERGE는 **부모가 영속성 컨텍스트로부터 분리 상태(detached)에서 관리되는 상태(managed)로 변할 때, 자식도 함께 변하는 영속성 전이의 한 종류이다.**

만약 save를 수행하게 되면 **연관된 엔티티들이 DETACHED 상태가 된다.**

그렇기 때문에 만약 CascadeType.ALL 을 사용했다면, 자신도 모르게 영속정 전이가 발생하게 되어 원하지 않는 작업이 일어나게 된다.

> CascadeType을 사용할 때 ALL을 사용하지 말라는게 이런 이유 때문이다.