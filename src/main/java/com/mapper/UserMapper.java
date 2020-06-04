package com.mapper;

import com.entities.UserEntity;
import com.model.User;


public class UserMapper {

    public static User entityToUser(UserEntity entity) {
        return User.builder()
                .email(entity.getEmail())
                .build();
    }

    public static UserEntity userToEntity(User userInput) {
        return UserEntity.builder()
                .email(userInput.getEmail())
                .build();
    }


}
