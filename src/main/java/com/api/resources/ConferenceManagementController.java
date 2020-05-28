package com.api.resources;

import com.model.EventInput;
import com.model.LocationInput;
import com.model.ProgramInput;
import com.model.SectionInput;
import com.output.EventJSON;
import com.output.LocationJSON;
import com.output.ProgramJSON;
import com.service.ConferenceManagementService;
import com.service.LocationService;
import com.web.json.JsonResponse;
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
    public EventJSON addEvent(EventInput eventInput) {

        return conferenceService.addEvent(eventInput);
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
    public EventJSON updateProgram(@PathParam("eventId") int eventId, ProgramInput programInput) {
        return conferenceService.updateEventProgram(eventId, programInput);
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
    public SectionInput addSection(@PathParam("eventId") int eventId, SectionInput sectionInput) {

        return conferenceService.addSection(eventId, sectionInput);
    }

    @DELETE
    @Path("event/section/{sectionId}")
    public void deleteSection(@PathParam("sectionId") int sectionId) {
        conferenceService.deleteSection(sectionId);
    }


    @PUT
    @Path("event/section/supervisor/{sectionId}/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public SectionInput assignSupervisor(@PathParam("sectionId") int sectionId, @PathParam("email") String email) {
        return conferenceService.assignSupervisor(sectionId, email);
    }

    // -------------------- location ---------------------------------

    @POST
    @Consumes("application/json")
    @Path("event/location/{eventId}")
    @Produces(MediaType.APPLICATION_JSON)
    public LocationJSON addLocation(@PathParam("eventId") int eventId, LocationInput locationInput) {

        return locationService.addLocation(eventId, locationInput);
    }

    @PUT
    @Path("event/committee/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse addProgramCommittee(@PathParam("email") String email){
        conferenceService.addProgramCommittee(email);
        return new JsonResponse().with("Response", "OK");
    }

}
