package com.study.todyproject.oauthdemo.security.oauth;

import com.study.todyproject.oauthdemo.security.jwt.AuthDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {       // 요청/반환 타입

    private final OAuth2Service oAuth2Service;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = oAuth2Service.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 서비스의 이름
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();     // 해당 계정의 PK와 같은 역할을 하는 필드의 이름.
        userRequest.getAccessToken();

        return new DefaultOAuth2User(new ArrayList<>(),         // 권한
                oAuth2User.getAttributes(),                     // attributes
                userNameAttributeName);                         // 필드 이름
    }

}
