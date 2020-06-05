package com.mapper;

import com.entities.SectionEntity;
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
                .papers(entity.getPapers() != null ? entity.getPapers()
                        .stream()
                        .map(p -> p.getTitle())
                        .collect(Collectors.toList()) : new ArrayList<>())
                .speakers(Utils.getSpeakers(entity))
                .build();
    }
}
