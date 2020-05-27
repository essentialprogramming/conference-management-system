package com.api.resources;

import com.model.Status;
import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/reviewer")
public class ReviewerController {

    private UserService userService;

    @Autowired
    public ReviewerController(UserService userService) {
        this.userService = userService;
    }



    @PUT
    @Path("/{email}/{sectionId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUserSectionId(@PathParam("email") String email, @PathParam("sectionId") int id) {
        userService.registerToSection(email, id);
    }


    @GET
    @Path("/bid/{paperId}/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public void bidProposal(@PathParam("paperId") int paperId, @PathParam("email") String email, Status status) {
        userService.bidProposal(paperId, email, status);
    }


}
