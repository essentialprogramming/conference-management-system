package com.mapper;

import com.entities.ApplicationUser;
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

    public static User accountToUser(ApplicationUser appU)
    {
        return User.builder()
                .email(appU.getUserName())
                .build();
    }

}
