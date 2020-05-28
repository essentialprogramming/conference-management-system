package com.mapper;

import com.entities.UserEntity;
import com.model.UserInput;


public class UserMapper {

    public static UserInput entityToUser(UserEntity entity) {
        return UserInput.builder()
                .email(entity.getEmail())
                .build();
    }

    public static UserEntity userToEntity(UserInput userInput) {
        return UserEntity.builder()
                .email(userInput.getEmail())
                .build();
    }


}
