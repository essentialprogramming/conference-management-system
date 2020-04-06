package com.service;


import com.entities.EventEntity;
import com.mapper.EventMapper;
import com.model.Event;
import com.repository.EventRepository;
import com.repository.LocationRepository;
import com.repository.ProgramRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class EventService {

    private EventRepository eventRepository;
    private UserRepository userRepository;
    private ProgramRepository programRepository;
    private LocationRepository locationRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, ProgramRepository programRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.locationRepository = locationRepository;
    }

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
}
