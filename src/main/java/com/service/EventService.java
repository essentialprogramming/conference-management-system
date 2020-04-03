package com.service;


import com.entities.EventEntity;
import com.mapper.EventMapper;
import com.model.Event;
import com.repository.EventRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private EventRepository eventRepository;
    private UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Event addEvent(Event event) {
        EventEntity entity = EventMapper.eventToEntity(event);
        entity.setProgramCommittee(userRepository.findAllById(event.getProgramCommittee()));
        entity.setParticipants(userRepository.findAllById(event.getParticipants()));
        entity.setSpeakers(userRepository.findAllById(event.getSpeakers()));

        return EventMapper.entityToEvent(eventRepository.save(entity));
    }
}
