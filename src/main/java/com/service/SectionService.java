package com.service;

import com.entities.SectionEntity;
import com.mapper.SectionMapper;
import com.model.Section;
import com.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SectionService {

    private SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Transactional
    public Section addSection(Section section) {
        SectionEntity entity = SectionMapper.sectionToEntity(section);

        return SectionMapper.entityToSection(sectionRepository.save(entity));
    }
}
