package com.mapper;

import com.entities.CommitteeMemberEntity;
import com.entities.EventEntity;
import com.entities.UserEntity;
import com.model.EventInput;
import com.output.EventJSON;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventEntity eventToEntity(EventInput eventInput) {
        return EventEntity.builder()
                .name(eventInput.getName())
                .build();
    }

    public static EventJSON entityToEvent(EventEntity entity) {
        return EventJSON.builder()
                .id(entity.getId())
                .name(entity.getName())
                .programCommittee(entity.getProgramCommittee() != null ? entity.getProgramCommittee().stream().map(CommitteeMemberEntity::getEmail).collect(Collectors.toList()) : new ArrayList<>())
                .participants(entity.getParticipants() != null ? entity.getParticipants().stream().map(UserEntity::getEmail).collect(Collectors.toList()) : new ArrayList<>())
                .speakers(entity.getSpeakers() != null ? entity.getSpeakers().stream().map(UserEntity::getEmail).collect(Collectors.toList()) : new ArrayList<>())
                .program(ProgramMapper.entityToProgram(entity.getProgram()))
                .location(LocationMapper.entityToLocation(entity.getLocation()))
                .build();
    }
}
