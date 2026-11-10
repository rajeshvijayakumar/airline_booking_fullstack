package com.vitara.user.mapper;

import com.vitara.common.dto.UserDto;
import com.vitara.user.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDTO(User user) {
        if(user == null) {return null;}
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .build();
    }

    public static List<UserDto> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
