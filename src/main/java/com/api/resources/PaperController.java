package com.api.resources;

import com.model.Paper;
import com.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/paper")
public class PaperController {

    private PaperService paperService;

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Paper addPaper(Paper paper) {

        return paperService.addPaper(paper);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Paper findById(@PathParam("id") int id){
        return paperService.findById(id);
    }
}
