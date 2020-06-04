package com.service;


import com.entities.*;
import com.model.EventInput;
import com.model.LocationInput;
import com.model.ProgramInput;
import com.model.SectionInput;
import com.mapper.*;
import com.output.EventJSON;
import com.output.ProgramJSON;
import com.output.SectionJSON;
import com.model.User;
import com.repository.*;
import com.web.json.JsonResponse;
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
    private PCMemberRepository pcMemberRepository;
    private ParticipantRepository participantRepository;

    @Autowired
    public ConferenceManagementService(ConferenceRepository eventRepository, UserRepository userRepository, LocationRepository locationRepository, SectionRepository sectionRepository, PCMemberRepository pcMemberRepository, ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.sectionRepository = sectionRepository;
        this.pcMemberRepository = pcMemberRepository;
        this.participantRepository = participantRepository;
    }

    // ------------------------------  Event management ------------------------------

    @Transactional
    public EventJSON addEvent(EventInput event) {
        EventEntity entity = EventMapper.eventToEntity(event);
        entity.setProgram(ProgramMapper.programToEntity(event.getProgram()));
        entity.setLocation(LocationMapper.locationToEntity(event.getLocation()));

        return EventMapper.entityToEvent(eventRepository.save(entity));
    }

    @Transactional
    public EventJSON updateEvent(int eventId, EventInput newEvent)
    {
        ProgramInput newProgram = newEvent.getProgram();
        LocationInput newLocation = newEvent.getLocation();
        String newName = newEvent.getName();
        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));

        ProgramEntity programEntity = event.getProgram();
        LocationEntity locationEntity = event.getLocation();

        ProgramMapper.updateProgram(programEntity, newProgram);
        LocationMapper.updateLocation(locationEntity, newLocation);
        event.setProgram(programEntity);
        event.setLocation(locationEntity);
        event.setName(newName);

        return EventMapper.entityToEvent(event);
    }

    @Transactional
    public void deleteEvent(int id) {

        EventEntity existingEntity = eventRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        eventRepository.delete(existingEntity);
    }

    @Transactional
    public EventJSON findEventById(int id) {
        EventEntity existingEvent = eventRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        return EventMapper.entityToEvent(existingEvent);
    }

    @Transactional
    public EventJSON updateEventProgram(int eventId, ProgramInput program) {
        EventEntity existingEvent = eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        ProgramEntity existingProgram = existingEvent.getProgram();
        ProgramMapper.updateProgram(existingProgram, program);
        existingEvent.setProgram(existingProgram);
        eventRepository.save(existingEvent);

        return EventMapper.entityToEvent(existingEvent);
    }

    @Transactional
    public List<EventJSON> getAllEvents() {
        return eventRepository.findAll().stream().map(EventMapper::entityToEvent).collect(Collectors.toList());
    }

    // ------------------------------  Program management ------------------------------





    @Transactional
    public ProgramJSON changeProposalDeadline(int eventId, String newDate) {

        EventEntity event = eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        ProgramEntity program = event.getProgram();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(newDate, formatter);
        program.setDate(dateTime);


        return ProgramMapper.entityToProgram(program);
    }

    // ------------------------------  Section management ------------------------------

    @Transactional
    public SectionJSON addSection(int eventId, SectionInput section) {
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
    public SectionJSON assignSupervisor(int sectionId, String email) {
        SectionEntity existingSection = sectionRepository.findById(sectionId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Section with id " + sectionId + " not found!"));
        existingSection.setSupervisor(pcMemberRepository.findById(email).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Pc Member not found!")));

        sectionRepository.save(existingSection);
        return SectionMapper.entityToSection(existingSection);
    }

    // ----------------------- Committee management -------------------------

    @Transactional
    public void addProgramCommittee(String email, int eventId) {

        EventEntity existingEvent = eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        CommitteeMemberEntity committeeMemberEntity = new CommitteeMemberEntity(email);

        existingEvent.getProgramCommittee().add(committeeMemberEntity);
        pcMemberRepository.save(committeeMemberEntity);
    }

    // ---------------------- Participants management -------------------------
    @Transactional
    public User addParticipant(int eventId, String email)
    {
        EventEntity existingEvent = eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        ParticipantEntity participantEntity = new ParticipantEntity(email);

        existingEvent.getParticipants().add(participantEntity);
        return UserMapper.entityToUser(participantRepository.save(participantEntity));
    }

    @Transactional
    public List<User> getParticipants(int eventId)
    {
        EventEntity existingEvent = eventRepository.findById(eventId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Event not found!"));
        return existingEvent.getParticipants().stream()
                .map(participant -> UserMapper.entityToUser(participant))
                .collect(Collectors.toList());
    }
}
