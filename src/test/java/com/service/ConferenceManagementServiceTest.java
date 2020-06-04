package com.service;

import com.entities.EventEntity;
import com.mapper.EventMapper;
import com.model.EventInput;
import com.output.EventJSON;
import com.repository.ConferenceRepository;
import com.repository.PCMemberRepository;
import com.repository.SectionRepository;
import com.util.ConferenceUtil;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

//@WebMvcTest
//@RunWith(SpringRunner.class)
public class ConferenceManagementServiceTest {

    @Mock
    private ConferenceRepository conferenceRepository;
    @Mock
    private SectionRepository sectionRepository;
    @Mock
    private PCMemberRepository pcMemberRepository;

    @InjectMocks
    private ConferenceManagementService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEvents() {
        List<EventEntity> events = new ArrayList<>();
        events.add(ConferenceUtil.event());

        when(conferenceRepository.findAll()).thenReturn(events);

        List<EventJSON> eventsList = service.getAllEvents();

        assertEquals(1, eventsList.size());
        verify(conferenceRepository, times(1)).findAll();
    }

    @Test
    public void getEventById() {
        EventEntity event = ConferenceUtil.event();

        when(conferenceRepository.findById(1)).thenReturn(Optional.of(event));

        EventJSON existingEntity = service.findEventById(1);

        assertEquals("Event", existingEntity.getName());
        assertEquals("Romania", existingEntity.getLocation().getCountry());
        assertEquals(LocalDateTime.parse("2019-05-12T13:20"), existingEntity.getProgram().getDate());
    }

    @Test
    public void createEvent() {
        EventInput input = ConferenceUtil.getEvent();

        when(conferenceRepository.save(any())).then(i -> i.getArgument(0, EventEntity.class));
        EventJSON event = service.addEvent(input);

        assertEquals("Event1", event.getName());
        assertEquals("Romania",event.getLocation().getCountry());

    }

}
