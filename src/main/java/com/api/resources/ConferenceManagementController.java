package com.api.resources;

import com.model.Event;
import com.model.Location;
import com.model.Program;
import com.model.Section;
import com.service.ConferenceManagementService;
import com.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class ConferenceManagementController {

    private ConferenceManagementService conferenceService;
    private LocationService locationService;

    @Autowired
    public ConferenceManagementController(ConferenceManagementService conferenceService, LocationService locationService) {
        this.conferenceService = conferenceService;
        this.locationService = locationService;
    }

    @POST
    @Path("event")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Event addEvent( Event event) {

        return conferenceService.addEvent(event);
    }

    @DELETE
    @Path("event/{id}")
    public void deleteEvent(@PathParam("id") int id) {
        conferenceService.deleteEvent(id);
    }

    @GET
    @Path("event/{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Event findEventById(@PathParam("id") int id) {
        return conferenceService.findEventById(id);
    }

    @PUT
    @Path("event/program/{eventId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Event updateProgram(@PathParam("eventId") int eventId, Program program) {
        return conferenceService.updateEventProgram(eventId, program);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events")
    public List<Event> getAllEvents() {
        return conferenceService.getAllEvents();
    }


    // ----------------------- program --------------------------------





    @PUT
    @Path("event/program/postpone/{eventId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Program changeProposalDeadline(@PathParam("eventId") int eventId, String newDate) {
        return conferenceService.changeProposalDeadline(eventId, newDate);
    }


    // -------------------- section ---------------------------------

    @POST
    @Path("event/section/{eventId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Section addSection(@PathParam("eventId") int eventId, Section section) {

        return conferenceService.addSection(eventId, section);
    }

    @DELETE
    @Path("event/section/{sectionId}")
    public void deleteSection(@PathParam("sectionId") int sectionId) {
        conferenceService.deleteSection(sectionId);
    }


    @PUT
    @Path("event/section/supervisor/{sectionId}/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Section assignSupervisor(@PathParam("sectionId") int sectionId, @PathParam("email") String email) {
        return conferenceService.assignSupervisor(sectionId, email);
    }

    // -------------------- location ---------------------------------

    @POST
    @Consumes("application/json")
    @Path("event/location/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Location addLocation(@PathParam("eventId") int eventId, Location location) {

        return locationService.addLocation(eventId, location);
    }


}
