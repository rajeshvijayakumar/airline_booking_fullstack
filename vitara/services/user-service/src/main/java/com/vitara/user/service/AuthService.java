package com.vitara.user.service;

import com.vitara.common.dto.UserDto;
import com.vitara.common.request.SignUpRequest;
import com.vitara.common.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password) throws Exception;
    AuthResponse signup(SignUpRequest req) throws Exception;
}
