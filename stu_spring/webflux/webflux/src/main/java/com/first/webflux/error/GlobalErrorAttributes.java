package com.first.webflux.error;

import com.first.webflux.exception.TestAlreadyExistException;
import com.first.webflux.exception.TestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> map = new HashMap<>();
        Throwable error = getError(request);
        if (error instanceof GlobalException) {
            GlobalException exception = (GlobalException) error;
            map.put("error", exception.getMessage());
            map.put("code", exception.getErrorCode());
        } else {
            map.put("error", "Unknown Error");
            map.put("code", 500);
        }
        return map;

    }

}
