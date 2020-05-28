package com.mapper;

import com.entities.EventEntity;
import com.model.EventInput;
import com.output.EventJSON;

public class EventMapper {

    public static EventEntity eventToEntity(EventInput eventInput){
        return EventEntity.builder()
                .name(eventInput.getName())
                .build();
    }

    public static EventJSON entityToEvent(EventEntity entity){
        return EventJSON.builder()
                .id(entity.getId())
                .name(entity.getName())
//                .programCommittee(entity.getProgramCommittee().stream().map(UserEntity::getEmail).collect(Collectors.toList()))
                //.participants(entity.getParticipants().stream().map(UserEntity::getEmail).collect(Collectors.toList()))
                //.speakers(entity.getSpeakers().stream().map(UserEntity::getEmail).collect(Collectors.toList()))
                .program(ProgramMapper.entityToProgram(entity.getProgram()))
                .location(LocationMapper.entityToLocation(entity.getLocation()))
                .build();
    }
}
