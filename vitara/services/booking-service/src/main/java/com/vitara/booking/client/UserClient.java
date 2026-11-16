package com.vitara.booking.client;

import com.vitara.common.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface UserClient {

    @GetMapping("/api/users/{userId}")
    UserDto getUserById(
            @PathVariable Long userId);
}
