package com.api.resources;

import com.model.EventInput;
import com.model.LocationInput;
import com.model.ProgramInput;
import com.model.SectionInput;
import com.model.*;
import com.output.EventJSON;
import com.output.LocationJSON;
import com.output.ProgramJSON;
import com.output.SectionJSON;
import com.service.ConferenceManagementService;
import com.service.LocationService;
import com.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class ConferenceManagementController {

    private final ConferenceManagementService conferenceService;
    private final LocationService locationService;

    @Autowired
    public ConferenceManagementController(ConferenceManagementService conferenceService, LocationService locationService) {
        this.conferenceService = conferenceService;
        this.locationService = locationService;
    }

    @POST
    @Path("event")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public EventJSON addEvent(EventInput event) {
        return conferenceService.addEvent(event);
    }

    @PUT
    @Path("event/update/{id}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public EventJSON updateEvent(@PathParam("id") int id, EventInput event) {
        return conferenceService.updateEvent(id, event);
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
    public EventJSON findEventById(@PathParam("id") int id) {
        return conferenceService.findEventById(id);
    }

    @PUT
    @Path("event/program/{eventId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public EventJSON updateProgram(@PathParam("eventId") int eventId, ProgramInput program) {
        return conferenceService.updateEventProgram(eventId, program);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("events")
    public List<EventJSON> getAllEvents() {
        return conferenceService.getAllEvents();
    }


    // ----------------------- program --------------------------------

    @PUT
    @Path("event/program/postpone/{eventId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public ProgramJSON changeProposalDeadline(@PathParam("eventId") int eventId, String newDate) {
        return conferenceService.changeProposalDeadline(eventId, newDate);
    }



    // -------------------- section ---------------------------------

    @POST
    @Path("event/section/{eventId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public SectionJSON addSection(@PathParam("eventId") int eventId, SectionInput section) {
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
    public SectionJSON assignSupervisor(@PathParam("sectionId") int sectionId, @PathParam("email") String email) {
        return conferenceService.assignSupervisor(sectionId, email);
    }


    // -------------------- location ---------------------------------

    @POST
    @Consumes("application/json")
    @Path("event/location/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public LocationJSON addLocation(@PathParam("eventId") int eventId, LocationInput location) {

        return locationService.addLocation(eventId, location);
    }


    @PUT
    @Path("event/committee/{eventId}/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse addProgramCommittee(@PathParam("email") String email, @PathParam("eventId") int eventId) {
        conferenceService.addProgramCommittee(email, eventId);
        return new JsonResponse().with("Response", "OK");
    }


    // ----------------------- participants --------------------------------
    @POST
    @Consumes("application/json")
    @Path("event/participants/{eventId}/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User addParticipant(@PathParam("eventId") int eventId, @PathParam("email") String email) {

        return conferenceService.addParticipant(eventId, email);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("event/participants/{eventId}")
    public List<User> getParticipants(@PathParam("eventId") int eventId)
    {
        return conferenceService.getParticipants(eventId);
    }
}
