# 현재 있는 exception들
- 토큰에서 user 가져오고 없으면 오류
- 보고서를 id로 찾고 없으면 오류
- 팀을 보고서id로 찾고 없으면 오류

# 내가 해야 할 exception 내기
- 이 보고서의 access가 admin이고 내 보고서가 아니면 오류
- 이 보고서의 access가 admin이고 내 보고서이면 통과
- 이 보고서의 access가 user이고 나는 토큰이 없으면 오류
- 이 보고서의 access가 user이고 나는 토큰이 있으면 통과
- 이 보고서의 access가 every면 무조건 통과



# 현재 문제점
- token
    - 지금 user에서 토큰을 가져올 때, 만약 토큰이 없으면 exception 처리하게 해놨음
    - 만약 이걸 그대로 두면 비회원이 로그인했을 때 처리하도록 메소드 또 파야됨
    - 근데 이걸 Optional이랑 boolean으로 싹다 바꿔먹을라니까 user에서 orElseThrow를 빼니까 Optional을 붙이니 어떻게 하는지 모름
- access
    - 지금 admin이고, user이고 모두 isMine이라는 boolean값은 필수적임
    - 근데 이걸 다 find를 각각 하면 코드가 매우 더러움
    - 근데 내 맘대로 boolean을 선언하는건 좀 무서움
    - 그리고 user일 때, 토큰이 존재하는지를 검사해야 함.
    - 근데 위에서 말했듯이 만약 토큰이 존재하지 않으면 애초에 오류가 남;

# 내 해결법
- 일단 user은 그대로 exception 처리하게 냅둠. 이건 뭐 미래의 내가 알아서 비회원 보고서 보기 만들겠지
- access에서 isMine은 boolean 만들어서 isEmpty로 찾게 만들었음
- 