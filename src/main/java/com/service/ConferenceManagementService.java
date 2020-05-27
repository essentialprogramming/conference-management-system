package com.service;


import com.entities.EventEntity;
import com.entities.ProgramEntity;
import com.entities.SectionEntity;
import com.mapper.EventMapper;
import com.mapper.LocationMapper;
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

    private ConferenceRepository eventRepository;
    private UserRepository userRepository;
    private LocationRepository locationRepository;
    private SectionRepository sectionRepository;

    @Autowired
    public ConferenceManagementService(ConferenceRepository eventRepository, UserRepository userRepository, LocationRepository locationRepository, SectionRepository sectionRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.sectionRepository = sectionRepository;
    }

    // ------------------------------  Event management ------------------------------

    @Transactional
    public Event addEvent(Event event) {
        EventEntity entity = EventMapper.eventToEntity(event);
        entity.setProgram(ProgramMapper.programToEntity(event.getProgram()));
        entity.setLocation(LocationMapper.locationToEntity(event.getLocation()));

        return EventMapper.entityToEvent(eventRepository.save(entity));
    }

    @Transactional
    public void deleteEvent(int id) {

        EventEntity existingEntity = eventRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        eventRepository.delete(existingEntity);
    }

    @Transactional
    public Event findEventById(int id) {
        EventEntity existingEvent = eventRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        return EventMapper.entityToEvent(existingEvent);
    }

    @Transactional
    public Event updateEventProgram(int eventId, Program program) {
        EventEntity existingEvent = eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        ProgramEntity existingProgram = existingEvent.getProgram();
        ProgramMapper.updateProgram(existingProgram, program);
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
    public Program changeProposalDeadline(int eventId, String newDate) {

        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        ProgramEntity program = event.getProgram();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(newDate, formatter);
        program.setDate(dateTime);


        return ProgramMapper.entityToProgram(program);
    }

    // ------------------------------  Section management ------------------------------

    @Transactional
    public Section addSection(int eventId, Section section) {
        SectionEntity sectionEntity = SectionMapper.sectionToEntity(section);
        sectionEntity.setEvent(eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!")));

        return SectionMapper.entityToSection(sectionRepository.save(sectionEntity));
    }

    @Transactional
    public void deleteSection(int id) {

        SectionEntity existingSection = sectionRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section with id " + id + " not found!"));
        sectionRepository.delete(existingSection);
    }


    @Transactional
    public Section assignSupervisor(int sectionId, String email) {
        SectionEntity existingSection = sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section with id " + sectionId + " not found!"));
        existingSection.setSupervisor(userRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found!")));

        sectionRepository.save(existingSection);
        return SectionMapper.entityToSection(existingSection);
    }
}
