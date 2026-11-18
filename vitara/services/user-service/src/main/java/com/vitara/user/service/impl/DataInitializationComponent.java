package com.vitara.user.service.impl;

import com.vitara.common.enums.UserRole;
import com.vitara.user.entities.User;
import com.vitara.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }

    private void initializeAdminUser(){
        String email="codewithrajesh@gmail.com";
        String password="codewithrajesh";

        if(userRepository.findByEmail(email)==null){
            User adminUser= User
                    .builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .fullName("rajesh")
                    .role(UserRole.ROLE_SYSTEM_ADMIN)
                    .build();

            userRepository.save(adminUser);
        }
    }
}
