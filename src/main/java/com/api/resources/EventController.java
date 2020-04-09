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

    @GET
    @Path("/{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Event findById(@PathParam("id") int id) {
        return eventService.findById(id);
    }

    @PUT
    @Path("/{eventId}/{programId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Event updateProgram(@PathParam("eventId") int eventId, @PathParam("programId") int programId) {
        return eventService.updateProgram(eventId, programId);
    }

}
