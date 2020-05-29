package com.mapper;

import com.entities.ProfileEntity;
import com.model.ProfileInput;

public class ProfileMapper {

    public static ProfileEntity profileToEntity(ProfileInput input) {
        return ProfileEntity.builder()
                .username(input.getUsername())
                .password(input.getPassword())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .build();
    }

    public static ProfileInput inputToEntity(ProfileEntity entity) {
        return ProfileInput.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }
}
