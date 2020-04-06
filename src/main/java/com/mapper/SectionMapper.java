package com.mapper;

import com.entities.SectionEntity;
import com.model.Section;

public class SectionMapper {

    public static SectionEntity sectionToEntity(Section section) {
        return SectionEntity.builder()
                .name(section.getName())
                .build();
    }

    public static Section entityToSection(SectionEntity entity) {
        return Section.builder()
                .id(entity.getId())
                .name(entity.getName())
                .eventId(entity.getEvent().getId())
                .supervisorEmail(entity.getSupervisor().getEmail())
                .build();
    }
}
