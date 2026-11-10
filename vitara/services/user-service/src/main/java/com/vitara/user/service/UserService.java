package com.vitara.user.service;

import com.vitara.common.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserByEmail(String email) throws Exception;
    UserDto getUserById(Long id) throws Exception;
}
