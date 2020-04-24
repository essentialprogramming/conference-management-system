package com.api.resources;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public User register(User user) {
        return userService.register(user);
    }

    @PUT
    @Path("/{email}/{sectionId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUserSectionId(@PathParam("email") String email, @PathParam("sectionId") int id) {
        userService.registerToSection(email, id);
    }

    @DELETE
    @Path("/{email}")
    public void deleteUser(@PathParam("email") String email) {
        userService.deleteUser(email);
    }

    @GET
    @Path("/bid/{paperId}/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public void bidProposal(@PathParam("paperId") int paperId, @PathParam("email") String email) {
        userService.bidProposal(paperId, email);
    }

}
