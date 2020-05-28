package com.mapper;

import com.entities.LocationEntity;
import com.model.LocationInput;
import com.output.LocationJSON;
import com.service.LocationService;

public class LocationMapper {

    public static LocationEntity locationToEntity(LocationInput locationInput) {
        return LocationEntity.builder()
                .country(locationInput.getCountry())
                .city(locationInput.getCity())
                .build();
    }

    public static LocationJSON entityToLocation(LocationEntity entity) {
        return LocationJSON.builder()
                .id(entity.getId())
                .country(entity.getCountry())
                .city(entity.getCity())
                .weather(LocationService.weather(entityToInput(entity)))
                .build();
    }

    public static LocationInput entityToInput(LocationEntity entity) {
        return LocationInput.builder()
                .city(entity.getCity())
                .country(entity.getCountry())
                .build();
    }

}
