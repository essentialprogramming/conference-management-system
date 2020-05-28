package com.mapper;

import com.entities.SectionEntity;
import com.model.SectionInput;

public class SectionMapper {

    public static SectionEntity sectionToEntity(SectionInput sectionInput) {
        return SectionEntity.builder()
                .name(sectionInput.getName())
                .build();
    }

    public static SectionInput entityToSection(SectionEntity entity) {
        return SectionInput.builder()
                .id(entity.getId())
                .name(entity.getName())
                .eventId(entity.getEvent().getId())
                .supervisorEmail(entity.getSupervisor() != null ? entity.getSupervisor().getEmail() : null)
                .build();
    }
}
