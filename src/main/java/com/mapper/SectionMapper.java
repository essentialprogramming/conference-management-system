package com.mapper;

import com.entities.SectionEntity;
import com.entities.UserEntity;
import com.model.SectionInput;
import com.output.SectionJSON;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SectionMapper {

    public static SectionEntity sectionToEntity(SectionInput sectionInput) {
        return SectionEntity.builder()
                .name(sectionInput.getName())
                .build();
    }

    public static SectionJSON entityToSection(SectionEntity entity) {
        return SectionJSON.builder()
                .id(entity.getId())
                .name(entity.getName())
                .eventId(entity.getEvent().getId())
                .supervisorEmail(entity.getSupervisor() != null ? entity.getSupervisor().getEmail() : null)
                .attendees(entity.getAttendees() != null ? entity.getAttendees().stream().map(UserEntity::getEmail).collect(Collectors.toList()) : new ArrayList<>())
                .build();
    }
}
