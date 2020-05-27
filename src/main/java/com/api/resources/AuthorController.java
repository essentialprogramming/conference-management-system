package com.api.resources;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/author")
public class AuthorController {

    private UserService userService;

    @Autowired
    public AuthorController(UserService userService) {
        this.userService = userService;
    }



    @PUT
    @Path("/section/{email}/{sectionId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public void assignSpeakerToSection(@PathParam("email") String email, @PathParam("sectionId") int id) {
        userService.registerToSection(email, id);
    }





}
