package com.webflux.auth.service;

import com.webflux.auth.domain.user.entity.User;
import com.webflux.auth.domain.user.entity.UserRepository;
import com.webflux.auth.domain.user.exception.UserAlreadyExistException;
import com.webflux.auth.domain.user.payload.request.CreateUserRequest;
import com.webflux.auth.domain.user.service.UserService;
import com.webflux.auth.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
@DisplayName("User 서비스 단위 테스트")
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private UserService service;

    @BeforeEach
    void setUp() {
        this.service = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Create User 성공 테스트")
    void createUserSuccess() {
        CreateUserRequest request =  CreateUserRequest.builder()
                .email("email")
                .password("password")
                .build();
        service.createUser(request)
                .onErrorMap(throwable -> {throw new UserAlreadyExistException();})
                .subscribe();
    }

    @Test
    @DisplayName("Create User 이미 존재하는 유저 Exception 발생")
    void createUserFailureTest() {

        User user = User.builder()
                .email("email")
                .password("asdf")
                .build();

        when(userRepository.save(user).thenReturn(user));

        CreateUserRequest request =  CreateUserRequest.builder()
                .email("email")
                .password("password")
                .build();

        StepVerifier.create(request)
                .thenConsumeWhile(result-> {
                    Assertions.assertEquals(result.getName(),"쿠앤크");
                    Assertions.assertEquals(result.getPrice(),1000);
                    return true;
                })
                .verifyComplete();

    }

}
