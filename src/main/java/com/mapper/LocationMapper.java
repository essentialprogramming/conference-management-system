package com.mapper;

import com.entities.LocationEntity;
import com.model.Location;

public class LocationMapper {

    public static LocationEntity locationToEntity(Location location) {
        return LocationEntity.builder()
                .country(location.getCountry())
                .city(location.getCity())
                .build();
    }

    public static Location entityToLocation(LocationEntity entity) {
        return Location.builder()
                .id(entity.getId())
                .country(entity.getCountry())
                .city(entity.getCity())
                .build();
    }
}
