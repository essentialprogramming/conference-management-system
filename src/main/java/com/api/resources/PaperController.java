package com.api.resources;

import com.model.PaperInput;
import com.output.PaperJSON;
import com.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class PaperController {

    private PaperService paperService;

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @POST
    @Path("paper")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public PaperJSON submitPaper(PaperInput paperInput) {

        return paperService.submitPaper(paperInput);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("paper/{paperId}")
    public PaperJSON findById(@PathParam("paperId") int id) {
        return paperService.findById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("papers")
    public List<PaperJSON> getAll() {

        return paperService.getAll();
    }

    @PUT
    @Path("paper/{paperId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updatePaper(@PathParam("paperId") int paperId, PaperInput paperInput) {
        paperService.updatePaper(paperId, paperInput);
    }


}
