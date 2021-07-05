package com.webflux.auth.domain.user.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {

    private List<UserResponse> userResponses;

}
