package com.util;

import com.entities.EventEntity;
import com.entities.LocationEntity;
import com.entities.ProgramEntity;
import com.model.EventInput;
import com.model.LocationInput;
import com.model.ProgramInput;

import java.time.LocalDateTime;

public class ConferenceUtil {

    public static EventInput getEvent() {
        return EventInput.builder()
                .name("Event1")
                .locationInput(getLocation())
                .programInput(getProgram())
                .build();
    }

    public static LocationInput getLocation() {
        return LocationInput.builder()
                .country("Romania")
                .city("Cluj-Napoca")
                .build();
    }

    public static ProgramInput getProgram() {
        return ProgramInput.builder()
                .interval("09:00-18:00")
                .date(LocalDateTime.parse("2019-05-12T13:20"))
                .abstractDeadline(LocalDateTime.parse("2019-05-13T13:20"))
                .biddingDeadline(LocalDateTime.parse("2019-05-14T13:20"))
                .proposalDeadline(LocalDateTime.parse("2019-05-15T13:20"))
                .build();
    }

    public static EventEntity event(){
        return EventEntity.builder()
                .id(1)
                .name("Event")
                .location(location())
                .program(program())
                .build();
    }

    public static LocationEntity location() {
        return LocationEntity.builder()
                .id(1)
                .country("Romania")
                .city("Cluj-Napoca")
                .build();
    }

    public static ProgramEntity program() {
        return ProgramEntity.builder()
                .id(1)
                .interval("09:00-18:00")
                .date(LocalDateTime.parse("2019-05-12T13:20"))
                .abstractDeadline(LocalDateTime.parse("2019-05-13T13:20"))
                .biddingDeadline(LocalDateTime.parse("2019-05-14T13:20"))
                .proposalDeadline(LocalDateTime.parse("2019-05-15T13:20"))
                .build();
    }
}
