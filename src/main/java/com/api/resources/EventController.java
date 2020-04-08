package com.api.resources;


import com.model.Event;
import com.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Event addEvent(Event event) {
        return eventService.addEvent(event);
    }

    @DELETE
    @Path("/{id}")
    public void deleteEvent(@PathParam("id") int id) {
        eventService.deleteEvent(id);
    }
}
