package com.service;

import com.entities.SectionEntity;
import com.mapper.SectionMapper;
import com.model.Section;
import com.repository.EventRepository;
import com.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class SectionService {

    private SectionRepository sectionRepository;
    private EventRepository eventRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository, EventRepository eventRepository) {
        this.sectionRepository = sectionRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Section addSection(Section section) {
        SectionEntity entity = SectionMapper.sectionToEntity(section);
        entity.setEvent(eventRepository.findById(section.getEventId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!")));

        return SectionMapper.entityToSection(sectionRepository.save(entity));
    }
}
