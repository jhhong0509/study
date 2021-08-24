//package com.study.todyproject.oauthdemo.security.oauth;
//
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
// 필요하다면 사용해야 할 수 있지만 Naver밖에 없는데다, OAuth2UserService의 attributes로도 충분해서 사용하지 않았다.
//@Getter
//@Builder(access = AccessLevel.PRIVATE)
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
//public class OAuth2Attributes {
//    private Map<String, Object> attributes;
//
//    public static Map<String, Object> ofNaver(String attributeKey, Map<String, Object> attributes) {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");        // Naver는 response에 담겨서 객체가 옴
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", attributeKey);        // attributeKey는 여러 OAuthService들을 구분하기 위한 이름
//        map.put("key", attributeKey);
//        map.put("name", response.get("name"));
//        map.put("email", response.get("email"));
//        map.put("picture", response.get("profile_image"));
//        return map;
//    }
//
//}
