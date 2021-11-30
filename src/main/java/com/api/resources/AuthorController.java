package com.api.resources;

import com.output.PaperJSON;
import com.service.AuthorService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/author")
public class AuthorController {

    private final UserService userService;
    private final AuthorService authorService;

    @Autowired
    public AuthorController(UserService userService, AuthorService authorService) {
        this.userService = userService;
        this.authorService = authorService;
    }

    @GET
    @Path("/papers/{email}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PaperJSON> getPapersOfAuthor(@PathParam("email") String email) {
        return authorService.getPapersOfAuthor(email);
    }


    @GET
    @Path("/acceptedPapers/{email}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PaperJSON> getAcceptedPapersOfAuthor(@PathParam("email") String email) {
        return authorService.getAcceptedPapersOfAuthor(email);
    }


    @PUT
    @Path("/section/{email}/{sectionId}")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public void assignSpeakerToSection(@PathParam("email") String email, @PathParam("sectionId") int id) {
        userService.registerToSection(email, id);
    }





}
