package com.webflux.auth.global.validation;

import com.webflux.auth.domain.blog.exception.QueryParameterNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueryParameter {

    public Map<String, String> getQueryParameter(ServerRequest request, String... name) {
        Map<String, String> map = new HashMap<>();
        for (String key : name) {
            String param = request.queryParam(key)
                    .orElseThrow(QueryParameterNotFoundException::new);
            map.put(key, param);
        }
        return map;
    }

}
