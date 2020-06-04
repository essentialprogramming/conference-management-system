package com.mapper;

import com.entities.LocationEntity;
import com.model.LocationInput;
import com.output.LocationJSON;
import com.service.LocationService;

public class LocationMapper {

    public static LocationEntity locationToEntity(LocationInput location) {
        return LocationEntity.builder()
                .country(location.getCountry())
                .city(location.getCity())
                .build();
    }

    public static LocationJSON entityToLocation(LocationEntity entity) {
        return LocationJSON.builder()
                .id(entity.getId())
                .country(entity.getCountry())
                .city(entity.getCity())
                .weather(LocationService.weather(entity))
                .build();
    }

    public static LocationEntity updateLocation(LocationEntity entity, LocationInput location){
        entity.setCountry(location.getCountry());
        entity.setCity(location.getCity());
        return entity;
    }
}
