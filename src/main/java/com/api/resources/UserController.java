package com.api.resources;

import com.model.Role;
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
    public User addUser(User user) {
        return userService.addUser(user);
    }

    @PUT
    @Path("/{email}/{sectionId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateSectionId(@PathParam("email") String email, @PathParam("sectionId") int id) {
        userService.updateSection(email, id);
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findByEmail(@PathParam("email") String email) {
        return userService.findByEmail(email);
    }

    @PUT
    @Path("/{email}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateRole(@PathParam("email") String email, Role role) {
        userService.updateRole(email, role);
    }


}
