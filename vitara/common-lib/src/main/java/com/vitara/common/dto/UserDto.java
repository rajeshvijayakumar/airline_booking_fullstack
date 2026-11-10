package com.vitara.common.dto;


import com.vitara.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private LocalDateTime lastLogin;
}

