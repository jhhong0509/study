# 토큰의 진행 과정
### 토큰 진행할 때 어떠한 과정을 거치는지(pear기준)를 정리한 글이다. 개인적으로 내가 모르는것은 집중적으로 정리했다.
1. signInRequest의 값들을 받아온다.  
2. URI에 따라 authController로 간다.  
3. http method를 보고, 만약 post 메소드라면 signIn 메소드를 탄다  
4. authService(authServiceImpl)으로 간다.  
5. 일단 
``` java
@Value("${auth.jwt.exp.refresh}")
private Long refreshExp;
```
라는 구문을 통해 refreshExp 값을 가져온다.   
- refreshExp 라는 값은 yml의 auth밑의 jwt 밑의 exp 밑의 refresh 라는 값을 찾는다
- 이 값이 ${USER_NAME} 와 같은 형태라면, 서버의 환경변수에서 값을 찾는다.   
6. 그 외 다른 의존성 주입들을 가져온다.  
7. 그리고 이제 signIn 메소드로 들어간다.  
8. userRepository로 간다.  
9. request에서 userEmail을 꺼내와서 그걸로 user에 해당 userEmail이 있는지 확인한다.  
10. 만약 있다면 user가 반환된다. 없다면 throw를 통해 exception이 발생한다.  
11. 그러면 user에서 