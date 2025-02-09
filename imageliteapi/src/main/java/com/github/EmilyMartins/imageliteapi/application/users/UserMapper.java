package com.github.EmilyMartins.imageliteapi.application.users;

import org.springframework.stereotype.Component;

import com.github.EmilyMartins.imageliteapi.domain.entity.User;

@Component
public class UserMapper {

    public User mapToUser(UserDTO dto){
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
    }
}