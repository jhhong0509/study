package com.study.todyproject.oauthdemo.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.study.todyproject.oauthdemo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.createToken(oAuth2User.getAttribute("email"), "access");
        String refreshToken = jwtTokenProvider.createToken(oAuth2User.getAttribute("email"), "refresh");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("access-token", accessToken);
        objectNode.put("refresh-token", refreshToken);
        response.getWriter().println(objectNode);
    }
}
