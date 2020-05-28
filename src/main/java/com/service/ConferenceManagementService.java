package com.service;


import com.entities.CommitteeMemberEntity;
import com.entities.EventEntity;
import com.entities.ProgramEntity;
import com.entities.SectionEntity;
import com.mapper.EventMapper;
import com.mapper.LocationMapper;
import com.mapper.ProgramMapper;
import com.mapper.SectionMapper;
import com.model.EventInput;
import com.model.ProgramInput;
import com.model.SectionInput;
import com.output.EventJSON;
import com.output.LocationJSON;
import com.output.ProgramJSON;
import com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConferenceManagementService {

    private ConferenceRepository eventRepository;
    private PCMemberRepository userRepository;
    private SectionRepository sectionRepository;
    private PCMemberRepository pcMemberRepository;

    @Autowired
    public ConferenceManagementService(ConferenceRepository eventRepository, PCMemberRepository userRepository, SectionRepository sectionRepository, PCMemberRepository pcMemberRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.pcMemberRepository = pcMemberRepository;
    }

    // ------------------------------  Event management ------------------------------

    @Transactional
    public EventJSON addEvent(EventInput eventInput) {
        EventEntity entity = EventMapper.eventToEntity(eventInput);
        entity.setProgram(ProgramMapper.programToEntity(eventInput.getProgramInput()));
        entity.setLocation(LocationMapper.locationToEntity(eventInput.getLocationInput()));

        return EventMapper.entityToEvent(eventRepository.save(entity));
    }

    @Transactional
    public EventJSON findEventById(int id) {
        EventEntity existingEvent = eventRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        return EventMapper.entityToEvent(existingEvent);
    }

    @Transactional
    public EventJSON updateEventProgram(int eventId, ProgramInput programInput) {
        EventEntity existingEvent = eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        ProgramEntity existingProgram = existingEvent.getProgram();
        ProgramMapper.updateProgram(existingProgram, programInput);
        existingEvent.setProgram(existingProgram);
        eventRepository.save(existingEvent);

        return EventMapper.entityToEvent(existingEvent);
    }

    @Transactional
    public List<EventJSON> getAllEvents() {
        return eventRepository.findAll().stream().map(EventMapper::entityToEvent).collect(Collectors.toList());
    }


    // ------------------------------  Section management ------------------------------

    @Transactional
    public SectionInput addSection(int eventId, SectionInput sectionInput) {
        SectionEntity sectionEntity = SectionMapper.sectionToEntity(sectionInput);
        sectionEntity.setEvent(eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!")));

        return SectionMapper.entityToSection(sectionRepository.save(sectionEntity));
    }

    @Transactional
    public void deleteSection(int id) {

        SectionEntity existingSection = sectionRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section with id " + id + " not found!"));
        sectionRepository.delete(existingSection);
    }


    @Transactional
    public SectionInput assignSupervisor(int sectionId, String email) {
        SectionEntity existingSection = sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section with id " + sectionId + " not found!"));
        existingSection.setSupervisor(userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!")));

        sectionRepository.save(existingSection);
        return SectionMapper.entityToSection(existingSection);
    }

    @Transactional
    public void addProgramCommittee(String email) {
        CommitteeMemberEntity committeeMemberEntity = new CommitteeMemberEntity(email);

        pcMemberRepository.save(committeeMemberEntity);
    }
}
