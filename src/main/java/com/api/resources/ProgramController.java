package com.api.resources;


import com.model.Program;
import com.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/program")
public class ProgramController {

    private ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Program addProgram(Program program) {
        return programService.addProgram(program);
    }

    @DELETE
    @Path("/{id}")
    public void deleteProgram(@PathParam("id") int id) {
        programService.deleteProgram(id);
    }
}
