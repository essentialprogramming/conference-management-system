package com.service;


import com.entities.EventEntity;
import com.entities.ProgramEntity;
import com.entities.SectionEntity;
import com.mapper.EventMapper;
import com.mapper.ProgramMapper;
import com.mapper.SectionMapper;
import com.model.Event;
import com.model.Program;
import com.model.Section;
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

    private EventRepository eventRepository;
    private UserRepository userRepository;
    private ProgramRepository programRepository;
    private LocationRepository locationRepository;
    private SectionRepository sectionRepository;

    @Autowired
    public ConferenceManagementService(EventRepository eventRepository, UserRepository userRepository, ProgramRepository programRepository, LocationRepository locationRepository, SectionRepository sectionRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.locationRepository = locationRepository;
        this.sectionRepository = sectionRepository;
    }

    // ------------------------------  Event management ------------------------------

    @Transactional
    public Event addEvent(Event event) {
        EventEntity entity = EventMapper.eventToEntity(event);
        entity.setProgramCommittee(userRepository.findAllById(event.getProgramCommittee()));
        entity.setParticipants(userRepository.findAllById(event.getParticipants()));
        entity.setSpeakers(userRepository.findAllById(event.getSpeakers()));
        entity.setProgram(programRepository.findById(event.getProgramId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Program not found!")));
        entity.setLocation(locationRepository.findById(event.getLocationId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Location not found!")));

        return EventMapper.entityToEvent(eventRepository.save(entity));
    }

    @Transactional
    public void deleteEvent(int id) {

        EventEntity existingEntity = findEventEntityById(id);
        eventRepository.delete(existingEntity);
    }

    private EventEntity findEventEntityById(int id) {
        return eventRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
    }

    @Transactional
    public Event findEventById(int id) {
        EventEntity existingEvent = findEventEntityById(id);
        return EventMapper.entityToEvent(existingEvent);
    }

    @Transactional
    public Event updateEventProgram(int eventId, int programId) {
        EventEntity existingEvent = findEventEntityById(eventId);
        ProgramEntity existingProgram = programRepository.findById(programId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Program with id " + programId + " not found!"));

        existingEvent.setProgram(existingProgram);
        eventRepository.save(existingEvent);

        return EventMapper.entityToEvent(existingEvent);
    }

    @Transactional
    public List<Event> getAllEvents() {
        return eventRepository.findAll().stream().map(EventMapper::entityToEvent).collect(Collectors.toList());
    }

    // ------------------------------  Program management ------------------------------

    @Transactional
    public Program addProgram(Program program) {
        ProgramEntity entity = ProgramMapper.programToEntity(program);
        return ProgramMapper.entityToProgram(programRepository.save(entity));
    }

    @Transactional
    public void deleteProgram(int id) {

        ProgramEntity existingProgram = findProgramEntityById(id);
        programRepository.delete(existingProgram);
    }

    private ProgramEntity findProgramEntityById(int id) {
        return programRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Program with id " + id + " not found!"));
    }

    @Transactional
    public Program findProgramById(int id) {
        ProgramEntity existingProgram = findProgramEntityById(id);
        return ProgramMapper.entityToProgram(existingProgram);
    }

    @Transactional
    public List<Program> getAllPrograms() {
        return programRepository.findAll().stream().map(ProgramMapper::entityToProgram).collect(Collectors.toList());
    }

    @Transactional
    public Program postponeProgramDate(int programId, String newDate) {

        ProgramEntity existingProgram = findProgramEntityById(programId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(newDate, formatter);
        existingProgram.setDate(dateTime);

        programRepository.save(existingProgram);

        return ProgramMapper.entityToProgram(existingProgram);
    }

    // ------------------------------  Section management ------------------------------

    @Transactional
    public Section addSection(Section section) {
        SectionEntity entity = SectionMapper.sectionToEntity(section);
        entity.setEvent(eventRepository.findById(section.getEventId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!")));

        return SectionMapper.entityToSection(sectionRepository.save(entity));
    }

    @Transactional
    public void deleteSection(int id) {

        SectionEntity existingSection = findEntityById(id);
        sectionRepository.delete(existingSection);
    }

    @Transactional
    public Section findSectionById(int id) {
        SectionEntity entity = findEntityById(id);
        return SectionMapper.entityToSection(entity);
    }

    private SectionEntity findEntityById(int id) {
        return sectionRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section with id " + id + " not found!"));
    }

    @Transactional
    public List<Section> getAllSections() {
        return sectionRepository.findAll().stream().map(SectionMapper::entityToSection).collect(Collectors.toList());
    }

    @Transactional
    public Section assignSupervisor(int sectionId, String email) {
        SectionEntity existingSection = findEntityById(sectionId);
        existingSection.setSupervisor(userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!")));

        sectionRepository.save(existingSection);
        return SectionMapper.entityToSection(existingSection);
    }
}
