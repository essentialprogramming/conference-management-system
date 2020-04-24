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


@Path("/conference")
public class ConferenceManagementController {

    private ConferenceManagementService conferenceService;
    private LocationService locationService;

    @Autowired
    public ConferenceManagementController(ConferenceManagementService conferenceService, LocationService locationService) {
        this.conferenceService = conferenceService;
        this.locationService = locationService;
    }

    @POST
    @Path("/event")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Event addEvent(Event event) {

        return conferenceService.addEvent(event);
    }

    @DELETE
    @Path("/event/{id}")
    public void deleteEvent(@PathParam("id") int id) {
        conferenceService.deleteEvent(id);
    }

    @GET
    @Path("/event/{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Event findEventById(@PathParam("id") int id) {
        return conferenceService.findEventById(id);
    }

    @PUT
    @Path("/event/{eventId}/{programId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Event updateProgram(@PathParam("eventId") int eventId, @PathParam("programId") int programId) {
        return conferenceService.updateEventProgram(eventId, programId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/event")
    public List<Event> getAllEvents() {
        return conferenceService.getAllEvents();
    }


    // ----------------------- program --------------------------------

    @POST
    @Path("/program")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Program addProgram(Program program) {

        return conferenceService.addProgram(program);
    }

    @DELETE
    @Path("/program/{id}")
    public void deleteProgram(@PathParam("id") int id) {
        conferenceService.deleteProgram(id);
    }

    @GET
    @Path("/program/{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Program findProgramById(@PathParam("id") int id) {
        return conferenceService.findProgramById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/program")
    public List<Program> getAllPrograms() {
        return conferenceService.getAllPrograms();
    }

    @PUT
    @Path("/program/postpone/{programId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Program postponeProgramDate(@PathParam("programId") int id, String newDate) {
        return conferenceService.postponeProgramDate(id, newDate);
    }


    // -------------------- section ---------------------------------

    @POST
    @Path("/section")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Section addSection(Section section) {

        return conferenceService.addSection(section);
    }

    @DELETE
    @Path("/section/{id}")
    public void deleteSection(@PathParam("id") int id) {
        conferenceService.deleteSection(id);
    }

    @GET
    @Path("/section/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Section findSectionById(@PathParam("id") int id) {
        return conferenceService.findSectionById(id);
    }

    @GET
    @Path("/section")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Section> getAllSections() {
        return conferenceService.getAllSections();
    }

    @PUT
    @Path("/section/supervisor/{sectionId}/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Section assignSection(@PathParam("sectionId") int sectionId, @PathParam("email") String email) {
        return conferenceService.assignSupervisor(sectionId, email);
    }

    // -------------------- location ---------------------------------

    @POST
    @Consumes("application/json")
    @Path("/location")
    @Produces(MediaType.APPLICATION_JSON)
    public Location addLocation(Location location) {

        return locationService.addLocation(location);
    }

    @DELETE
    @Path("/location/{id}")
    public void deleteLocation(@PathParam("id") int id) {
        locationService.deleteLocation(id);
    }

    @GET
    @Path("/location")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

}
