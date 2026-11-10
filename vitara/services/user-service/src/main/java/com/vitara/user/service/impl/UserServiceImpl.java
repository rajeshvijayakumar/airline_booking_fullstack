package com.vitara.user.service.impl;

import com.vitara.common.dto.UserDto;
import com.vitara.user.entities.User;
import com.vitara.user.mapper.UserMapper;
import com.vitara.user.repository.UserRepository;
import com.vitara.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Override
    public UserDto getUserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("user not found with email");
        }

        return UserMapper.toDTO(user);
    }

    @Override
    public UserDto getUserById(Long id) throws Exception {
        User user=  userRepository.findById(id).orElseThrow(
                ()->new Exception("user not found with id "+id)
        );
        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=userRepository.findAll();
        return UserMapper.toDTOList(users);
    }
}
